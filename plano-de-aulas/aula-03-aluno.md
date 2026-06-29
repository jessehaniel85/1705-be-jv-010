# Material do Aluno — Aula 3: Comunicação entre microsserviços (o panorama)

> **Tempo de leitura:** ~13 min. Na Aula 2 você quebrou um fluxo de negócio em serviços com bases próprias e viu que manter o dado correto virou responsabilidade sua (SAGA, compensação, idempotência). Ficou no ar a pergunta certa: **e como esses serviços conversam, afinal?** Esta aula é o mapa dessa conversa. Não é sobre uma ferramenta — é sobre os **três eixos de decisão** que toda comunicação distribuída atravessa, e sobre onde encaixam nomes que apareceram na turma: **Apache Camel**, **service bus / ESB**, **arquitetura orientada a eventos**. No fim, você vai conseguir ouvir "a gente usa um barramento" ou "isso é coreografia" e saber exatamente de que camada a pessoa está falando.

---

## 1. O problema gerador: três perguntas que viraram uma só

Depois da SAGA, as dúvidas da turma convergiram para variações da mesma pergunta:

- *"Os serviços se chamam por HTTP ou jogam mensagem numa fila?"*
- *"Um colega usava Apache Camel; outro falou em service bus — isso é a mesma coisa que fila?"*
- *"Quando o erro acontece, quem decide o que fazer? Tem um chefe ou cada um se vira?"*

Parecem três perguntas. São **três eixos independentes** da mesma decisão, e a confusão quase sempre nasce de tratá-los como um só. Vamos separá-los:

1. **O acoplamento temporal** — síncrono ou assíncrono? (§2)
2. **A intenção da mensagem** — comando ou evento? (§3)
3. **Quem coordena o fluxo** — orquestração ou coreografia? (§4)

E há uma quarta coisa, que **não é um eixo de decisão de arquitetura**, mas de **ferramental**: como a mensagem fisicamente trafega, é roteada e transformada — é aí que entram **broker, ESB, service bus e Apache Camel** (§6). Misturar essa camada com as três decisões acima é o erro nº 1. Um "barramento" não decide se você usa evento ou comando; ele só transporta.

> A regra que organiza a aula inteira: **EDA é o meio, orquestração/coreografia é quem manda no fluxo, e Camel/ESB é como a mensagem anda.** São perguntas diferentes, com respostas independentes.

---

## 2. Eixo 1 — Síncrono × assíncrono (o acoplamento temporal)

A primeira decisão é se o emissor **espera** a resposta.

- **Síncrono (request/response):** o serviço A chama B e **bloqueia** até B responder. REST sobre HTTP, gRPC, GraphQL. Simples de raciocinar — parece uma chamada de método. O preço: **acoplamento de disponibilidade** (se B caiu, A falha) e **acoplamento de latência** (A paga o tempo de B).
- **Assíncrono (mensageria):** A entrega a mensagem a um **intermediário durável** e segue a vida; B processa quando puder. Desacopla disponibilidade e latência — ao custo de um broker para operar e de uma classe nova de problemas (duplicação, ordem, "a mensagem sumiu?").

| Dimensão | Síncrono (HTTP/gRPC) | Assíncrono (mensageria) |
|---|---|---|
| O emissor espera? | Sim, bloqueia | Não, dispara e segue |
| Acoplamento de disponibilidade | Alto (caem juntos) | Baixo (o broker absorve) |
| Latência vista pelo cliente | Soma de todo o trabalho | Só validação + publicação |
| Absorção de pico | Nenhuma | A fila é o buffer natural |
| Complexidade operacional | Baixa | Alta (broker, dedup, DLQ) |
| Quando preferir | A resposta é necessária **já** para continuar | Trabalho lento/instável, picos, fan-out |

A pergunta de triagem é a mesma da Aula 5: **"o chamador precisa do resultado para a próxima ação dele?"** Se sim, comece síncrono. Se não — e há latência, instabilidade ou pico no caminho — o assíncrono se paga. Não é "moderno × antigo": um sistema real usa **os dois**, cada um onde dói menos.

> Erro clássico do júnior empolgado: tornar **tudo** assíncrono porque "microsserviço de verdade é por evento". Consultar o saldo para autorizar um PIX **tem** que ser síncrono — o cliente não pode receber `202 Aceito, te aviso depois se tinha saldo`.

---

## 3. Eixo 2 — Comando × evento (a intenção)

Dentro do mundo assíncrono, **toda mensagem é uma de duas coisas** — e confundi-las recria o acoplamento que a fila deveria quebrar. (Esta é a espinha da Aula 5; aqui fica o resumo que organiza o panorama.)

- **Comando** — *"faça isto"*. Dirigido a **um** dono lógico, no imperativo: `GravarComprovanteCommand`. O emissor **espera** que a ação aconteça e sabe que pediu.
- **Evento** — *"isto aconteceu"*. Um fato no passado, no particípio: `ComprovanteGravadoEvent`. O emissor **não sabe nem se importa** com quem reage — pode ser zero, um ou dez consumidores.

> A regra prática: **se você consegue adicionar um novo consumidor sem tocar no produtor, é um evento. Se o produtor precisaria saber que o consumidor existe, é um comando.**

Esse eixo é o que liga este panorama de volta à SAGA da Aula 2: a compensação coreografada que você discutiu — *"deu erro, publica um evento, cada um reage"* — é exatamente **eventos** disparando reações. Já a orquestração tende a usar **comandos** (o maestro manda cada serviço agir). Guarde isso para o §4.

---

## 4. Eixo 3 — Orquestração × coreografia (quem coordena)

Este é o eixo que gerou a sua resposta em aula — e o que mais merece precisão, porque o custo da coreografia é fácil de subestimar.

### Orquestração — um maestro central
Um componente conhece o fluxo inteiro e **comanda** cada passo (tipicamente via **comandos**), decidindo a cada resposta se avança ou compensa. O fluxo de negócio mora **num lugar só**.

- **Ganho:** observabilidade alta (leia o estado da saga num lugar), fácil de depurar e provar correto.
- **Custo:** o maestro é um componente crítico e todos o conhecem (mais acoplamento a ele). *(O quanto isso é mesmo um SPOF — e como um pool de orquestradores realoca o problema em vez de eliminá-lo — foi a discussão da Aula 2.)*

### Coreografia — sem maestro, por eventos
Ninguém comanda. Cada serviço **reage** a um evento e **emite** o seu. A emissão publica `ComprovanteAceito`; o gravador escuta, grava, publica `ComprovanteGravado`; se falha, publica `GravacaoFalhou`, e quem se importa reage — inclusive disparando uma **ação compensatória**.

- **Ganho:** desacoplamento máximo. Adicionar um novo reator (antifraude, BI) é só assinar o evento — o produtor nem fica sabendo.
- **Custo honesto — e é simétrico ao SPOF do maestro:** o fluxo fica **espalhado**. Não existe lugar para "ler a saga inteira"; entender e depurar exige **reconstruir mentalmente a cadeia de eventos**. Surgem três armadilhas específicas:
  - **Opacidade:** "em que passo a saga travou?" não tem resposta num só log. Você precisa de **correlation id** atravessando os serviços para reconstruir.
  - **Cadeias cíclicas:** A reage a um evento de B emitindo um evento que dispara B de novo — laços emergentes difíceis de prever.
  - **"Terminou?":** ninguém sabe sozinho se a saga concluiu ou ficou órfã. Precisa de timeout/sweeper externo.

| Critério | Orquestração | Coreografia |
|---|---|---|
| Onde mora o fluxo | Centralizado (maestro) | Distribuído (nos eventos) |
| Mensagem típica | Comando | Evento |
| Observabilidade | Alta | Baixa (precisa correlacionar) |
| Acoplamento | Maior (todos conhecem o maestro) | Menor (só conhecem eventos) |
| Risisco focal | Maestro é crítico | Cadeia opaca / cíclica |
| Quando preferir | Fluxo com decisão complexa; **caminho crítico auditável** | Fluxo linear; **muitos reatores** |

**A correção que vale para o contexto Caixa.** Dizer que "coreografia é muito comum" precisa de asterisco em domínio bancário regulado: o **caminho crítico do dinheiro** (uma saga de pagamento) costuma ser **orquestrado de propósito** — você quer um estado de transação auditável e observável num lugar só. A **coreografia brilha na periferia reativa**: notificação, antifraude e BI reagindo a `ComprovanteGravado`. A resposta madura é **mistura**: orquestre o núcleo, coreografe os reatores. A heurística da Aula 2 continua valendo — **comece orquestrado** (mais fácil de provar correto) e migre passos para coreografia conforme o desacoplamento justifique o custo de opacidade.

---

## 5. Arquitetura orientada a eventos (EDA) — o estilo, não a estratégia

Quando a coreografia deixa de ser "um truque numa saga" e vira o **estilo dominante** do sistema — serviços se integram primariamente publicando e consumindo eventos — você está fazendo **arquitetura orientada a eventos (EDA)**.

O ponto que desfaz a confusão mais comum: **EDA é o meio (o sistema conversa por eventos); coreografia é uma forma de usar esse meio para coordenar um fluxo.** Você pode ter EDA **e** orquestração ao mesmo tempo — um orquestrador que consome e emite eventos é perfeitamente EDA. Não são sinônimos.

EDA tende a aparecer em três sabores, em complexidade crescente:

- **Notificação por evento:** o evento avisa "algo aconteceu", carregando só o id; o interessado vai buscar o resto. Acoplamento mínimo, mas gera chamadas de volta.
- **Transferência de estado por evento (event-carried state):** o evento carrega os dados de que o consumidor precisa, evitando o callback. Mais autonomia, ao custo de duplicar dados.
- **Event sourcing:** o estado do sistema **é** a sequência de eventos (a "memória" é o log). Poderoso e auditável — combina com o domínio bancário —, mas é um salto de complexidade. Fica para a Aula 7 (Kafka), onde o log de eventos é cidadão de primeira classe.

> Para guardar: notificação e fan-out reativo cabem em **filas/tópicos** comuns (Aulas 6–7). Event sourcing é EDA "levada a sério" — não comece por ele.

---

## 6. A camada de integração: broker, ESB, service bus e Apache Camel

Aqui mora a pergunta que os colegas trouxeram. **Nada nesta seção decide os três eixos acima** — é tudo sobre **como a mensagem fisicamente anda, é roteada e transformada**. Vamos do mais simples ao mais "esperto".

### Broker (o transporte durável)
Um **broker** guarda a mensagem de forma durável até alguém consumir: RabbitMQ, Kafka, IBM MQ, **Azure Service Bus**. É "burro" no bom sentido — ele transporta e entrega; a inteligência de negócio fica **nos serviços**. Esse é o modelo que o resto do módulo usa (Aulas 5–7).

> **Sobre "Azure Service Bus" especificamente** (alvo de nuvem da Caixa): apesar do nome "bus", ele é um **broker gerenciado** — filas e tópicos como serviço. É saudável e moderno; **não** é um ESB pesado. Quando alguém na Caixa disser "vamos no Service Bus", quase sempre é isto: um broker na nuvem, equivalente em papel ao RabbitMQ/Kafka que veremos.

### ESB (Enterprise Service Bus) — o barramento "esperto"
O **ESB clássico** é um barramento **central** que faz muito mais que transportar: roteamento por conteúdo, transformação de formatos, orquestração, adaptação de protocolos — tudo num componente central de mediação (TIBCO, Oracle ESB, IBM Integration Bus). Foi o padrão corporativo dos anos 2000 — e é provavelmente o que a turma viveu no legado da Caixa.

**Por que o movimento de microsserviços reagiu contra o ESB.** Martin Fowler resumiu na frase **"smart endpoints and dumb pipes"** (endpoints espertos, canos burros): a inteligência deve viver **nos serviços**, e o transporte deve ser **simples**. O ESB centraliza a esperteza num só lugar, e isso traz dois problemas que microsserviços tentam evitar: ele vira **SPOF** (cai o barramento, cai a integração toda) e **gargalo de deploy** (toda mudança de roteamento passa por um time central). O ESB não é "errado" — é uma escolha que troca autonomia de times por governança central. Microsserviços fazem a troca oposta.

### Apache Camel — EIP como biblioteca
**Apache Camel** é um *toolkit* de **Enterprise Integration Patterns** (os padrões do livro de Hohpe & Woolf): roteamento, *content-based router*, *splitter/aggregator*, transformação, com **300+ conectores** (HTTP, JMS, Kafka, FTP, e-mail, S3...). A mecânica central é a **rota**:

```java
// Apache Camel: uma rota = receba daqui, transforme, mande para lá.
// "Comprovante chega numa fila AMQP, normaliza, roteia por valor."
from("amqp:queue:comprovantes.entrada")
    .unmarshal().json(Comprovante.class)
    .choice()
        .when(simple("${body.valor} > 50000"))
            .to("amqp:queue:comprovantes.alto-valor")   // antifraude reforçada
        .otherwise()
            .to("amqp:queue:comprovantes.padrao");
```

O ponto que **desfaz a confusão do colega**: Camel **não é uma fila** e **não é uma estratégia de saga**. É a *cola* que conecta e medeia sistemas heterogêneos. E a diferença que importa hoje: o Camel clássico podia ser usado **como** um ESB (um hub central de integração); o Camel moderno é usado tipicamente como **biblioteca de integração dentro de um microsserviço** (ou um pequeno serviço de integração dedicado) — "smart endpoint", não barramento central. Mesma ferramenta, filosofia de implantação oposta.

### O mapa de uma frase

| Camada | O que é | Exemplos | Decide o fluxo de negócio? |
|---|---|---|---|
| **Transporte (broker)** | Entrega durável de mensagens | RabbitMQ, Kafka, IBM MQ, Azure Service Bus | Não — só transporta |
| **Mediação (integração)** | Rotear, transformar, adaptar protocolo | Apache Camel, ESB clássico | Não — só conecta/medeia |
| **Coordenação** | Quem decide o próximo passo | Orquestrador (Saga), coreografia | **Sim** — é decisão de arquitetura |

Quando alguém disser "usamos um barramento", a pergunta certa é: *"barramento como transporte (broker) ou como mediação central esperta (ESB)?"* — são coisas muito diferentes.

---

## 7. Recap: entidade, value object e agregado — com código

A turma sinalizou que **entidade** e **agregado** escorregam — e a raiz da confusão é uma pergunta justa: *"a `Fatura` tem `id`, então ela é entidade ou agregado?"*. A resposta que desfaz o nó:

> **"Entidade" e "agregado" não são categorias rivais.** Entidade × value object é uma decisão sobre **um objeto**. Agregado é uma decisão sobre **um grupo de objetos**. A **raiz de um agregado é sempre uma entidade** — ter `id` não a impede de ser raiz; é justamente o que a qualifica.

São, portanto, **duas perguntas independentes**:

**Pergunta 1 — para cada objeto: entidade ou value object?**
- **Entidade:** tem **identidade própria + ciclo de vida**. É "a mesma" ao longo do tempo, mesmo mudando de estado. Igualdade **por id**. (`Comprovante` vai de `ACEITO`→`GRAVADO` e continua o mesmo.)
- **Value object:** **sem identidade**, definido **só pelos valores**, **imutável**. Igualdade por valor; você não altera, cria outro. (`ChavePix`, `Dinheiro`.)
- A heurística que decide: **"eu me importo com *qual* é este, ou só com *o que* é este?"** Se preciso distinguir, rastrear ou mudar o estado deste em particular → **entidade**. Se só importam os valores → **VO**.

**Pergunta 2 — para um grupo de objetos: onde fica a fronteira do agregado?**
- **Agregado** = um *cluster* de objetos (entidades + VOs) que **mudam juntos** sob uma **invariante** comum, com uma **raiz** (sempre uma entidade) como **única porta de entrada**. Ninguém de fora toca os membros internos — só a raiz.

### O caso que confunde: `Lançamento` é entidade ou VO?

Aplique a Pergunta 1 ao `Lançamento`. *"Me importo com qual é este lançamento em particular?"* — **sim**: um lançamento pode ser **contestado** ou **estornado individualmente**, você se refere a "o lançamento nº X", e ele tem **estado que muda** (`NORMAL`→`CONTESTADO`). Logo **`Lançamento` é uma entidade** — só que uma **entidade interna**: tem identidade *dentro* da fatura e é manipulada **apenas através** da `Fatura` (a raiz). Um agregado pode conter **várias entidades**; só uma é a raiz.

> Contraexemplo: se na sua modelagem um lançamento **nunca** é alterado nem referenciado isoladamente — é só "uma linha imutável do extrato" —, então ele seria um **value object**. A resposta depende do **comportamento que o domínio exige**, não do formato dos dados. Por isso não há resposta universal: é uma decisão de projeto.

### Agregado 1 — `Comprovante` (raiz entidade + só VOs)

O agregado mais simples: uma raiz e **nenhuma entidade interna**, só value objects.

```java
// VALUE OBJECTS — sem id, imutáveis, validados no construtor.
public record ChavePix(TipoChave tipo, String valor) {
    public ChavePix {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException("chave PIX vazia");
    }
}

public record Dinheiro(BigDecimal valor, String moeda) {
    public Dinheiro {
        if (valor.signum() < 0) throw new IllegalArgumentException("valor negativo");
    }
    public Dinheiro soma(Dinheiro o) {
        if (!moeda.equals(o.moeda)) throw new IllegalArgumentException("moedas diferentes");
        return new Dinheiro(valor.add(o.valor), moeda);   // retorna NOVO objeto
    }
}

// ENTIDADE-RAIZ do agregado Comprovante: tem id e ciclo de vida (o status muda).
public class Comprovante {                 // aggregate root
    private final UUID id;                  // identidade — define igualdade
    private final ChavePix destino;         // VO
    private final Dinheiro valor;           // VO
    private StatusComprovante status;       // estado: ACEITO -> GRAVADO

    public Comprovante(UUID id, ChavePix destino, Dinheiro valor) {
        this.id = id; this.destino = destino; this.valor = valor;
        this.status = StatusComprovante.ACEITO;
    }

    public void marcarGravado() {           // única forma de mudar o estado
        if (status != StatusComprovante.ACEITO)
            throw new IllegalStateException("só ACEITO pode ir para GRAVADO");
        this.status = StatusComprovante.GRAVADO;
    }
    // equals/hashCode SOMENTE por id
}
```

Fronteira do agregado: `{ Comprovante (raiz), ChavePix, Dinheiro }`. Invariante simples — "o status só avança de `ACEITO` para `GRAVADO`". Não há entidade interna: `ChavePix` e `Dinheiro` são VOs.

### Agregado 2 — `Fatura` (raiz entidade + entidade interna + VO)

Agora um agregado com uma **entidade interna** (`Lançamento`) e uma **invariante de verdade**: *o total da fatura é sempre a soma dos lançamentos*.

```java
// ENTIDADE INTERNA: tem id e ciclo de vida (pode ser contestada), mas é
// manipulada SÓ através da Fatura — nunca direto de fora (acessores package-private).
public class Lancamento {
    private final UUID id;                  // identidade local ao agregado
    private final String descricao;
    private final Dinheiro valor;           // VO
    private StatusLancamento status;        // NORMAL -> CONTESTADO

    Lancamento(UUID id, String descricao, Dinheiro valor) {  // visível só no pacote
        this.id = id; this.descricao = descricao; this.valor = valor;
        this.status = StatusLancamento.NORMAL;
    }
    UUID id()         { return id; }
    Dinheiro valor()  { return valor; }
    void contestar()  { this.status = StatusLancamento.CONTESTADO; }
}

// ENTIDADE-RAIZ: a única porta de entrada do agregado.
public class Fatura {                       // aggregate root
    private final UUID id;
    private final List<Lancamento> lancamentos = new ArrayList<>();
    private Dinheiro total;
    private boolean fechada;

    public Fatura(UUID id, String moeda) {
        this.id = id;
        this.total = new Dinheiro(BigDecimal.ZERO, moeda);
    }

    // Mexer nos lançamentos SÓ por aqui — é o que protege a invariante.
    public void adicionar(String descricao, Dinheiro valor) {
        if (fechada) throw new IllegalStateException("fatura fechada");
        lancamentos.add(new Lancamento(UUID.randomUUID(), descricao, valor));
        recalcularTotal();                  // invariante: total == soma dos lançamentos
    }

    public void contestar(UUID idLancamento) {
        lancamentos.stream()
            .filter(l -> l.id().equals(idLancamento)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("lançamento inexistente"))
            .contestar();                    // muda estado de UMA entidade interna
    }

    private void recalcularTotal() {
        this.total = lancamentos.stream()
            .map(Lancamento::valor)
            .reduce(new Dinheiro(BigDecimal.ZERO, total.moeda()), Dinheiro::soma);
    }
}
```

Repare no que a raiz garante: **não existe** um `lancamento.setValor(...)` chamável de fora, nem um `lancamentoRepository.save(...)` que altere um lançamento sem passar pela `Fatura`. Por isso a invariante (`total == soma`) **nunca** fica inconsistente — toda mudança entra pela porta única e recalcula. É isso que "fronteira de consistência" significa na prática.

Fronteira do agregado: `{ Fatura (raiz), Lancamento* (entidade interna), Dinheiro (VO) }`. `Lancamento` **tem id, mas não é raiz**.

| | Agregado 1 — Comprovante | Agregado 2 — Fatura |
|---|---|---|
| Raiz (entidade) | `Comprovante` | `Fatura` |
| Entidades internas | — (nenhuma) | `Lancamento` |
| Value objects | `ChavePix`, `Dinheiro` | `Dinheiro` |
| Invariante protegida | status só avança `ACEITO`→`GRAVADO` | `total == soma dos lançamentos` |

### Por que isso é uma decisão de comunicação

> **Uma transação altera um agregado.** O agregado é a **menor unidade de consistência transacional**. Dentro dele: consistência forte, transação local, **chamada de método — sem rede**. *Entre* agregados (e portanto entre serviços), você não tem transação: é exatamente onde entram mensageria, eventos e SAGA.

Em outras palavras: **se você está precisando de comunicação distribuída e SAGA para algo que está dentro de um mesmo agregado, a fronteira está errada** — junte. E se dois "agregados" só vivem se falando de forma síncrona e transacional, talvez sejam um só. A dor de comunicação distribuída é, muitas vezes, um corte de agregado mal feito vindo à tona. Por isso a Aula 1 (DDD) vem antes desta: **o desenho dos agregados é o que determina o mapa de comunicação** da §8.

---

## 8. Mapa de decisão (a colável)

Diante de "o serviço A precisa que algo aconteça no serviço B", percorra as perguntas **nesta ordem** e pare na primeira que se aplica. Os exemplos usam os agregados da §7 e o fluxo PIX.

1. **B está no mesmo agregado de A?** → Então **não são dois serviços**. Junte; chamada de método; transação local. Pare aqui.
   > **Exemplo:** adicionar um lançamento e recalcular o total da fatura. `Lancamento` e `Fatura` são o **mesmo agregado** → `fatura.adicionar(...)`, uma transação local. **O erro que delata fronteira mal cortada:** criar um `LancamentoService` que a `Fatura` chama por HTTP para somar — você inventou uma transação distribuída para proteger uma invariante (`total == soma`) que era **local**.

2. **A precisa da resposta de B para a própria próxima ação?** → **Síncrono** (REST/gRPC). Aceite o acoplamento de disponibilidade ou proteja com resiliência (Aula 8).
   > **Exemplo:** antes de **aceitar** o PIX, o emissor precisa saber se a `Conta` tem saldo/limite. Ele não pode responder "aceito, te aviso depois se tinha saldo" → chamada **síncrona** ao serviço de Conta; o "aprovado/negado" decide a próxima ação (aceitar ou recusar).

3. **A não precisa esperar, e há um responsável claro pela ação?** → **Comando** assíncrono numa **fila** (Aulas 5–6).
   > **Exemplo:** PIX aceito; agora é preciso **gravar** o comprovante na base de verdade — lento, mas com **dono claro** (o gravador). Ninguém espera → `GravarComprovanteCommand` numa fila. (É o que a Aula 5 constrói.)

4. **A não precisa esperar, e quem reage é problema de quem reage?** → **Evento** num **tópico** (Aula 7). Vários consumidores, fan-out.
   > **Exemplo:** o comprovante **foi gravado**. Quem se importa? Notificação (SMS), antifraude, BI — e amanhã pode surgir um quarto interessado. O gravador não quer saber → publica `ComprovanteGravado` num **tópico**; cada um reage por conta própria.

5. **O fluxo cruza vários serviços com passos que podem falhar e precisam ser desfeitos?** → é uma **SAGA** (Aula 2).
   > **Exemplo:** emissão → gravação → baixa de limite, em bases diferentes; se a baixa falha, é preciso **compensar** a gravação. Orquestre o caminho crítico do dinheiro (auditável) e coreografe os reatores periféricos.

6. **B é um sistema heterogêneo que fala outro formato/protocolo?** → camada de **integração** (Camel), preferencialmente como *smart endpoint*, não como ESB central.
   > **Exemplo:** o antifraude legado roda no mainframe e só aceita arquivo de **largura fixa**, não JSON. Você precisa **transformar e rotear** entre o seu evento e o formato dele → uma **rota Camel** dedicada, não acoplar essa tradução dentro do serviço de negócio.

---

## 9. Ponte com o legado Caixa

Quase tudo desta aula a turma **já viveu**, com outros nomes:

- **IBM MQ / MQSeries / JMS** = o broker. "Aceitar agora, processar depois" nasceu no mundo dos grandes processadores transacionais, não na nuvem. Quem operou filas de entrada/saída do mainframe já fez producer/consumer décadas atrás.
- **O ESB corporativo** (o "barramento" que muita integração da Caixa atravessa) = a mediação central esperta. Conhecer suas dores — SPOF, fila de deploy no time do barramento, transformação opaca — é entender **por que** os serviços novos (Quarkus/Spring) preferem broker burro + endpoints espertos.
- **Orquestração por job control / stored procedure** = a SAGA orquestrada implícita do mainframe — só que escondida e sem compensação explícita.

O que muda não é o conceito; é o **ferramental e a filosofia de implantação**: do barramento central para o broker gerenciado (Azure Service Bus); da esperteza central para o serviço autônomo; da capacidade fixa para a escala elástica. Quem vem do legado costuma entender o **porquê** mais rápido — porque já sentiu a dor que cada escolha moderna tenta evitar.

---

## 10. IA & agentes hoje

Os mesmos três eixos governam sistemas de IA — e a fronteira de **bounded context** vira a fronteira do **agente**:

- **EDA para agentes:** um sistema multiagente maduro raramente é uma corrente síncrona de chamadas. Agentes **publicam eventos** ("documento analisado", "resposta gerada") e outros reagem — coreografia. O orquestrador-agente, quando existe, manda **comandos** ("pesquise isto") — orquestração. É o mesmo trade-off observabilidade × desacoplamento, agora com passos não-determinísticos.
- **Message bus de agentes:** protocolos emergentes de comunicação entre agentes (agente-para-agente, *tool use* via servidores) são, no fundo, a escolha "broker burro + endpoints espertos" reaparecendo: transporte simples, inteligência no agente.
- **Camel/EIP para IA:** rotear, transformar e agregar continua valendo quando o "endpoint" é um LLM — *content-based routing* para escolher o modelo, *aggregator* para juntar respostas de vários agentes. EIP não envelheceu; ganhou um conector novo.
- **A opacidade da coreografia piora com IA:** se já é difícil depurar uma cadeia de eventos, imagine quando cada reator é não-determinístico. Por isso **correlation id** e observabilidade deixam de ser higiene e viram requisito.

---

## 11. Para ir além

- **Gregor Hohpe & Bobby Woolf**, *Enterprise Integration Patterns* — a bíblia da mediação (a base do Camel). Os padrões que você vai reencontrar a vida toda.
- **Sam Newman**, *Building Microservices* (cap. de comunicação) — orquestração × coreografia, síncrono × assíncrono, com a honestidade dos trade-offs.
- **Martin Fowler**, *"What do you mean by Event-Driven?"* e *"MicroservicesAndTheESB" / "smart endpoints, dumb pipes"* — os textos que separam EDA de barramento esperto.
- **Apache Camel** — *Camel in Action* / a doc de rotas, para ver os EIP virarem código.

> **Na próxima aula (Aula 4 — Cache):** você mapeou *como* os serviços conversam. Antes de construir essa comunicação na prática (Aula 5), atacamos um gargalo ortogonal mas urgente para o seu projeto: a operação mais frequente do PIX — a **consulta** de comprovante — está martelando o banco a cada chamada. E quando forem milhões por dia? A Aula 4 alivia essa leitura com **cache** (Redis): estratégias (cache-aside, write-through…), TTL e o problema mais difícil de todos — a **invalidação**. Depois dela (Aula 5) voltamos ao eixo síncrono→assíncrono para construir o `POST` que responde `202` e publica um comando.
