# Sessão 1 — Kickoff: contexto, problema e escopo

- **Projeto:** API de Comprovantes de PIX — 2ª via
- **Data:** 06/04/2026 (segunda-feira), 14h00–14h30
- **Duração:** 30 min
- **Plataforma:** Microsoft Teams
- **Participantes:** Marcela Tavares (PO, facilitadora), Roberto Khoury (Gerente de Produto), Sandra Lima (Coordenadora de Atendimento), Daniel Prado (Arquiteto de Soluções)
- **Ausente:** Dra. Helena Sasaki (compliance — entra na Sessão 5; enviou recado pedindo que retenção e LGPD não sejam fechadas sem ela)

---

**Marcela:** Bom, oi pessoal, são duas da tarde, acho que estamos todos. Roberto, Sandra, Daniel... a Helena avisou que não consegue hoje, está numa pauta do Bacen, mas mandou um recado bem claro: nada de retenção, LGPD ou auditoria fechado sem ela. Anotei. A gente reserva a última sessão pra isso.

**Roberto:** Perfeito. Eu tenho meia hora cravada, às duas e meia tenho outra. Então vamos no que interessa.

**Marcela:** Combinado. Essa é a reunião de kickoff. Objetivo de hoje: alinhar por que estamos fazendo esse sistema, qual a dor, e — principalmente — cravar o escopo. Eu vou anotando aqui um glossário também, porque já percebi que cada um chama uma coisa de um jeito. Sandra, você que vive a dor, abre pra gente. O que está acontecendo?

**Sandra:** Então. A gente recebe um volume absurdo de ligação e de chamado no app pedindo a mesma coisa: "quero o comprovante do meu PIX". O cliente fez o PIX, às vezes ontem, às vezes semana passada, às vezes cinco minutos atrás, e quer aquele papelzinho, a 2ª via. Hoje isso é um sofrimento. O atendente abre não sei quantas telas, às vezes nem acha, o cliente fica na linha...

**Roberto:** E isso é caro. Cada minuto de atendente na linha é dinheiro. A gente tem um TMA — tempo médio de atendimento — que sobe direto por causa de "cadê meu comprovante". Isso vira recontato, vira reclamação no Bacen às vezes, vira nota baixa de satisfação. É uma dor de cliente que a gente sente no bolso e na reputação.

**Marcela:** Então deixa eu já anotar o problema central: o cliente quer a 2ª via do comprovante de um PIX que já aconteceu, e hoje a gente não entrega isso de forma rápida e confiável.

**Sandra:** Isso. "Já aconteceu" é a palavra. O PIX já caiu. O dinheiro já saiu e já chegou. Ele só quer o documento.

**Daniel:** Esse ponto é o mais importante do escopo, e eu quero martelar nele agora pra não dar confusão lá na frente. Esse sistema **não efetiva PIX**. Ele não move dinheiro, não fala com o SPI, não tem nada a ver com a liquidação. Isso é o core bancário, é outro sistema, outro time. O nosso sistema entra **depois**: ele **emite e consulta comprovantes** de PIX que já foram efetivados.

**Roberto:** Isso é fundamental, Daniel, obrigado por cravar. Porque se alguém da auditoria ou do board ouvir "sistema de PIX novo", já pensa em risco financeiro, liquidação, essas coisas. Não é isso. É 2ª via. É comprovante.

**Marcela:** Anotando no glossário então. **Comprovante de PIX:** o documento que comprova uma transação PIX que **já foi efetivada por outro sistema**. **2ª via:** a reemissão/consulta desse comprovante depois. E uma linha em negrito pra mim mesma: **fora de escopo — efetivar, liquidar ou cancelar PIX.** Daniel, então o que entra no nosso escopo, em alto nível?

**Daniel:** Duas capacidades. Uma: **emitir** o comprovante — alguém, algum sistema, manda pra gente os dados de um PIX que aconteceu e a gente registra esse comprovante. Duas: **consultar** — dado um identificador, devolver o comprovante pra ser exibido ou impresso. Emissão e consulta. Só isso, mas bem feito.

**Sandra:** Pra mim, no atendimento, o que importa de verdade é a consulta. É o que o cliente pede. Mas eu entendo que sem alguém gravar antes, não tem o que consultar.

**Roberto:** E só pra eu entender o tamanho disso, Daniel — quem mais usa? Consulta ou emissão?

**Daniel:** Consulta, disparado. A emissão acontece uma vez por PIX. A consulta acontece toda vez que alguém quer ver de novo — o cliente no app, o atendente, às vezes o mesmo comprovante é visto várias vezes. A proporção é desbalanceada. A gente lê muito mais do que escreve. Isso vai pesar na forma como a gente desenha a consulta, mas isso é papo de outra sessão.

**Marcela:** Anotado: **leitura muito mais frequente que escrita**. Daniel, segura esse ponto pra Sessão 3, que é a de consulta e desempenho.

**Daniel:** Seguro. Só planto a sementinha: se a gente vai ser lido muito mais do que escrito, e em volume de banco grande, a forma como a gente guarda e devolve isso importa demais.

**Roberto:** Falando em volume — qual é a volumetria que a gente está imaginando? Porque PIX na Caixa não é pouca coisa.

**Daniel:** Não é. E tem o agravante dos **picos**. Tem dia que o volume é X, e tem dia que é cinco, dez vezes X. Quais? Dia de pagamento de salário, dia de benefício social, e o clássico: décimo terceiro. Nessas datas o número de PIX explode, e por consequência o número de comprovante emitido e — algumas semanas depois — o número de consulta também sobe, porque é quando todo mundo quer comprovar pra alguém que pagou.

**Roberto:** Então a gente tem que aguentar o pico sem cair. Porque cair no dia do 13º é manchete.

**Daniel:** Exato. E isso muda o desenho. Um sistema que aguenta a média mas morre no pico não serve. A gente vai ter que conversar sobre como absorver esse pico sem perder nada — mas de novo, isso é papo das próximas sessões.

**Marcela:** Glossário ganhou mais uma: **pico de volumetria** — datas de pagamento, benefício e 13º, quando o volume multiplica. Requisito não-funcional grande: **aguentar pico sem perder comprovante e sem cair**. Sandra, do seu lado, tem um cenário que te tira o sono?

**Sandra:** Tem, e é exatamente o do "agorinha". O cliente faz o PIX e, dois minutos depois, liga ou abre o app querendo o comprovante. E aí o atendente procura e... não está lá ainda. O cliente jura que fez, e está certo, ele fez mesmo. Mas o nosso sistema ainda não tem. Isso gera uma desconfiança horrível: "vocês perderam meu PIX?". Não perdemos, o PIX está lá no core, é só o comprovante que ainda não apareceu pra gente.

**Daniel:** Esse cenário é ouro, Sandra, guarda ele com carinho que ele vai voltar com força na sessão de consulta. Tem uma razão técnica pra esse atraso e tem um jeito de tratar isso bem. Mas é cedo pra abrir.

**Marcela:** Já anotei como um item destacado: **"caso do comprovante recém-emitido"** — cliente consulta logo após o PIX e pode haver atraso. A tratar na Sessão 3.

**Roberto:** Marcela, pra eu fechar a cabeça: a gente está falando de um MVP, certo? Não é pra resolver o universo na primeira entrega.

**Marcela:** Certo. MVP é: **emitir comprovante** e **consultar comprovante por identificador, rápido e confiável.** O resto — notificação automática pro cliente, integração com outras áreas, relatório de BI — a gente cataloga, mas decide depois o que é MVP e o que é fase 2.

**Roberto:** Perfeito. Pra mim, MVP é o cliente conseguir a 2ª via rápido. Notificar ele automaticamente, mandar o comprovante sozinho... isso é lindo, mas é fase 2. Não trava o MVP.

**Daniel:** Concordo, e tem coisas de integração — outras áreas querendo saber quando um comprovante nasce — que também são importantes mas não são MVP de cliente. A gente cataloga na Sessão 4.

**Marcela:** Fechado. Então pra recapitular antes de soltar o Roberto: o sistema **emite e consulta comprovantes de PIX já efetivados**, **não efetiva nada**; a dor é a 2ª via lenta e não confiável no atendimento; a consulta é muito mais frequente que a emissão; temos picos de volumetria sérios; e tem o caso espinhoso do comprovante recém-feito. MVP é emissão e consulta. Notificação e integrações ficam catalogadas pra decidir depois.

**Roberto:** Isso. Mandou bem. Tenho que pular. Obrigado, pessoal. (*sai*)

**Sandra:** Eu fico. Só reforçando: o que mais machuca lá na ponta é o "não acho" e o "ainda não apareceu". Se vocês resolverem esses dois, eu já beijo os pés do time.

**Daniel:** A gente vai resolver os dois, e tem nome técnico pra cada um. Próxima a gente entra na emissão, que é onde nascem os dados todos.

**Marcela:** Isso. Sessão 2 é emissão: quais dados entram, o que é obrigatório, formato dos campos, e o que acontece quando alguém manda emitir. Daniel, prepara que você vai ter que explicar pra gente de negócio uma coisa que você já comentou comigo no corredor, esse tal de "aceite assíncrono".

**Daniel:** Trago pronto. Vai ser a parte divertida.

---

## Decisões da Sessão 1

1. O sistema **emite e consulta comprovantes de PIX já efetivados**. **Não efetiva, não liquida, não cancela PIX** — isso é o core bancário, fora do escopo.
2. O **MVP** é: emitir comprovante e consultar comprovante por identificador, de forma **rápida e confiável**.
3. **Notificação automática ao cliente** e **integrações com outras áreas** são catalogadas, mas tratadas como candidatas a **fase 2** (decisão final nas próximas sessões).
4. Retenção, LGPD e auditoria **não serão fechadas sem a Dra. Helena** — reservado para a Sessão 5.

## Action items

- **Marcela:** consolidar o glossário inicial (linguagem ubíqua) e circular.
- **Daniel:** trazer para a Sessão 2 a explicação de negócio do "aceite assíncrono" na emissão.
- **Sandra:** detalhar na Sessão 3 o caso do "comprovante recém-emitido que ainda não aparece".
- **Marcela:** garantir presença da Dra. Helena na Sessão 5.

## Glossário inicial (linguagem ubíqua)

- **Comprovante de PIX:** documento que comprova uma transação PIX já efetivada por outro sistema.
- **2ª via:** reemissão/consulta de um comprovante já existente.
- **Emissão:** registro de um novo comprovante a partir dos dados de um PIX já ocorrido.
- **Consulta:** recuperação de um comprovante por identificador.
- **Pico de volumetria:** datas de pagamento de salário, benefício social e 13º, quando o volume de PIX (e de comprovantes) multiplica.
- **Caso do comprovante recém-emitido:** cliente consulta logo após o PIX, podendo haver atraso até o comprovante estar disponível.
