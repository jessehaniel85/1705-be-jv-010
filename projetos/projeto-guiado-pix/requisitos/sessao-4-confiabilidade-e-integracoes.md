# Sessão 4 — Confiabilidade e integrações

- **Projeto:** API de Comprovantes de PIX — 2ª via
- **Data:** 27/04/2026 (segunda-feira), 14h00–14h30
- **Duração:** 30 min
- **Plataforma:** Microsoft Teams
- **Participantes:** Marcela Tavares (PO, facilitadora), Daniel Prado (Arquiteto de Soluções), Téo Mendonça (Segurança/Antifraude), Roberto Khoury (Gerente de Produto), Sandra Lima (Coordenadora de Atendimento)

---

**Marcela:** Pessoal, Sessão 4. Temos uma cara nova: Téo Mendonça, da Segurança e Antifraude. Téo, bem-vindo, depois você se apresenta no ponto certo. Hoje o tema é confiabilidade e integrações. Daniel vai puxar, porque ele prometeu há semanas que "não vamos perder comprovante", e hoje é dia de provar. Daniel.

**Daniel:** Valeu. Então, lembra do desenho: a emissão aceita com 202 e enfileira a gravação; um consumidor pega da fila e grava no banco. Esse desacoplamento é o que nos salva no pico. Mas ele abre uma pergunta de confiabilidade: **e se algo falhar entre o aceite e a gravação?** A regra número um, inegociável, é: **a gente não pode perder comprovante.** Nunca. Um PIX aconteceu, o cliente tem direito ao comprovante, e a gente não pode dar "sumiu".

**Roberto:** Esse é o tipo de coisa que, se acontecer, vira processo. Comprovante de transação financeira não pode evaporar. Concordo, número um.

**Daniel:** Então vamos pelos cenários de falha. Cenário um: a mensagem está na fila, o consumidor pega pra gravar, mas o banco está fora do ar naquele instante, ou lento, ou deu um soluço de rede. Não é culpa da mensagem, é uma falha **temporária**. Nesse caso, a gente **não joga a mensagem fora**. A mensagem volta pra fila e o consumidor **tenta de novo** mais tarde. Falha temporária = re-tenta. Em algum momento o banco volta e a gravação acontece. O comprovante não se perde.

**Marcela:** Então falha temporária na gravação → re-tenta, não descarta.

**Daniel:** Isso. Agora o cenário dois, que é o mais traiçoeiro. E se a mensagem está **quebrada**? Malformada, com um dado corrompido, algo que faz a gravação falhar **toda vez**, não importa quantas vezes a gente tente. Isso é uma mensagem **envenenada**.

**Sandra:** Por que é traiçoeiro?

**Daniel:** Porque se eu só fico re-tentando pra sempre, essa mensagem quebrada vira uma **rolha**. Ela tenta, falha, volta pra fila, tenta de novo, falha de novo... e enquanto isso ela **trava** ou atrasa as mensagens boas que estão atrás dela na fila. Uma mensagem podre segura a fila inteira. Isso é péssimo no pico.

**Roberto:** Então a mensagem quebrada não pode nem travar a fila nem ser jogada fora. Mas se ela falha sempre, o que a gente faz com ela?

**Daniel:** A gente tira ela da fila principal e manda pra uma **fila separada**, uma espécie de "fila dos casos problemáticos". O nome técnico é **fila morta**, ou DLQ. A ideia: depois de tentar gravar algumas vezes e ver que aquela mensagem específica **falha sempre**, em vez de seguir tentando pra sempre e travando a fila, a gente **move ela pra fila morta**. Ali ela fica **parada, guardada, sem se perder**, esperando alguém olhar com calma — entender por que ela está quebrada, corrigir, reprocessar. E a fila principal **continua fluindo** com as mensagens boas.

**Marcela:** Deixa eu separar bem porque são dois mecanismos diferentes: re-tentar a falha temporária, e mandar pra fila morta a mensagem que falha sempre.

**Daniel:** Exatamente dois mecanismos. **Um:** falhou temporário, re-tenta. **Dois:** falhou sempre depois de algumas tentativas, **move pra fila morta (DLQ)** pra inspeção, sem perder e sem travar a fila. Os dois juntos garantem o "não perder comprovante" e o "não travar no pico". A fila morta não é o lixo — é a sala de espera dos casos que precisam de gente.

**Téo:** Posso entrar aqui? Porque isso me interessa demais.

**Marcela:** Por favor, Téo, é a sua deixa.

**Téo:** Eu sou da Segurança e Antifraude. Pra mim, essa fila morta é ótima de auditoria — toda mensagem que falhou fica registrada em algum lugar inspecionável, ninguém varre pra debaixo do tapete. Mas o que me traz aqui de verdade é outra coisa: eu preciso saber **quando um comprovante é gravado**. Toda vez que nasce um comprovante de verdade, eu quero ser avisado, pra cruzar com os modelos de antifraude. Comprovante gerado em massa, padrão estranho, essas coisas.

**Daniel:** E você quer ser avisado **sem** que o meu gravador precise te conhecer, certo?

**Téo:** Exatamente, esse é o ponto fino. Eu **não** quero que você, gravador, tenha que chamar a minha API. Primeiro porque se a minha estiver fora, eu não posso travar a sua gravação — a gravação do comprovante é mais importante que o meu aviso. Segundo porque hoje sou eu, amanhã é o pessoal de notificação, depois o de BI... você não pode ficar colecionando integração. Eu quero **me inscrever** pra receber o aviso, e você só **anuncia** que gravou, sem saber quem está ouvindo.

**Daniel:** Música pros meus ouvidos, porque é exatamente o modelo que eu ia propor: **eventos**. Quando o gravador grava um comprovante com sucesso, ele **publica um evento** — tipo um aviso num mural, "comprovante tal foi gravado" — num **tópico**. Quem se interessar, **assina** o tópico e reage. O gravador **não conhece** os interessados. Ele só anuncia.

**Roberto:** Quem mais vai querer assinar esse mural, além do Téo?

**Daniel:** Pelo que a gente mapeou, três áreas, e nenhuma precisa ser conhecida pelo gravador. **Notificação ao cliente** — pra eventualmente avisar o cliente que o comprovante está pronto. **Antifraude** — o Téo, cruzar com modelos. E **BI** — o pessoal de dados, pra métrica e relatório. Todas reagem ao mesmo evento "comprovante gravado", cada uma do seu jeito, sem interferir uma na outra nem no gravador.

**Marcela:** Importante eu separar: o **evento** existe pra todas essas áreas reagirem. Mas a **notificação ao cliente** em si — mandar o SMS, o push, o e-mail dizendo "seu comprovante está pronto" — isso é MVP ou fase 2? Porque na Sessão 1 o Roberto disse que notificar o cliente é fase 2.

**Roberto:** É fase 2, confirmo. Não trava o MVP. O que **é** importante desde já — e isso eu quero deixar claro — é que o sistema **publique o evento** "comprovante gravado". A infraestrutura de evento tem que existir desde cedo, porque é ela que destrava o Téo e o BI e, depois, a notificação. Mas o ato de mandar a mensagem pro cliente, o SMS, isso a gente liga na fase 2. **Publicar o evento: agora. Notificar o cliente de fato: fase 2.**

**Téo:** Por mim ótimo. Eu só preciso do evento existindo e de poder assinar. O que cada um faz depois do evento é problema de cada um.

**Daniel:** Perfeito, e isso é a beleza do desacoplamento: a gente entrega a publicação do evento no MVP, o Téo já pluga o antifraude dele, e a notificação ao cliente entra na fase 2 sem mexer no gravador. Ninguém renegocia contrato com ninguém.

**Sandra:** Do meu lado de atendimento, eu só quero garantir que esse "não perder comprovante" é pra valer, porque "sumiu o comprovante" é a pior ligação que existe.

**Daniel:** É pra valer, e é justamente o conjunto todo: re-tenta a falha temporária, manda pra fila morta a mensagem envenenada sem descartar, e publica evento quando grava. Nada se perde e nada trava.

**Marcela:** Deixa eu cravar uma sutileza que eu não quero perder na compilação: a **fila morta (DLQ)** é um requisito **por si só**. Não é "detalhe da entrega", é um mecanismo nomeado: mensagem que falha sempre vai pra fila morta pra inspeção. Anotado destacado.

**Daniel:** Por favor, mantém isso explícito. Misturar "fila morta" com um genérico "garantir a entrega" é o tipo de coisa que se perde e a gente sente na produção.

**Roberto:** Concordo. Confiabilidade não é uma linha só. São três coisas: não perder, não travar, e avisar quem precisa. Tenho que sair. Téo, bom te ter no time. Valeu, pessoal.

**Téo:** Valeu. Eu fico até o fim, tenho um ponto de auditoria que casa com a Helena na próxima.

**Marcela:** Ótimo, porque a Sessão 5 é compliance e fechamento, com a Helena. Téo, segura teu ponto de auditoria pra lá. Recapitulando a Sessão 4: **não perder comprovante**, regra número um; **falha temporária → re-tenta**; **mensagem envenenada que falha sempre → fila morta (DLQ)** pra inspeção, sem perder e sem travar; e **publicar evento "comprovante gravado"** num tópico pra notificação, antifraude e BI assinarem, com o gravador sem conhecer os assinantes. **Publicar o evento é MVP; notificar o cliente de fato é fase 2.**

**Daniel:** Tudo certo. Essa é a sessão que faz o sistema sobreviver à vida real.

---

## Decisões da Sessão 4

1. **Regra número um:** o sistema **não pode perder comprovante**, em hipótese alguma.
2. **Falha temporária na gravação** (banco fora, lentidão, soluço de rede): a mensagem **não é descartada** — **re-tenta** até gravar.
3. **Mensagem envenenada** (malformada / falha **sempre**): depois de algumas tentativas, é **movida para uma fila morta (DLQ)** para inspeção. Ela **não se perde** e **não trava** a fila principal, que segue fluindo. A DLQ é um requisito **nomeado e explícito**, não um detalhe de "garantir a entrega".
4. Quando o gravador grava um comprovante com sucesso, ele **publica um evento "comprovante gravado"** num **tópico**. O gravador **não conhece** os assinantes (desacoplamento).
5. **Assinantes do evento:** notificação ao cliente, antifraude (Téo), BI. Cada um reage independentemente, sem interferir no gravador nem entre si.
6. **Escopo MVP vs fase 2:** **publicar o evento** "comprovante gravado" é **MVP** (destrava antifraude/BI). **Notificar o cliente de fato** (SMS/push/e-mail) é **fase 2**.

## Action items

- **Daniel:** desenhar fila morta (DLQ) e publicação de evento na arquitetura.
- **Téo:** levar o ponto de auditoria/rastreabilidade para a Sessão 5 (alinhar com Helena).
- **Marcela:** acrescentar ao glossário "fila morta (DLQ)", "evento/tópico", "mensagem envenenada".

## Glossário acrescentado

- **Fila morta (DLQ):** fila separada para onde vão mensagens que falham sempre, para inspeção; evita perda e evita travar a fila principal.
- **Mensagem envenenada:** mensagem malformada que falha em todas as tentativas de processamento.
- **Evento / tópico:** mecanismo de aviso publica-assina; o gravador publica "comprovante gravado" e os interessados assinam, sem o gravador conhecê-los.
