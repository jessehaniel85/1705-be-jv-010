# Aula 7 — Resiliência: retry, circuit breaker e `@RetryableTopic`

**Data:** Seg 06/07 · **3h** · **Competência:** Resiliência e retry em microsserviços
**Incremento no projeto PIX:** notificação resiliente a partir do tópico, com retry e circuit breaker.
**Leitura do aluno:** `aula-07-aluno.md` · **Slides:** `aula-07-slides.pptx`

## Provocação (D-1, opcional)
*"Retry ingênuo em cima de um serviço já sobrecarregado é como dar mais soco em quem está caído. Como você tenta de novo sem piorar o incidente?"*

## 1. Problema gerador (PBL) (0–20)
*"O serviço de notificação depende de um gateway externo que oscila. Queremos reentregar sem inundar, e parar de tentar quando claramente está fora."*

## 2. Discussão de alto nível (20–45)
- **Retry com backoff exponencial + jitter**; por que retry fixo causa *retry storm*.
- **Idempotência** como pré-condição de qualquer retry seguro.
- **Circuit breaker** (closed/open/half-open): falhar rápido para proteger o sistema e a dependência.
- **Timeout e bulkhead**: isolar recursos para uma falha não derrubar o todo.
- **Ponte do legado:** o "reprocessamento do batch que deu erro" e os "limites de retentativa" do job scheduler são os ancestrais disso — agora reativos e por requisição.

## 3. Solução possível ao vivo (45–90)
Live coding: aplicar **`@RetryableTopic`** no consumidor do tópico de notificação (retry com backoff + tópico de DLT); adicionar **circuit breaker** na chamada ao gateway mockado; demonstrar abertura do circuito.

### Ângulo IA/Agentes
- **APIs de LLM são instáveis por natureza** (rate limit, timeout, 5xx) — resiliência deixa de ser opcional.
- **Fallback de modelo:** circuito abriu no modelo primário → cair para um secundário/menor (ou resposta degradada) em vez de falhar.
- **Idempotência com saída não-determinística:** reexecutar um passo de IA pode gerar resultado diferente — controlar por id de tarefa e *caching* da resposta.

## Intervalo

## 4. Desafio de evolução — studio (100–150)
- **Base (todos):** configurar `@RetryableTopic` com backoff e ver a mensagem percorrer os tópicos de retry até a DLT. Mob guiado.
- **Aprofundamento (opcional):** combinar retry + circuit breaker + idempotência e demonstrar comportamento sob falha intermitente **e** sob falha permanente; medir reentregas.

## 5. Tempo de projeto em grupo (150–175)
Grupos: mapear as **dependências instáveis** do projeto e definir a política de resiliência de cada uma (timeout/retry/breaker/fallback).

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Resiliência é decidir como falhar de propósito, antes que o sistema decida por você."*
- Provocação da Aula 8: *"Você mudou o JSON que o gravador espera. Como descobrir que quebrou o emissor **antes** de subir em produção?"*

## Infra (Plano B — pura-JVM)
- **B (o plano):** `EmbeddedKafkaBroker` + `@RetryableTopic` + Resilience4j (puro-Java, sem Docker).
- **C (se o Nexus não publicar o broker B):** retry/breaker sobre o event bus in-process (design da DLT e do backoff).
- *Referência (produção/modernização): Kafka via Docker + Resilience4j.*

## Exercícios do banco
Retry / resiliência: `BE_JV_010_37..40` (Basic) — consolidação formativa, **via LMS**.
