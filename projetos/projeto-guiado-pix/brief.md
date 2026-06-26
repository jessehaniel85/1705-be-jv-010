# Projeto Guiado em Aula — API de Comprovantes PIX

**O que é:** o projeto-referência do módulo BE-JV-010, **construído ao vivo** (live/mob coding) ao longo das aulas. Não é avaliado — é a **demonstração canônica** dos padrões que o projeto final exige. Apresentado na Aula 1.

## Problema
API que **armazena e consulta comprovantes de PIX** já efetivados (a efetivação não é nosso escopo — só emissão do comprovante e consulta).

- **POST /comprovantes** → valida campos obrigatórios → responde **`202 Accepted`** com `{ identificador_comprovante (UUID v4), data_hora_requisicao }` → publica a tarefa de gravação. Um **consumidor** grava no banco (H2 ou SGBD à escolha).
- **GET /comprovantes/{id}** → busca **primeiro no cache (Redis)**; miss → banco → popula cache → retorna; ausência real → **3 retentativas** → **`404`**.

Request body de referência (do planejamento do módulo): nome, tipo/numero do documento, agência, conta, dígito, valor, tipo/chave PIX destino, nome destino, identificação, data/hora.

## Como cresce, aula a aula

| Aula | Incremento |
|---|---|
| 1 (DDD) | 3 serviços (emissor · gravador · consulta), bases segregadas (H2 por serviço) |
| 2 (SAGA) | orquestração emissão→gravação com compensação + idempotência por chave |
| 3 (Redis) | cache na consulta: Redis → fallback banco → 3 retries → 404 |
| 4 (async) | POST responde 202 e **publica**; consumidor grava (desacoplado) |
| 5 (RabbitMQ) | fila real com ack manual, **retry e DLQ** |
| 6 (Kafka) | tópico `comprovante-gravado`; consumer groups (notificação, antifraude-mock) |
| 7 (resiliência) | notificação com `@RetryableTopic` + circuit breaker no gateway mock |
| 8 (PACT) | contract test emissor (consumer) ↔ gravador (provider) |

## Stack e perfis de execução
Spring Boot 3 (Java 21), Maven multi-módulo. **Programar contra as abstrações** (Spring Data Redis / Spring AMQP / Spring Kafka) e trocar infra por **profile**:

- **Plano A (ideal):** Docker/Testcontainers (redis, rabbitmq, kafka).
- **Plano B (restrito):** `embedded-redis`/Caffeine · **Qpid Broker-J** embarcado · `EmbeddedKafkaBroker` · Pact-JVM com pactfiles. Tudo puro-Java, só Java 21 + Maven + Nexus.
- **Plano C (conceitual):** event bus in-process (`ApplicationEventPublisher` / `BlockingQueue`) quando o artefato faltar no Nexus — foco no design.

> A definição de qual plano vale por tema sai da **Semana 0** (`ambiente/checklist-semana-0.md`).

## Papel didático
- É a **fonte da verdade** dos padrões: o grupo pode (e deve) se inspirar, **sem copiar tema**.
- Cada incremento fica em um **branch/tag por aula**, pronto para colar caso a demo ao vivo trave (resiliência de rede no Teams).
- Ao final, o repositório PIX é também o **exemplo de referência** contra o qual o grupo compara sua própria arquitetura.

## Estrutura sugerida do repositório-semente
```
projeto-guiado-pix/
├── pom.xml                      # parent multi-módulo
├── comprovante-emissor/         # REST, validação, 202, publica tarefa
├── comprovante-gravador/        # consumidor, persistência
├── comprovante-consulta/        # GET com cache + retries
├── shared-contracts/            # DTOs/eventos compartilhados + pacts
└── docs/
    └── adr/                     # decisões tomadas ao vivo (exemplo p/ os grupos)
```

> O esqueleto Maven (poms + módulos vazios + profiles A/B/C) é o entregável do Turno 3 do plano de trabalho — produzir após validar a Semana 0.
