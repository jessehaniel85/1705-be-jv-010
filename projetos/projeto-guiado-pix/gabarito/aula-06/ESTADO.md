# Estado após a Aula 6 — Tópicos / eventos com Kafka

**Foco:** publicar um FATO para vários consumidores sem o produtor conhecê-los.

## O que funciona / o que mudou
- **gravador**: após persistir, publica `ComprovanteGravadoEvent` no tópico
  `comprovante-gravado` (chave = id → ordenação por comprovante; 3 partições).
- **novo módulo `comprovante-notificacao`** (porta 8084): `@KafkaListener` no consumer
  group `notificacao` reage ao evento (loga a notificação).
- Fila (RabbitMQ) continua entregando o **trabalho** de gravar; o tópico (Kafka) publica o **fato**.

## Vários consumidores
Antifraude e BI seriam novos consumer groups lendo o MESMO tópico, com offset próprio —
basta um novo `@KafkaListener(groupId="...")`, sem tocar no gravador.

## Runtime do broker
- **Plano A:** Kafka/Redpanda via Docker.
- **Plano B:** `EmbeddedKafkaBroker` (spring-kafka-test) / Kafka local. Compila sem broker;
  o fluxo ponta a ponta exige um broker no ar.

## Próxima aula
Resiliência no consumo de eventos: `@RetryableTopic` + circuit breaker.
