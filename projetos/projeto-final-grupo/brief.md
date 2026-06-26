# Projeto Final em Grupo — BE-JV-010 (Nível III)

**Formato:** **4 grupos de 4** (N=16 confirmado) · **tema livre** · defesa na **banca da Aula 9 (10/07)**.
**Apresentado na Aula 1; rascunho de tema/contextos retomado na Aula 2.** Desenvolvido majoritariamente **dentro do tempo de aula** (2ª metade de cada encontro) + bancada na Aula 9.

---

## 1. Princípio: o tema é livre, os critérios são a amarra

O grupo **escolhe o domínio e o produto** que quiser. O que **não** é negociável são os **critérios de avaliação** (§4): o projeto precisa **exercitar os padrões do módulo de forma justificada**. A API de Comprovantes PIX (projeto guiado) é apenas **uma sugestão de referência** — copiá-la não é o objetivo; transferir os padrões para um problema próprio é.

> Por que tema livre: autonomia eleva o engajamento (e o NPS) e força **decisão arquitetural real** — que é o que a banca de Nível III avalia. Como a turma é **mid-level e não praticou DDD**, o CORE (§2) é dimensionado para ser **alcançável por todos** com mob guiado; o desafio vem da **decisão**, não do volume.

### Sugestões de tema (escolher uma OU propor a sua)
- **Central de cobrança / boletos** — emissão → fila de registro → cache de consulta → tópico de baixa/liquidação.
- **Antifraude em streaming** — transações em tópico → análise → cache de score → fila de bloqueio (Kafka-pesado).
- **Consolidador de extrato / Open Finance** — ingestão por tópicos → agregação → cache de consulta.
- **Qualquer domínio do dia a dia do grupo na Caixa** que justifique os padrões — preferível, pois aproxima do trabalho real.

---

## 2. Escopo por grupo (N=16 → 4 grupos de 4)

**CORE é obrigatório** para todo grupo. Como os grupos têm **4 integrantes**, cada grupo assume **1 item opcional** (o 4º integrante acima da base de 3). Distribuir os ~5 alunos com mais bagagem como **âncoras** (1 por grupo).

**CORE (todo grupo entrega):**
- ≥ 2 microsserviços com **bases segregadas** (decomposição por domínio).
- **1 fluxo assíncrono** com fila (producer/consumer) e **consumidor idempotente**.
- **1 cache** com estratégia e invalidação explícitas.
- **1 contrato executável** (contract test) entre dois serviços.
- **ADRs** documentando as decisões + **README** de arquitetura.
- **Roda** no perfil **B (pura-JVM)** ou, se faltar artefato no Nexus, **C** — declarado. (Docker/Plano A não se aplica neste ambiente.)

**Opcionais (+1 por integrante acima de 3):**
- **SAGA** de orquestração ou coreografia com compensação.
- **Tópico Kafka** com múltiplos consumer groups.
- **Resiliência** avançada (`@RetryableTopic` + circuit breaker + DLQ).
- **2º par de contract test** / verificação estilo *eval*.
- **Observabilidade** básica (logs estruturados + correlação de id entre serviços).
- **Uso documentado e crítico de IA** no design/desenvolvimento (vira evidência do critério 8).

**Grupos:** **4 grupos de 4** (N=16). Papéis rotativos: arquiteto · dev mensageria · dev cache/dados · dev testes/contrato. Tolerar oscilação por faltas (2 faltaram no 1º dia); se um grupo ficar com 3 num dia, ele cobre só o CORE naquele encontro.

---

## 3. Contrato de entrega (estrutura padronizada do repositório)

**Obrigatória** — é o que permite avaliação consistente e **automatizável via Claude**. Repositório (git interno Caixa) com:

```
<projeto-do-grupo>/
├── README.md                # tema, problema, visão de arquitetura, como rodar (perfil B/C)
├── AVALIACAO.md             # auto-avaliação: cada critério → evidência (arquivo/commit). Ver §5.
├── docs/
│   ├── adr/                 # 1 arquivo por decisão (ADR-001-..., formato curto)
│   └── arquitetura.md       # diagrama de contextos + fluxo de eventos/filas
├── <servico-1>/ ... <servico-N>/   # um módulo por microsserviço, base segregada
├── shared-contracts/        # DTOs/eventos/pacts compartilhados
└── pom.xml                  # multi-módulo, profiles B/C (pura-JVM)
```

**Regras que tornam a entrega avaliável:**
- `AVALIACAO.md` é **obrigatório** e mapeia **cada critério → evidência** (caminho de arquivo, classe, teste ou commit). Sem ele, a nota não considera o que não for encontrado.
- O grupo declara no README o **perfil de execução (B/C)** em que rodou e os **fallbacks** usados. **Não há penalização por restrição de ambiente.**
- Commits ao longo das semanas (não um único dump) — evidência de processo.

---

## 4. Critérios de avaliação (rubrica)

Pesos somam 100. Cada critério tem 4 níveis: **0 Insuficiente · 1 Básico · 2 Proficiente · 3 Avançado**. Nota do critério = `(nível/3) × peso`. A coluna **Evidência esperada** diz onde procurar (no código e no `AVALIACAO.md`) — serve para a banca **e** para a avaliação via Claude.

| # | Critério | Peso | Evidência esperada |
|---|---|:---:|---|
| 1 | **Decomposição de domínio** (bounded contexts, bases segregadas, sem acoplamento por dados) | 15 | módulos por serviço; bases separadas; `docs/arquitetura.md`; ADR de corte |
| 2 | **Comunicação assíncrona** (producer/consumer, mensagem×evento, garantia de entrega) | 15 | config de fila/broker; consumidor; README declara a garantia |
| 3 | **Idempotência e consistência** (consumidor idempotente; SAGA/compensação/outbox quando aplicável) | 12 | chave de idempotência; teste que reprocessa sem duplicar; ADR |
| 4 | **Cache** (estratégia, TTL, invalidação, fallback) | 10 | camada de cache; lógica de invalidação; ADR da estratégia |
| 5 | **Resiliência** (retry c/ backoff, DLQ, timeout, circuit breaker) | 12 | config de retry/DLQ/breaker; teste de falha transitória × permanente |
| 6 | **Testabilidade** (contract test executável; testes rodam em perfil sem Docker) | 13 | pact files + verificação; `mvn verify` verde; testes de integração pura-JVM |
| 7 | **Decisões arquiteturais** (ADRs com trade-offs reais, não descrição) | 13 | `docs/adr/` com alternativas consideradas e justificativa |
| 8 | **Uso crítico de IA** (como usaram IA no design/dev; o que validaram à mão) | 5 | seção no README/AVALIACAO; reflexão honesta, não marketing |
| 9 | **Execução comprovada** (roda no perfil declarado; instruções reproduzíveis) | 5 | README "como rodar"; perfil A/B/C declarado; evidência de execução |

**Atitudinais / banca (à parte, para participação):** clareza na defesa, resposta à arguição, colaboração no grupo, avaliação entre pares.

> **Princípio anti-ambiente (reforço):** os critérios avaliam **domínio do padrão e qualidade da decisão**, não a infra disponível. Um grupo que rodou tudo no **Plano C** e **justificou bem** pode tirar nota máxima.

---

## 5. `AVALIACAO.md` — template que o grupo preenche

```markdown
# Auto-avaliação — <nome do projeto>
Grupo: <integrantes e papéis>
Tema/domínio: <descrição em 2 linhas>
Perfil de execução: B | C  ·  Fallbacks usados: <quais>

## Evidências por critério
1. Decomposição de domínio — nível auto-atribuído: X
   Evidência: <serviços/módulos, bases, docs/arquitetura.md, ADR-00X>
2. Comunicação assíncrona — X
   Evidência: <arquivos, garantia de entrega declarada>
3. Idempotência e consistência — X
   Evidência: <chave, teste, ADR>
4. Cache — X / 5. Resiliência — X / 6. Testabilidade — X
   Evidência: <...>
7. Decisões arquiteturais — X  → ver docs/adr/
8. Uso crítico de IA — X
   Como usamos IA e o que validamos manualmente: <texto honesto>
9. Execução — X
   Como rodar: <comandos>; perfil declarado.

## Opcionais entregues
<lista, se grupo > 3 pessoas>
```

---

## 6. Avaliação via Claude (para o docente, pós-banca)

A estrutura padronizada (§3) foi desenhada para que **cada repositório seja avaliável pelo Claude Code** contra a rubrica (§4). Fluxo sugerido:

1. Clonar/abrir cada repositório de grupo localmente.
2. Rodar o Claude apontando para a pasta do grupo com um prompt como:

   > *"Avalie este repositório contra a rubrica em `projetos/projeto-final-grupo/brief.md` §4. Para cada um dos 9 critérios: leia o `AVALIACAO.md`, confirme a evidência no código (cite arquivo:linha), atribua nível 0–3 com justificativa e calcule a nota ponderada. Aponte critérios sem evidência localizável. Não penalize por perfil de execução A/B/C. Gere uma tabela final + nota total + 3 pontos fortes e 3 a melhorar."*

3. Claude produz: tabela critério→nível→nota com citações de código, total, e feedback. O docente **revisa e ajusta** (a nota final é do docente; o Claude acelera e padroniza).
4. Consolidar com as observações da banca e as auto-avaliações.

> Por isso o `AVALIACAO.md` e os ADRs são obrigatórios: dão ao Claude (e à banca) **onde olhar**. Entrega sem o mapa de evidências é avaliada só pelo que for encontrável.

---

## 7. Banca (Aula 9 · 10/07)
Cada grupo: demo (declarando perfil B/C) + **defesa das decisões** (não só "o que faz", mas "por que assim") + arguição do docente e dos pares + reflexão sobre uso de IA. Tempo por grupo ≈ `130 / nº de grupos` min; muitos grupos → rodadas + avaliação cruzada.

## 8. Cronograma de maturação (dentro das aulas)
- **Aula 1:** tema + bounded contexts (rascunho).
- **Aulas 2–8:** a cada encontro, ~25 min de studio em grupo aplicando o padrão da aula ao projeto + 1 ADR.
- **Aula 8:** última janela antes da banca (foco em contrato e em fechar o `AVALIACAO.md`).
- **Aula 9:** banca + entrega final do repositório.
