# Aula 5 — Filas com RabbitMQ + idempotência e DLQ

**Data:** Qua 01/07 · **3h** · **Competência:** Filas com RabbitMQ (criação, produção, consumo)
**Incremento no projeto PIX:** fila de gravação completa, com tratamento de falha (retry + dead-letter).
**Leitura do aluno:** `aula-05-aluno.md` · **Slides:** `aula-05-slides.pptx`

## Provocação (D-1, opcional)
*"Uma mensagem 'envenenada' (sempre falha ao processar) pode travar sua fila inteira. Como você isola ela sem perder o dado?"*

## 1. Problema gerador (PBL) (0–20)
*"A gravação falha de forma intermitente (banco oscila) e às vezes de forma permanente (mensagem malformada). Os dois casos não podem ser tratados igual. Como?"*

## 2. Discussão de alto nível (20–45)
- Anatomia do RabbitMQ: **exchange, binding, routing key, queue** — por que não se publica "direto na fila".
- Tipos de exchange (direct/topic/fanout) e quando cada um.
- **Retry × Dead Letter Queue (DLQ):** falha transitória se reprocessa; falha permanente vai para a DLQ para inspeção — **nunca** se descarta silenciosamente.
- **Ack/nack** e o que acontece no crash do consumidor (redelivery).
- **Ponte do legado:** DLQ é a "fila de exceção / arquivo de rejeitados" que o batch já tinha — agora explícita e observável.

## 3. Solução possível ao vivo (45–90)
Live coding: declarar exchange + fila + binding; produtor publica; consumidor com **ack manual**; configurar **DLQ** e **retry** com limite. Demonstrar a mensagem indo para a DLQ após N falhas.

### Ângulo IA/Agentes
- **Work queue para jobs de IA:** múltiplos *workers* consomem chamadas de LLM em paralelo, respeitando **rate limit** do provedor (a fila vira regulador de vazão).
- **Backpressure** quando o LLM é o gargalo: a fila cresce → sinal para escalar *workers* ou throttlar a entrada.
- **DLQ para chamadas de IA que falham** (timeout, conteúdo recusado) — inspeção humana em vez de perda silenciosa.

## Intervalo

## 4. Desafio de evolução — studio (100–150)
- **Base (todos):** configurar a DLQ e provocar uma mensagem envenenada até cair nela. Mob guiado.
- **Aprofundamento (opcional):** implementar **retry com backoff** antes da DLQ e tornar o consumidor idempotente sob redelivery; medir o efeito.

## 5. Tempo de projeto em grupo (150–175)
Grupos: desenhar a topologia de filas do projeto (exchanges, bindings, DLQ) e quem produz/consome o quê.

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Mensagem nunca 'some' por acidente — ou foi processada, ou está numa DLQ por decisão."*
- Provocação da Aula 6: *"E se vários serviços precisarem reagir ao MESMO fato, sem o produtor saber quem são?"*

## Infra (Plano B — pura-JVM)
- **B (o plano):** Qpid Broker-J embarcado + Spring AMQP (DLQ via argumentos de fila) — depende do Nexus (mesmo artefato da Aula 4).
- **C (se o Nexus não publicar):** `BlockingQueue` + fila de "rejeitados" simulando DLQ (design do retry/backoff).
- *Referência (produção/modernização): RabbitMQ via Docker com painel de management.*

## Exercícios do banco
RabbitMQ / filas: `BE_JV_010_25..30` (Basic) — consolidação formativa, **via LMS**.
