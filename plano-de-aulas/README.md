# Plano de Aulas — BE-JV-010 · Caixa EmbarqueTI · Nível III

Guia mestre das 9 aulas. Cada `aula-0X.md` segue o método, a trilha de IA e a estratégia de turma mista definidos **aqui** — leia este README antes dos planos individuais.

## Calendário (9 × 3h · Seg/Qua/Sex)

| Aula | Data | Tema |
|---|---|---|
| 1 | Seg **22/06** | Abertura + DDD (panorama) — *realizada; corrida (checklist + apresentações ao vivo). Prática de DDD adiada p/ Aula 2.* |
| 2 | Qua **24/06** | **DDD na prática** (event storming adiado) → Consistência distribuída e **SAGA** |
| 3 | Sex **26/06** | **Cache** com Redis (e semantic caching) |
| 4 | Seg **29/06** | Comunicação assíncrona: **producer/consumer** |
| 5 | Qua **01/07** | **Filas** com RabbitMQ + idempotência e DLQ |
| 6 | Sex **03/07** | **Tópicos / eventos** com Kafka |
| 7 | Seg **06/07** | **Resiliência**: retry, circuit breaker, `@RetryableTopic` |
| 8 | Qua **08/07** | **Contract testing** (PACT) e evals como contrato |
| 9 | Sex **10/07** | **Bancas dos projetos** + devolutiva por rubrica |

> Carga: 27h (9×3h). Divergência das 20h da proposta original assumida pela coordenação (o próprio docente).

> **Perfil real da turma (apurado na Aula 1, 22/06):** 16 alunos (2 faltaram no 1º dia). **Não é uma turma sênior** — a maioria tem **3–5 anos** de experiência e alguns têm **menos de 1 ano** (a Caixa é o primeiro emprego). Trabalham majoritariamente em **legado** (chega a **Java 6 / EJB 2**), mas a Caixa está **modernizando**: já há serviços novos em **Quarkus e Spring com Java 21** (um aluno relatou ter pedido stack Java 25). **DDD:** só ~5 já ouviram falar, de forma teórica — **ninguém praticou**. **~1/3 da turma foi meu aluno** nos módulos de introdução do EmbarqueTI (`pe-na-004`, `up-jv-001`) — há rapport e referências em comum. **Copilot: acesso desigual** — alguns têm o agente no IDE (quota mensal que esgota), outros só o M365 Chat; não pressupor que todos têm. Modernização real = **Java 17 + Quarkus** (Java 21 é minoria) + diretriz de **nuvem pública Azure**. Roster, perfis e dossiê vivo em `transcript/aula-01.md`.

## Por que este módulo precisa de adaptação

O material foi finalizado em **jan/2024**; o banco de exercícios é **100% nível Basic**. Três decisões de design partem do perfil real da turma (acima):
1. **Trazer IA/agentes como fio transversal, agora mão-na-massa** — não está no módulo (escrito antes da maturação atual), mas é a realidade de quem vai operar esses sistemas hoje, e a turma **tem Copilot com agentes**. Cada aula tem um **Ângulo IA/Agentes** que conecta o padrão clássico ao presente **e**, onde couber, um micro-exercício com o Copilot seguido de **crítica** (o arquiteto valida, não terceiriza).
2. **Andaime de fundamentos, não "teto sênior"** — como ninguém praticou DDD e há juniores, o **banco Basic é consolidação formativa de verdade** (não warm-up descartável). O hands-on é **mob guiado**, não "vire-se".
3. **Trilha de aprofundamento opcional** — os **debates de alto nível** e os desafios de **aprofundamento** existem para quem quer ir além naquele dia, sem deixar ninguém para trás. Não pressupõem senioridade.

## Método Ada por aula (PBL + Sala de Aula Invertida) — molde de 180 min

> A inversão é **adaptada ao contexto Caixa**: assume-se que **o aluno não estuda fora do expediente**. Por isso a "pré-aula" é mínima e opcional, e **o tempo de estudo/projeto acontece dentro da aula** (2ª metade). Isso é deliberado e também protege o NPS.

| Bloco | Tempo | O que acontece |
|---|---|---|
| **Provocação (sala invertida lite)** | D-1, ~5 min, opcional | 1 pergunta + 1 link curto no Teams. Quem não viu, a abertura cobre. |
| **1. Problema (PBL)** | 0–20 | Problema gerador **real**. Plenária/breakout: *"como vocês resolvem isso hoje?"* Coleta de hipóteses. |
| **2. Discussão de alto nível** | 20–40 | Trade-offs, falácias de distribuídos, **ponte do legado** (Caixa: mainframe/MQ/batch). |
| **3. Solução possível (ao vivo)** | 40–90 | Live/mob coding no **projeto PIX**, incremento da aula. Ângulo IA/Agentes entra aqui. |
| *Intervalo* | 90–100 | — |
| **4. Desafio de evolução (studio)** | 100–150 | Alunos **estendem** o que fizemos, em **mob guiado** (há juniores). Trilha **base** (todos consolidam) e **aprofundamento** (opcional, para quem quer ir além). |
| **5. Tempo de projeto em grupo** | 150–175 | Breakout rooms: grupos avançam o **projeto final** em aula (endereça a falta de tempo fora). |
| **6. Fechamento + gancho NPS** | 175–180 | Síntese de 1 frase + *"o que você leva hoje"* + provocação da próxima. |

Se **breakout rooms** não existirem (ver Semana 0): blocos 4 e 5 viram **mob programming guiado** + canais por grupo.

## Trilha transversal IA/Agentes (resumo)

| Aula | Padrão clássico | Ângulo IA/Agentes |
|---|---|---|
| 1 | Bounded context / DDD | Fronteiras de **agentes** = bounded contexts; LLM para extrair linguagem ubíqua; *context engineering* como DDD do prompt |
| 2 | SAGA / consistência | **Agentic workflows** como sagas; ações compensatórias em pipelines de IA; orquestração × coreografia em multiagente |
| 3 | Cache (Redis) | **Semantic caching** de respostas LLM; Redis como **vector store** (RAG); cache de embeddings; custo/latência de inferência |
| 4 | Producer/consumer | Desacoplar **inferência cara**; filas de tarefas de agentes; human-in-the-loop assíncrono |
| 5 | Filas / DLQ | Work queues para jobs de IA; **backpressure** quando o LLM é o gargalo; DLQ para chamadas que falham |
| 6 | Tópicos / eventos | Arquitetura **event-driven** para agentes; **event sourcing como memória** de agente; streaming para ingestão/RAG |
| 7 | Resiliência / retry | APIs de LLM instáveis: timeout, **fallback de modelo**, circuit breaker, idempotência com saída não-determinística |
| 8 | Contract testing | Testar saída **não-determinística**; **evals como contrato**; validação de schema de JSON do LLM (*structured outputs*) |
| 9 | — | Como a IA muda o **papel do arquiteto**; uso crítico × dependência |

## Estratégia para turma mista (legado × moderno, com juniores)

O eixo da turma **não é "sênior moderno × veterano"** — é **experiência em legado (Java 6/EJB 2) e pouca prática em distribuído/cloud-native**, com **alguns juniores** (<1 ano). A força a explorar: eles **conhecem o problema** (já sentiram a dor do acoplamento, do batch que falha, do contrato em Word), só não conhecem o **padrão moderno** que o resolve. Cada aula equilibra:

- **Ponte do legado** — ancorar o conceito novo em algo que eles já viveram na Caixa:
  - Filas/tópicos ↔ **IBM MQ / JMS / MQSeries**; batch noturno ↔ **streaming de eventos**;
  - orquestração por stored procedure ↔ **SAGA**; cache caseiro em tabela ↔ **Redis**;
  - contrato em documento Word ↔ **contract testing executável**.
- **Ponte da modernização** — fechar cada conceito mostrando que é **exatamente o que os novos serviços Quarkus/Spring (Java 21) da Caixa precisam**. O módulo não é teoria distante: é a stack para onde o trabalho deles está indo. Java 21 é o baseline; 21→25 é o horizonte.
- **Trilha base × aprofundamento** no desafio da 2ª metade: base = fazer funcionar, **todos chegam** (mob guiado); aprofundamento = otimizar/quebrar/decidir trade-off, **opcional** para quem quer ir além naquele dia.
- **Banco de 40 questões (Basic)** = **consolidação formativa** (genuína para quem nunca praticou e para os juniores), aplicada **via LMS** (disponível desde ~23/06; exercícios liberados a partir da Aula 2). É consolidação, não avaliação central — o foco do módulo é decisão arquitetural.
- **IA/Copilot mão-na-massa** — eles têm Copilot com agentes; usá-lo para andaimar e depois **criticar a saída** ajuda os juniores a acompanhar o live coding **sem** terceirizar a decisão de arquitetura.
- **Debates de alto nível** (todos participam, níveis diferentes contribuem): consistência eventual, idempotência, *exactly-once* é mito, *outbox pattern*, falácias dos sistemas distribuídos, quando **NÃO** usar microsserviços.

## Mirando NPS 90+ (padrão de excelência Ada)

Ganchos desenhados nas aulas:
- **Respeito ao tempo** — estudo e projeto acontecem na aula; nada de "vire-se em casa".
- **Relevância imediata** — problemas no domínio Caixa, ponte com o legado real deles.
- **Tema quente** — IA/agentes em todas as aulas, sem virar hype vazio (sempre ligado ao padrão de engenharia).
- **Autonomia** — projeto final de **tema livre** (só critérios), times decidem o que construir.
- **Desafio na medida** — quem quer mais é provocado em debate e aprofundamento; quem vem de legado ou está começando é acolhido pela ponte e pelo mob guiado. Ninguém fica para trás nem entediado.
- **Fechamento memorável** — cada aula entrega 1 frase-síntese e 1 "leva pra casa".

## Os dois projetos (apresentados na Aula 1)

- **Guiado em aula** — API de **Comprovantes PIX**, construída incrementalmente (ver `projetos/projeto-guiado-pix/`). É o que demonstramos ao vivo.
- **Final em grupo** — **tema livre** definido por cada grupo, avaliado por **critérios** (ver `projetos/projeto-final-grupo/`). Estruturado para ser **avaliável via Claude** ao final. O PIX é só uma **sugestão de referência**, não uma amarra. **N=16 → 4 grupos de 4** (ver brief).

## Perfil de execução — Plano B é o piso (confirmado)

A Aula 1 confirmou: **sem Docker e sem sandbox** na rede Caixa. O **Plano A (Docker/Testcontainers) está descartado** — fica só como referência de "onde isto roda em produção" (a ponte da modernização). O plano de cada aula roda em **pura-JVM**:
- **Plano B (o plano):** pura-JVM — embedded-redis/Caffeine, Qpid embarcado, `EmbeddedKafkaBroker`, Pact-JVM com pactfiles. Roda em qualquer máquina com Java 21 + Maven + Nexus.
- **Plano C (fallback conceitual):** se o **Nexus não publicar** o artefato pura-JVM — event bus in-process + exercício de design. O **aprendizado do padrão não depende da infra**.

> **Risco aberto:** a resolução dos artefatos de fallback no **Nexus** ainda **não foi confirmada** (ficou como tarefa de casa dos alunos — ver `ambiente/relatorio-semana-0.md`). Mensageria (filas/tópicos) pode cair no Plano C. Cada aula traz B **e** C prontos por isso.
