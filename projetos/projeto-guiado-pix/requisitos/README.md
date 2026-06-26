# Requisitos — API de Comprovantes de PIX

Pacote de **elicitação de requisitos** do projeto-guia do módulo **BE-JV-010** (turma Caixa Econômica Federal, nível especialista). Simula a coleta de requisitos de um sistema real e serve de **entrada** para o projeto que o time vai desenvolver.

## O sistema, em uma frase

Uma API que **emite e consulta comprovantes de PIX já efetivados**. Ela **não efetiva PIX** — a transação acontece em outro sistema (o core bancário). Aqui só se registra e se reexibe o comprovante (a "2ª via").

## O que tem neste pacote

| Arquivo | O que é |
|---|---|
| `personas.md` | Ficha de cada participante das reuniões (papel, objetivos, jeito de falar, vieses). |
| `sessao-1-kickoff.md` | Transcrição — contexto, problema e escopo. |
| `sessao-2-emissao.md` | Transcrição — emissão do comprovante, campos, formatos, aceite assíncrono. |
| `sessao-3-consulta-e-desempenho.md` | Transcrição — consulta, 2ª via, cache, desempenho. |
| `sessao-4-confiabilidade-e-integracoes.md` | Transcrição — não perder comprovante, fila morta, eventos. |
| `sessao-5-compliance-e-fechamento.md` | Transcrição — LGPD, retenção, auditoria, fechamento. |
| `user-stories.md` | Documento compilado pela PO — **primeira fonte de verdade**. |

> O pacote inclui as **transcrições na íntegra** das cinco reuniões (Product Owner + stakeholders) e o documento de **user stories** que a PO compilou ao final.

## Como usar (leia antes de começar)

1. **As `user-stories.md` são a primeira fonte de verdade.** Comecem por elas. É o documento que a PO entregou ao time como ponto de partida do desenvolvimento.

2. **As transcrições estão aqui para vocês recorrerem a elas.** Use as cinco sessões para:
   - **entender o contexto** — por que cada decisão foi tomada, qual dor do negócio ela resolve, o que cada área precisa;
   - **resolver erros e omissões da PO** — a compilação foi feita rápido (a própria PO avisa isso). Onde a user story estiver vaga, incompleta ou em conflito, **a transcrição manda**. Quem disse, em qual reunião, é o que vale.

3. **Em caso de divergência entre a user story e a transcrição, a transcrição prevalece** — ela é o registro do que os stakeholders efetivamente pediram. Tratem as user stories como uma boa primeira versão, não como verdade infalível.

> Dica de método: ao ler uma user story, pergunte-se "isso bate com o que foi dito na reunião correspondente?". Vale a pena montar uma pequena lista de correções (um *erratum*) conforme forem encontrando divergências, citando a sessão e o trecho.

## Relação com o projeto-guia

Estes requisitos alimentam o **projeto guiado em aula** (`../`), construído ao vivo ao longo das 8 aulas, e servem de referência para o **projeto final em grupo**. O arco de requisitos mapeia, de forma natural, os padrões trabalhados no módulo:

- DDD e contextos delimitados (emissão · gravação · consulta);
- aceite assíncrono e processamento desacoplado;
- cache e consulta resiliente;
- filas e tratamento de falhas;
- eventos/tópicos para integração entre áreas;
- resiliência (re-tentativas);
- e contratos entre serviços.

Os detalhes técnicos de implementação (stack, profiles de execução) estão no `brief.md` e na pasta `docs/` do projeto-guia. **Este pacote é sobre o quê e o porquê — o domínio e os requisitos —, não sobre o como.**

## Importante

- Escreva em português (pt-BR), como o restante do material.
- Não há "resposta única" para o desenho: o objetivo é praticar **leitura crítica de requisitos** e **rastreabilidade** (ligar cada decisão de projeto a uma fala de stakeholder).
