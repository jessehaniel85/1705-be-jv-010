# Estado após a Aula 7 — Resiliência: retry, circuit breaker e @RetryableTopic

**Foco:** reentregar sem inundar e falhar rápido quando a dependência está fora.

## O que funciona / o que mudou
- **notificacao**: o consumo usa **@RetryableTopic** (4 tentativas, backoff exponencial) —
  o spring-kafka cria os tópicos de retry e a **DLT** automaticamente; `@DltHandler` registra o que esgotou.
- A chamada ao **gateway externo** tem **circuit breaker** (Resilience4j, instância `notificacao-gateway`)
  com **fallback** — aberto o circuito, não martela a dependência.
- Para exercitar: descomente o `throw` em `NotificacaoGateway.enviar` e observe retry → DLT + abertura do circuito.

## Runtime
Requer Kafka no ar (Plano A Docker / Plano B EmbeddedKafka ou local). Compila sem broker.

## Próxima aula
Contract testing (PACT) entre emissor (consumer) e gravador (provider).
