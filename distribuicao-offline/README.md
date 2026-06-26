# Distribuição do material — turma 1705

> ✅ **Atualização (~23/06): o LMS está disponível.** O canal principal voltou a ser a plataforma — o material compartilhado por WhatsApp (requisitos, checklist, material do aluno em PDF) está sendo subido ao LMS, e **exercícios ficam disponíveis a partir da Aula 2**. **Este documento vira backup** (envio por WhatsApp se a rede/LMS falhar). O índice abaixo continua válido como referência do que liberar.

**Decisão (combinado com a turma na Aula 1):** liberar **todo o conteúdo de estudo logo no início**, para que os alunos **estudem antes** e **acompanhem durante** as aulas. O módulo é **avançado e focado em discussão e decisão arquitetural** — não em digitar código — então o aluno chegar tendo lido/visto o material e o gabarito **eleva** a qualidade do debate em aula (não estraga uma "revelação", porque a revelação não é o ponto).

---

## 1. Liberar AGORA (pacote inicial, único envio)

| Item | Origem | Formato |
|---|---|---|
| **Gabarito completo do projeto guiado (PIX)** — todos os estados, aula 01→08 | `projetos/projeto-guiado-pix/gabarito/` | zip do diretório |
| **Material do aluno — 9 aulas** | `plano-de-aulas/aula-0X-aluno.md` | **PDF** (md impresso) |
| Requisitos do PIX (já enviado na Aula 1) | `projetos/projeto-guiado-pix/requisitos/` | zip |
| Brief do projeto final + rubrica | `projetos/projeto-final-grupo/brief.md` | PDF |
| `settings.xml` do Nexus | `ambiente/settings.xml` | arquivo |

> **Gabarito aberto desde o início é deliberado:** vira **material de estudo e dissecação** ("por que está assim? que alternativa havia?"), não um exercício de reprodução. Combina com o foco em decisão arquitetural.

## 2. Quando a plataforma voltar

| Item | Observação |
|---|---|
| **Exercícios do banco** (`BE_JV_010_01..40`, todos Basic) | **Aguardar a plataforma** — não distribuir PDF agora (decisão do docente). Servem como consolidação formativa, não avaliação. |
| Slides `.pptx` | Migrar para a plataforma; enviar PDF avulso só se algum aluno pedir. |

---

## Como gerar os PDFs do material do aluno

`plano-de-aulas/aula-0X-aluno.md` → PDF (qualquer conversor md→pdf que preserve os blocos de código e tabelas). Manter o texto **cliente-agnóstico** (ver [[feedback_evitar_nome_cliente_conteudo]]) — o material do aluno não cita "Caixa".

## Checklist do pacote inicial
- [ ] Gabarito do PIX (zip de `gabarito/`)
- [ ] 9 materiais do aluno em PDF
- [ ] Brief do projeto final em PDF
- [ ] `settings.xml` do Nexus
- [ ] (requisitos já enviados na Aula 1)
