# Aula 8 — Contract testing (PACT) e evals como contrato

**Data:** Qua 08/07 · **3h** · **Competência:** Arquitetura amigável a testes / contract testing
**Incremento no projeto PIX:** contrato executável entre emissor (consumer) e gravador (provider).
**Leitura do aluno:** `aula-08-aluno.md` · **Slides:** `aula-08-slides.pptx`

## Provocação (D-1, opcional)
*"Testar microsserviços só com o ambiente todo de pé é caro e lento. Como você ganha confiança de integração **sem** subir os 5 serviços juntos?"*

## 1. Problema gerador (PBL) (0–20)
*"Times diferentes mantêm emissor e gravador. Uma mudança no contrato de um quebra o outro em produção, e ninguém percebeu no merge. Como travar isso no CI?"*

## 2. Discussão de alto nível (20–45)
- **Pirâmide de testes** em distribuídos; por que **E2E não escala** e **contract test** preenche a lacuna.
- **Consumer-driven contracts:** o consumidor declara o que precisa; o provedor é verificado contra isso.
- **PACT:** o consumidor gera o *pact file*; o provedor verifica. Versionamento e *can-I-deploy*.
- **Arquitetura amigável a testes:** injeção de dependência, fronteiras explícitas, determinismo onde importa.
- **Ponte do legado:** o "contrato" que vivia num documento Word / planilha de layout de arquivo agora é **executável** e roda no pipeline.

## 3. Solução possível ao vivo (45–90)
Live coding: escrever o **teste do consumer** (emissor) gerando o pact; rodar a **verificação no provider** (gravador) contra o pact file em disco. Quebrar o contrato de propósito e ver o teste pegar.

### Ângulo IA/Agentes
- **Testar saída não-determinística:** não dá para `assertEquals` em texto de LLM — testa-se **schema, invariantes e propriedades** (o JSON tem os campos? respeita o range?).
- **Evals como contrato:** uma suíte de *evals* é o "contract test" de um componente de IA — define o comportamento aceitável e roda no CI.
- ***Structured outputs* / JSON schema:** forçar o modelo a um schema é o equivalente a um contrato de API — e dá para validar como contrato.

## Intervalo

## 4. Desafio de evolução — studio (100–150)
- **Base (todos):** escrever um contract test para um par de serviços do incremento e fazê-lo falhar ao mudar o contrato. Mob guiado; o Copilot pode gerar o esqueleto do teste — a turma **valida** se o pact realmente cobre o contrato.
- **Aprofundamento (opcional):** adicionar uma verificação de **schema/propriedade** (estilo eval) sobre uma resposta variável e integrar ao build (`mvn verify`).

## 5. Tempo de projeto em grupo (150–175)
Grupos: identificar o par de serviços mais crítico do projeto e escrever (ou planejar) o contract test dele. **Última janela de aula antes da banca.**

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Contrato bom é o que falha no seu CI, não na produção do outro time."*
- Provocação da Aula 9: *"Na banca, defenda **por que** sua arquitetura é assim — não só **o que** ela faz."*

## Infra (Plano B — pura-JVM)
- **B (o plano):** **Pact-JVM com pactfiles em disco** — já é **Docker-free**; verificação do provider in-process. **Tema mais seguro do módulo** (não depende de broker). Confirmar só que o Nexus resolve `au.com.dius.pact*`.
- *Referência (produção/modernização): Pact + Pact Broker via Docker para o `can-I-deploy` no pipeline.*

## Exercícios do banco
O banco não cobre contract testing especificamente — usar a discussão e o hands-on como avaliação formativa do tema. (Sem exercício do banco para distribuir nesta aula.)
