# Estado após a Aula 5 — Filas com RabbitMQ + idempotência e DLQ

**Foco:** rotear, confirmar e isolar mensagens que não dá para processar.

## O que funciona / o que mudou
- **Topologia formal:** `gravacao.ex` (direct) → `gravacao.q` (routing key `gravacao`).
- **Retry** com backoff (3 tentativas, no `application.yml` do gravador) para falha **transitória**.
- **DLQ**: esgotadas as tentativas, a mensagem vai para `gravacao.dlq` (via `gravacao.dlx`),
  **preservada para inspeção** — um listener da DLQ registra o ocorrido.
- **Idempotência** mantida no `gravar()` (redelivery seguro).

## Como provar a DLQ
Force o `gravar()` a lançar exceção para um id específico (mensagem "envenenada") e observe:
3 tentativas → cai na `gravacao.dlq` → log de aviso. Mensagem boa segue normal.

## Runtime do broker
Igual à Aula 4 (Plano A: RabbitMQ Docker; Plano B: Qpid embarcado/local).

## Próxima aula
Eventos: o gravador publica "comprovante gravado" num **tópico Kafka** para vários consumidores.
