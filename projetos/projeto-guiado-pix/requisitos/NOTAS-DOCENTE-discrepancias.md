# NOTAS DO DOCENTE — Discrepâncias plantadas

> ⚠️ **MATERIAL EXCLUSIVO DO INSTRUTOR. NÃO DISTRIBUIR AOS ALUNOS.**
> Este arquivo revela, de propósito, os erros que o Product Owner cometeu ao compilar as user stories. Os alunos **devem descobri-los sozinhos** relendo as transcrições. Se este arquivo vazar, o exercício perde o sentido. Mantenha-o fora do material entregue (e idealmente fora do repositório que o aluno clona).

## Como o exercício funciona

- `user-stories.md` é apresentado como **primeira fonte de verdade**.
- As **transcrições** (`sessao-1` a `sessao-5`) ficam disponíveis para o aluno (a) entender contexto e (b) **corrigir erros/omissões do PO**.
- Foram plantadas **6 discrepâncias** entre as user stories e as transcrições. Cada uma é **plausível** (erro humano de quem compila rápido — a Marcela é apresentada com esse viés) e **descobrível** relendo a sessão correspondente.
- A Marcela ainda deixa "dicas de processo" no texto: ela diz em US-stories e na Sessão 5 que compilou rápido e que "em caso de divergência, recorram às transcrições". Isso autoriza pedagogicamente o aluno a não confiar cegamente na story.

Recomendação de condução: peça aos alunos que produzam um **erratum** (lista de correções às user stories, citando sessão + trecho). É um exercício excelente de leitura crítica de requisito e de rastreabilidade.

---

## Discrepância 1 — Código HTTP da emissão: 201 vs 202

- **Onde na story:** US-01, primeiro critério de aceite — "responde **`201 Created`**".
- **O que a transcrição diz:** **Sessão 2 (Emissão)**. Daniel explica longamente o **aceite assíncrono** e crava: *"Não é o 201. É o **202**, que em HTTP significa literalmente 'aceito'... 201 diz 'já está gravado, pronto'. **202 diz 'aceitei, mas a gravação vai acontecer logo depois'**. No nosso caso é 202, porque a gravação é assíncrona."* A própria Marcela repete: *"a emissão responde **202 Accepted**, não 201."*
- **Valor pedagógico:** semântica de status HTTP em sistemas assíncronos. 201 implica recurso já criado; 202 é o contrato correto para "aceite + processamento posterior". Erro clássico de quem compila sem reter a nuance. Liga direto com a Aula 4 (async) do projeto-guia.

## Discrepância 2 — Consulta retorna 404 imediato (omite as re-tentativas)

- **Onde na story:** US-03, segundo critério — "o sistema retorna **`404`**" (direto, sem re-tentativas).
- **O que a transcrição diz:** **Sessão 3 (Consulta)**. Daniel: *"quando a consulta não encontra... ela **não desiste na hora**. Ela faz **algumas re-tentativas**... Só **depois** de tentar algumas vezes e ainda não achar é que ela... responde o **404**."* Marcela crava o número: *"a consulta NÃO retorna 404 no primeiro miss. Re-tenta (três vezes) e só então 404."* As re-tentativas existem para cobrir a **janela da gravação assíncrona** (caso do comprovante recém-emitido).
- **Valor pedagógico:** o coração da Aula 3 (consulta resiliente: cache → banco → 3 retries → 404). Omitir as re-tentativas reintroduz exatamente a dor que a Sandra levantou ("acabei de fazer e não aparece"). Excelente para discutir consistência eventual e por que o 404 ingênuo é um bug de negócio.

## Discrepância 3 — Fila morta (DLQ) omitida; vira "garantir a entrega"

- **Onde na story:** US-05, segundo critério — agrupa tudo num vago *"o sistema deve **garantir a entrega** da gravação"*. Não há menção a mensagem envenenada nem a fila morta / DLQ.
- **O que a transcrição diz:** **Sessão 4 (Confiabilidade)**. Daniel distingue **dois** mecanismos: falha temporária → re-tenta; **mensagem envenenada que falha sempre → move para a fila morta (DLQ)** para inspeção, sem perder e sem travar a fila. Marcela inclusive avisa: *"a fila morta (DLQ) é um requisito por si só... não é 'detalhe da entrega'."* E Daniel: *"Misturar 'fila morta' com um genérico 'garantir a entrega' é o tipo de coisa que se perde e a gente sente na produção."* — ou seja, a transcrição **prevê e nomeia** justamente o erro cometido.
- **Valor pedagógico:** Aula 5 (filas + DLQ). Sem DLQ, uma mensagem malformada vira "rolha" e trava a fila no pico — falha de disponibilidade. Ótimo para ensinar a diferença entre retry (transitório) e DLQ (poison message).

## Discrepância 4 — Prazo de retenção: 10 anos vs 5 anos

- **Onde na story:** US-08 — "retido por **10 anos** a partir da data da transação".
- **O que a transcrição diz:** **Sessão 5 (Compliance)**. Dra. Helena é enfática e antecipa o erro: *"deve ser **retido por 5 anos** — cinco anos, contados a partir da data da transação... Anota com o número, Marcela, porque eu já vi projeto **trocar isso na compilação** e virar problema de auditoria. **Cinco anos**."*
- **Valor pedagógico:** rastreabilidade de requisito regulatório e por que números de compliance não admitem "arredondamento de reunião". Errar para mais (10) parece conservador, mas ainda é um requisito errado — armazenamento, custo e política de descarte ficam todos incorretos. Ensina a tratar requisito não-funcional/regulatório como dado preciso, citável à fonte.

## Discrepância 5 — Formato do `numero_conta`: 6 caracteres vs 5

- **Onde na story:** US-01, tabela de campos — `numero_conta` listado como **6 caracteres**.
- **O que a transcrição diz:** **Sessão 2 (Emissão)**. Daniel: *"A **agência tem 4 caracteres**. A **conta tem 5 caracteres**. E o **dígito verificador tem 1 caractere**. Quatro, cinco, um."* Marcela repete: *"agência quatro, conta cinco, dígito um."* (Atenção: agência=4 e dígito=1 estão **corretos** na story; só a conta foi trocada para 6, o que torna o erro sutil e realista.)
- **Valor pedagógico:** validação de formato/tamanho na borda de entrada. Daniel ainda reforça que são **strings** (zero à esquerda importa) e que tamanho errado deve ser rejeitado. Um campo com tamanho errado quebra a validação de entrada e a integridade do dado. Bom para testes de contrato/validação (liga com Aula 8, contract testing).

## Discrepância 6 — Notificar o cliente marcada como MVP (era fase 2)

- **Onde na story:** US-07 — marcada com *"Escopo: **MVP**."*
- **O que a transcrição diz:** **Sessão 1** e, sobretudo, **Sessão 4**. Roberto, na Sessão 4: *"**Publicar o evento: agora. Notificar o cliente de fato: fase 2.**"* E Marcela registra na própria ata da Sessão 4: *"**publicar o evento** 'comprovante gravado' é **MVP**... **notificar o cliente de fato** (SMS/push/e-mail) é **fase 2**."* Já na Sessão 1 Roberto havia dito que notificar o cliente automaticamente é fase 2.
- **Cuidado / sutileza:** **US-06 (publicar o evento) está correta como MVP.** O erro é só na **US-07 (notificar o cliente de fato)**, que deveria ser fase 2. A proximidade das duas stories torna a troca verossímil — a Marcela "puxou" o escopo do evento para a notificação.
- **Valor pedagógico:** separar **publicar evento** (infraestrutura de integração, MVP) de **agir sobre o evento / notificar** (fase 2). Ensina priorização de escopo e a não inflar o MVP. Também reforça desacoplamento: a notificação é só mais um assinante do tópico, plugável depois sem mexer no gravador.

---

## Tabela-resumo (gabarito do erratum)

| # | User story | Story diz | Transcrição diz | Sessão |
|---|---|---|---|---|
| 1 | US-01 | emissão responde **201 Created** | **202 Accepted** (aceite assíncrono) | 2 |
| 2 | US-03 | **404 imediato** no miss | re-tenta (**3x**) antes do 404 | 3 |
| 3 | US-05 | vago "garantir a entrega" | **fila morta (DLQ)** para mensagem envenenada | 4 |
| 4 | US-08 | retenção **10 anos** | retenção **5 anos** (da data da transação) | 5 |
| 5 | US-01 | `numero_conta` = **6 chars** | conta = **5 chars** | 2 |
| 6 | US-07 | notificar cliente = **MVP** | notificar cliente = **fase 2** (só publicar evento é MVP) | 1 e 4 |

## O que NÃO é discrepância (para o docente não se confundir)

- US-06 (publicar evento "comprovante gravado") = MVP: **correto**.
- `numero_agencia` = 4 chars e `digito_verificador_conta` = 1 char: **corretos** (só a conta está errada).
- Ordem da consulta (cache → banco → popula cache): **correta** na US-03 (o erro é só o 404 imediato).
- Re-tentar falha temporária na gravação (US-05, 1º critério): **correto**; o que falta é a DLQ.
- Escopo "não efetiva PIX", base legal de obrigação de guarda, trilha de auditoria, dado sensível: **corretos**.
