# Material do Aluno — Aula 9: Guia da Banca de Defesa de Arquitetura

> **Tempo de leitura:** ~11 min. Este é um **guia prático**, não um capítulo conceitual: ele existe para você chegar à banca preparado, não surpreendido. Na Aula 9 você não entrega só código — você **defende decisões** diante do docente e dos seus pares. Leia este material **antes** de montar sua apresentação e use o banco de perguntas (seção 5) como simulado: se você consegue responder cada uma sem hesitar, está pronto. Volte ao checklist (seção 6) na véspera, com o repositório aberto.

---

## 1. O que é a banca e o que ela avalia

A banca é uma **defesa de arquitetura de nível especialista (Nível III)**. Cada grupo apresenta o projeto final, demonstra o que roda e — o ponto central — **justifica as escolhas** sob arguição. O que está em jogo não é "vocês implementaram microsserviços?", e sim "vocês entendem **por que** cortaram assim, e sabem defender o que abriram mão?".

A regra que organiza tudo, e que você precisa internalizar: **a banca avalia a decisão arquitetural, não a infraestrutura que coube ao time.** Um grupo que rodou tudo no **Plano C** (sem Docker, tudo em processo) e **justificou bem** cada escolha pode tirar nota máxima. Um grupo que subiu Kafka, Redis e cinco contêineres mas não sabe explicar **por que** usou tópico em vez de fila tira menos. O módulo inteiro foi construído sobre esse princípio anti-ambiente — a banca é onde ele se realiza.

Isso muda o que você prepara. Não gaste a apresentação provando que o ambiente subiu; gaste-a provando que **a decisão foi consciente**. A frase que resume a postura esperada: *"escolhemos X, abrindo mão de Y, porque no nosso contexto Z pesa mais."* Decisão sem trade-off declarado é o que a banca mais penaliza.

---

## 2. Como defender trade-offs

Arquitetura distribuída é, do início ao fim, uma sequência de trade-offs. Não há resposta universalmente certa — há a escolha **adequada ao seu contexto, declarada com lucidez**. Os eixos que a banca vai cobrar:

- **Consistência forte × eventual.** Forte garante que todo leitor vê o último dado, mas exige coordenação (transação, *lock*) e custa disponibilidade e latência. Eventual aceita uma janela de defasagem em troca de escala e resiliência. No projeto PIX: **dentro** do agregado `Comprovante` a consistência é forte (uma transação local); **entre** emissor e gravador é eventual (fila + idempotência). Saber **onde** cada uma vale é a decisão central — e ela cai direto do desenho dos agregados (Aula 1).
- **CAP.** Sob partição de rede (P, inevitável em distribuído), você escolhe entre consistência (C) e disponibilidade (A). Não é um *slogan*: é a pergunta "se o gravador ficar inacessível, o consulta **falha** (preserva C) ou **serve do cache** mesmo que velho (preserva A)?". Você precisa saber qual dos dois seu sistema escolheu, **e por quê** isso é certo no domínio de comprovantes.
- **Custo × resiliência.** Cada retry, DLQ, *circuit breaker* e réplica compra robustez e cobra complexidade e dinheiro. Resiliência não é "quanto mais, melhor" — é dimensionar ao risco. Defenda **por que** o seu nível de resiliência é proporcional à criticidade daquele fluxo.
- **Quando NÃO usar microsserviços.** A defesa mais forte de uma arquitetura distribuída é mostrar que você sabe **quando ela é desnecessária**. Microsserviços compram escala e autonomia de time ao custo de latência de rede, falha parcial e complexidade operacional. Para um time pequeno, um domínio coeso ou um produto incerto, um **monólito modular** costuma ser a escolha superior. Se você defende microsserviços, defenda **contra** essa alternativa — não como reflexo.

A técnica de defesa, em qualquer eixo, tem três partes: **(1)** nomeie a escolha, **(2)** nomeie o que sacrificou, **(3)** ancore no contexto que justifica o sacrifício. "Usamos consistência eventual entre emissor e gravador, abrindo mão de ver o comprovante imediatamente na consulta, porque no pico do 13º a vazão de emissão importa mais que a latência de leitura — e tratamos a janela com retries na consulta." Isso é uma defesa; "usamos consistência eventual porque é o padrão de microsserviços" não é.

---

## 3. O papel dos ADRs

Um **ADR (Architecture Decision Record)** é um registro curto e datado de **uma** decisão arquitetural. Ele é a memória da banca antes da banca: o lugar onde o trade-off da seção 2 fica escrito. O critério 7 da rubrica avalia exatamente isso — e penaliza ADR que **descreve** em vez de **decidir**.

A diferença é tudo. Um ADR ruim diz "usamos RabbitMQ"; um ADR bom diz "consideramos RabbitMQ, Kafka e chamada REST síncrona; escolhemos fila RabbitMQ porque precisamos de garantia de entrega com reprocessamento idempotente e **não** precisamos de *replay* histórico (que justificaria Kafka), nem podemos acoplar emissor e gravador de forma síncrona (que a REST imporia)". O valor do ADR está nas **alternativas consideradas e rejeitadas** — é isso que prova que a decisão foi pensada, não default.

Formato enxuto (Michael Nygard), um arquivo por decisão em `docs/adr/`:

```markdown
# ADR-003: Comunicação emissor → gravador por fila

## Status
Aceito

## Contexto
O emissor responde 202 ao canal e não pode bloquear esperando a gravação.
A gravação não pode perder comprovantes, mesmo sob pico ou falha do gravador.

## Decisão
Comunicação assíncrona por fila (RabbitMQ), consumidor idempotente por
chave de idempotência (id do comprovante), com DLQ após N retentativas.

## Alternativas consideradas
- REST síncrona: rejeitada — acopla a disponibilidade do emissor à do gravador.
- Tópico (pub/sub): rejeitado — só um serviço consome; fila é o ajuste certo.
- Kafka: rejeitado — não precisamos de replay/retenção longa; custo operacional maior.

## Consequências
+ Emissor desacoplado; gravação resiliente a indisponibilidade transitória.
- Consistência eventual: a consulta pode não achar o comprovante por uma janela
  curta → tratada com 3 retentativas antes de 404.
```

Tenha um ADR para **cada decisão que a rubrica cobra**: corte de contextos (critério 1), fila × tópico e garantia de entrega (2), idempotência/SAGA (3), estratégia de cache (4), resiliência (5). Esses ADRs são, literalmente, o roteiro das suas respostas de arguição.

---

## 4. Estrutura da apresentação (8–12 min)

O tempo é curto e a arguição vem depois — não desperdice minutos provando que o ambiente subiu. Sugestão de divisão:

| Bloco | Tempo | O que mostrar |
|---|:---:|---|
| **Problema e domínio** | ~1 min | que sistema vocês construíram e por quê (não a tecnologia, o problema) |
| **Mapa de contextos** | ~2 min | os bounded contexts e a justificativa do corte (Aula 1) |
| **Demo do que roda** | ~3–4 min | declare o **perfil A/B/C** e os *fallbacks*; mostre o fluxo, não cada tela |
| **Defesa das decisões** | ~3 min | SAGA × evento, cache, garantia de entrega, resiliência, contratos — apontando os **ADRs** |
| **Uso crítico de IA** | ~1 min | como usaram IA no design/dev e o que **validaram à mão** |

Princípios de quem apresenta bem nesta banca:

- **Comece pelo problema, não pela stack.** "Construímos um sistema de comprovantes que precisa aceitar picos sem perder dado" prende mais que "temos cinco serviços Spring Boot".
- **Declare o perfil de execução logo na demo** e siga em frente. Não peça desculpas pelo Plano C — defenda-o ("rodamos em processo porque a decisão arquitetural independe do broker; o contrato e a idempotência são os mesmos").
- **Mostre evidência, não slides genéricos.** Abra o ADR, abra o contract test passando, mostre a chave de idempotência. A banca acredita no que está **localizável** no repositório.
- **Antecipe a arguição na própria defesa.** Se você já diz "o cache pode servir dado com até 60s de defasagem, e isso é aceitável porque o comprovante é imutável após gravado", tirou a pergunta da boca do avaliador.

---

## 5. Banco de perguntas de arguição (com respostas-modelo)

A arguição testa se a decisão foi **consciente**. Treine estas — não decore as respostas, entenda o **raciocínio** de cada uma e adapte ao seu projeto.

**"Por que microsserviços aqui, e não um monólito modular?"**
> Modelo: "Separamos porque emissão, gravação e consulta têm perfis não-funcionais **diferentes**: a emissão precisa absorver picos e responder rápido (202 assíncrono), a gravação precisa de confiabilidade e não pode perder, a consulta tem volumetria altíssima e quer latência baixa (cache). Perfis diferentes → escala independente → fronteiras diferentes. Se fossem o mesmo perfil, um monólito modular seria melhor — e teríamos feito isso."

**"O que acontece se o consumidor receber a mesma mensagem duas vezes?"**
> Modelo: "Nada de errado, porque o consumidor é **idempotente**: ele usa o id do comprovante como chave de idempotência e, ao reprocessar, detecta que já gravou e descarta sem duplicar. Em entrega *at-least-once* (que é o que a fila garante), reprocessamento é esperado, não exceção — por isso a idempotência é requisito, não opcional. Temos um teste que reenvia a mesma mensagem e verifica que só há um registro."

**"Seu cache pode servir dado velho? Por quanto tempo, e isso é aceitável?"**
> Modelo: "Pode, dentro do TTL de X segundos. É aceitável porque o comprovante é **imutável depois de gravado** — uma vez gravado, o dado não muda, então servir do cache nunca devolve algo incorreto, só evita um *hit* no banco. O único risco é o cache responder antes de o comprovante existir, e isso a consulta trata com retentativas, não com o cache."

**"Esse fluxo é fila ou tópico? Por quê?"**
> Modelo: "Emissor → gravador é **fila**: há **um** consumidor lógico e a mensagem é um **comando** (faça a gravação) que deve ser processado **uma vez**. Gravador → notificação/antifraude/BI é **tópico**: é um **evento** (`ComprovanteGravado`, um fato no passado) que **vários** interessados consomem de forma independente. Regra: comando com um dono → fila; evento com N interessados → tópico."

**"Onde está o risco de inconsistência, e como vocês o tratam?"**
> Modelo: "Entre o emissor aceitar (202) e o gravador persistir há uma janela de consistência eventual. O risco é a consulta buscar nesse intervalo e não achar. Tratamos com **retentativas** na consulta antes de declarar 404, e com idempotência na gravação para que reprocessar não duplique. Não usamos transação distribuída de propósito — ela acoplaria os serviços e mataria a resiliência que justifica tê-los separados."

**"O que quebra se a dependência X ficar 10s lenta?"**
> Modelo: "Se o gravador fica lento, a consulta **não** trava esperando: temos **timeout** curto e *circuit breaker*, então após N falhas o circuito abre e a consulta responde do cache (ou degrada para 503 explícito) em vez de empilhar requisições. A lentidão de um serviço não vira indisponibilidade em cascata — é o ponto da resiliência da Aula 7."

**"Por que consistência eventual e não forte aqui?"**
> Modelo: "Porque consistência forte exigiria coordenar emissor e gravador na mesma transação, o que os reacopla e derruba a disponibilidade no pico. No domínio de comprovantes, uma defasagem de segundos na leitura é tolerável (o comprovante não 'expira'); perder vazão no pico do 13º, não. Escolhemos eventual conscientemente, e mitigamos a janela com retries."

A meta-resposta para qualquer pergunta que você não previu: **não existe resposta única certa — existe trade-off bem defendido.** "Escolhemos X, abrindo mão de Y, porque no nosso contexto Z pesa mais" é sempre uma resposta válida; "é o padrão" nunca é.

---

## 6. Checklist de entrega do repositório

A avaliação — pela banca **e** pela leitura via Claude pós-banca — considera o que está **localizável**. O que não for encontrado **não conta**, por melhor que esteja na sua cabeça. Antes de submeter:

- [ ] **`README.md`** — tema, arquitetura em uma figura, **como rodar**, e o **perfil A/B/C declarado** com os *fallbacks* usados.
- [ ] **`AVALIACAO.md`** — mapeia **cada critério → evidência** (caminho de arquivo, classe, teste ou commit). Sem ele, o que não for encontrado não pontua. É o item mais importante da entrega.
- [ ] **`docs/adr/`** — um ADR por decisão, com **alternativas consideradas e trade-offs reais**, não descrição (seção 3).
- [ ] **`docs/arquitetura.md`** — mapa de bounded contexts + fluxo de filas/tópicos/eventos.
- [ ] **CORE completo:** ≥2 serviços, **bases segregadas** (sem acoplamento por dados), 1 fila com consumidor **idempotente**, 1 **cache**, 1 **contract test** executável (Aula 8).
- [ ] **Opcionais** entregues conforme o tamanho do grupo (SAGA/outbox, DLQ, *circuit breaker*, message pact...).
- [ ] **`mvn verify` passando** no perfil declarado — incluindo a verificação do pact.
- [ ] **Commits ao longo das semanas** — evidência de processo, não um *dump* único na véspera.

```bash
# Antes de submeter, rode o que a banca vai rodar:
mvn verify -P<perfil-declarado>   # build + testes + verificação do contrato
```

> Faça o `AVALIACAO.md` ser um **mapa de tesouro**: para cada um dos 9 critérios da rubrica, aponte o arquivo, a classe, o teste ou o commit que o comprova. Quem avalia (humano ou Claude) segue esse mapa. Critério sem evidência localizável vale zero, ainda que implementado.

### Os 9 critérios da rubrica (saiba onde cada um está no seu repo)

| # | Critério | Peso | Onde está a evidência |
|---|---|:---:|---|
| 1 | Decomposição de domínio | 15 | módulos por serviço, bases separadas, `docs/arquitetura.md`, ADR de corte |
| 2 | Comunicação assíncrona | 15 | config de fila/broker, consumidor, garantia de entrega no README |
| 3 | Idempotência e consistência | 12 | chave de idempotência, teste que reprocessa sem duplicar, ADR |
| 4 | Cache | 10 | camada de cache, invalidação/TTL, ADR da estratégia |
| 5 | Resiliência | 12 | retry/backoff, DLQ, timeout, *circuit breaker*; teste falha transitória × permanente |
| 6 | Testabilidade | 12 | pact files + verificação, `mvn verify` verde, testes pura-JVM |
| 7 | Decisões arquiteturais | 12 | `docs/adr/` com alternativas e trade-offs |
| 8 | Uso crítico de IA | 5 | seção no README/AVALIACAO, reflexão honesta |
| 9 | Execução comprovada | 5 | README "como rodar", perfil declarado, evidência de execução |

Os pesos somam 100; cada critério tem 4 níveis (0 Insuficiente → 3 Avançado), e a nota é `(nível/3) × peso`. Note que **Testabilidade** (critério 6) é onde o contract test da Aula 8 pontua — não deixe de fora.

---

## 7. IA e o papel do arquiteto (reflexão de fechamento)

Fechamos o módulo com a pergunta que paira sobre a profissão: se a IA gera código, escreve testes, explica trechos e até propõe modelagens, **o que sobra para o arquiteto?**

A resposta separa o que a IA **acelera** do que ela **não decide**. A IA acelera a produção: ela rascunha um serviço, gera o esqueleto de um contract test, explica um *stack trace*, sugere um agregado a partir de uma transcrição de reunião. Tudo isso vira commodity, e bem usado é um multiplicador real — vocês usaram IA no projeto e devem reportar isso com honestidade (critério 8).

O que a IA **não** faz por você é precisamente o que esta banca avalia:

- **Decidir a fronteira do domínio** — onde cortar os bounded contexts depende de entender o negócio, a Lei de Conway e os perfis não-funcionais, coisas que o modelo não enxerga no seu contexto.
- **Escolher entre consistência forte e eventual** — é uma aposta sobre o que o **seu** domínio tolera, com consequências de negócio que o modelo não avalia.
- **Assumir o risco de uma garantia de entrega** — alguém responde quando um comprovante se perde; um modelo não assume risco.
- **Julgar o trade-off** — a IA lista alternativas; **escolher** uma, abrindo mão das outras, e responder por isso, é julgamento.

A conclusão é contraintuitiva e vale levar: **quanto mais a geração de código vira commodity, mais valioso fica o julgamento arquitetural.** Quando escrever o código é barato, o gargalo se desloca para **saber qual sistema construir e por quê** — qual problema resolver, onde cortar, o que sacrificar. Essa é a competência que o módulo desenvolveu e que esta banca mede. O arquiteto não é quem escreve mais código no mundo dos agentes; é quem **decide** o que vale a pena construir, e sabe defender a decisão.

> **Síntese do módulo:** *"Sistemas distribuídos são a arte de transformar falha inevitável em comportamento previsível."* Você passou nove aulas aprendendo a fazer isso — do corte de domínios (Aula 1) à defesa da decisão (hoje). O código você levará no repositório; o julgamento, na carreira.

---

## 8. Para ir além

- **Michael Nygard**, *Documenting Architecture Decisions* — o formato ADR da seção 3, do autor original.
- **Joel Parker Henderson** — coleção `architecture-decision-record` (templates e exemplos de ADR no GitHub).
- ***The Architecture of Open Source Applications*** — estudos de decisão arquitetural real, com os trade-offs explicados pelos próprios autores.
- **Neal Ford & Mark Richards**, *Fundamentals of Software Architecture* — o capítulo sobre trade-offs e o papel do arquiteto fecha bem a reflexão da seção 7.
