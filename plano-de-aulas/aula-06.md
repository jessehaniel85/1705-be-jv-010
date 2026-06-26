# Aula 6 — Tópicos / eventos com Kafka

**Data:** Sex 03/07 · **3h** · **Competência:** Tópicos / pub-sub / streams com Kafka
**Incremento no projeto PIX:** publicar o evento "comprovante gravado" em um tópico; múltiplos consumidores reagem.
**Leitura do aluno:** `aula-06-aluno.md` · **Slides:** `aula-06-slides.pptx`

## Provocação (D-1, opcional)
*"Fila e tópico parecem a mesma coisa para quem está chegando. Em uma frase: qual a diferença que muda a arquitetura?"*

## 1. Problema gerador (PBL) (0–20)
*"Depois que o comprovante é gravado, três áreas querem reagir: notificação, antifraude e BI. Não dá para o gravador conhecer todas. Como avisar 'aconteceu' sem acoplar?"*

## 2. Discussão de alto nível (20–45)
- **Fila × tópico:** fila = trabalho consumido **uma vez** por um worker; tópico = fato **lido por vários** consumer groups, **retido** no log.
- **Log de eventos**, offset, consumer group, partição/ordenação — o modelo mental do Kafka.
- **Event sourcing** e por que o "fato" retido permite **reprocessar a história** (reconstruir estado, plugar consumidor novo no passado).
- **Ponte do legado:** o "arquivo de movimento do dia" processado por vários jobs noturnos **já era** um log de eventos batch — Kafka é isso em streaming e contínuo.

## 3. Solução possível ao vivo (45–90)
Live coding: criar tópico `comprovante-gravado`; o gravador **produz** o evento; criar **dois consumer groups** (notificação e antifraude-mock) que reagem independentemente, cada um com seu offset.

### Ângulo IA/Agentes
- **Arquitetura event-driven para agentes:** agentes reagem a eventos no log em vez de serem chamados diretamente — desacopla e escala.
- **Event sourcing como memória de agente:** o histórico de eventos é a "memória" reproduzível do agente; dá auditoria e *replay*.
- **Streaming para ingestão/RAG:** novos documentos entram como eventos e atualizam o índice vetorial continuamente.

## Intervalo

## 4. Desafio de evolução — studio (100–150)
- **Base (todos):** plugar um **terceiro** consumer group (BI-mock) e mostrar que ele lê os eventos **desde o início** sem afetar os outros. Mob guiado.
- **Aprofundamento (opcional):** discutir **particionamento e ordenação** (por chave = id do comprovante) e o efeito no paralelismo; mostrar o que quebra se a chave for mal escolhida.

## 5. Tempo de projeto em grupo (150–175)
Grupos: identificar os **eventos de domínio** do projeto e quais consumidores reagem; decidir o que é fila (comando) e o que é tópico (fato).

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Fila entrega trabalho; tópico publica história."*
- Provocação da Aula 7: *"O consumidor de notificação caiu no meio. Quando volta, perdeu eventos? Reprocessa em duplicidade?"*

## Infra (Plano B — pura-JVM)
- **B (o plano):** **`EmbeddedKafkaBroker`** (`spring-kafka-test`) — broker Kafka in-JVM, sem Docker. Depende do Nexus publicar `spring-kafka-test`.
- **C (se o Nexus não publicar):** `ApplicationEventPublisher` (eventos in-process) para ilustrar pub/sub multi-consumidor (design do log/offset).
- *Referência (produção/modernização): Kafka/Redpanda via Docker.*

## Exercícios do banco
Kafka / tópicos: `BE_JV_010_31..36` (Basic) — consolidação formativa, **via LMS**.
