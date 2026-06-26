# Saída SDD — Guia de modelagem DDD (Comprovantes PIX)

> ⚠️ **MATERIAL DE CONDUÇÃO DO DOCENTE — NÃO DISTRIBUIR AOS ALUNOS.**
> Este é o **resultado esperado** da leitura crítica das transcrições: a especificação consolidada (saída do *Spec-Driven Development*) que alimenta o **event storming** e o desenho de **bounded contexts** da **Aula 2 — Parte A**. Use-o como gabarito vivo para conduzir a discussão (`plano-de-aulas/aula-02.md` §A1). Os alunos devem **chegar** a este mapa relendo as transcrições; o docente o usa para saber aonde a turma precisa convergir e qual fala ancora cada decisão.

---

## Como ler este documento

- **Fonte primária:** as cinco transcrições das reuniões com o PO em `requisitos/sessao-1..5`. As `user-stories.md` são uma compilação **rápida e falível** da PO — onde divergem da transcrição, **a transcrição manda** (ver `requisitos/README.md` §"Como usar" e o gabarito de erros em `requisitos/NOTAS-DOCENTE-discrepancias.md`).
- **Rastreabilidade:** cada item traz a âncora `[sessão · fala]` com o **trecho citado**, para o docente apontar a origem em tempo real. Esse é o método que se cobra do aluno: *ligar cada decisão de projeto a uma fala de stakeholder*.
- **Para que serve na aula:** a §3 (linguagem ubíqua), a §4 (matéria-prima do event storming) e a §5 (bounded contexts) são a **entrada** do hands-on de DDD. A §6 (problemas/hotspots) é a **ponte para o SAGA** da Parte B.

---

## 1. O sistema em uma frase

Uma API que **emite e consulta comprovantes de PIX já efetivados**. Ela **não efetiva PIX** — a transação ocorre no core bancário (outro sistema). Aqui só se **registra** (emissão) e se **reexibe** (2ª via / consulta) o comprovante.

> **Âncora — `sessao-1-kickoff.md` · Daniel:** *"Esse sistema **não efetiva PIX**. Ele não move dinheiro, não fala com o SPI, não tem nada a ver com a liquidação. Isso é o core bancário... O nosso sistema entra **depois**: ele **emite e consulta comprovantes** de PIX que já foram efetivados."*
> **Reforço — Marcela:** *"fora de escopo — efetivar, liquidar ou cancelar PIX."*

---

## 2. As duas dores de negócio (por que o sistema existe)

| Dor | Quem levanta | Âncora |
|---|---|---|
| **2ª via lenta e não confiável** infla TMA, vira recontato e reclamação no Bacen | Sandra / Roberto | `sessao-1` · Roberto: *"Cada minuto de atendente na linha é dinheiro... 'cadê meu comprovante'... vira reclamação no Bacen às vezes, vira nota baixa de satisfação."* |
| **"Acabei de fazer e não aparece"** — cliente consulta segundos após o PIX e o comprovante ainda não está disponível | Sandra | `sessao-1` · Sandra: *"O cliente faz o PIX e, dois minutos depois... o atendente procura e... não está lá ainda... 'vocês perderam meu PIX?'. Não perdemos... é só o comprovante que ainda não apareceu pra gente."* |

Essas duas dores **organizam toda a modelagem**: a primeira puxa **consulta rápida** (cache, baixa latência); a segunda é a semente do **hotspot da janela assíncrona** (§6.1) e da **consulta resiliente** (§4.4).

---

## 3. Linguagem ubíqua (glossário consolidado)

Compilado das quatro seções "Glossário" das transcrições. É o vocabulário que **deve** aparecer nos nomes de classes, eventos e serviços.

| Termo | Definição | Origem |
|---|---|---|
| **Comprovante de PIX** | Documento de uma transação PIX **já efetivada por outro sistema** | `sessao-1` (glossário) |
| **2ª via** | Reemissão/consulta de um comprovante já existente | `sessao-1` |
| **Emissão** | Registro de um novo comprovante a partir dos dados de um PIX ocorrido (POST sistema-a-sistema) | `sessao-1`, `sessao-2` |
| **Consulta** | Recuperação de um comprovante por identificador | `sessao-1` |
| **Aceite assíncrono** | Sistema valida, **aceita e responde já**, e **grava depois**, de forma desacoplada, para aguentar picos | `sessao-2` |
| **202 Accepted** | Código de resposta da emissão — "aceitei e vou gravar depois" (≠ 201 "já gravado") | `sessao-2` |
| **Identificador** | UUID v4 devolvido na emissão; é a **chave** da consulta | `sessao-2` |
| **Cache** | Memória rápida na frente do banco; consultada primeiro e populada no acerto | `sessao-3` |
| **Fila morta (DLQ)** | Fila separada para mensagens que **falham sempre**, sem perder e sem travar a principal | `sessao-4` |
| **Mensagem envenenada** | Mensagem malformada que falha em **toda** tentativa de processamento | `sessao-4` |
| **Evento "comprovante gravado"** | Aviso publica-assina emitido quando um comprovante é persistido | `sessao-4` |
| **Pico de volumetria** | Datas de salário, benefício social e 13º — o volume multiplica | `sessao-1` |
| **Retenção** | Guarda obrigatória do comprovante — **5 anos a partir da data da transação** | `sessao-5` |
| **Trilha de auditoria de acesso** | Registro de **quem** consultou **qual** comprovante e **quando** | `sessao-5` |
| **Dado pessoal sensível** | Os dados do comprovante, sob LGPD em **todos os canais** (banco, fila, tópico, consulta) | `sessao-5` |

> **Distinção que sempre escapa:** `data_hora_transacao` (quando o PIX aconteceu) **≠** data/hora da requisição (quando o sistema recebeu). `sessao-2` · Daniel: *"Isso é a data do PIX em si, não a data em que a gente recebeu."*

---

## 4. Matéria-prima do event storming

Despeje na lousa nesta ordem (cores conforme `aula-02.md` §A1). Cada item traz a fala que o sustenta.

### 4.1 Eventos de domínio (🟧)

| Evento | Âncora |
|---|---|
| **Comprovante Aceito** (emissão validou e respondeu 202) | `sessao-2` · Daniel: *"a gente **valida os obrigatórios, dá um 'aceitei', e responde já**... A gravação de verdade no banco acontece **depois**, de forma assíncrona."* |
| **Comprovante Gravado** (persistido no banco) | `sessao-4` · Daniel: *"Quando o gravador grava um comprovante com sucesso, ele **publica um evento**... 'comprovante tal foi gravado' — num **tópico**."* |
| **Gravação Falhou (temporário)** | `sessao-4` · Daniel: *"o banco está fora do ar naquele instante... é uma falha **temporária**... a mensagem volta pra fila e o consumidor **tenta de novo**."* |
| **Mensagem Movida para Fila Morta** (falha sempre) | `sessao-4` · Daniel: *"depois de tentar gravar algumas vezes e ver que aquela mensagem específica **falha sempre**... a gente **move ela pra fila morta**."* |
| **Comprovante Consultado** (gera trilha de auditoria) | `sessao-5` · Helena: *"Todo **acesso** a um comprovante — toda consulta — precisa ser **rastreável**."* |

### 4.2 Comandos (🟦) e atores/sistemas (🧍)

| Comando | Ator/Sistema que dispara | Âncora |
|---|---|---|
| **Emitir Comprovante** (POST) | **Sistema de origem** (core/intermediário de PIX) — nunca uma pessoa | `sessao-2` · Daniel: *"Quem chama a emissão é um sistema, não uma pessoa... é **sistema-para-sistema**, é uma API que recebe um POST."* |
| **Gravar Comprovante** | Consumidor assíncrono (gravador) | `sessao-2` · Daniel: *"**enfileira** a gravação pra um consumidor processar no ritmo dele."* |
| **Consultar Comprovante** (GET por id) | **Atendente** ou **cliente** (app) | `sessao-3` · Daniel: *"a consulta é **por identificador**. GET pelo id, devolve aquele comprovante."* |

Atores que **reagem** (não disparam o fluxo principal): **Antifraude** (Téo), **Notificação**, **BI** — todos assinantes do evento (§4.3). `sessao-4` · Daniel: *"três áreas, e nenhuma precisa ser conhecida pelo gravador. **Notificação ao cliente**... **Antifraude**... e **BI**."*

### 4.3 Agregado e value objects (🟨)

- **Agregado raiz: `Comprovante`** — entidade central, identificada pelo **UUID v4** (o identificador da emissão).
- **Value objects candidatos** (sair do slide e virar código no §A2):
  - **`ChavePix`** = tipo + valor; tipos: **celular, e-mail, CPF, CNPJ, aleatória**. `sessao-2` · Daniel: *"Cinco: celular, e-mail, CPF, CNPJ e aleatória... Dependendo do tipo, o conteúdo da chave muda."*
  - **`Dinheiro` / `Valor`** — valor da transação (monetário, obrigatório).
  - **`Documento`** = tipo (CPF/CNPJ) + número.
  - **`ContaOrigem`** = agência (**4** chars) + conta (**5** chars) + dígito (**1** char), **string** (zero à esquerda importa). `sessao-2` · Daniel: *"A **agência tem 4 caracteres**. A **conta tem 5 caracteres**. E o **dígito verificador tem 1 caractere**. Quatro, cinco, um."* / *"a gente trata como string. Zero à esquerda importa. Agência '0341' é diferente de '341'."*

**Campos do comprovante** (obrigatórios salvo indicação): `nome`, `tipo_documento` (CPF/CNPJ), `numero_documento`, `numero_agencia` (4), `numero_conta` (**5**), `digito_verificador_conta` (1), `valor_transacao`, `tipo_chave_pix_destino`, `chave_pix_destino`, `nome_cliente_destino`, `data_hora_transacao`; **opcional:** `identificacao_pix` (mensagem livre). `sessao-2` · Daniel: *"O único que eu trataria como **opcional** é a **identificação do PIX**... Todos os outros são obrigatórios."*

### 4.4 Políticas (🟪) — "quando X, então Y"

| Política | Âncora |
|---|---|
| Ao **aceitar a emissão** → **enfileirar a gravação** (assíncrona) e responder **202 + UUID + data/hora da requisição** | `sessao-2` · Daniel: *"é o **202**... 'aceitei, mas a gravação vai acontecer logo depois'."* |
| Ao **gravar com sucesso** → **publicar evento** "comprovante gravado" no tópico (gravador não conhece assinantes) | `sessao-4` · Daniel: *"ele **publica um evento**... O gravador **não conhece** os interessados. Ele só anuncia."* |
| Ao **falhar a gravação por causa temporária** → **re-tentar**, sem descartar | `sessao-4` · Daniel: *"Falha temporária = re-tenta."* |
| Ao **falhar sempre** (mensagem envenenada) → **mover para a DLQ** para inspeção | `sessao-4` · Daniel: *"move ela pra fila morta (DLQ)... a fila principal **continua fluindo**."* |
| Ao **consultar e não achar** → **olhar cache → banco → re-tentar 3× → só então 404** | `sessao-3` · Daniel: *"cache → se miss, banco → se achou, popula o cache e devolve → se não achou nem no banco, **re-tenta algumas vezes (três)** → se ainda não achou, 404."* |
| Ao **consultar** → **registrar trilha de auditoria** (quem, qual, quando) | `sessao-5` · Helena: *"toda consulta... precisa ser **rastreável**: quem consultou, qual comprovante, quando."* |

---

## 5. Bounded contexts → microsserviços

Lendo os agrupamentos do storming, emergem **três contextos**, cada um com **perfil não-funcional distinto** — é essa diferença que justifica separá-los em serviços com **bases segregadas** (incremento da Aula 2 · Parte A).

| Bounded context | Serviço | Responsabilidade | Perfil não-funcional dominante | Âncora |
|---|---|---|---|---|
| **Emissão** | `comprovante-emissor` | Validar campos, aceitar (202), publicar a tarefa de gravação | **Absorver pico** sem engasgar | `sessao-2` · Daniel: *"no pico o banco vira gargalo... Então a gente desacopla."* |
| **Gravação** | `comprovante-gravador` (base própria) | Consumir, persistir de forma confiável, publicar evento | **Não perder comprovante** (durabilidade) | `sessao-4` · Daniel: *"a gente **não pode perder comprovante.** Nunca."* |
| **Consulta** | `comprovante-consulta` | GET por id: cache → banco → 3 retries → 404; gera auditoria | **Baixa latência** e **volumetria alta** (lê >> escreve) | `sessao-3` · Daniel: *"a consulta é a operação **mais frequente**... latência baixa... aguentar pico."* / `sessao-1`: *"A gente lê muito mais do que escreve."* |

> **Ponte para a Parte B (SAGA):** estes três contextos em **bases diferentes** são exatamente o cenário de "emitir" e "gravar" sem transação única. Sem fronteiras claras aqui, o SAGA da Parte B não faz sentido.

---

## 6. Os problemas (hotspots 🟥) — a ponte para SAGA

Estes são os **conflitos/tensões** que o desenho precisa resolver. **São o coração da transição Parte A → Parte B.** Cada um nasce de uma fala; deixe-os emergirem da turma e ancore na transcrição.

### 6.1 A janela entre "aceitei" e "gravei" (comprovante recém-emitido)

O aceite é assíncrono: existe uma **janela curta** em que o comprovante já tem identificador (já respondeu 202) mas **ainda não está no banco**. É a dor da Sandra do "acabei de fazer e não aparece".

> **Âncora — `sessao-2-emissao.md` · Daniel:** *"a gente **não grava no banco na hora**... A gravação de verdade no banco acontece **depois**, de forma assíncrona."*
> **Consequência — `sessao-2` · Daniel:** *"existe uma **janela curta** em que o comprovante já tem identificador, já foi aceito, mas ainda não está no banco pra ser consultado. É essa janela que a Sandra sente lá na ponta."*
> **Mitigação — `sessao-3` · Daniel:** *"as re-tentativas existem: pra dar tempo da **gravação assíncrona** terminar... As re-tentativas são a ponte sobre a janela."*

> 📌 **Este é o trecho que o enunciado da tarefa pede como exemplo** — o evento de gravação do comprovante tratado como **assíncrono** está em `requisitos/sessao-2-emissao.md` (fala do Daniel sobre o aceite assíncrono / gravação "depois") e é reforçado em `requisitos/sessao-3-consulta-e-desempenho.md` (a janela e as re-tentativas) e em `requisitos/sessao-4-confiabilidade-e-integracoes.md` (o que fazer se algo falhar entre o aceite e a gravação).

### 6.2 Atomicidade emissão → gravação (o problema gerador do SAGA)

A emissão responde **202**, mas a gravação acontece **depois**, em **outro serviço e outra base**. **Se a gravação falhar, o cliente tem um comprovante que não existe no banco.** Não há transação única que cubra os dois passos → é onde entra **SAGA** (compensação + idempotência).

> **Âncora — `sessao-2-emissao.md` · Roberto:** *"Peraí. Você responde 'aceitei' antes de gravar? **E se a gravação falhar depois?**"*
> **Âncora — `sessao-4-confiabilidade-e-integracoes.md` · Daniel:** *"esse desacoplamento... abre uma pergunta de confiabilidade: **e se algo falhar entre o aceite e a gravação?** A regra número um, inegociável, é: **a gente não pode perder comprovante.**"*

Isto é literalmente o **problema gerador da Parte B** (`aula-02.md` §B.1). A **idempotência por chave do comprovante** (reexecutar a emissão **não** duplica) é um requisito **engenheirado** a partir do "não perder" + "re-tentar com segurança" — **não** está nomeado nas transcrições; é a peça que a aula introduz para tornar a compensação segura.

### 6.3 A mensagem envenenada que vira "rolha"

Uma mensagem malformada que **falha sempre** trava a fila inteira no pico se ficar em retry infinito. Resolve-se com **DLQ** — dois mecanismos distintos: *retry* (transitório) **≠** *DLQ* (poison).

> **Âncora — `sessao-4` · Daniel:** *"se eu só fico re-tentando pra sempre, essa mensagem quebrada vira uma **rolha**... **trava** ou atrasa as mensagens boas... a gente **move ela pra fila morta (DLQ)**."*
> **Aviso da PO — `sessao-4` · Marcela:** *"a **fila morta (DLQ)** é um requisito **por si só**. Não é 'detalhe da entrega'."* (cai direto na Aula 5)

### 6.4 Pico de volumetria e leitura >> escrita

O sistema tem de **absorver pico sem perder e sem cair**, e a consulta (muito mais frequente) precisa de **baixa latência mesmo no pico**. É o que justifica o **desacoplamento** (emissão aceita rápido) e o **cache** (consulta).

> **Âncora — `sessao-1` · Daniel:** *"tem o agravante dos **picos**... Dia de pagamento de salário, dia de benefício social, e o clássico: décimo terceiro."* / *"A gente **lê muito mais do que escreve**."*

### 6.5 Dado sensível em todos os canais + retenção precisa

O dado do comprovante é **pessoal sensível** (LGPD) em **qualquer canal** — inclusive no **evento** publicado no tópico. E a **retenção é número de compliance**, não arredondável.

> **Âncora — `sessao-5` · Helena:** *"o evento que vocês publicam, 'comprovante gravado', ele também **carrega dado pessoal**... não é porque é evento interno que deixa de ser dado pessoal."*
> **Âncora — `sessao-5` · Helena:** *"deve ser **retido por 5 anos** — cinco anos, contados a partir da data da transação... eu já vi projeto **trocar isso na compilação** e virar problema de auditoria."*

---

## 7. Requisitos não-funcionais (consolidado, rastreado)

| RNF | Origem |
|---|---|
| Absorver **pico** sem perder comprovante e sem cair | `sessao-1` · Marcela: *"aguentar pico sem perder comprovante e sem cair"* |
| **Baixa latência** na consulta/2ª via, inclusive em pico | `sessao-3` · Daniel: *"baixa latência na consulta, mesmo em pico"* |
| **Não perder comprovante** — regra nº 1, inegociável | `sessao-4` · Daniel: *"a gente **não pode perder comprovante.** Nunca."* |
| **Retenção de 5 anos** a partir da data da transação | `sessao-5` · Helena |
| **Trilha de auditoria** de todo acesso (quem/qual/quando) | `sessao-5` · Helena/Téo |
| **Dado sensível** sob LGPD em todos os canais (banco/fila/tópico/consulta) | `sessao-5` · Daniel/Helena |

---

## 8. Escopo — MVP × fase 2 (para não inflar a modelagem)

| MVP | Fase 2 / fora de escopo |
|---|---|
| Emitir comprovante (202 + UUID) | **Notificar o cliente de fato** (SMS/push/e-mail) — só **publicar o evento** é MVP |
| Consultar por identificador (cache → banco → 3 retries → 404) | Consulta por período / listagem |
| Publicar evento "comprovante gravado" (destrava antifraude/BI) | Anonimização / descarte pós-5-anos |
| Não perder (retry + DLQ); retenção; auditoria | Relatórios de BI |
| | **Efetivar/liquidar/cancelar PIX — sempre fora** (é o core bancário) |

> **Âncora MVP×fase 2 — `sessao-4` · Roberto:** *"**Publicar o evento: agora. Notificar o cliente de fato: fase 2.**"*

---

## 9. Apêndice — Erros plantados na compilação da PO (gabarito do erratum)

> A `user-stories.md` foi compilada às pressas pela PO (viés declarado da Marcela) e contém **6 discrepâncias** propositais frente às transcrições. Elas **não** são necessárias para o event storming, mas são "problemas" do pacote SDD e podem aparecer quando a turma cruzar story × transcrição. Gabarito completo e valor pedagógico em **`requisitos/NOTAS-DOCENTE-discrepancias.md`** — resumo de rastreabilidade abaixo.

| # | Story diz | Transcrição diz | Sessão-fonte |
|---|---|---|---|
| 1 | emissão responde **201 Created** | **202 Accepted** (aceite assíncrono) | `sessao-2` |
| 2 | **404 imediato** no miss | **re-tenta 3×** antes do 404 | `sessao-3` |
| 3 | vago "garantir a entrega" | **fila morta (DLQ)** para mensagem envenenada | `sessao-4` |
| 4 | retenção **10 anos** | retenção **5 anos** (da data da transação) | `sessao-5` |
| 5 | `numero_conta` = **6 chars** | conta = **5 chars** | `sessao-2` |
| 6 | notificar cliente = **MVP** | notificar cliente = **fase 2** (só publicar evento é MVP) | `sessao-1` e `sessao-4` |

---

### Fontes
`requisitos/sessao-1-kickoff.md` · `requisitos/sessao-2-emissao.md` · `requisitos/sessao-3-consulta-e-desempenho.md` · `requisitos/sessao-4-confiabilidade-e-integracoes.md` · `requisitos/sessao-5-compliance-e-fechamento.md` · `requisitos/user-stories.md` · `requisitos/personas.md` · `requisitos/NOTAS-DOCENTE-discrepancias.md` · plano: `plano-de-aulas/aula-02.md` · arquitetura-alvo: `inicio/docs/arquitetura.md`
