# Plano de Trabalho — Docência BE-JV-010 (Caixa EmbarqueTI · Nível III)

**Missão:** ministrar pela primeira vez o módulo **BE-JV-010 · Arquitetura de Software e Ágil II** para uma turma do **Nível III (Especialista)** do programa EmbarqueTI Caixa, com aulas práticas e aplicadas, **um projeto guiado em aula** e **um projeto final em grupo** para avaliação.
**Docente:** Jesse Haniel · **Primeira oferta deste módulo.**
**Diretório de escrita:** `Labs/Caixa-EmbarqueTI-NovaProposta/planejamento-de-aulas/1705-be-jv-010/`
**Origem do conteúdo:** `Modulos/BE-JV-Backend-Java/BE-JV-010 Arquitetura de Software e Ágil II`

---

## 1. Contexto e condições de operação

> **Atualizado após a Aula 1 (22/06).** Ver `plano-de-aulas/README.md` (perfil da turma) e `ambiente/relatorio-semana-0.md` (ambiente).

- **Onde a turma roda:** alunos são profissionais da **Caixa**, em **horário de expediente**, acessando as aulas **de dentro da rede corporativa**, **via Microsoft Teams**.
- **Perfil da turma (apurado na transcrição — ver `transcript/aula-01.md`):** **16 alunos** (2 faltaram no 1º dia). **Não é sênior** — maioria **3–5 anos**, alguns **<1 ano** (1º emprego), 2 veteranos de carreira (Sandy ~20a, Lucas 15a) mas **não em distribuído**. Legado pesadíssimo (**Java 6, EJB 2, JBoss 4, Hibernate 3, struts, JSF, IBM MQ**). Modernização real = **Java 17 + Quarkus** (Java 21 minoria; Java 25 aspiração) + diretriz **nuvem pública Azure**. **DDD:** só teoria, ninguém praticou. **~1/3 foi meu aluno** no EmbarqueTI. **Copilot: acesso desigual** (alguns com agente/quota que esgota, outros só M365 Chat) → atividades de IA opcionais.
- **Implicações diretas:**
  - Compartilhamento de tela / live coding pelo Teams; trabalho em grupo depende de **breakout rooms** (a confirmar).
  - **Não se pode depender de sites externos** (GitHub, Docker Hub, Maven Central direto, start.spring.io). Tudo que a turma baixar precisa vir do **Nexus Caixa**.
  - Distribuição de código não pode assumir GitHub público — usar git interno ou pacote via Teams/WhatsApp.
  - **Plataforma Ada (LMS): disponível desde ~23/06.** O material compartilhado por WhatsApp (requisitos, checklist, material do aluno em PDF) está sendo subido ao LMS; **exercícios disponíveis a partir da Aula 2**. A distribuição offline (`distribuicao-offline/`) vira **backup**.
- **Baseline de ferramentas garantido (mínimo):** **Java SDK 21+**, **Maven**, **Nexus Caixa**.
- **Confirmado na Aula 1:** **sem Docker e sem sandbox** → **Plano B (pura-JVM) é o piso**, Plano A descartado. **Em aberto:** o **Nexus resolve os artefatos de fallback** (embedded-redis/Caffeine, Qpid, `EmbeddedKafkaBroker`, Pact-JVM)? Ficou como **tarefa de casa dos alunos**; se não resolver, mensageria cai no **Plano C** (in-process + design).

### 1.1. Conteúdo do módulo (resumo do planejamento oficial)

DDD e segregação de bancos por microsserviço · replicação em cloud (teórica) · **padrão SAGA** (orquestração) · **cache compartilhado com Redis** · **comunicação assíncrona** (producer/consumer) · **filas com RabbitMQ** · **tópicos com Kafka** · **retry/resiliência** (`@RetryableTopic`) · **contract testing** (PACT) · arquitetura amigável a testes.
**Projeto-referência do módulo:** API de **armazenamento e consulta de comprovantes PIX** (POST retorna `202` e publica em fila → consumidor grava no banco; consulta busca primeiro no Redis, com fallback no banco e 3 retentativas → `404`).

---

## 2. Decisões travadas (com o docente)

| # | Decisão | Escolha |
|---|---------|---------|
| 1 | Carga e cadência | **27h — 9×3h (3×/semana)**, igual ao desenho oficial do módulo em cadência 3×/semana (8 aulas de conteúdo + 1 de devolutiva). |
| 2 | Papel dos dois projetos | **Projeto guiado em aula = API de comprovantes PIX** (o projeto-referência do módulo, construído incrementalmente aula a aula). **Projeto final em grupo = variante temática Caixa** (mesmos padrões, domínio diferente), para avaliar transferência. |
| 3 | Middleware no cenário restrito | **Confirmado restrito (sem Docker/sandbox) → Plano B (pura-JVM) é o piso.** Resta confirmar a resolução dos artefatos no Nexus (tarefa de casa); se faltarem, tema cai no Plano C. |
| 4 | Perfil da turma | **Mid-level/misto, não sênior** (3–5 anos; alguns <1 ano), legado-pesado, DDD nunca praticado. Recalibra andaime, ritmo e a moldura "stretch sênior" → "aprofundamento opcional". |
| 5 | Tamanho/grupos | **N=16 → 4 grupos de 4** (papéis rotativos). |

> **Carga confirmada:** **27h (9×3h, Seg/Qua/Sex, 22/06→10/07)**. A coordenação desta turma é o próprio docente, que decidiu por 27h — diverge das 20h da proposta comercial, mas está aprovado localmente. Registrado para rastreabilidade caso a PM Caixa questione.

---

## 3. Estratégia de middleware com degradação graciosa

Princípio de engenharia que sustenta os **dois cenários sem reescrever os projetos**: **programar contra as abstrações** (Spring Data Redis, Spring AMQP, Spring Kafka) e **trocar apenas a implementação/infra por _profile_ Maven**. O mesmo código-fonte roda no cenário ideal e no restrito; muda só de onde vem o broker/cache.

> **Pós-Aula 1:** o **cenário IDEAL (coluna Docker) está descartado** na prática — fica só como referência didática de "onde isto roda em produção / nos novos serviços Quarkus-Spring da Caixa" (ponte da modernização). O eixo real passou a ser **RESTRITO (Plano B) × conceitual (Plano C)**, decidido pela resolução no Nexus.

| Recurso | **Cenário IDEAL** (Docker + sites externos) | **Cenário RESTRITO** (sem Docker) — fallback pura-JVM | Fallback conceitual (se nem o artefato Nexus existir) |
|---|---|---|---|
| **Cache (Redis)** | `redis:7` via Docker / Testcontainers | **embedded-redis** (binário embarcado) **ou** cache local **Caffeine** atrás da abstração Spring Cache | `ConcurrentHashMap` como "cache" + Redis explicado como cache **distribuído** (conceito) |
| **Filas (RabbitMQ)** | RabbitMQ via Docker | **Apache Qpid Broker-J** (broker AMQP 100% Java, embarcável no processo) + Spring AMQP | `BlockingQueue` para ilustrar producer/consumer, depois mapear para AMQP |
| **Tópicos (Kafka)** | Kafka/Redpanda via Docker | **`EmbeddedKafkaBroker`** (`spring-kafka-test`, broker Kafka in-JVM) | Eventos in-process (`ApplicationEventPublisher`) para ilustrar pub/sub |
| **Contract test (PACT)** | Pact Broker via Docker | **Pact-JVM com pactfiles em disco** (consumidor gera, provedor verifica in-process) — **já é Docker-free** | — |
| **Replicação cloud** | — | Teórica em ambos (sem base legado para migrar) — pesquisa dirigida + estudo de caso | — |

**Consequência crítica:** os fallbacks pura-JVM (embedded-redis, Qpid, EmbeddedKafka, Pact-JVM, Caffeine) **são artefatos Maven** — só funcionam se o **Nexus Caixa os disponibilizar**. **Verificar isso é a tarefa nº 1 da Semana 0** (§4). Se faltarem, caímos no fallback conceitual e o hands-on do tema vira demonstração + exercício de design.

---

## 4. Semana 0 — Checklist de descoberta do ambiente

Sondagem a fazer **antes da Aula 1** (idealmente com 1 aluno-piloto ou TI da Caixa numa máquina real da rede). Resultado define qual plano (ideal/restrito) vale para cada tema. Saída → `ambiente/relatorio-semana-0.md`.

**Infra base**
- [ ] `java -version` (confirmar 21+), `mvn -version`.
- [ ] `settings.xml` aponta para o **Nexus Caixa**? Build offline resolve do Nexus?
- [ ] `docker version` / `docker run hello-world` — Docker existe e roda? (decide ideal × restrito).
- [ ] Há um **runtime de contêiner corporativo** alternativo (Podman, registry interno)?

**Resolução de artefatos no Nexus** (tentar `mvn dependency:get` de cada um)
- [ ] `spring-boot-starter-data-redis`, `spring-boot-starter-amqp`, `spring-kafka`
- [ ] `spring-kafka-test` (traz `EmbeddedKafkaBroker`)
- [ ] `org.apache.qpid:qpid-broker` (broker AMQP embarcável)
- [ ] artefato **embedded-redis** (ex.: `com.github.codemonstur:embedded-redis`) **ou** `caffeine`
- [ ] `au.com.dius.pact*` (Pact-JVM) · `org.testcontainers:*` (útil só se Docker existir)

**Rede e processos locais**
- [ ] É possível **abrir uma porta em localhost** (subir broker/cache local)? Firewall bloqueia?
- [ ] start.spring.io / Maven Central / GitHub — bloqueados? (confirma que tudo vem do Nexus)

**Colaboração e didática no Teams**
- [ ] **Breakout rooms** liberados? (essencial para trabalho em grupo)
- [ ] Alunos conseguem compartilhar tela / dar controle? (pair/mob programming)
- [ ] Onde os grupos versionam código? (GitLab/Bitbucket interno? compartilhamento por Teams?)
- [ ] IDE disponível na máquina Caixa (IntelliJ/VS Code/Eclipse)? Plugins liberados?

---

## 5. Os dois projetos

### 5.1. Projeto guiado em aula — **API de Comprovantes PIX** (projeto-referência do módulo)
Construído **incrementalmente, aula a aula**, em live/mob coding. É o fio condutor prático que materializa cada conceito. Mapa concept→incremento:

| Aula | Incremento no projeto PIX |
|---|---|
| 1 (DDD) | Separar domínios: emissão · gravação · consulta → microsserviços com bases segregadas |
| 2 (SAGA) | Garantir atomicidade emissão→gravação (orquestração) |
| 3 (Redis) | Cache na consulta de comprovante (Redis → fallback banco → 3 retries → 404) |
| 4–5 (RabbitMQ) | POST retorna `202` e publica na **fila**; consumidor grava o comprovante |
| 6–7 (Kafka) | **Tópico** de "comprovante gravado" + notificação, com `@RetryableTopic` |
| 8 (PACT) | Teste de contrato entre emissor e gravador |

### 5.2. Projeto final em grupo — **Variante temática Caixa** (avaliação)
Mesmos padrões do módulo, **domínio diferente** do PIX para avaliar transferência. Alimenta a **banca de defesa de arquitetura (Nível III)** das 21h complementares.

**Menu de temas proposto** (escolher 1 no brief; recomendação em negrito):
- **Central de Cobrança / Boletos** *(recomendado — proximidade justa do PIX)*: emissão de boleto → fila de registro → cache de consulta → tópico de baixa/liquidação + retry.
- **Antifraude em streaming** *(stretch, Kafka-pesado)*: stream de transações → análise → cache de score → fila de bloqueio.
- **Consolidador de extrato / Open Finance**: ingestão por tópicos → agregação → cache de consulta.

**Escopo escalável para turma de tamanho desconhecido** — o brief define um **CORE obrigatório** (entregável por grupo de 3) + **módulos opcionais** (cada um ≈ 1 microsserviço/feature) que **grupos maiores assumem**, mantendo o esforço proporcional ao tamanho do grupo:

- **CORE (todos):** ≥2 microsserviços com bases segregadas · 1 fila (producer/consumer) · 1 cache · 1 teste de contrato · README de arquitetura.
- **Opcionais (dial de escopo, +1 por integrante acima de 3):** SAGA de orquestração · tópico Kafka + `@RetryableTopic` · 2º par de contrato · resiliência/retry adicional · observabilidade básica (logs estruturados).
- **Formação de grupos (N=16 confirmado):** **4 grupos de 4**. Papéis rotativos: **arquiteto · dev mensageria · dev cache/dados · dev testes/contrato**. Distribuir os **~5 que já praticaram algo / foram meus alunos** como âncoras (1 por grupo) e equilibrar os juniores. Cada integrante acima de 3 (logo, o 4º de cada grupo) assume **1 módulo opcional**. Tolerar oscilação por faltas (no 1º dia faltaram 2).
- **Perfil de execução:** o brief traz uma matriz ideal × restrito (igual §3) para o grupo declarar em que cenário rodou e quais fallbacks usou — **a nota não penaliza** quem caiu no fallback por restrição de ambiente.

---

## 6. Plano de aulas (9 × 3h) — esqueleto

Segue o planejamento oficial do módulo na cadência 3×/semana, **adaptado** para (a) hands-on no projeto PIX e (b) bifurcação ideal/restrito por tema. Cada aula detalhada vira artefato em `plano-de-aulas/` (§9).

| Aula | Tema | Problema gerador | Entregável da aula |
|---|---|---|---|
| 1 | DDD + segregação de bancos | Separar domínios da solução de comprovantes em microsserviços | Domínios modelados + esqueleto multi-serviço |
| 2 | Replicação cloud (teórica) + **SAGA** (orquestração) | Garantir atomicidade emissão→gravação | Orquestração SAGA + pesquisa dirigida (replicação) |
| 3 | **Redis** / cache compartilhado | Consultar comprovante em cache com fallback no banco | Consulta com cache + retries → 404 |
| 4 | Comunicação assíncrona — **RabbitMQ** (producer/consumer) | Por que e quando assíncrono? primeiro envio/recebimento | Hello-world de fila + nota de cenários |
| 5 | **RabbitMQ** — fila de gravação | POST `202` + publicar na fila; consumidor grava | Gravação assíncrona ponta a ponta |
| 6 | **Kafka** — tópicos / pub-sub | Primeira mensagem em tópico | Tópico + publisher/subscriber |
| 7 | **Kafka** — produção/consumo + **retry** | Notificar "comprovante gravado" com `@RetryableTopic` | Tópico de notificação resiliente |
| 8 | **Contract testing** (PACT) + arquitetura testável | Testar entradas/saídas entre microsserviços | Contrato emissor↔gravador verificado |
| 9 | **Devolutiva** + defesas dos grupos | Auto-avaliação por rubrica | Feedback + banca do projeto final |

**Padrão de cada aula prática:** abertura (estudo de caso) → expositiva curta → **live/mob coding no PIX** → exercícios de fixação (banco do módulo) → pesquisa dirigida. **Plano A (ideal)** usa Docker; **Plano B (restrito)** usa o fallback pura-JVM da §3; **Plano C (conceitual)** se o artefato faltar no Nexus.

---

## 7. Avaliação

- **Rubricas do módulo:** Arquitetura de Microsserviços · Cloud replicado · Cache com Redis · Producer/Consumer · Comunicação assíncrona (filas/tópicos) · Arquitetura amigável a testes (+ atitudinais).
- **Instrumentos:** listas de exercícios (banco do módulo, individual) · participação · **projeto final em grupo** (peso principal) · **banca de defesa de arquitetura** (21h complementares).
- **Princípio anti-ambiente:** rubrica avalia **decisão arquitetural e domínio dos padrões**, não a infra que coube ao aluno — fallback por restrição não reduz nota.

---

## 8. Princípios didáticos para o ambiente Caixa/Teams

- **Resolver tudo via Nexus:** entregar um `settings.xml` pronto + projeto-semente que builda offline; nada de "baixe do Maven Central".
- **Material de apoio offline:** cheatsheets/diagramas em PDF no pacote (Redis, AMQP, Kafka, PACT), já que sites externos podem cair.
- **Live coding resiliente a rede:** ter um **branch/asset de cada incremento pronto** para colar se a demo travar; gravar a aula quando possível.
- **Grupos no Teams:** breakout rooms + repositório interno; se breakout não existir, mob programming guiado + canais por grupo.

---

## 9. Entregáveis desta missão e plano por turnos

> Atuação incremental. Este documento (plano de trabalho) é o **Turno 0**, já entregue. Os demais turnos produzem os artefatos — **nenhum gerado antes da sua validação deste plano.**

| Turno | Objetivo | Saída | Status |
|---|---|---|---|
| **0** | **Plano de trabalho da missão** (este doc) | `plano-de-trabalho.md` | ✅ |
| **1** | Checklist + modelo da **Semana 0** | `ambiente/checklist-semana-0.md` + `ambiente/settings.xml` | ✅ |
| **2** | **Plano de aulas detalhado** (9 aulas, A/B/C, método Ada, trilha IA) | `plano-de-aulas/README.md` + `aula-01..09.md` | ✅ |
| **3** | **Brief do projeto guiado (PIX)** + esqueleto Maven | `projetos/projeto-guiado-pix/` (brief + projeto multi-módulo, profiles A/B/C, **compila no Plano B**) | ✅ |
| **4** | **Brief do projeto final em grupo** (tema livre + rubrica + avaliação via Claude) | `projetos/projeto-final-grupo/brief.md` | ✅ |
| **5** | **Slides .pptx por aula** (marca Ada) | `plano-de-aulas/aula-0X-slides.pptx` (9 decks; Aula 1 = apresentação do curso) | ✅ |
| **6** | **Material do aluno por aula** (leitura complementar → PDF) | `plano-de-aulas/aula-0X-aluno.md` (9 textos) | ✅ |
| **7** | **Gabarito evolutivo do projeto guiado** (estado final por aula) | `projetos/projeto-guiado-pix/gabarito/aula-01..08/` (compila no Plano B) | ✅ |
| **8** | **Material de apoio offline** (cheatsheets/diagramas PDF) | `material-apoio-offline/` | ⬜ pendente |
| **9** | **Revisão pós-Aula 1** (perfil real, Plano B-first, N=16, Copilot, modernização) | `plano-de-aulas/*`, `plano-de-trabalho.md`, `projetos/*`, `ambiente/relatorio-semana-0.md` | ✅ |
| **10** | **Distribuição** (liberação antecipada: gabarito completo + materiais do aluno em PDF) — **migrou para o LMS** (disponível ~23/06); `distribuicao-offline/` vira backup | `distribuicao-offline/` + LMS | ✅ |

**Pendências de produção:** (a) **Nexus** — confirmar resolução dos artefatos de fallback (tarefa de casa dos alunos) para travar Plano B × C; (b) **material de apoio offline** (cheatsheets PDF); (c) conversão dos `aula-0X-aluno.md` e dos `.pptx` para PDF para o kit offline; (d) refinar grupos/personas com a **transcrição** da Aula 1. O esqueleto Maven já está no piso **Plano B**.

> **Nota slides:** decks gerados com o **template oficial Ada** (`Labs/assets/Ada _ Template PPT.pptx`), 2.5MB cada. Estrutura da Aula 1 espelha o deck-exemplo de apresentação de curso.

---

## 10. Estrutura de pastas (alvo)

```
planejamento-de-aulas/1705-be-jv-010/
├── plano-de-trabalho.md                # este doc (Turno 0)
├── ambiente/
│   ├── checklist-semana-0.md           # Turno 1
│   ├── settings.xml                    # modelo Nexus
│   └── relatorio-semana-0.md           # preenchido após a sondagem
├── plano-de-aulas/
│   └── aula-01..09.md                  # Turno 2 (cada aula com Plano A/B/C)
├── projetos/
│   ├── projeto-guiado-pix/             # Turno 3 (brief + semente Maven)
│   └── projeto-final-grupo/            # Turno 4 (brief + rubrica + matriz ideal×restrito)
└── material-apoio-offline/             # Turno 5 (PDFs)
```

---

## 11. Pendências / decisões em aberto

> **Atualizado após a Aula 1.** Itens resolvidos riscados; novos no fim.

- ~~**Tamanho da turma**~~ → **N=16** (4 grupos de 4). ✅
- ~~**Cenário ideal/restrito**~~ → **restrito confirmado (Plano B é o piso)**; Docker descartado. ✅
- **Nexus resolve os artefatos de fallback?** (embedded-redis/Caffeine, Qpid, `EmbeddedKafkaBroker`, Pact-JVM) — **tarefa de casa dos alunos**; decide Plano B × C por tema de mensageria. **Maior risco aberto.**
- ~~**Plataforma Ada indisponível**~~ → **LMS disponível desde ~23/06**; material subido, exercícios a partir da Aula 2. Offline vira backup. ✅
- **Carry-over de DDD:** a prática (event storming + esqueleto PIX) foi adiada da Aula 1 para a **abertura da Aula 2** — confirmar que cabe nos 180 min com o conteúdo de SAGA.
- **Tema do projeto final:** confirmar 1 do menu (§5.2) — recomendação: Central de Cobrança/Boletos. Preferir domínio do dia a dia dos grupos.
- **Breakout rooms no Teams:** confirma o formato do trabalho em grupo.
- **Transcrição da Aula 1:** aguardando da secretaria — usar para refinar personas/grupos e identificar quem são os juniores.
- **Carga horária:** 27h (escolhido, aprovado localmente) × 20h (proposta Caixa) — registrado para rastreabilidade.
```
