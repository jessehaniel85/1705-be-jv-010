# Aula 4 — Comunicação assíncrona: producer/consumer

**Data:** Seg 29/06 · **3h** · **Competência:** Comunicação assíncrona (conceito producer/consumer)
**Incremento no projeto PIX:** desacoplar a emissão da gravação — POST responde `202` e publica; um consumidor grava.
**Leitura do aluno:** `aula-04-aluno.md` · **Slides:** `aula-04-slides.pptx`

## Provocação (D-1, opcional)
*"Síncrono é mais simples de raciocinar. Quando vale pagar o preço da complexidade do assíncrono — e quando é só modismo?"*

## 1. Problema gerador (PBL) (0–20)
*"A gravação no banco é lenta e às vezes o banco está fora. Não dá para o cliente esperar nem perder o comprovante. Como aceitar agora e processar depois?"*

## 2. Discussão de alto nível (20–45)
- **Mensagem × evento** (comando "grave isto" vs fato "isto aconteceu") — decisão de design, não detalhe.
- Garantias de entrega: **at-most-once / at-least-once / exactly-once** — e por que **exactly-once é um mito** na prática (você implementa **at-least-once + idempotência**).
- Acoplamento temporal: produtor e consumidor não precisam estar vivos juntos.
- **Ponte do legado:** quem usou **IBM MQ / JMS / MQSeries** já fez producer/consumer — muda o ferramental, não o conceito. Bom momento para quem vem do legado liderar a explicação (valoriza a experiência deles).
- **Ponte da modernização:** é o mesmo padrão que os serviços Quarkus/Spring novos usam para desacoplar — só troca o broker.

## 3. Solução possível ao vivo (45–90)
Live coding: a emissão valida, responde **`202`** com o id e **publica** a tarefa de gravação; um **consumidor** recebe e grava. Começar com o broker "pronto" para focar no padrão.

### Ângulo IA/Agentes
- **Desacoplar inferência cara:** chamada de LLM (segundos, instável, custosa) não deve ficar no caminho síncrono da request — vira **tarefa em fila**.
- **Filas de tarefas de agentes:** orquestrador publica tarefas; *workers* (agentes) consomem — escala e isola falha.
- **Human-in-the-loop assíncrono:** passo que exige aprovação humana é naturalmente uma mensagem que espera resposta.

## Intervalo

## 4. Desafio de evolução — studio (100–150)
- **Base (todos):** garantir que o consumidor seja **idempotente** (reprocessar a mesma mensagem não duplica o comprovante). Mob guiado.
- **Aprofundamento (opcional):** simular consumidor lento/caído e observar o acúmulo; discutir **backpressure** e o que acontece com a mensagem não consumida.

## 5. Tempo de projeto em grupo (150–175)
Grupos: identificar no projeto o ponto que **deve** ser assíncrono e modelar mensagem × evento; registrar a garantia de entrega escolhida.

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Assíncrono não é 'mais rápido' — é 'não falhar junto'."*
- Provocação da Aula 5: *"E quando a gravação falha 3 vezes? A mensagem some?"*

## Infra (Plano B — pura-JVM)
- **B (o plano):** **Apache Qpid Broker-J** embarcado no processo (AMQP puro-Java) + Spring AMQP — **depende do Nexus publicar `qpid-broker`** (e da exclusão de `com.sleepycat:je`; ver checklist).
- **C (se o Nexus não publicar):** `BlockingQueue` in-process para ilustrar producer/consumer, depois mapear para AMQP (design). **Este é o tema de maior risco de cair em C** — ter o fallback pronto.
- *Referência (produção/modernização): RabbitMQ via Docker. O código Spring AMQP é o mesmo.*

## Exercícios do banco
Comunicação assíncrona / producer-consumer: `BE_JV_010_19..24` (Basic) — consolidação formativa, **via LMS**.
