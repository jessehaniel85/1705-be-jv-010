# Aula 2 — DDD na prática (carry-over) + Consistência distribuída e SAGA

**Data:** Qua 24/06 · **3h** · **Competência:** DDD (prática) → Consistência distribuída / padrão SAGA
**Incremento no projeto PIX:** (A) separar os domínios em microsserviços com bases segregadas; (B) garantir atomicidade do fluxo emissão→gravação (orquestração).
**Leitura do aluno:** `aula-02-aluno.md` (SAGA) + `aula-01-aluno.md` (DDD, já no WhatsApp) · **Slides:** `aula-02-slides.pptx` + âncoras de DDD do `aula-01-slides.pptx`.

> **Por que esta aula tem duas partes.** A prática de DDD foi **adiada da Aula 1** (que ficou só no panorama — ver `aula-01.md`). Como **ninguém na turma praticou DDD** e ele é a fundação dos microsserviços que o SAGA vai coordenar, **abrimos com o hands-on de DDD** (Parte A) e só então entramos em SAGA (Parte B). **É apertado de propósito** — se a Parte A render, comprimir a Parte B para **demo + conceito de compensação** e deixar o hands-on profundo de SAGA encostar na Aula 3 (que tem folga). Não sacrificar a Parte A: sem bounded contexts claros, SAGA não faz sentido.

---

## PARTE A — DDD na prática (carry-over da Aula 1)

### Recap relâmpago + provocação (0–10)
- Retomar em 1 slide o panorama dado na Aula 1 (domínio/subdomínio, linguagem ubíqua, bounded context, blocos táticos). Não re-expor tudo — a leitura `aula-01-aluno.md` está no WhatsApp para consolidar.
- Provocação: *"Vocês receberam as transcrições das reuniões com o PO (pasta `requisitos/`). Onde estão as fronteiras escondidas naquele texto?"*

### A1. Mini event storming do PIX (10–35)
Conduzir **ao vivo, com a turma** (a técnica que o material detalha — `aula-01-aluno.md` §6):
- Despejar **eventos** (🟧): *Comprovante Emitido → Aceito → Gravado → Consultado*; achar **comandos** (🟦) e **atores** (🧍); agrupar sob **agregados** (🟨); marcar **políticas** (🟪: "ao aceitar, gravar de forma assíncrona"; "ao gravar, notificar/antifraude/BI") e **hotspots** (🟥: "o cliente quer o comprovante na hora, mas a gravação é assíncrona").
- **Ler os agrupamentos** → bounded contexts → microsserviços: **emissor · gravador · consulta**. Justificar pelos **perfis não-funcionais diferentes** (emissão aceita pico; gravação não pode perder; consulta tem volumetria alta).
- **Copilot agent (mão-na-massa):** pedir ao Copilot para extrair a **linguagem ubíqua** e candidatos a entidade/VO de um arquivo de `requisitos/` — e a turma **critica**: o que ele acertou, o que inventou, qual fronteira ele errou. Mostra "LLM ajuda, arquiteto decide".

### A2. Esqueleto multi-módulo ao vivo (35–55)
Live/mob coding (**mob guiado**, ritmo de quem nunca fez): esqueleto Maven com os 3 serviços, cada um com **base H2 segregada**. Modelar a **entidade** `Comprovante` e ao menos um **value object** (`ChavePix`/`Dinheiro`) — VO sai do slide e vira código. Apontar em voz alta: as **políticas** do storming viram a **mensageria** das Aulas 4–6; os **hotspots** viram a **resiliência** das Aulas 5/7. *(O esqueleto está em `projetos/projeto-guiado-pix/inicio/` — colar branch pronto se a digitação ao vivo travar.)*

> **Ponte:** este esqueleto com 3 bases segregadas é **exatamente o cenário** que a Parte B precisa — "emitir" e "gravar" em serviços/bases diferentes, sem transação única.

---

## PARTE B — Consistência distribuída e SAGA

## 1. Problema gerador (PBL) (55–70)
*"A emissão do comprovante respondeu 202, mas a gravação falhou. O cliente tem um comprovante que não existe no banco. Como evitar e como compensar?"* — **é um hotspot que o storming da Parte A já marcou.**

## 2. Discussão de alto nível (70–90)
- **Por que 2PC morreu** em sistemas modernos (bloqueio, disponibilidade, falácias de distribuídos).
- **Consistência eventual** e o que ela custa ao negócio (bancário: quando é aceitável? quando não?).
- **SAGA**: orquestração × coreografia; **ações compensatórias**; idempotência como pré-requisito.
- **Outbox pattern** como peça que falta para "publicar evento E gravar" atomicamente.
- **Ponte do legado:** a "orquestração via stored procedure / job control" do mainframe é uma SAGA implícita — só que escondida e sem compensação explícita.
- **Ponte da modernização:** é assim que os novos serviços Quarkus/Spring da Caixa coordenam fluxos sem 2PC.

## Intervalo (90–100)

## 3. Solução possível ao vivo (100–135)
Live coding (**mob guiado**): **orquestrador** que coordena emissão→gravação com passo de **compensação** (estornar/marcar inválido) se a gravação falhar. Tornar cada passo **idempotente** (chave do comprovante). Reaproveita o esqueleto da Parte A.

> **Se a Parte A rendeu e o tempo apertou:** reduzir aqui para **demo do orquestrador + o conceito de compensação no quadro**, e deixar o aluno completar no desafio/Aula 3. Não pular a ideia de compensação — é o coração do SAGA.

### Ângulo IA/Agentes
- **Agentic workflow = SAGA.** Um pipeline de agentes (planejar → executar → revisar) precisa de **passos compensatórios** quando um passo "alucina" ou falha.
- **Orquestração × coreografia em multiagente:** orquestrador central (mais controle/observável) × agentes reagindo a eventos (mais desacoplado, mais difícil de depurar) — o mesmo trade-off do SAGA.
- Idempotência importa ainda mais com IA: **re-executar um passo não-determinístico** pode duplicar efeito colateral.

> ### 🂡 Carta na manga — instrutor (NÃO puxar; deixar emergir)
> Quando o orquestrador central aparece no quadro, **algum aluno tende a perguntar**: *"isso não é um single point of failure?"*. **Não induza** — é a sacada que queremos que venha da turma. Se vier, conduza; se não vier até o fim da aula, fica guardada (vale soltar como provocação de fechamento ou puxar na Aula 6/7, quando partição e resiliência derem o gancho). Munição para conduzir bem:
> - **"Pool de orquestradores" resolve a parte fácil (disponibilidade), não a difícil (correção).** Orquestrador *stateless* + estado da saga num *store* durável compartilhado → N instâncias atrás de um balanceador. Tira o SPOF de *iniciar* sagas. É o baseline.
> - **O problema real são as sagas em voo.** Instância caiu no passo 2 de 4 → o estado precisa estar **persistido a cada passo** (não na memória dela) e outra instância precisa **detectar a saga órfã e retomá-la** (lease/timeout, *sweeper* de "sagas presas", ou re-disparo por evento).
> - **O perigo é execução dupla** (duas instâncias retomam a mesma saga). Defesas, em ordem de robustez: **(1) idempotência por passo** — o `UNIQUE(idempotency_key)` que eles já viram é a rede que não depende de coordenação; **(2) ownership por saga** — lease com TTL, lock de linha, ou **partição** (`key = saga-id` no consumer group → um único dono por saga).
> - **Reframe de ouro:** o pool **não elimina** o SPOF, **realoca** para o *store* durável (que você torna HA na infra). *"Não existe arquitetura sem SPOF — existe SPOF bem escolhido."*
> - **Fecha o ciclo da aula:** pool com ownership particionado **converge para coreografia** (máquina de estados dirigida por eventos, cada saga dona de um consumidor). É o mesmo trade-off orquestração × coreografia, visto por outro ângulo.
> - **Nome no mercado:** *workers* stateless num pool + cluster com histórico durável/replicado = **Temporal/Cadence**, **Camunda Zeebe**. É "pool de orquestradores" feito direito. Contraste barato: **eleição de líder** dá *single-active* (simples, mas não é pool de verdade e limita vazão).
> - **Demo possível no Plano B (sem Docker):** estado da saga em H2 + um *sweeper* que retoma sagas não concluídas após timeout + a chave de idempotência mostrando que duas instâncias simuladas **não** duplicam. Só puxar se a turma pedir sangue.

## 4. Desafio de evolução — studio (135–160)
- **Base (todos):** adicionar a compensação ao incremento e provar que reexecutar a emissão não duplica o comprovante (idempotência por chave). Mob guiado; Copilot pode andaimar o teste de reprocessamento.
- **Aprofundamento (opcional):** esboçar a versão **coreografada** (por eventos) do mesmo fluxo e listar prós/contras vs orquestração; identificar onde entraria o **outbox**.

## 5. Tempo de projeto em grupo (160–175)
Grupos (**4 grupos de 4**): definir tema/domínio do projeto final (rascunho que ficou da Aula 1) **e** identificar onde há um fluxo que cruza serviços e precisa de SAGA; decidir orquestração × coreografia e registrar a decisão (vira um ADR).

## 6. Fechamento + gancho NPS (175–180)
- Frase-síntese: *"Em distribuído você não escolhe ter falha parcial — só escolhe se vai tratá-la de propósito."*
- Provocação da Aula 3: *"A consulta de comprovante vai bater no banco a cada chamada? E quando forem milhões/dia?"*

## Infra (Plano B)
Sem broker ainda; orquestração via chamada direta entre serviços + H2 segregado. Roda em qualquer cenário (não depende de Docker/Nexus). *Em produção (serviços Quarkus/Spring da Caixa), os mesmos serviços rodariam containerizados — é só a infra que muda, não o código.*

## Exercícios do banco
DDD/bounded context: `BE_JV_010_01..05` (ficaram da Aula 1) · SAGA/orquestração: `BE_JV_010_06..12` (Basic) — consolidação formativa, **via LMS** (já disponível).
