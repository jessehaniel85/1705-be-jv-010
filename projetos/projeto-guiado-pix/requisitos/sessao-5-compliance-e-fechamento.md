# Sessão 5 — Compliance, segurança, retenção e fechamento

- **Projeto:** API de Comprovantes de PIX — 2ª via
- **Data:** 04/05/2026 (segunda-feira), 14h00–14h30
- **Duração:** 30 min
- **Plataforma:** Microsoft Teams
- **Participantes:** Marcela Tavares (PO, facilitadora), Dra. Helena Sasaki (Compliance e Regulação), Téo Mendonça (Segurança/Antifraude), Daniel Prado (Arquiteto de Soluções), Roberto Khoury (Gerente de Produto)

---

**Marcela:** Última sessão, pessoal. Dra. Helena, finalmente — a gente reservou esse tema todo pra senhora, como combinado lá no kickoff. Compliance, segurança, retenção, e aí eu fecho recapitulando tudo e me comprometo a compilar as user stories. Helena, a palavra é sua. O que a gente precisa garantir pra esse sistema passar pela régua regulatória?

**Helena:** Obrigada, Marcela, e obrigada por terem segurado os temas regulatórios pra mim em vez de chutarem. Vou por camadas. Primeira camada: **o que é o dado** que esse sistema guarda. Vocês estão armazenando nome, documento — CPF, CNPJ —, conta, agência, valor, chave PIX, nome do destinatário. Isso é, sem nenhuma dúvida, **dado pessoal**, e parte dele é **dado pessoal sensível** no contexto financeiro. Então tudo aqui está sob a LGPD. Isso muda como vocês tratam, guardam e dão acesso.

**Daniel:** Helena, do ponto de vista técnico, o que isso me obriga a fazer de diferente?

**Helena:** Três coisas, no mínimo. Um: **base legal**. Vocês precisam ter uma base legal clara pra esse tratamento. No caso, a boa notícia é que comprovante de transação financeira tem **obrigação legal e regulatória** de guarda — então a base não é só consentimento, é cumprimento de obrigação legal. Mas isso tem que estar **documentado**, não pode ser implícito.

**Marcela:** Então o consentimento puro não é a única base — tem a obrigação legal. Anotei.

**Helena:** Exato, e isso é importante porque significa que vocês **devem** guardar mesmo sem o cliente pedir, por exigência regulatória. Dois: **trilha de auditoria**. E aqui o Téo vai gostar. Todo **acesso** a um comprovante — toda consulta — precisa ser **rastreável**: quem consultou, qual comprovante, quando. Não é só guardar o comprovante; é guardar **quem mexeu nele**. Porque amanhã, se houver suspeita de vazamento ou acesso indevido, eu preciso poder responder ao Bacen "quem viu o quê e quando".

**Téo:** É exatamente o meu ponto de auditoria que segurei da sessão passada. Eu preciso conseguir reconstruir o histórico de acesso. Não basta saber que o comprovante existe; preciso saber a trilha de quem o consultou.

**Daniel:** Então **registro de auditoria de acesso** é requisito, não opcional. Toda consulta gera trilha.

**Helena:** Requisito. Registrado em ata, por favor, Marcela.

**Marcela:** Registrado: **trilha de auditoria de acesso — toda consulta registra quem, qual comprovante e quando.** Terceira coisa, Helena?

**Helena:** Terceira: **retenção**. E aqui eu preciso ser muito precisa, porque é o tipo de número que não admite arredondamento de reunião. O comprovante de transação PIX, pela nossa política interna alinhada à exigência regulatória, deve ser **retido por 5 anos** — **cinco anos**, contados a partir da data da transação. Não é "uns anos", não é "o tempo que der". São **cinco anos**, e isso vem de obrigação regulatória de guarda de registros de transação financeira. Antes disso, **não se descarta**.

**Marcela:** Cinco anos. A partir da data da transação.

**Helena:** Cinco. Anota com o número, Marcela, porque eu já vi projeto trocar isso na compilação e virar problema de auditoria. **Cinco anos**, data da transação como marco inicial. Depois de cinco anos, aí sim entra política de descarte ou anonimização, mas isso é assunto de outra fase. No MVP, o que importa: **reter por cinco anos, não descartar antes.**

**Roberto:** Helena, isso impacta meu custo de armazenamento, mas eu entendo que é inegociável. Cinco anos é cinco anos.

**Helena:** Inegociável. É o Bacen, não sou eu. E tem uma quarta camada, mais leve, sobre **minimização e acesso**: o sistema só deve expor o dado pessoal a quem tem necessidade legítima — o próprio cliente, o atendente autorizado. Não é pra qualquer um dentro do banco sair consultando comprovante alheio. Mas como vocês já vão ter a trilha de auditoria, isso fica controlado. Eu não vou abrir controle de acesso fino agora porque sei que tem o login corporativo da Caixa por trás; só registro o princípio.

**Daniel:** Faz sentido. O princípio fica: dado pessoal sensível, acesso só por necessidade legítima, e tudo auditado.

**Marcela:** Téo, fechou seu ponto de auditoria com a Helena?

**Téo:** Fechou. A trilha de acesso que a Helena pediu é a mesma que eu preciso pro antifraude. Casou bonito. E o evento "comprovante gravado" da sessão passada me dá o outro lado, o de geração. Estou servido.

**Helena:** Só reforço uma coisa pro Téo e pro Daniel: o evento que vocês publicam, "comprovante gravado", ele também carrega dado pessoal. Então quem assina aquele tópico está recebendo dado sob LGPD também. Isso precisa estar no radar — não é porque é evento interno que deixa de ser dado pessoal.

**Daniel:** Anotado. O dado é sensível em qualquer canal — na consulta, no banco, na fila, no tópico. Mesmo cuidado em todo lugar.

**Marcela:** Boa, isso é importante e eu quase deixava passar. **Dado pessoal sensível em todos os canais, inclusive nos eventos.** Helena, do seu lado, falta alguma coisa pro MVP passar na régua?

**Helena:** Pro MVP, o essencial é: base legal documentada (obrigação legal de guarda), **retenção de cinco anos a partir da data da transação**, **trilha de auditoria de todo acesso**, e tratamento do dado como sensível em todos os pontos. Com isso eu assino embaixo. Refinamentos — relatório de descarte, anonimização pós-cinco-anos, política de consentimento pra usos secundários — são fases seguintes.

**Marcela:** Perfeito. Então deixa eu fechar a sessão e o projeto. Vou recapitular tudo e já aviso: **eu vou compilar isso em user stories essa semana**, e essas user stories vão ser a primeira fonte de verdade pro time. As transcrições das cinco sessões ficam disponíveis pra qualquer um conferir o contexto.

**Roberto:** Manda a recap, que eu valido.

**Marcela:** Recap geral. **Escopo:** emitir e consultar comprovantes de PIX já efetivados; não efetiva PIX. **Emissão:** POST sistema-a-sistema, campos obrigatórios com formatos cravados — agência 4, conta 5, dígito 1 —, só a identificação do PIX é opcional; responde aceite assíncrono com identificador UUID e data/hora da requisição; gravação assíncrona. **Consulta:** GET por identificador; cache primeiro, banco depois, popula cache, re-tenta antes de desistir; não-funcionais de latência baixa e pico. **Confiabilidade:** não perder comprovante; re-tenta falha temporária; fila morta pra mensagem que falha sempre; publica evento "comprovante gravado" pra notificação, antifraude e BI; notificar o cliente de fato é fase 2. **Compliance:** dado sensível LGPD, retenção de cinco anos da data da transação, trilha de auditoria de acesso. É isso, time?

**Daniel:** É isso. E só um pedido de arquiteto pra você, Marcela, na hora de compilar: cuidado com os números e com os mecanismos nomeados. O "aceite assíncrono" não é "criado". As re-tentativas da consulta não são "404 na hora". A fila morta não é "garantir entrega". E os cinco anos são cinco. Esses detalhes são o projeto.

**Marcela:** Anotado, Daniel, prometo caprichar. Mas você me conhece, eu compilo rápido pra não travar o time — se escapar algo, as transcrições estão aí pra corrigir. É pra isso que a gente registrou tudo direitinho.

**Helena:** Por mim, com a retenção e a auditoria registradas em ata, está aprovado do lado regulatório.

**Roberto:** Aprovado do lado de negócio. Bom projeto, pessoal. Marcela, manda as user stories quando estiverem prontas.

**Téo:** Servido e satisfeito. Valeu.

**Marcela:** Obrigada a todos. Fecho a elicitação aqui. Compilo as user stories e circulo. As cinco transcrições ficam no repositório de requisitos pra consulta. Até a próxima.

---

## Decisões da Sessão 5

1. Os dados do comprovante são **dado pessoal**, parte **sensível** (contexto financeiro): tudo sob **LGPD**, em **todos os canais** (banco, fila, tópico, consulta).
2. **Base legal:** **obrigação legal/regulatória de guarda** (não apenas consentimento). Deve estar documentada. O sistema guarda mesmo sem o cliente pedir.
3. **Retenção:** o comprovante deve ser **retido por 5 (cinco) anos**, contados **a partir da data da transação**. Não descartar antes. Descarte/anonimização pós-prazo é fase futura.
4. **Trilha de auditoria de acesso:** toda **consulta** registra **quem** consultou, **qual** comprovante e **quando**. Requisito, não opcional.
5. **Minimização/acesso:** expor dado pessoal só a quem tem necessidade legítima (cliente, atendente autorizado), apoiado no login corporativo e na trilha de auditoria.
6. Marcela vai **compilar as user stories** (primeira fonte de verdade); as **transcrições** ficam disponíveis para conferência de contexto e correção.

## Action items

- **Marcela:** compilar e circular as user stories; manter as cinco transcrições no repositório de requisitos.
- **Daniel:** garantir na arquitetura a trilha de auditoria de acesso e o tratamento de dado sensível em todos os canais.
- **Helena:** validar a versão final das user stories quanto a retenção e auditoria.

## Glossário acrescentado

- **Retenção:** prazo obrigatório de guarda do comprovante — **5 anos a partir da data da transação**.
- **Trilha de auditoria de acesso:** registro de quem consultou qual comprovante e quando.
- **Dado pessoal sensível:** os dados do comprovante, protegidos pela LGPD em todos os canais.
