# Aula 3 — Cache com Redis (e semantic caching)

**Data:** Sex 26/06 · **3h** · **Competência:** Cache compartilhado com Redis
**Incremento no projeto PIX:** consulta de comprovante com cache (Redis → fallback no banco → 3 retentativas → 404).
**Leitura do aluno:** `aula-03-aluno.md` · **Slides:** `aula-03-slides.pptx`

## Provocação (D-1, opcional)
*"Cache mal feito é fonte de bug difícil: dado velho, invalidação errada, 'funciona na minha máquina'. Qual foi o pior bug de cache que você já viu?"*

## 1. Problema gerador (PBL) (0–20)
*"A consulta de comprovante é lida muito mais do que escrita e está martelando o banco. Como aliviar sem servir dado inconsistente?"*

## 2. Discussão de alto nível (20–45)
- **Cache local × distribuído** (por que Redis e não um `HashMap`): consistência entre réplicas, escala horizontal.
- **Estratégias:** cache-aside (lazy) × write-through × write-behind; TTL; **as duas coisas difíceis** (nomear e **invalidar**).
- **Stampede / thundering herd** e como mitigar.
- **Ponte do legado:** muita app legado "cacheia" em tabela de banco ou em memória do app server (sessão pegajosa); Redis tira o estado do nó e resolve a replicação de sessão.
- **Ponte da modernização:** nos serviços Quarkus/Spring novos da Caixa, cache distribuído é o que permite escalar horizontalmente sem sessão pegajosa.

## 3. Solução possível ao vivo (45–90)
Live coding: consulta busca **primeiro no cache**; miss → banco → **popula o cache** e retorna; ausência real → **3 retentativas** → `404`. TTL e chave por id do comprovante.

### Ângulo IA/Agentes
- **Semantic caching:** cachear respostas de LLM por **similaridade de embedding** (não por chave exata) corta custo e latência de inferência — Redis tem suporte a busca vetorial.
- **Redis como vector store** para **RAG**: o mesmo Redis que cacheia comprovante pode guardar embeddings.
- Custo real: uma chamada de LLM é ordens de magnitude mais cara que uma leitura de banco — cache deixa de ser "otimização" e vira **requisito econômico**.

## Intervalo

## 4. Desafio de evolução — studio (100–150)
- **Base (todos):** adicionar TTL e medir hit/miss (log simples) no incremento. Mob guiado.
- **Aprofundamento (opcional):** implementar **invalidação** ao atualizar um comprovante e demonstrar o cenário de **stampede**; propor mitigação (lock, TTL jitter ou *request coalescing*).

## 5. Tempo de projeto em grupo (150–175)
Grupos: decidir **o que** cachear no projeto e **qual estratégia**; registrar invalidação e TTL como decisão.

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Cache não é 'guardar tudo' — é decidir o que pode estar um pouco velho."*
- Provocação da Aula 4: *"O gravador é mais lento que a emissão. Por que fazer a emissão esperar por ele?"*

## Infra (Plano B — pura-JVM)
- **B (o plano):** `embedded-redis` (binário embarcado) **ou** Caffeine atrás da abstração Spring Cache — mesmo código de serviço, sem Docker.
- **C (se o Nexus não publicar o artefato B):** `ConcurrentHashMap` como cache local + Redis explicado como cache **distribuído** (demo conceitual + design da invalidação).
- *Referência (produção/modernização): `redis:7` via Docker/Testcontainers — onde isto roda nos serviços novos da Caixa. O código contra a abstração Spring Cache é o mesmo.*

## Exercícios do banco
Redis / cache: `BE_JV_010_13..18` (Basic) — consolidação formativa, **via LMS**.
