# Sessão 3 — Consulta, 2ª via e desempenho

- **Projeto:** API de Comprovantes de PIX — 2ª via
- **Data:** 20/04/2026 (segunda-feira), 14h00–14h30
- **Duração:** 30 min
- **Plataforma:** Microsoft Teams
- **Participantes:** Marcela Tavares (PO, facilitadora), Sandra Lima (Coordenadora de Atendimento), Daniel Prado (Arquiteto de Soluções), Roberto Khoury (Gerente de Produto)

---

**Marcela:** Bom dia... boa tarde, sempre erro. Sessão 3. Hoje é a estrela da Sandra: consulta, 2ª via e desempenho. Sandra, você guardou o caso do "ainda não aparece" há duas semanas. Abre a reunião com ele, porque eu acho que ele organiza tudo.

**Sandra:** Guardei com carinho. Então: o cenário que mais dói é o cliente que **acabou de fazer o PIX** e já quer o comprovante. Liga, ou abre o app, dois, três minutos depois. O atendente vai lá, busca pelo identificador, e... não acha. Aí o cliente surta: "como assim não acha, acabei de fazer!". E ele está certo. Ele fez. O dinheiro saiu. Mas o nosso comprovante ainda não está disponível pra consulta.

**Daniel:** E agora a gente sabe **por quê**, porque na Sessão 2 a gente desenhou isso. Lembra do aceite assíncrono? A emissão aceita e responde 202 na hora, mas a gravação no banco acontece logo depois, de forma assíncrona. Existe uma **janela curta** entre "aceitei" e "gravei". Se o cliente consulta dentro dessa janela, o comprovante já tem identificador mas ainda não está no banco.

**Sandra:** Então não é bug. É essa janelinha.

**Daniel:** Não é bug, é consequência do desenho que nos deixa aguentar o pico. Mas a gente **não pode** simplesmente responder "não existe" pra um comprovante que vai existir daqui a um segundo. Isso seria mentir pro cliente. Então a consulta tem que ser **esperta** nesse caso.

**Roberto:** Esperta como? Porque do meu lado, "não achei" pra um PIX que existe é inaceitável. Isso é o tipo de coisa que vira reclamação.

**Daniel:** Esperta assim: quando a consulta não encontra o comprovante de primeira, ela **não desiste na hora**. Ela faz **algumas re-tentativas** — espera um tiquinho, tenta de novo, espera, tenta de novo — porque tem grande chance de o comprovante estar gravando exatamente naquele instante. Só **depois** de tentar algumas vezes e ainda não achar é que ela conclui "esse comprovante realmente não existe" e responde o **404**, que é o "não encontrado".

**Marcela:** Deixa eu cravar o número, porque "algumas" é vago e eu não quero compilar vago. Quantas re-tentativas?

**Daniel:** A gente fechou em **três re-tentativas** antes de desistir. Três tentativas extras, com um intervalinho entre elas, cobrem bem a janela de gravação na prática. Se depois de três ainda não achou, aí sim é 404. Pode ser que a gente ajuste o número fino lá na implementação, mas o requisito é: **re-tenta algumas vezes — na nossa conta, três — antes de devolver 404.** O 404 nunca é imediato no primeiro miss.

**Marcela:** Anotado em maiúsculo pra mim: **a consulta NÃO retorna 404 no primeiro miss. Re-tenta (três vezes) e só então 404.**

**Sandra:** Isso resolve metade da minha vida. Porque hoje o atendente recebe um "não achou" seco e repassa o "não achou" pro cliente. Se o sistema tentar de novo sozinho, na maioria das vezes ele acha na segunda ou terceira.

**Daniel:** Exato. E pro caso raro em que realmente não existe — alguém digitou um identificador errado, por exemplo — aí o 404 é honesto: tentamos, de verdade não tem.

**Roberto:** Tá. E o outro lado da Sandra, o "demora a achar"? Porque você falou de duas dores na Sessão 1: o "ainda não apareceu" e o "demora".

**Sandra:** Isso, a segunda dor é velocidade. Mesmo quando o comprovante existe, hoje demora. O cliente fica na linha, o atendente abre tela, espera carregar. Se for 2ª via de um PIX de três meses atrás, aí que demora. Eu queria que fosse **instantâneo**.

**Daniel:** E aqui entra o ponto de desempenho que eu plantei na Sessão 1. Lembra que eu disse que a gente **lê muito mais do que escreve**? A consulta acontece muito mais vezes que a emissão — o mesmo comprovante pode ser consultado várias vezes, pelo cliente, pelo atendente, de novo no dia seguinte. Pra isso não pesar no banco e pra ser rápido, a gente coloca um **cache** na frente.

**Roberto:** Explica o cache em português de negócio.

**Daniel:** Cache é uma memória rápida, na frente do banco, que guarda as respostas que a gente já buscou. Pensa assim: o banco é um arquivo enorme e organizado, mas ir até ele toda vez é lento. O cache é a gaveta da sua mesa: o que você usou agora há pouco fica ali à mão. Quando chega uma consulta, a gente **olha primeiro no cache**. Se está lá, devolve na hora, rapidíssimo, sem nem incomodar o banco. Se **não** está no cache, aí a gente vai no banco, pega, devolve pro cliente — **e guarda no cache** pra próxima vez que pedirem o mesmo comprovante ser instantânea.

**Marcela:** Então a ordem da consulta é: **olha no cache primeiro; se não tem, vai no banco; achou no banco, popula o cache e devolve.** E se não tem nem no cache nem no banco, é aí que entram as re-tentativas antes do 404.

**Daniel:** Perfeito, você juntou tudo. A ordem completa é: cache → se miss, banco → se achou, popula o cache e devolve → se não achou nem no banco, re-tenta algumas vezes (três) → se ainda não achou, 404.

**Sandra:** E aquele caso do recém-emitido, ele encaixa onde nisso?

**Daniel:** Encaixa no finalzinho. O recém-emitido não está no cache (nunca foi consultado) e ainda não está no banco (está gravando). Então cai no caminho do "não achou no banco" — e é exatamente por isso que as re-tentativas existem: pra dar tempo da gravação assíncrona terminar e a próxima tentativa achar. As re-tentativas são a ponte sobre a janela.

**Roberto:** Sacou bonito. E me dá uma noção de volume de novo, porque desempenho sem número é conversa.

**Daniel:** A consulta é **a operação mais frequente do sistema**, de longe. E nos picos — 13º, salário, benefício — a consulta também sobe, com algumas semanas de atraso em relação à emissão, porque é quando as pessoas vão comprovar pagamento. Então a consulta tem que ser **muito rápida** e **aguentar volume alto**. A latência da 2ª via tem que ser baixa. É um requisito não-funcional duro: **baixa latência na consulta, mesmo em pico.**

**Marcela:** Anoto: **RNF — consulta é a operação mais frequente; latência baixa na 2ª via; aguentar pico.** Roberto, isso te serve de promessa pro cliente?

**Roberto:** Serve. "2ª via na hora" é exatamente o que eu quero poder prometer. E o "não achei" virando "achei na segunda tentativa" some uma reclamação grande do meu radar. Pra mim, Sessão 3 entrega o coração do MVP de cliente.

**Sandra:** Concordo. Se a consulta for rápida e ela re-tentar antes de dizer "não tem", o atendimento muda de patamar.

**Marcela:** Uma dúvida minha de PO, pra fechar a consulta: a busca é sempre **por identificador**? Pelo UUID que a emissão devolveu? Ou o cliente vai querer buscar por "todos os meus PIX do mês"?

**Daniel:** No MVP, a consulta é **por identificador**. GET pelo id, devolve aquele comprovante. Busca por período, por cliente, listagem — isso é outra história, mais pesada, e não é MVP. A gente cataloga como possível fase 2, mas o coração é: dado o identificador, devolve o comprovante rápido.

**Roberto:** Concordo, listagem é fase 2. O cliente que ligou já tem o identificador, ou o atendente acha o identificador pela transação. MVP é por id.

**Marcela:** Fechado. Recapitulando a Sessão 3: a consulta é **GET por identificador**; ela olha **primeiro no cache**, depois no **banco**, **popula o cache** quando acha no banco; se não acha, **re-tenta algumas vezes — três na nossa conta — antes de retornar 404**; o 404 nunca é no primeiro miss. Não-funcionais: consulta é a operação mais frequente, latência baixa, aguentar pico. Busca por período fica como fase 2.

**Daniel:** Assinado embaixo.

**Roberto:** Ótimo. Tenho que sair de novo, vida de gerente. Semana que vem é o quê?

**Marcela:** Sessão 4: confiabilidade e integrações. Não perder comprovante, o que fazer com mensagem que falha pra sempre, e as outras áreas que querem saber quando um comprovante nasce. O Téo, da segurança, entra. Daniel vai brilhar.

**Daniel:** Vou trazer os cenários de falha todos. É a sessão que separa o sistema bonito do sistema que aguenta produção.

---

## Decisões da Sessão 3

1. A consulta do MVP é **GET por identificador** (o UUID v4 da emissão). Busca por período/listagem fica como **fase 2**.
2. **Ordem da consulta:** olha **primeiro no cache** → se miss, busca no **banco** → se encontrou, **popula o cache** e devolve.
3. Quando **não encontra** o comprovante, a consulta **NÃO retorna 404 imediatamente**: ela **re-tenta algumas vezes — três na conta fechada — com pequeno intervalo**, e só então retorna **404**. O 404 nunca ocorre no primeiro miss.
4. As re-tentativas existem para cobrir a **janela da gravação assíncrona** (caso do comprovante recém-emitido): dão tempo de a gravação concluir e a próxima tentativa achar.
5. **RNF de desempenho:** a consulta é a operação **mais frequente** do sistema; exige **baixa latência** na 2ª via, inclusive em **picos** de volumetria.

## Action items

- **Daniel:** levar à Sessão 4 os cenários de falha (não perder comprovante; mensagem que falha sempre; integrações por evento).
- **Marcela:** convidar Téo Mendonça (Segurança/Antifraude) para a Sessão 4.
- **Marcela:** acrescentar ao glossário "cache", "re-tentativas antes do 404".

## Glossário acrescentado

- **Cache:** memória rápida na frente do banco; a consulta olha nele primeiro e é populado quando se acha um comprovante no banco.
- **Re-tentativas antes do 404:** ao não encontrar o comprovante, a consulta tenta novamente algumas vezes (três) antes de concluir "não existe" e retornar 404.
