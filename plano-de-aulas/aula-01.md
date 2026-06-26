# Aula 1 — Abertura + DDD (panorama) · ✅ REALIZADA (22/06)

> ## 📋 Registro do realizado (22/06)
> A aula **rodou, mas corrida**. O que de fato aconteceu:
> - **Apresentações individuais** de cada aluno (perfil, anos de experiência, stack atual) — base do perfil da turma agora em `plano-de-aulas/README.md` e `plano-de-trabalho.md` §1.
> - **Checklist de ambiente ao vivo** (parcial): os alunos começaram as verificações, mas tomou muito tempo; como nada de Docker/broker seria usado na Aula 1, o **restante virou tarefa de casa** (ver `ambiente/relatorio-semana-0.md`). Confirmado: **sem Docker/sandbox → Plano B**.
> - **DDD conceitual coberto com engajamento real** (não só panorama): problema gerador do PIX, noção de event storming (eventos/comandos/agregados/políticas/hotspots) e um exemplo forte de **agregado + imutabilidade** (lista de lançamentos da fatura), com participação de Renan, André, Relder e Sandy. Compartilhada a pasta `projetos/projeto-guiado-pix/requisitos/` (personas + transcrições do PO) via WhatsApp, com `aula-01-aluno.md` e o checklist.
> - **NÃO houve:** **código** (esqueleto Maven ao vivo), **breakout em grupo** e **leitura dos requisitos em aula**. É isso que migra para a Aula 2 — não a teoria de DDD, que já foi bem coberta.
>
> ## ➡️ Carry-over para a Aula 2 (24/06)
> A Aula 2 abre **aplicando** o DDD já discutido: breve recap → **traduzir para código** (esqueleto multi-módulo + entidade/VO) usando os requisitos → formar grupos — **antes** de SAGA. Não re-ensinar a teoria (a turma já a viu); o gap é a **prática/código**. Ver `aula-02.md` (Parte A).
>
> O conteúdo abaixo é o **plano original da Aula 1** — vale agora como **referência do que migra para a Aula 2** e do panorama que já foi dado.

---

**Data:** Seg 22/06 · **3h** · **Competência:** Arquitetura de Microsserviços / DDD
**Incremento no projeto PIX:** separar os domínios (emissão · gravação · consulta) em microsserviços com bases segregadas. *(adiado p/ Aula 2)*
**Leitura do aluno:** `aula-01-aluno.md` — capítulo completo de DDD (domínio/subdomínios, linguagem ubíqua, **entidades, value objects, agregados**, bounded context, context map, **event storming** e **dois cenários** ponta a ponta). **Já compartilhado via WhatsApp** como leitura de consolidação.
**Slides:** `aula-01-slides.pptx` — abertura do curso (11 slides) + âncoras de DDD (8 slides: problema do corte → dois níveis → blocos táticos → bounded context/context map → event storming → dois cenários → síntese).

> Aula dupla função: **abre o módulo** (contrato de convivência, método, NPS, apresenta os dois projetos e forma os grupos) **e** entra no 1º tema (DDD). Por isso a parte 1 começa com ~20 min de setup antes do problema. *(Na prática, setup + checklist + apresentações consumiram a aula; a parte hands-on de DDD migrou para a Aula 2.)*

> **Mapa bloco → material do aluno** (para o instrutor puxar a referência certa ao vivo): Discussão = seções 2–5 (domínio, blocos táticos, bounded context, context map); Solução ao vivo = seção 6 (event storming) + cenário 1 (seção 7); Desafio = cenário 2 (seção 8, fatura) + heurísticas (seção 9).

## Provocação (D-1, opcional, ~5 min)
No Teams: *"Você recebe um monólito Java de 400 mil linhas que faz emissão, fechamento e pagamento. Vão te pedir para 'quebrar em microsserviços'. Por onde você corta — e por onde **não** corta?"*

## Abertura do módulo (0–20)
- Contrato de convivência, método Ada (PBL + sala invertida adaptada), **como o tempo de aula funciona** (estudo e projeto **dentro** da aula).
- **Apresentação dos dois projetos:**
  - **Guiado (PIX):** o que construiremos juntos, aula a aula.
  - **Final em grupo (tema livre):** mostrar os **critérios de avaliação** (`projetos/projeto-final-grupo/brief.md`), deixar claro que **o time escolhe o tema** desde que cubra os critérios; PIX é só sugestão.
- **Formação de grupos** (alvo 3–4). Combinar onde versionam código (ver Semana 0).

## 1. Problema gerador (PBL) (20–40)
*"Os comprovantes de PIX hoje vivem dentro do core bancário. Precisamos extrair um serviço que **emite, grava e consulta** comprovantes sem acoplar ao core. Onde ficam as fronteiras?"*
Breakout rápido: cada grupo propõe um corte de domínio e justifica. **Não** entregar a resposta — ela emerge no event storming (bloco 3).

## 2. Discussão de alto nível (40–60)
Conduzir como construção do vocabulário que o aluno vai usar no resto do módulo. Pontos a cravar:
- **Domínio e subdomínios** (core / supporting / generic) — por que classificar orienta investimento e fronteira (material §2).
- **Linguagem ubíqua** e **bounded context** como ferramenta de corte (não é "dividir por camada técnica"); o mesmo termo ("Cliente") com modelos diferentes por contexto (§2, §4).
- **Blocos táticos**, rápido e concreto (§3): **entidade** (identidade/ciclo de vida — `Comprovante`), **value object** (imutável, igualdade por valor — `ChavePix`, `Dinheiro`), **agregado + raiz** (a invariante; **1 transação = 1 agregado**; o agregado **nunca** atravessa fronteira de serviço). Este último é o conceito que mais decide microsserviço.
- **Context map** (§5): customer/supplier, conformist, **ACL** (essencial ao integrar legado), OHS/published language.
- Trade-offs: 1 serviço grande × N pequenos; **acoplamento por dados** é o inimigo → **base por serviço**.
- **Quando NÃO usar microsserviços** (custo operacional, consistência distribuída) — provocar os seniores.

## 3. Solução possível ao vivo (60–90)
**Rodar um mini event storming** (15 min) antes de codar — é a técnica de descoberta que o material detalha (§6) e o que conecta "negócio falando" a "serviços desenhados":
- Despejar os **eventos** (🟧): *Comprovante Emitido → Aceito → Gravado → Consultado*; achar **comandos** (🟦) e **atores** (🧍); agrupar sob **agregados** (🟨); marcar **políticas** (🟪: "ao aceitar, gravar de forma assíncrona"; "ao gravar, notificar/antifraude/BI") e **hotspots** (🟥: "o cliente quer o comprovante na hora, mas a gravação é assíncrona" → re-tentativas na consulta).
- **Ler os agrupamentos** → bounded contexts → microsserviços: **emissor · gravador · consulta** (e, depois, notificação). Justificar pelos **perfis não-funcionais diferentes** (emissão aceita pico; gravação não pode perder; consulta tem volumetria alta).
- Então **live/mob coding:** esqueleto multi-módulo Maven com os 3 serviços, cada um com sua base (H2 segregado). Modelar a **entidade** `Comprovante` e ao menos um **value object** (`ChavePix`/`Dinheiro`) para o conceito de VO sair do slide e virar código.

> Repare ao vivo, em voz alta: as **políticas** do storming viram a **mensageria** das Aulas 4–6, e os **hotspots** viram os **requisitos de resiliência** das Aulas 5/7. O DDD já apontou onde estarão os problemas distribuídos do módulo inteiro.

### Ângulo IA/Agentes
- **Fronteira de agente = bounded context.** Um sistema multiagente bem desenhado separa responsabilidades como o DDD separa domínios; agente "faz tudo" falha como monólito.
- **LLM para extrair linguagem ubíqua** de entrevistas/documentos (gancho direto com os artefatos de requisitos do PO em `requisitos/`) — útil, mas o arquiteto valida (não terceiriza a decisão).
- ***Context engineering*** é o "DDD do prompt": definir o que entra no contexto do agente é decidir sua fronteira.

## Intervalo (90–100)

## 4. Desafio de evolução — studio (100–150)
- **Base:** acrescentar um 4º bounded context (ex.: `notificacao`) e justificar a fronteira a partir de uma **política** do storming.
- **Stretch:** rodar o método no **2º cenário** (fatura de cartão — material §8): identificar o agregado `Fatura`, a **invariante** "soma dos lançamentos = total", e por que `Lancamento` **tem** que viver dentro do agregado (senão exige transação distribuída). Desenhar o **context map** e apontar onde a consistência é **forte** (dentro do agregado) e onde é **eventual** (entre contextos).

## 5. Tempo de projeto em grupo (150–175)
Grupos: escolher (provisoriamente) **tema** e **domínio** do projeto final e rascunhar os **bounded contexts** via um mini event storming próprio. Entregam um parágrafo + diagrama de contextos no fim.

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Microsserviço não é tamanho de código — é fronteira de domínio com dono."*
- Leva pra casa: o context map do próprio projeto + a leitura de `aula-01-aluno.md`.
- Provocação da Aula 2: *"Se cada serviço tem sua base, quem garante que 'emitir' e 'gravar' não se contradigam quando um falha e o outro não?"*

## Infra (Plano B)
Nenhuma dependência externa nesta aula (só H2 in-memory) — roda em qualquer cenário. *(Hands-on migrou para a Aula 2.)*

## Exercícios do banco (consolidação)
DDD / bounded context: `BE_JV_010_01..05` (Basic) — consolidação formativa (a turma nunca praticou DDD); **via LMS** (já disponível). Aplicar na **Aula 2**.
