# Registro das Aulas — Turma 1705 · BE-JV-010 (Avançado Caixa)

Arquivo de trabalho do docente. Para **cada aula**, a transcrição bruta (`.docx`) é organizada aqui em Markdown, com os **pontos-chave destacados**. O **Dossiê da turma** (vivo) abre o arquivo e é atualizado a cada aula; cada aula tem sua própria seção (pontos-chave → decisões → pendências → transcrição organizada por blocos).

> **Próximas aulas:** repetir este formato em `transcript/aula-0N.md`. Trazer o `.docx`, eu organizo igual.
> **Fonte bruta desta aula:** `1705-202606220-transcript.docx` (3h02). O Whisper estava com o idioma mal configurado → injeta trechos soltos em inglês ("Yeah", "Okay", frases curtas); o conteúdo em português está íntegro e legível.

---

## 🧭 Dossiê da turma (vivo)

> **Uma linha:** turma **legado-pesadíssima** (Java 6, EJB 2, JBoss 4, Hibernate 3, struts, JSF, IBM MQ, "cartão perfurado"), **mid-level com vários juniores e 2 veteranos-de-carreira (não em distribuído)**, muito engajada e bem-humorada. DDD: só teoria, nunca praticado. Mensageria moderna (Rabbit/Kafka/tópicos) é **novidade** para quase todos.

### Roster e perfis (13 identificados na fala; ~14 presentes, 2 faltaram)

| Aluno | Exp. | Perfil / stack | Notas para a docência |
|---|---|---|---|
| **Sandy da Silva Santos** | ~20 anos (desde 2005; infra→dev 2014); 6a Caixa, 43 anos | PHP, front, Java, Flutter/Dart, React; SI + pós BD | **Âncora reflexiva.** Trouxe a sacada "IA escreve o código, nós ficamos no alto nível". Admite dificuldade real com DDD ("decorei, mas não entendi"). Veio do intermediário (voto de confiança da chefe). |
| **Lucas Guimarães Gassert** | 15 anos dev; Caixa nov/24, 37 anos | Python/Ruby (dados) → Java; Comp. (não concluiu)+Economia | **Âncora experiente.** Time do André e Renan. |
| **Antonio David Breno Souza Lima** | ~5 anos (web, IoT, mobile); Caixa fev/25, 25 anos | Eng. Comp. UFC (não concluiu), cursando ADS | **Âncora + liaison de infra** (centraliza chamados; tem contato na segurança). Muito participativo. |
| **Alexandre Machado Rosa Filho** | 3–4 anos Java | **Full-stack Angular + Java/Quarkus**, área de riscos (CAPC) | **Stack mais moderna da turma.** Bom para puxar Quarkus. |
| **Figura Carrijo Viana** | 3 anos pré-Caixa; concurso mai/25 | Matemática + pós ciência comp.; negociou **Quarkus + Java 17** | Analítico. Sabe que a fila da área é **IBM MQ**; nunca viu tópicos em produção. |
| **Renan Sarto Gregório** | ~1 ano+; mai/25 | Java; mexe em **Java 6**, tem **1 API Java 21** (minoria); "17 com Quarkus" | Conhece a dor do legado. Time do André/Lucas. |
| **Matheus Henrique Pereira Vaz** | (incerto) | Estratégico; conhece a diretriz **nuvem pública Azure** e a falta de padronização | Sharp. Bom para arquitetura macro. Já levantou a restrição de rodar o projeto na máquina. |
| **André Felipe Corradi Botelho** | 1 ano (mudança de carreira), 27 anos; Caixa ago | Sistema de **20 anos: Java 6, EJB 2, JBoss 4, struts, Hibernate 3, XML**; negocia **Spring + Java 25** | Junior em tempo, **especialista do legado** — ouro para a "ponte do legado". Engajadíssimo. Time do Lucas/Renan. |
| **Leonardo Oliveira Faria** | 1–1,5 ano (1º emprego), BH | Eng. mecânica → ADS; 1º contato em bootcamp Ada | **Junior.** Boas ideias de calendário. |
| **Leanderson Freire Ficagna** | pouca (freelas); set/24, 25 anos | Comp. (parou 2021) → ADS; algo de Python | **Junior.** |
| **Leonardo Garcia Melo** | iniciante (na faculdade); mai/25 | — | **Junior.** Entrou com Figura. |
| **Marcos Chaves Paim** | pouca; out/24 | Algo de Python | **Junior.** Falou pouco. |
| **Relder Maia da Silva Batista** | (incerto) | Tem **livro de DDD**; participou da modelagem/validação | Interessado em modelagem; bom par para juniores. |

> Faltaram 2 no 1º dia (Jessé não obteve a lista no LMS — sistema bugado). Encaixar nos grupos depois.

### Grupos provisórios sugeridos (4×4 — ajustar no dia)
Cada grupo = 1 âncora + 1 stack-moderna + 1–2 juniores. Papéis rotativos (arquiteto · mensageria · cache/dados · testes/contrato).

| Grupo | Composição sugerida | Âncora |
|---|---|---|
| **G1** | Sandy · Alexandre · Leonardo O. · Marcos | Sandy |
| **G2** | Lucas · Renan · Leanderson · Leonardo G. | Lucas |
| **G3** | Antonio · Figura · André · (ausente) | Antonio |
| **G4** | Matheus · Relder · (ausente) · +1 | Matheus |

> Afinidades reais: **André+Lucas+Renan** (mesmo time/sistema); **Figura+Leonardo G.** (entraram juntos). Manter junto (domínio comum, ADR mais realista) ou separar (cross-poliniza) — a seu critério.

### Ambiente (apurado ao vivo)
- **Docker:** Desktop indisponível (licença); **WSL não liberado** → **Plano B confirmado**.
- **Nexus 🔴:** `dependency:get` no `caixa-group` **falhou** ao buscar o `spring-boot-starter` (mesmo fixando 3.3.13), embora `clean install` baixe nos projetos deles. Resolução dos fallbacks in-JVM é **incerta → risco real de Plano C** em mensageria.
- **Compartilhamento:** Teams da ADA sem OneDrive → docente **não** envia arquivos pelo Teams; só os alunos enviam. Canal = **WhatsApp** (+ e-mail pessoal). LMS liberando aos poucos.
- **Liaison de infra:** **Antonio David** (chamados de segurança/infra).

### Copilot / IA (acesso DESIGUAL — não pressupor)
- **Alguns** têm o agente do Copilot no IDE (quota mensal que **esgota** — André já tinha estourado); **outros só o M365 Chat**. **Antonio** usa agente no VS Code com vários projetos no workspace (bom caso de demonstração).
- **Implicação:** atividades com Copilot são **opcionais e resilientes** — quem tem demonstra; os demais acompanham a crítica.

### Modernização Caixa (quadro real)
- Baseline moderno realista = **Java 17 + Quarkus** (Java 21 é minoria; Java 25 é aspiração do André).
- Diretriz oficial: migrar p/ **nuvem pública Azure**; mensageria via **Azure Service Bus** (AMQP). Kafka começando em alguns sistemas; RabbitMQ oferecido em modernizações. Fila legada = **IBM MQ**.
- **Sequência:** este é o **módulo 1 de 3** → **(1) Arquitetura/Ágil II** → **(2) Introdução ao Quarkus** → **(3) Arquitetura de serviços e dados**. Quarkus é o **próximo** módulo.

---

# Aula 1 — Seg 22/06/2026 · 3h02

## ⭐ Pontos-chave
- **Perfil confirmado:** legado-pesadíssimo; **ninguém é sênior em distribuído**; DDD só na teoria. Valida toda a recalibração do plano.
- **Jessé previu "metade trabalha em legado moderno" e a turma corrigiu:** *"aqui é só legado praticamente"* — Java 6, EJB 2, JBoss 4, Hibernate 3, struts, JSF; Sandy: *"tem cartão perfurado aqui também"*.
- **Tese central de IA (combinada com a turma):** Sandy puxou e Jessé endossou — *"vamos digitar cada vez menos código; a IA escreve, nós ficamos na especificação e revisão"*. É o fio do módulo (foco em decisão, não em código).
- **DDD coberto na 2ª metade com engajamento** (problema PIX, event storming conceitual, **agregado + imutabilidade** na lista de lançamentos da fatura). **Não houve código nem leitura dos requisitos** → migra para a próxima aula.
- **Ambiente:** sem Docker/WSL → Plano B; **Nexus falhou no teste ao vivo** (risco de Plano C); Copilot desigual.
- **Modernização é real e iminente** (Quarkus/Java 17, nuvem Azure) e **Quarkus é o próximo módulo** — a relevância é literal.

## ✅ Decisões e combinados
- **Metodologia:** PBL + sala invertida, **adaptada** (1ª aula sem pré-leitura). Conteúdo é para **estudar antes**; aula é para **praticar e debater**. Foco **não** é escrever código.
- **Avaliação/NPS:** lembrar nos 10–15 min finais; meta de excelência **> 90**. Cada estrelinha (engajamento) "vale PLR" (brincadeira recorrente, bom clima).
- **Canal:** WhatsApp (QR no chat). Material da Aula 1 já enviado: requisitos, checklist, `aula-01-aluno.md` (PDF).
- **Intervalo:** 30 min, ~no meio (negociado para casar com o intervalo da área).
- **Calendário:** em aberto — vários conflitos (ver abaixo). **Decisão do docente (24/06): não nos preocuparmos com a grade por enquanto.**

## ⏳ Pendências levantadas
- **Nexus:** reexecutar `dependency:get` dos fallbacks (Qpid/EmbeddedKafka/Pact) com calma — tarefa de casa, via Antonio David.
- **Sandbox/Supabase:** explorar alternativa gerenciada de broker/banco (via Antonio).
- **Java por máquina:** versões variam (instalam o ZIP do site).
- **Projeto final:** Matheus alertou que muitos itens não rodam na máquina deles → H2 resolve o banco; pensar entrega.
- **Certificado:** Jessé não sabe se detalha carga horária/conteúdo (Matheus perguntou).

## 📝 Transcrição organizada

### Bloco 0 — Pré-aula: acesso, restrições e setup

**Antonio David Breno Souza Lima** · _0:05_ — Se você sair de novo, cara, beleza?

**Jessé Haniel** · _0:10_ — E aí, beleza? Boa tarde!

**Antonio David Breno Souza Lima** · _0:13_ — Ei, estamos aqui de novo. Boa tarde.

**Lucas Guimaraes Gassert** · _0:20_ — Boa tarde.

**Jessé Haniel** · _0:23_ — Goodbye.Em que turma que a gente se encontrou?

**Antonio David Breno Souza Lima** · _0:39_ — Foi é o de integração do começo do ano, começou em fevereiro, eu acho de Java back end.Isso.

**Jessé Haniel** · _0:57_ — Tem mais alguém por aqui?Daquela.

**Antonio David Breno Souza Lima** · _1:03_ — Reconheça não.

**Leonardo Oliveira Faria** · _1:03_ — Eu já tive aula com você também, Jessé, mas foi em outra turma, eu acho.

**Jessé Haniel** · _1:10_ — Boa.Então, nessa turma aqui já tá todo mundo ambientado, já tá todo mundo aí cheio de demandas e projetos.

**Antonio David Breno Souza Lima** · _1:24_ — Isso aí.

**Jessé Haniel** · _1:26_ — Todo mundo já esperando o PLR.Essa parte boa, né?Sabe me dizer quantos somos nessa turma?Aqui para mim, o sistema está meio bugado, não está aparecendo a presença.Então já estou entrando em contato aqui com o nosso suporte para verCity **** to Polyphoza.

**Antonio David Breno Souza Lima** · _1:59_ — Isso é tudo lá pelo LMS de novo?

**Jessé Haniel** · _2:02_ — Mhm.

**Antonio David Breno Souza Lima** · _2:03_ — Eu acho que o meu não atualizou.

**Jessé Haniel** · _2:08_ — I sent you.Agora que vocês já são 15 veteranos aí na caixa, como é que está as restrições de acesso de vocês?

**Antonio David Breno Souza Lima** · _2:25_ — Mesmas restrições, pelo menos na última trilha, a gente descobriu que consegue falar com o pessoal da segurança para liberar os envios de código.

**Jessé Haniel** · _2:25_ — Mais tranquilo.Yeah.Boa.

**Figura Carrijo Viana Figur** · _2:39_ — A gente consegue falar com o pessoal da segurança para liberar o envio de código.

**Antonio David Breno Souza Lima** · _2:44_ — É, teve um pessoal da minha última turma que acho que foi.Não lembro o nome do professor, mas eles falaram com o pessoal de segurança e liberaram durante o módulo todo o envio de código para pela plataforma do LMS.

**Jessé Haniel** · _2:59_ — Olha aí, que maravilha! Ou seja, o que isso significa? Quem tiver qualquer problema, entra em contato aí com quem disse no David e ele tem o caminho das pedras.

**Antonio David Breno Souza Lima** · _3:08_ — E aí eu entro em contato com a pessoa que entrou em contato com a segurança, que eu também não lembro quem foi.

**Jessé Haniel** · _3:11_ — Exatamente!E para facilitar toda essa troca, grupo do WhatsApp, aí o QR Code na tela.Por favor, apontem seus celulares, entrem.

**Figura Carrijo Viana Figur** · _3:29_ — A mim não está na tela ainda não.

**Jessé Haniel** · _3:32_ — Desculpa no chat.

**Figura Carrijo Viana Figur** · _3:34_ — At.

**Jessé Haniel** · _4:09_ — Gente, hoje a gente está com a atenção dividida aqui.Um olho na alma, outro olho na Áustria.Ou não?

**Antonio David Breno Souza Lima** · _4:25_ — Esse ainda não abri aqui não.

**Jessé Haniel** · _4:57_ — Gente, eu acho que somos são nós.Somos 9 aqui na.My only.Até agora eu não tenho acesso aqui a lista completa.

**Leonardo Oliveira Faria** · _5:15_ — OLMS aqui para mim também está da turma antiga, está 1339 ainda não apareceu 1705 para mim não.

**Jessé Haniel** · _5:19_ — Yes.Okay.Não, beleza. Mas aí pra gente não ficar estendendo muito essa espera.Tem mais gente chegando aí. Vamos lá começando?E aí a gente vai adaptando aquilo que for possível, está?Deixa eu ver como que eu vou compartilhar aqui com vocês, se eu consigo enviar o site.das últimas vezes não conseguiaQualquer coisa eu envio pelo WhatsApp. Se eu enviar pelo WhatsApp, vocês conseguem acessar?

**Sandy da Silva Santos** · _6:04_ — Sim.

**Jessé Haniel** · _6:04_ — Tipo, vocês tem como pegar do celular para o computador ou fica só no celular?

**Antonio David Breno Souza Lima** · _6:11_ — Difícil, acho que enviando pelo e-mail daria, mas ia demorar um tempinho para chegar.

**Jessé Haniel** · _6:11_ — Okay.Tá, então talvez eu vou projetando aqui.They usingPode funcionar?Só mais alguns instantes.Beleza, thanks.Chegou aí para vocês?Mahravida.

**Leanderson Freire Ficagna** · _8:22_ — Chegou.


### Bloco 1 — Abertura e boas-vindas

**Jessé Haniel** · _8:24_ — Pessoal, mais do que oficialmente, agora são 3 horas e 10 minutos, 15 horas e 10 minutos.Muito boa tarde, sejam muito bem-vindos. Para quem ainda não me conhece, meu nome é Jester. Estarei aqui com vocês ao longo de todo esse módulo.Resumindo um pouquinho da minha história, que já é um tanto longa, mas sou natural de Natal, estou morando em João Pessoa, já tive um tempo nos Estados Unidos, voltei ano passado e estou aqui na terrinha matando saudade. Um lugar maravilhoso.Sou arquiteto de software no TJPB desde 2014 e já trabalhei um tempo fazendo consultorias de software na área de pagamentos, de financeira, na parte também de seguros.tive uma experiência aqui na própria água mesmo como que o durante uma fase onde a gente estava desenvolvendo alguns sistemas E aí como eu estava disponível aceitei aí essa oportunidade para esse desafio fiquei dois anosMas estou aqui como instrutor aí na coordenação desde 2020, que é quase 6 anos, agora em setembro a gente completa de 6 anos, tá?Então, alguns de vocês já me conhecem aí da experiência que a gente teve lá no embarque TI, provavelmente com o módulo de ágil e transformação digital, lá onde a gente falava sobre o desenvolvimento do software ágil, sobre as metodologias de gerenciamento.e de projetos ágeis também sobre a parte de transformação digital sobre a parte de inteligência artificial e talvez alguns aqui podem a gente pode estar juntos no módulo seguinte que era oNivelamento de lógica de programação e orientação de objetos, onde a gente fazia ali introdução, uma revisão ao Java.Tá, mas um a gente estava mais na parte de projetos, na parte de era mais uma coisa introdutória, uma coisa mais de nivelar todo mundo da realidade de 2025, 26.E o outro era mais ali, uma parte de introdução, uma linguagem aqui.Digamos que o calor está um pouquinho mais grosso, tá? E parabéns! Estou muito feliz por encontrar vocês aqui, porque essa é a turma de nível 3, é a turma avançada. Então, que bom encontrar vocês por aqui. Tem uma outra turma também que estáÉ acontecendo nesse nesse mesmo momento de nível intermediário, que tem módulos parecidos, mas aqui é onde a gente tem aquelas discussões, é.Onde a gente não se prende tanto a tecnologia e onde a gente pode usar para praticamente tudo como resposta aquela.Aquela expressão que só quem é senhor pode dizer.Vocês sabem qual é aquela expressão, aquela palavrinha que começa a esconder?

**Antonio David Breno Souza Lima** · _11:38_ — Depende.


### Bloco 2 — Apresentações da turma

**Jessé Haniel** · _11:40_ — Is that for me?A resposta para quase tudo.Bom, mas antes da gente entrar efetivamente aqui.Conteúdo do nosso módulo.Queria também conhecer um pouquinho de vocês, como somos poucos, então acho que dá para cada um abrir aí microfone e câmera, se apresentar rapidamente, tá?É só dizer nome de onde está falando e resume brevemente aí sobre a tua formação, a tua experiência, se vem mudando de área, se é formado em computação, enfim.

**Antonio David Breno Souza Lima** · _12:26_ — Então, deixa eu começar, então. Sou Antônio David. Eu trabalho há uns 5 anos já com desenvolvimento de software, principalmente com web, mas já trabalhei com RP, com integração de dispositivo de rastreamento de automóveis.Com mobile, eu sou do Ceará, do interior do Ceará. Eu cursei engenharia de computação no UFC por 3 anos, mas acabei não conseguindo concluir com a vinda da pandemia aí e já estava trabalhando, não consegui voltar quando voltou presencial.Hoje eu estou fazendo ADS no minha esquina aí mesmo pelo diploma e entrei na caixa aqui faz pouco tempo. Entrei em fevereiro, comecinho de fevereiro.E basicamente é isso.Tá, tô com 25 anos.

**Jessé Haniel** · _13:15_ — I mean that.

**Andre Felipe Corradi Botelho** · _13:16_ — The movie, say it.

**Jessé Haniel** · _13:19_ — Estamos te ouvindo, só não estamos te vendo, mas estamos ouvindo bem.

**Andre Felipe Corradi Botelho** · _13:21_ — Ah, pera aí, pera aí.Ai, eu estou sem camiseta, espera aí.Pode uma outra pessoa rapidão.

**Leonardo Oliveira Faria** · _13:32_ — Eu posso ir. Meu nome é Leonardo, meu primeiro contato com programação, meu primeiro aprendizado foi aqui na ADU. Foi uma imersão da Sinker, chamava Let's Code. Minha formação é na engenharia mecânica, na verdade, estou fazendo um ADS.E também no mesmo esquema.É, e a minha primeira experiência profissional na área também foi aqui na caixa. Está sendo aqui na caixa entrei em janeiro de 2025. Fiz um ano para fazer um ano e meio agora e estou em BH.Manta aí, André, botou a camisa e pode ir, pode ir.

**Andre Felipe Corradi Botelho** · _14:18_ — Opa, what's saying?Aproveitou o almoço, tomei um banho.

**Jessé Haniel** · _14:24_ — Aí estou te vendo.

**Andre Felipe Corradi Botelho** · _14:26_ — Por causa disso, pô, meu nome é André, tenho 27 anos, sou de São Bernardo do Campo, em São Paulo, estado de São Paulo. É sou formado em análise de movimento de sistemas.É, sou desenvolvedor só há um ano, apesar dos 27 anos. Eu me formei, mas fui trabalhar com outra coisa. É, entrei na caixa em agosto.E é isso.

**Jessé Haniel** · _14:52_ — What?

**Figura Carrijo Viana Figur** · _15:04_ — Eu não prestei atenção, se tem uma ordem, mas eu vou falar.

**Jessé Haniel** · _15:07_ — Não tem ordem.

**Figura Carrijo Viana Figur** · _15:11_ — Eu sou o figura.Eu entrei no concurso da caixa em maio de 2025.And.Eu sou formado na graduação em matemática, na pós-graduação em é ciência da computação.Matemática aplicada a ciência da orientação.Eu trabalhei como desenvolvedor antes de entrar na caixa, durante 3 anos.E aí eu fiz, eu fiz um dos cursos com, ou seja, você dá entrada na caixa e eu acho que foi o primeiro módulo de algum curso e.

**Jessé Haniel** · _15:57_ — Point.

**Figura Carrijo Viana Figur** · _16:00_ — E?Putz, eu estou aqui há um tempo e *****, que tem trabalho, Hein? *******, *****, haja trabalho aqui dentro, mas isso está legal.

**Jessé Haniel** · _16:13_ — Maravilha.

**Sandy da Silva Santos** · _16:27_ — Foram todos pessoal que alguém vai falar aí na frente.

**Marcos Chaves Paim** · _16:30_ — Well, what's falling?

**Leonardo Garcia Melo** · _16:31_ — Uh-huh.

**Jessé Haniel** · _16:33_ — Bye.

**Marcos Chaves Paim** · _16:33_ — Eu sou o Marcos, eu estou trabalhando na Caixa desde outubro do ano passado. Antes disso, eu tive um pouco de experiência trabalhando com Python, desenvolvimento em Python. E é isso.

**Jessé Haniel** · _16:51_ — And.

**Lucas Guimaraes Gassert** · _16:54_ — Okay.

**Leonardo Garcia Melo** · _16:54_ — Eu sou Leonardo, eu ainda estou na faculdade e eu comecei a trabalhar na caixa em maio de 2025, junto com figura.

**Jessé Haniel** · _17:09_ — Boa.

**Leanderson Freire Ficagna** · _17:12_ — Sou Leanderson, posso sou Leanderson tenho 25 anos entrei na caixa setembro de 2024 eu cheguei a cursar a ciência da computação 2019 né mas eu

**Figura Carrijo Viana Figur** · _17:12_ — Mesmo.

**Leanderson Freire Ficagna** · _17:28_ — Parei em 2021 por conta da pandemia, né? Aí agora eu estou cursando, é ADS para ver se pega o diploma e consigo subir aí. E é isso aí. E antes de daqui da caixa, eu tive pouca experiência, né? Eu fazia alguns freelancers.E é isso cara, pega a pena essa um pouco de Python. É isso.Eu não conheço nenhum que usa PHP aqui na caixa.

**Sandy da Silva Santos** · _18:05_ — É mais departamental, né? É.

**Antonio David Breno Souza Lima** · _18:06_ — Eu sei de um, eu sei de um, porque quando você abre aqui, pelo menos eu, quando abro, ele dá um erro de PHP, ele não abre.

**Jessé Haniel** · _18:13_ — É que normalmente, assim, coisas em PHP, normalmente a gente aumente um pouco, né?

**Antonio David Breno Souza Lima** · _18:19_ — Ah, você está com preconceito com as versão antiga do PHP? Não pode.

**Jessé Haniel** · _18:19_ — Vocês very?

**Sandy da Silva Santos** · _18:21_ — Broad.

**Jessé Haniel** · _18:22_ — Então eu contei um pouquinho da minha experiência aqui, eu não mencionei PHP.Mas todo mundo já passou um dia ou vai passar, quem sabe?Vamos lá.

**Sandy da Silva Santos** · _18:34_ — É o PHP é lendário, né pessoal? É o realmente muita coisa em PHP, né? Na web. Bom pessoal, boa tarde. Meu nome é Sand. Eu trabalho com tecnologia aí desde 2005.É, trabalhei com infraestrutura mais ou menos a metade desse período, 2014 eu passei para desenvolvimento efetivamente.É trabalho aqui na caixa, vai fazer 6 anos.Eu sou formado em sistema de informação e tenho pós-graduação em banco de dados. Então, e aí eu até vi aí, realmente minha chefe me colocou nesse curso. E ela falou assim, aí eu olhei, era java avançado, aí eu vi que teve aí uma galera do intermediário, então até agradeço aí.né? Pela confiança da minha chefe em colocar no avançado, né? Espero aprender com vocês aí.

**Jessé Haniel** · _19:28_ — Sandy, é isso. Você recebeu o voto de confiança. Agora vai lá, honra a camisa e aí, quem sabe ela já não está com planos aí avançados para você?

**Antonio David Breno Souza Lima** · _19:40_ — Deus te ouça.

**Sandy da Silva Santos** · _19:40_ — Eu vou ter que dar do meu jeito. Nesse tack, pessoal, eu já trabalhei com PHP, já trabalhei com é muito front-end, tá pessoal? Com alguns frameworks aí é tem atuado com Java e também gosto da parte mobile. Alguém falou aí.Eu estou atuando com, mas em questões pessoais mesmo aqui com o Flutter, né? É que usa Dart ali, tem atuado aí para ser esse meio que multiplataforma, né? Estou também com um projeto aqui em React, mas entre Flutter e React, eu prefiro Flutter, sabe?He said this.E gosto do Java também, tá, pessoal? Apesar de que eu achar que depois que essa inteligência artificial avançar, aí a linguagem em si, que é feito para nós, seres humanos, eu acho que vai deixar de ser tão relevante assim. Acho que.Ela vai dar o comando para a máquina e ela vai fazer o binário.

**Jessé Haniel** · _20:36_ — Muito bom você ter tocado nesse ponto, porque esse que vai ser aí, digamos o hora, esse que vai ser o fundamento de tudo que a gente vai trabalhar daqui para frente, tá?

**Sandy da Silva Santos** · _20:49_ — Beleza, e tenha paciência comigo pessoal, sei que tá a galera tudo nova aí, né? Eu tenho 43 anos, tá? Sou casado, tenho 4 filhos, né? Então tenha paciência aí com o velho, sou mais 40.

**Jessé Haniel** · _21:02_ — Você acabou de perder meio o ponto por falar dessa história de velho, porque eu sou desse time também, então.

**Sandy da Silva Santos** · _21:08_ — Nossa, ele está com cara de novo aí, viu?

**Jessé Haniel** · _21:12_ — Boa.

**Lucas Guimaraes Gassert** · _21:19_ — Opa, boa tarde pessoal. Desculpa a voz aí se estiver muito ruim que eu.

**Jessé Haniel** · _21:24_ — Imagine.

**Lucas Guimaraes Gassert** · _21:24_ — Fiquei meio sem voz esse final de semana. Bom, sou Lucas, eu tenho 37 anos, eu sou nascido em São Paulo, mas hoje eu moro em São Bernardo. Depois que eu casei, a gente mudou para cá. Eu atuo já faz 15 anos com desenvolvimento. Eu comecei a atuar.

**Jessé Haniel** · _21:27_ — He does.

**Lucas Guimaraes Gassert** · _21:42_ — Quando eu estava na faculdade de computação, ciência da computação, que eu não terminei.Mas depois eu me formei em economia, mas eu já estava trabalhando com desenvolvimento e acabei ficando no mercado mesmo. Mas de qualquer forma, aqui tem tudo a ver. Hoje eu estou aqui na caixa já desde novembro de 2024, estou atuando.noções online aí junto com o André e acho que o Renan está aí também. Hoje eu estou atuando com o Java aqui, né? Mas antes eu tinha a minha experiência, era principalmente com Python eRuby, principalmente na parte de tratamento de dados, tá? Pra mexer com algumas outras coisas também, mas esse é o principal.Thanks.

**Jessé Haniel** · _22:33_ — O.E agora faltam poucos.When I listen.

**Alexandre Machado Rosa Filho** · _22:53_ — É, eu me embaranei aqui com os links, não tava achando o link da aula, eu perdi exatamente qual que é a ordem, etc. Como é que funciona?

**Jessé Haniel** · _23:02_ — a gente não definiu ordem tá indo aqui voluntário então só uma apresentação rápida mesmo

**Alexandre Machado Rosa Filho** · _23:05_ — Ah, tranquilo.Tranquilo. Boa tarde, eu sou o Alexandre. Eu trabalho aqui no CAPC, em uma comunidade de riscos, de desenvolvedor full stack. Eu mexo tanto com o Angular quanto com o Java, mais especificamente no Quarkus.Eu venho estudando o Java faz uns 3, 4 anos por aí. Estudando e trabalhando com o Java uns 3, 4 anos. Estou bem animado para o assunto do curso. Não é um assunto de tão fácil.É conteúdo assim na internet, conteúdo de qualidade, então eu fico, tô animado aí com a ideia. E é isso.

**Jessé Haniel** · _23:56_ — Boa maravilha!

**Renan Sarto Gregorio** · _24:01_ — Boa tarde, pessoal. Meu nome é Renan, também trabalho aqui no Senac junto com André, com Lucas.

**Jessé Haniel** · _24:03_ — What that?

**Renan Sarto Gregorio** · _24:10_ — Eu entrei aqui lá para maio de maio de 2025, mas um pouquinho mais de um ano. Também estou trabalhando com Java. Estava um pouquinho mais antigo do que a gente está aqui, ainda Java 6 que a gente mexe aqui.Why is?Quase sempre mexi mais com Java do que outras coisas também.That's coming, chase.


### Bloco 3 — Visão do módulo e metodologia (PBL + sala invertida)


### Bloco 4 — Legado × moderno: o raio-X da turma

**Jessé Haniel** · _24:37_ — Maravilha, agora fomos todos?Falta alguém?No, man.Beleza, gente.Bom, então vamos lá.Este é o primeiro módulo, tá? Deixa eu ver aqui o anti serão.Nós temos aqui, serão 3 módulos.A gente tem arquitetura de história ágil dois, introdução ao Quarkus e depois arquitetura de serviços e dados. Que aqui dentro a gente chama de arquitetura avançada, arquitetura dois.Beleza? E tem também esse daqui do Quarkus. Então, tanto esse primeiro quanto o segundo, eles vão ser aqui adaptados para a realidade de vocês, tá? Então, de acordo com o que a caixa pediu para gente, esses módulos aqui, elesEram bons matches, mas eles não eram exatamente o que vocês trabalham aí no dia a dia de vocês. Então a gente vai ter aqui alguns materiais complementares, alguns PDFs que vão ser disponibilizados para.Ajustar melhor a realidade que vocês têm aí, certo? O último, eu acredito que ele é praticamente idêntico. Bom, então vamos lá ver o que a gente tem nesse primeiro módulo, então pra agenda de hoje, metodologia Adelga, a maioria de vocês aqui mencionaram aqui na equipe que já tiveram.algum contato, então isso aqui vai ser bem tranquilo. Conteúdo do módulo, cronograma, teremos dois projetos e aí eu vou explicar depois para vocês, avaliação e rubrica e aí a gente, porque entram aqui os nossos combinados. Maravilha, hein? Nas metodologias dadas, a gente tem aqui a aprendizagem ativa comPBL e sala de aula invertida. PBL significa Problem Based Learning, então é uma aprendizagem que ela sempre parte de um problema e depois a gente vai discutindo a teoria, tá? Então, em vez de a gente chegar e aprender um monte de teoria que a gente fica durante todo aquele tempo de exposição,viajando, tentando imaginar em que que aquilo ali se aplica e às vezes a gente nem tem essa pergunta respondida, né? Às vezes a gente fica, tá, mas e aí, para que que eu tô aprendendo isso? Aí a gente faz o contrário, que esse contrário ele tem mais a ver com que as coisas que a gente aprende naturalmente, aquelas que a gente realmentedesempenho, gente, tem um desafio ali para resolver e a partir daquilo eu vou buscando aprender, eu vou buscando ali capacitações, eu vou buscando novas habilidades para conseguir vencer aquele desafio. Então essa é a PBL, sempre a gente vai terO problema que vai ser o porteador da nossa aula.A sala de aula invertida, ela realmente inverte os elementos da sala de aula. Normalmente, na sala de aula tradicional, a gente tem um professor em destaque falando um monte de coisa e os alunos de maneira mais passiva, ouvindo aquilo ali.A gente nada entende que esse é um conceito errado porque coloca o professor como se fosse o oráculo de tudo e os alunos como se fossem aqueles que são quase esponjas que tem que tentar absorver o máximo.Quais são então aí as consequências dessa inversão? A gente coloca os alunos como destaque. Então a nossa medida de sucesso sempre vai ser o quanto vocês estão aprendendo, o quanto vocês estão avançando, mas com grandes poderes, grandes responsabilidades.Também coloca vocês numa posição menos passiva, menos de ficar apenas ouvindo. Então preciso que vocês participem ao máximo que vocês interajam aqui nos momentos de discussão e também.O nosso ambiente aqui de aula, ele não vai ser apenas para exposição, ele vai ser muito mais para discussão. Então, no modelo tradicional, durante a aula, a gente escuta, escuta, escuta, depois a gente vai para casa ou onde for praticar aquilo ali em exercícios.A gente inverte, então o momento de vocês serem expostos ao conteúdo é fora do nosso horário de aula e o nosso horário de aula é para a gente praticar e discutir isso juntos, tá?Adaptado por quê? Porque o nosso primeiro encontro está sendo mora aqui, então não teve como vocês fazerem nenhuma atividade antes. Então, principalmente essa primeira, ela vai ser mais adaptada. Depois, conto muito com a colaboração de vocês para que a gente possa colocar isso aqui em prática.So.Primeira metade da nossa aula, a gente vai ter o problema, a gente vai ter a discussão e a gente vai ter aqui uma possível solução ao vivo. Na segunda parte da aula, o que a gente vai fazer? Vocês evoluem a solução e também vocês trabalham juntos.num projeto em grupo. Então a gente vai ter um estudo e projeto acontecendo dentro aqui do nosso horário diálogo, onde eu vou poder estar acompanhando vocês, passando orientação, tirando dúvidas, trocando ideias.Beleza então uma turma de vocês é avançada então espero que debates em alto nível contando bastante com a experiência de vocês A grande maioria trouxe aqui e já tem experiência com projetos ou e se não tinham tanta experiênciaEstão tendo trabalho para caramba, né? Então com certeza essa curva de aprendizado e experiência, ela é bastante acentuada.E nenhum de vocês mencionou que trabalham com sistemas legados. Então acho que isso aqui era uma previsão minha, mas que eu errei, né?Ou temos alguém lidando com legados?

**Antonio David Breno Souza Lima** · _30:47_ — Acho que sempre tem algum sisteminha legado junto do guarda-chuva da gente, não é?

**Andre Felipe Corradi Botelho** · _30:47_ — Todo mundo, não?

**Renan Sarto Gregorio** · _30:48_ — Yeah.

**Sandy da Silva Santos** · _30:51_ — É aqui é aqui é legado que.

**Leonardo Garcia Melo** · _30:51_ — Acho que todo mundo aqui.

**Renan Sarto Gregorio** · _30:52_ — A gente aqui.

**Alexandre Machado Rosa Filho** · _30:53_ — É, tem um ou outro.

**Figura Carrijo Viana Figur** · _30:55_ — Aqui é só legados, praticamente.

**Andre Felipe Corradi Botelho** · _30:56_ — Bom, nós temos um sistema de 20 anos Java 6.

**Leanderson Freire Ficagna** · _30:58_ — Okay.

**Jessé Haniel** · _31:00_ — Só legados? Então, a partir do Java 6 eu peguei, mas e aí? Tem uns JSFs aí, tem uns struts, tem umas coisas assim. Eu mexo com o Java 8 struts.

**Sandy da Silva Santos** · _31:02_ — And.

**Antonio David Breno Souza Lima** · _31:07_ — Não, no meu time tem um sistema com JSF, prime faces.

**Andre Felipe Corradi Botelho** · _31:07_ — Thanks, threat.

**Renan Sarto Gregorio** · _31:07_ — É stretch JSP

**Andre Felipe Corradi Botelho** · _31:13_ — TJB 2 struts.EJB 2 é que mais JBoss 4.

**Jessé Haniel** · _31:19_ — Faz tempo que eu não ouvia isso.

**Renan Sarto Gregorio** · _31:19_ — J Boss 4.

**Jessé Haniel** · _31:23_ — Oh.

**Antonio David Breno Souza Lima** · _31:24_ — Meu Deus.

**Andre Felipe Corradi Botelho** · _31:24_ — É, tem mais coisas, é pior, calma, piora.

**Leonardo Oliveira Faria** · _31:27_ — Mm.

**Sandy da Silva Santos** · _31:27_ — Aqui tem cartão cartão perfurado, tem aqui também.

**Andre Felipe Corradi Botelho** · _31:28_ — Ajuda aí, Lucas.Quase.

**Renan Sarto Gregorio** · _31:33_ — Hibernate 3.

**Andre Felipe Corradi Botelho** · _31:34_ — Hibernate não é 3.

**Renan Sarto Gregorio** · _31:37_ — Acho que é 3.

**Andre Felipe Corradi Botelho** · _31:38_ — Is 3, é?

**Jessé Haniel** · _31:38_ — Vocês estão falando aí, está chegando o cheiro de mofo por aqui.

**Andre Felipe Corradi Botelho** · _31:42_ — É, não é hibernate 3, você tem que mapear no XML as classes do as entidades.

**Jessé Haniel** · _31:49_ — Yes.Olha, o que eu posso dizer é que euE me solidarizo com vocês, está?

**Renan Sarto Gregorio** · _32:00_ — Não fica assim não, é divertido.

**Jessé Haniel** · _32:00_ — Beleza, gente. Mas por outro lado, vocês tem alguma coisa aí de já no 21?

**Antonio David Breno Souza Lima** · _32:10_ — Acho que os sistemas mais novos aqui estão com 17.É difícil ter um já com 21.

**Renan Sarto Gregorio** · _32:14_ — Eu já tenho, já tenho API com o Java 21, mas é a minoria.

**Sandy da Silva Santos** · _32:19_ — Já ouvi falar, é?

**Jessé Haniel** · _32:21_ — As coisas de quartos.

**Renan Sarto Gregorio** · _32:21_ — Okay.

**Figura Carrijo Viana Figur** · _32:21_ — É, a gente, a gente conseguiu. A gente conseguiu negociar aqui no sistema uma arquitetura que a gente está começando a implementar o Quartus com o Java 17. A gente não conseguiu negociar o Java 21 ainda não.

**Renan Sarto Gregorio** · _32:23_ — Aqui é 17 com quarks.

**Andre Felipe Corradi Botelho** · _32:28_ — Okay.

**Figura Carrijo Viana Figur** · _32:38_ — English.

**Andre Felipe Corradi Botelho** · _32:38_ — aqui eu tô eu tô tentando negociar um Spring com 25 já 25 ó então

**Renan Sarto Gregorio** · _32:44_ — ******* Andressa é corajoso

**Jessé Haniel** · _32:44_ — Ousado.

**Andre Felipe Corradi Botelho** · _32:45_ — Yeah, logically, I think you should talk with.

**Jessé Haniel** · _32:50_ — Mas é isso, gente. E assim, isso a gente comentava lá, para quem fez o módulo de ágil e transformação. Isso depende muito de como a gente argumenta. Então, a gente tem que argumentar sempre com a visão de gestão de produto.Então, quando vocês vão pedir, ah, eu quero fazer um projeto aqui começando com Java 25.Normalmente, normalmente eu falo isso porque eu já escutei bastante e eu já falei muito isso. O gestor vai perguntar, por que o 25?Ah, mas é porque saiu agora recente e tem um Monte de novidade massa. A resposta com certeza vai ser o quê? Não.não vai correr um risco de adotar alguma coisa novaSó por causa da versão mais nova?Ele sempre vai trabalhar com gestão de risco e ele sempre vai trabalhar, né? Eles sempre vão trabalhar com gestão de risco e com o retorno esperado. Qual é o ROI?Qual é o retorno esperado em cima desse investimento? Eu estou investindo, eu estou assumindo um risco aqui. O que eu vou receber de volta?E aí, então, como é que a gente deve argumentar? Olha, acabou o suporte. Se estourar qualquer vulnerabilidade aí, dançou.Você sabe quanto que os bancos perderam recentemente por causa de ataques e vulnerabilidades de segurança?Coloca isso como argumento.Vai ajudar bastante a vocês chegarem no 25.Boa, então essa é a nossa metodologia, dúvidas, perguntas.Vamos fazer o seguinte, vou fazer uma enquete rápida aqui. Quem já foi alunoado, levanta a mão, levanta a mão sempre aqui na reação, tá gente? Assim.

**Andre Felipe Corradi Botelho** · _35:01_ — Eu acho que todo mundo não.

**Jessé Haniel** · _35:02_ — Leanderson e Mateus, não foram?

**Leanderson Freire Ficagna** · _35:05_ — Oi, pera aí, é que caiu aqui, minha internet ficou muda? Eu não acompanhei aí o que que?

**Jessé Haniel** · _35:09_ — Data.Quem já foi alunoada?

**Leanderson Freire Ficagna** · _35:14_ — Ah eu já fui no fui na na trilha de arquitetura né no logo quando eu entrei aqui

**Jessé Haniel** · _35:15_ — And nessa turma.Mhm.My pills.

**Matheus Henrique Pereira Vaz** · _35:26_ — Ainda know, ainda know.

**Jessé Haniel** · _35:28_ — Ainda não maravilha, gente. Então vamos fazer o seguinte, três conselhos rápidos sobre essa parte de metodologia que vocês podem dar para o Mateus.Tendo 3 voluntários aqui para falar em relação a essa parte de metodologia, o que vocês podem falar para uma vez?Podem abaixar as mãos todas.

**Antonio David Breno Souza Lima** · _35:59_ — Sempre participativamente para ganhar as estrelinhas aí.

**Jessé Haniel** · _36:05_ — Já que você lembrou das estrelinhas, então vamos lá, primeira turma.

**Antonio David Breno Souza Lima** · _36:14_ — Lembrando que cada estrelinha é um bônus de 20% na PLR.

**Jessé Haniel** · _36:15_ — Yes.

**Alexandre Machado Rosa Filho** · _36:19_ — O.

**Jessé Haniel** · _36:21_ — 20%, na verdade, 15, porque aí 5% vocês repassam.Que mais?

**Matheus Henrique Pereira Vaz** · _36:30_ — beleza tá tranquilo é só tu me avisar então aí com essas estrelinhas que eu dou um jeito de conseguir te passo no pix fechouHey, nice.

**Jessé Haniel** · _36:37_ — Boy.E aí gente, que mais?

**Alexandre Machado Rosa Filho** · _36:44_ — É, tem as leituras de antes das aulas, né? As leituras e exercícios.

**Jessé Haniel** · _36:50_ — Maravilha.This is the último.

**Matheus Henrique Pereira Vaz** · _37:10_ — Provavelmente deve ser alguma coisa ligada à prática, a praticar aquilo que foi.Explicado.

**Jessé Haniel** · _37:18_ — Boa, tô vendo que você é um cara esperto, já pegou no ar e ninguém falou, mas você captou muito bem.

**Matheus Henrique Pereira Vaz** · _37:22_ — Tem que ser ligeiro, mano. Garantindo as 20 garantindo as 20


### Bloco 5 — Conteúdo do módulo

**Jessé Haniel** · _37:26_ — Maravilha, isso aí, gente, conteúdo do módulo, o que é que nós teremos? Microsserviços e DDD, fronteiras de domínio, consistência distribuída e padrão salário, cache compartilhado com Redis, mensageria, filas com Redis MQ e tópicos com o Kafka.resiliência, retry, circuit breaker, dual queue e contract testing com o PACT, certo? Então esses aí são os principais temas que nós vamos trabalhar neste módulo.De tudo isso aqui, tem alguma coisa que vocês não usam na caixa?De repente, ah, não é a gente usa aqui active. Ah, não, a gente usa não sei o que lá.

**Andre Felipe Corradi Botelho** · _38:13_ — Aqui é um desenvolvimento cascajo com arquitetura bemSim, go horse zone.It.Sem filas, sem mensageria.

**Matheus Henrique Pereira Vaz** · _38:27_ — Sim, essa é uma das, inclusive, um dos problemas que a gente tem aqui na caixa, que é a gente não tem padronização das coisas.

**Andre Felipe Corradi Botelho** · _38:32_ — Exatamente.Cobertura de testes zero.

**Figura Carrijo Viana Figur** · _38:39_ — É, eu não sei dizer se o Rabbit, se o Rabbit MQ é homologado aqui, acho que eu nunca olhei isso, mas eu sei que a nossa solução para filas, até onde eu sei, costuma ser o IBM MQ.

**Andre Felipe Corradi Botelho** · _38:45_ — Yeah.

**Figura Carrijo Viana Figur** · _38:53_ — É kafta. Eu não sei se é homologado, eu não sei. Eu não vi um sistema ainda trabalhando com tópicos, mas filas eu sei que costuma ser BMMQ.

**Jessé Haniel** · _38:54_ — Legal.

**Lucas Guimaraes Gassert** · _39:00_ — I'll stick liter things, things.

**Andre Felipe Corradi Botelho** · _39:01_ — Yep.

**Leonardo Garcia Melo** · _39:01_ — Okay.I guess all website, website.

**Andre Felipe Corradi Botelho** · _39:05_ — Kafka.

**Lucas Guimaraes Gassert** · _39:07_ — Tem sistema que está começando a implementar o Kafka já, então eu sei.

**Andre Felipe Corradi Botelho** · _39:11_ — What was your rep to you?

**Jessé Haniel** · _39:13_ — Thank you.

**Alexandre Machado Rosa Filho** · _39:15_ — Se eu não me.

**Jessé Haniel** · _39:15_ — Apareceu aí.

**Andre Felipe Corradi Botelho** · _39:18_ — E pelo que eu sei, é.

**Alexandre Machado Rosa Filho** · _39:18_ — É, se eu não me engano, tá tendo uma modernização do Syrique que tá utilizando o WebTMQ e o Kafka eu sei que usa, não sei onde, mas eu sei que usa.

**Jessé Haniel** · _39:20_ — Boa.

**Andre Felipe Corradi Botelho** · _39:26_ — É o Kafka, tem um sistema já, o ciclista usando, vai começar a usar para alguns tópicos para os outros sistemas consumirem. E o RabbitMQ, a gente estava vendo da modernização aqui do Sinac, até foi oferecido. Você não quer usar o RabbitMQ para as filas?

**Jessé Haniel** · _39:32_ — Boa.Mm-hmm.

**Andre Felipe Corradi Botelho** · _39:45_ — Então, se eles ofereceram

**Matheus Henrique Pereira Vaz** · _39:48_ — Assim, teoricamente, a diretriz hoje é a gente migrar todos os serviços que são tanto desenvolvimento próprio aqui, que a gente chama de desenvolvimento interno ou departamental, e a gente tinha o desenvolvimento que era na nuvem on-premises.

**Jessé Haniel** · _39:49_ — Olha.

**Andre Felipe Corradi Botelho** · _39:49_ — Don't move it.

**Matheus Henrique Pereira Vaz** · _40:07_ — E aí, a diretriz é que a gente faça toda a migração desses sistemas e criação de novos para nuvem pública. Então, utilizando os recursos Microsoft. Então, por exemplo, em termos de mensageria, seria a mensageria do próprio Azure, que é baseado em.

**Jessé Haniel** · _40:15_ — Yeah.

**Matheus Henrique Pereira Vaz** · _40:23_ — Raption Q.

**Jessé Haniel** · _40:25_ — Tá, entendi, entendi. Beleza, Sandy?

**Sandy da Silva Santos** · _40:30_ — É que eu estou vendo os colegas falarem assim um pouquinho sobre o nosso. É porque a caixa, digamos assim, é muito grande. Então assim, nossas respostas aqui estão limitadas ao nosso, nossa realidade, né? Mas assim, sobre, digamos assim, as tecnologias.

**Jessé Haniel** · _40:42_ — Uh-huh, sim.

**Sandy da Silva Santos** · _40:46_ — É, provavelmente em algum lugar já usa tudo isso aí, mas tipo assim, não no nosso escopo, porque isso aí realmente são coisas modernas e é o que acredito que a nossos gestores, nossa coordenação aqui, ela quer buscar que a gente venha modernizar o nosso parque.

**Jessé Haniel** · _40:51_ — Dangi.Perfeito.Boa.

**Sandy da Silva Santos** · _41:02_ — Mas a questão de eu até ruim de falar, observabilidade é, por exemplo, dotnet, o pessoal usa lá algumas coisas já, a gente tem aqui alguns é, como é que eu esqueci o nome agora, talvez alguém lembre, a gente tem uns painéis já de.Realmente para relatórios e ver algumas coisas e também para filas. Quando é.NET, a gente tem, acho que é service bus, a do Azure, então tem aí umas frentes para trabalhar com works, essa coisa toda.Mas na nossa realidade aqui, como os colegas falaram, é muito bem falaram aí, realmente é o que a gente está buscando.

**Jessé Haniel** · _41:42_ — Maravilha, Sandy, por causa dessa tua fala, tá aí a segunda estrelinha do dia, porque essa que é a sacada. Então assim, eu sou uma pessoa que eu gosto de ver o copo meio cheio e eu tô vivendo isso aqui no tribunal.Então, o Tribunal do Estado, assim como a Caixa, é uma empresa com mais de 100 anos. E daí, às vezes a gente chega, a gente da área de tecnologia, a gente chega no ambiente e faz, nossa, isso aqui é só legado, tá tudo defasado, não tem padronização, não tem nada.Temos duas opções.Ou a gente pode se deparar e agarrar essa realidade, ou a gente pode chegar e fazer, cara, então estou enxergando oportunidade aqui.E eu estou puxando agora aqui uma junto com outras pessoas, mas uma frente de modernização. A gente está aplicando o IA em muita coisa e o melhor argumento está sendo o quê? As entregas que a gente está fazendo.Então, na hora que a gente entrega um produto ali que resolve uma dor e que aumenta a produtividade, então todo mundo faz, cara, então eu tenho que dar o braço a torcer, realmente, vamos tocar isso aí.E o que o Sandy citou foi exatamente isso, do tipo cara.E a Caixa, ela está modernizando, ela está querendo trazer coisas novas. Então se a gente apresentou esse programa aqui para a Caixa, foi aprovado para a gente trazer para treinamento com vocês, então é sinal que se ainda não tem.é bem provável que eles estão aí exatamente fazendo esses treinamentos para poder usar, para poder homologar, para poder implantar em novos serviços, em novos produtos. O que a gente sabe é, principalmente com essa parte toda agora das capacitações, a Caixa está investindo muitoModernização.Então, tomara aí que a gente veja realmente essa virada de página, essa atualização, essa aplicação desses novos padrões. E aí vocês vão fazer a festa, porque todo mundo já está dominando isso e vocês vão.Puxar várias frentes por aí, né? Bom.então tem algumas coisas aqui que talvez possa ser novidade para para alguns mas o que que é interessante de tudo issoAqui a gente não está se prendendo necessariamente a tecnologias e frameworks. Então a gente vai discutir muito de que formas que eu tenho esse conjunto de ferramentas e em que situações que eu vou aplicar isso, tá? Então acaba que a gente vai trabalhar com código aqui e.Aplicações de desenvolvimento? Vai, mas isso é secundário.E outra, o nosso foco não vai ser o desenvolvimento, a escrita de código. Então, até como alguém mencionou, acho que foi o Sandy também que falou, eu acho que a gente, essa questão de linguagens, a gente vai deixar tudo para a IA, ela vai escrever até o binário aí.Você vai ficar mais no alto nível. É por aí mesmo, tá?É exatamente isso. Então, a minha leitura, e que eu tenho visto muita gente aí que escreve sobre isso, falando, né? E influenciadores, youtubers e tal, e o pessoal que produz cursos.É que o que a gente está vivendo agora é parecido com o que.Foi vivido o quê? 30, 40 anos atrás, 50 anos, quando tinha lá a questão do cartão perfurado, né? Do gerenciamento de válvulas. E aí, de repente, começou a ter as linguagens de programação. Então, ao invés de eu escreverAté de um é, ao invés de escrever assembly, que é uma linguagem ali de baixo nível, né? Então, para a gente fazer alguma coisa, tem que escrever muito.E começaram a surgir as linguagens mais modernas, orientado ao objeto e outros paradigmas. Então, o que a gente está vivendo hoje?É um segundo momento disso.Então é a gente praticamente ali interagindo com as IAs e a IA escrevendo o código. Porque digamos que ela tem melhor domínio de Java doc e de manuais de linguagem do que a gente tem.Se me perguntar aqui qual é a classe que faz tal coisa, qual é o método que faz tal coisa, dificilmente eu vou conseguir lembrar.de cabeça assim pá é issoEntão, a gente precisa estudar, a gente precisa continuar aprendendo as coisas para que a gente tenha as noções que são essenciais para que a gente consiga revisar as coisas, mas não necessariamente mais a gente colocar a mão e escrever linha por linha, digitar cada uma das coisas.E a gente fica muito mais na parte de especificação e revisão, tá?Beleza, então esse é o conteúdo proposto.E o que a gente tem aqui de cronograma dia 22 hoje, abertura DDD e fronteiras, depois quarta-feira, consistência distribuída e saga sexta teste com redes, segunda comunicação assíncrona, depois de primeiro filas com o Red MQ, dia3, temos que fazer um ajuste tópicos e eventos com Kafta, resiliência, contract testing compact e dia 10, encerrando o módulo banco dos projetos e a devolutiva aí das rubricas. Tá, por que que precisamos fazer ajustes? Dia 3, dia 3, eu tenho.compromisso tá então como eu trabalho aqui no tribunal então alguns feriados aqui acabam sendo emendados então é para compensar isso em alguns dias a gente trabalha dobrado ao invés de fazer um único expediente de 6 horas pela manhã a gente faz aí jornadainteira presencial e dia três é uma dessa sexta-feira estarei lá o dia inteiro então não não não teremos aula aí no dia três outra coisa também eu coloquei aqui com os 22 asteriscosDia 29, próxima segunda-feira, é uma possível data de jogo do Brasil às 14 horas. Vai depender aí da ordem de classificação, se primeiro ou segundo, se passar em primeiro é às 14 e se passar em segundo lugar vai ser às 10 da noite.Então, vamos ficar atentos aí para ver como vai ser esse dia 29. Mateus, pois não?

**Matheus Henrique Pereira Vaz** · _48:34_ — Queria saber se depois do de todo o processo a gente vai ter algum certificado?

**Jessé Haniel** · _48:41_ — É, vocês recebem um certificado de conclusão do curso.Mas assim, eu não sei quais são os efeitos práticos desse certificado, tá? Porque é muito assim, uma coisa de concluir o curso, mas eu não sei se tem detalhamento de horas, detalhamento do conteúdo, essas coisas que às vezes, para você comprovar carga horária de curso,Precisa dessas informações. Pessoal aí que já concluiu trilhas com a ADA, pode falar, talvez.

**Renan Sarto Gregorio** · _49:13_ — Ou de quando, ou de quando a gente entrou, tinha carga horária assim.Não sei se no.

**Antonio David Breno Souza Lima** · _49:17_ — Inclusive, foi cadastrado automaticamente no sistema da caixa de processamento interno.


### Bloco 6 — Cronograma e conflitos de calendário

**Jessé Haniel** · _49:18_ — Boa.Boa.Beleza, gente, então aí para esse dia 3, alguém tem sugestão aí de quando podemos fazer a reposição?Ou melhor, alguém tem restrições de quando não podemos fazer?

**Renan Sarto Gregorio** · _49:45_ — acho que qualquer dia

**Sandy da Silva Santos** · _49:47_ — É, eu acho que depende de ver a densidade do conteúdo aí, acho que mais próximo, desse do 1 do 7, ou 6 do 7, ou o que for menos denso, faz um aulão já junto, porque são 3 horas de aula.Acredito que possa ser possível, não é?

**Jessé Haniel** · _50:04_ — Então a gente não pode fazer um aulão, porque como vocês estão em horário de expediente, o máximo são 3 horas, então a gente vai ter que fazer num dia alternativo, ou numa terça ou numa quinta-feira, nesse mesmo horário, das 15 às 18.

**Sandy da Silva Santos** · _50:19_ — Entendi, entendi, entendi.Então, por mim, está tranquilo assim que é, é.

**Matheus Henrique Pereira Vaz** · _50:25_ — So yes, this one is.

**Alexandre Machado Rosa Filho** · _50:26_ — Pimento tranquilo qualquer dia.

**Jessé Haniel** · _50:30_ — Beleza, gente, então pra facilitar as coisas, eu acho que pode ser aí.

**Leonardo Oliveira Faria** · _50:31_ — Yes, yes, yes, I'm.

**Figura Carrijo Viana Figur** · _50:32_ — Qualquer dia é só se eu tentar que dia só se eu tentar que dia 9 do 7 é feriado em São Paulo, só para tentar isso.

**Leonardo Oliveira Faria** · _50:35_ — Some of this.

**Jessé Haniel** · _50:42_ — Tá, então acho que podemos fazer dia 2, a princípio, ao invés de fazer dia 3, a gente faz aí um dia antes, dia 2.Stop.

**Leonardo Oliveira Faria** · _50:51_ — Jessé, e se deixasse marcado também para o dia do jogo? Aí que se for as 2 horas, aí se o jogo for as 10, não tem problema. Mas se não tivesse, já adianta uma aula. Não daria para adiantar o cronograma.

**Figura Carrijo Viana Figur** · _50:51_ — Mhm.

**Matheus Henrique Pereira Vaz** · _50:52_ — There is.

**Jessé Haniel** · _51:02_ — Boa.Então é porque.

**Leonardo Oliveira Faria** · _51:06_ — Já deixa de te reservar na próxima terça ou quinta.

**Jessé Haniel** · _51:11_ — É, a gente vai saber isso na quarta-feira, que é que é quando encerra aí a terceira rodada do grupo do Brasil.Então.Para a gente fazer 25, acho que fica muito em cima.Talvez a gente poderia fazer, mas se fizer no dia 30, aí seriam 3 dias seguidos, segunda, quarta e quinta.Quer dizer, terça, quarta e quinta.

**Leonardo Oliveira Faria** · _51:48_ — O que eu pensei, porque de qualquer forma, talvez esse do dia 29 não ocorra, então.Reservasse já o dia 30 e aí do dia 3 jogava para outra semana.

**Figura Carrijo Viana Figur** · _51:56_ — Okay.

**Jessé Haniel** · _52:00_ — Tá, vamos fazer o seguinte, a princípio a gente tem uma reposição via 2.E talvez a gente possa fazer uma outra reposição no dia 7.

**Leonardo Oliveira Faria** · _52:12_ — Okay.

**Jessé Haniel** · _52:12_ — Yeah.Então, a princípio a gente tem no dia 2.E talvez a gente tenha uma reposição também no dia 7.Tá, então essa do dia 3 aqui é a única certeza que nós temos dia 3 e não teremos aula com certeza.Possivelmente também não teremos aula dia 29, então ficam 2 reposições. Com certeza dia 2 teremos reposição e talvez no dia 7 também teremos a reposição, certo?Beleza gente, então para quem já teve aula comigo, por que que esse cronograma ele é tão importante?

**Matheus Henrique Pereira Vaz** · _53:04_ — De novo, de novo?

**Jessé Haniel** · _53:04_ — O que é o duching?Por que esse cronograma ele é tão importante? Por que a gente traz aqui detalhado aula, aula? Qual é o tema?

**Matheus Henrique Pereira Vaz** · _53:14_ — Pra bater com o cronograma da dos das aulas do da plataforma.

**Jessé Haniel** · _53:14_ — Esperado.

**Leonardo Oliveira Faria** · _53:20_ — É para gente ler o material antes também.

**Jessé Haniel** · _53:21_ — Não apenas isso, exatamente, para vocês lerem o conteúdo antes. Certo? Então, lembrando, a metodologia da ADA trabalha aqui com a PBL, sala de aula invertida. Então, eu preciso que vocês leiam o conteúdo da aulaantes da aula, para que a gente foque a nossa aula e ela não ser apenas expositiva, mas que a gente possa praticar e debater juntos. Daí eu preciso que cada um de vocês leiam o conteúdo antes para a gente conseguir atingir esses objetivos nas aulas, tá?Lembrando que a aula de hoje é adaptada porque vocês sequer estão com acesso ao LMS e não teriam como ler o conteúdo antes. Então a de hoje é a exceção, a gente vai fazer uma adaptação aqui, mas a partir da quarta-feira já deve estar tudo liberado para vocês e aí vocês fazem essa leitura.Beleza?Certo, então, o que é que nós temos aqui de projeto? A gente mencionou que seriam dois projetos. Nós teremos um projeto guiado em aula, que é a API de comprovantes PIX. Então ela vai ser construída aqui e evoluída a cada aula. Assim que vocês tiverem acesso ao LMS, eu também vou disponibilizar lá para vocês.Tá todo o conteúdo, o conteúdo, o projeto, ele está segmentado por aula, então a cada aula eu vou adicionando as pastas lá, ou se vocês preferirem, já posso adicionar todas de uma vez e vocês vão fazendo esse gerenciamento.De a gente tá na aula um, vou abrir aqui a pastinha da aula um, a gente tá na aula 5, vou abrir a pastinha da aula 5, tá? Então fica aí ao critério de vocês. O que vocês preferem? Tem alguma preferência?

**Renan Sarto Gregorio** · _55:08_ — Eu prefiro se botar tudo de uma vez.

**Jessé Haniel** · _55:13_ — Beleza, então já adiciono tudo lá, tá? Até porque isso casa muito bem com o que a gente acabou de falar. A nossa atividade vai ser cada vez menos digitar código. Então, aqui na aula, pode ser que a gente faça alguma avaliação, pode ser que a gente escreva algum trecho.Mas o nosso foco, vocês já saíram lá da parte de nivelamento, de introdução de linguagem, então não tem mais porque a gente ir bem passo a passo, a gente ir mais devagar, digitando, escrevendo ali as coisas, e o nosso foco aqui vai ser discutir.Arquitetura, quais são as ferramentas que eu uso para cada tipo de problema, certo? Beleza, então, como que é o funcionamento geral aqui?o que a gente tem desse projeto é feito um post ele retorna um status 202 para gente dizendo a beleza comprovante emitido vou fazer aqui a gravação no banco então a gente faz a requisição retorna 202 dizendo que aceitou que vai processarVai para fila, faz a gravação no banco e a consulta ela é feita com cash e fallback, porque, afinal de contas, nós esperamos ter muito mais operações de leitura do que de escrita, certo?E ela é a referência dos padrões, de como a gente está aplicando as coisas. Então vocês podem usar aqui como consulta para o projeto que vocês vão desenvolver em grupos. Projeto final, aquele que vocês vão fazer em grupos. Aqui nós somos.13 14 comigo. Então acho que a gente pode fazer grupos com três pessoas, por a gente ter aí quatro grupos. E aíinvariavelmente algum grupo vai ter quatro integrantes então tema de livre escolha tem aqui algumas sugestões de temas para vocês caso vocês não tenham alguma ideia Se tiverem uma ideia melhor ainda o que amarras são os critérios de avaliação não o temagrupos de três a cinco pessoas no caso aqui três ou quatro pessoas um grupo quatro pessoas o escopo escala com o tamanho então grupo que vai ter quatro integrantes tem uma tarefinha extra e vai ser desenvolvido na segunda metade das aulas com apresentação aí no dia dez beleza forma e os gruposA próxima aula.Avaliação, rubrica de 9 critérios, avaliação de domínio do padrão e decisão arquitetural, não a infra disponível, apresentação, autoavaliação, listas de exercício e participação, inclusive sobre infra. Depois eu vou fazer aqui um checklist direitinho, mas.Imagino que vocêsTem um Docker disponível aí?

**Sandy da Silva Santos** · _58:07_ — Ohh, no, no, na máquina caixa, no, no.

**Renan Sarto Gregorio** · _58:07_ — No.

**Relder Maia da Silva Batista** · _58:08_ — No.

**Figura Carrijo Viana Figur** · _58:09_ — No.

**Andre Felipe Corradi Botelho** · _58:09_ — Nossa, eu seria muito feliz se eu tivesse.

**Relder Maia da Silva Batista** · _58:10_ — No.

**Alexandre Machado Rosa Filho** · _58:10_ — Quem me dera.

**Matheus Henrique Pereira Vaz** · _58:12_ — Esse é um elemento que eu até perguntei para você em relação ao projeto final, porque a maioria dos itens a gente não tem disponibilização para implementação aqui na própria máquina. A gente consegue implementar em outra máquina, enviar?

**Jessé Haniel** · _58:13_ — Tá bom?Então, tem algumas soluções online, a gente pode testar algumas aqui, tá? Se vocês têm acesso, se não tiverem, aí o que a gente vai fazer? Vocês podem checar aí, lembrando que o David, ele é o.o representante aqui do pessoal de chamadas infra, tá? Vocês podem concentrar para ele aí todas as demandas e ele tem um contato quente lá. Para ver se é possível um ambiente sandbox para vocês.Se for possível lá um sandbox, maravilha é o ideal. Se nada disso for possível, então eu imagino que pelo menos vocês tenham aí um javinha ou qualquer outra linguagem que vocês desejem. Afinal de contas, o nosso foco aqui não é Java.Tá, mas claro, todos os códigos que eu vou trazer serão em Java. Então, imagino que vocês tenham aí um Java, que vocês tenham um Maven, que vocês tenham um Nexus disponível.Pelo menos isso tem.

**Andre Felipe Corradi Botelho** · _59:30_ — Tem.

**Renan Sarto Gregorio** · _59:30_ — Great.

**Sandy da Silva Santos** · _59:31_ — Thing.

**Alexandre Machado Rosa Filho** · _59:31_ — assim.

**Jessé Haniel** · _59:33_ — Maravilha, beleza.

**Leonardo Garcia Melo** · _59:33_ — Sim.

**Andre Felipe Corradi Botelho** · _59:34_ — Só não vai dar para rodar Kafka

**Jessé Haniel** · _59:41_ — É, a gente pode trabalhar com as abstrações, né? A gente pode trabalhar lá com as dependências, principalmente se a gente for olhar aí para o Spring.Dá para a gente fazer alguma coisa, tá? É, vocês têm acesso a supa base ou não?

**Alexandre Machado Rosa Filho** · _59:58_ — No, no.

**Andre Felipe Corradi Botelho** · _1:00:00_ — No.

**Jessé Haniel** · _1:00:02_ — Já tentaram, né?

**Andre Felipe Corradi Botelho** · _1:00:02_ — Abusão H2.

**Alexandre Machado Rosa Filho** · _1:00:04_ — É H2, é o H2 ou nada.

**Andre Felipe Corradi Botelho** · _1:00:06_ — Na H211

**Jessé Haniel** · _1:00:08_ — Não, H2 beleza. Eu estava pensando no super base porque ele tem algumas outras coisas lá, não é? Não só o banco.

**Andre Felipe Corradi Botelho** · _1:00:11_ — Nesquelite.

**Sandy da Silva Santos** · _1:00:15_ — Eu acho que a Skelite eu consegui aqui um projetinho aqui aham, a Skelite estava quer ser um arquivo, não é?

**Andre Felipe Corradi Botelho** · _1:00:18_ — Conseguiu?


### Bloco 7 — Combinados, avaliação (NPS) e ambiente (Copilot/Docker/sandbox)

**Jessé Haniel** · _1:00:20_ — Não, mas do banco é, mas do banco H2 ele resolve.Beleza, gente. Lembrando, avaliem a satisfação, tá? É fundamental. Então, todo final de aula, ali pelos 10, 15 minutos finais, a gente relembra e deixa disponível aí para vocês preencherem a avaliação.Lembrando que na ADA a gente tem aí uma nota de excelência acima de 90, tá? Então conto muito com essa colaboração de vocês, principalmente para a gente poder também otimizar e fazer o melhor uso do nosso tempo, não faz o menor sentido se vocês tiverem satisfeitos com alguma coisa aqui da aula, vocêsFicarem segurando isso, vocês ficarem engolindo isso a seco, não faz sentido, não façam isso.Não faz sentido, tá? Então, se tiverem aí qualquer crítica, qualquer coisa a fazer, tragam mesmo, coloca lá no formulário, traz aqui na aula, me manda mensagem no privado, sinal de fumaça, pomba o correio ou o que for.mas estou aqui disponível para que a gente possa sempre melhorar a nossa experiência das aulas, beleza?E aí, agentes, como é que isso se conecta com o nosso conteúdo? Então, quando a gente está falando, por exemplo, no DDD de inbound context, a gente está falando de fronteiras de agentes. Como a gente está falando de SAB, a gente está falando sobre agentes que work.Flow, quando a gente está falando sobre cache também a gente tem o Semantic caching ou Hag. E quando a gente está falando de tópicos, a gente tem aí a memória dos agentes. Então, tudo que a gente vai estar trabalhando aqui nesse nível de arquitetura, de alguma forma está ligado com.na parte de e as e de agentes e como a gente já falou aqui mais de uma vez cada vez menos a nossa tarefa vai ser digitar código e fica aqui escrevendo assembly ninguém fica escrevendo aqui binário ninguém ficafazendo cartão perfurado, ninguém fica fazendo gerenciamento de válvulas.Apesar que é isso que os computadores entendem.Então, cada vez menos a nossa tarefa vai ser digitar código.Então, a atividade de engenharia da computação ou de qualquer outra área relacionada aí, qualquer outra disciplina relacionada com isso, de engenharia de software, enfim, é a gente resolver problemas através da computação, a gente resolver problemas através da tecnologia.Tem que trazer é soluções para a área fim.através da computação e da tecnologia.E isso a gente precisa fazer o quê? Aprender e dominar o uso de IAs. Então, para tal, o que que vocês têm aí disponível?

**Renan Sarto Gregorio** · _1:03:15_ — So.

**Sandy da Silva Santos** · _1:03:17_ — Eu tenho GitHub Copilot, não é?

**Renan Sarto Gregorio** · _1:03:20_ — Ainda tem, então ok.

**Jessé Haniel** · _1:03:20_ — Certainly, I'll.

**Antonio David Breno Souza Lima** · _1:03:21_ — Na teoria, quem solicitar tem copilot, mas aí tá bem capado.

**Andre Felipe Corradi Botelho** · _1:03:22_ — Eu tinha.

**Alexandre Machado Rosa Filho** · _1:03:24_ — É, algumas pessoas tem o é, algumas pessoas tem o copilot, outras tem só o aplicativo lá, a IA mesmo. Isso, 365.

**Andre Felipe Corradi Botelho** · _1:03:24_ — É, eu tinha.

**Sandy da Silva Santos** · _1:03:26_ — É que o perfil dev, né?

**Renan Sarto Gregorio** · _1:03:34_ — 365.

**Andre Felipe Corradi Botelho** · _1:03:35_ — Yeah.

**Jessé Haniel** · _1:03:36_ — Boa.

**Andre Felipe Corradi Botelho** · _1:03:38_ — É, tinha copado, mas acabou já, né? Acabou o mês, já é dia 22.

**Renan Sarto Gregorio** · _1:03:43_ — Acabou faz 2 semanas.

**Andre Felipe Corradi Botelho** · _1:03:45_ — Já acabou faz 2 semanas, acabou os toques total, já faz 2 semanas.

**Antonio David Breno Souza Lima** · _1:03:51_ — Oh, estou usando pouco, estou topado nos 28%.

**Jessé Haniel** · _1:03:55_ — Oh.Todo mundo manda a demanda lá e processo.beleza gente então vejam o que é possível tá fazer isso em relação a versão de Java que vocês vão ter acesso a gente vai copilot vocês têm a parte de agentes do copilot ou não

**Alexandre Machado Rosa Filho** · _1:04:20_ — Acredito que foi adicionado recentemente. Não mexo muito, mas acredito que sim. É, acho que sim.

**Jessé Haniel** · _1:04:23_ — Opa.

**Relder Maia da Silva Batista** · _1:04:25_ — Tem sim, tem sim.

**Jessé Haniel** · _1:04:27_ — Maravilha, maravilha. Se por acaso não tiver aparecendo aí para vocês, entrem em contato, abram um chamado para tentar fazer alguma atualização, liberar isso para vocês, porque vai ser muito importante.

**Antonio David Breno Souza Lima** · _1:04:40_ — É muito problema com o VS Code que a gente que de quem usa baixado lá do meu software, que ele tava muito desatualizado, mas se tiver desatualizado, dá pra baixar o user installer pelo site do VS Code mesmo, que aí não precisa de permissão de administrador.

**Jessé Haniel** · _1:04:55_ — So.Maravilha!

**Sandy da Silva Santos** · _1:04:57_ — Eu estou usando o IntelliJ aqui. Acho que ele está até mais atualizadinho. Não sei se alguém usa aí o IntelliJ.

**Jessé Haniel** · _1:04:58_ — Beleza, gente.Open.

**Alexandre Machado Rosa Filho** · _1:05:04_ — Inteligente, nem a gente tem, pô.

**Relder Maia da Silva Batista** · _1:05:05_ — Uai, todo mundo aí, uai, não é possível, que isso, cara?

**Sandy da Silva Santos** · _1:05:09_ — É lá no naquele software ponto, eu instarei pela pelo pelo próprio teams aí naquele negócio de software.

**Antonio David Breno Souza Lima** · _1:05:10_ — LJ.É chato que a IntelliJ não tem o é, não tem o ultimate.

**Matheus Henrique Pereira Vaz** · _1:05:16_ — É o começo que a gente tem acesso.

**Relder Maia da Silva Batista** · _1:05:20_ — Pô, velho, desenvolvedor Java e é no IntelJ, cara, pô.

**Andre Felipe Corradi Botelho** · _1:05:25_ — Não, eu vou no VS Code.

**Renan Sarto Gregorio** · _1:05:25_ — Good.

**Jessé Haniel** · _1:05:26_ — Cadê a galera do IntelliJ aí? Cadê?

**Relder Maia da Silva Batista** · _1:05:27_ — VS Code? Não, que isso cara, VS Code não, hein?

**Renan Sarto Gregorio** · _1:05:28_ — no presencial ainda não consegui fazer não consegui fazer o copa funcionar

**Sandy da Silva Santos** · _1:05:31_ — É VS Code pra Java.

**Antonio David Breno Souza Lima** · _1:05:31_ — Ah velho, você perde um tempinho configurando, mas depois que você configura, funciona.

**Sandy da Silva Santos** · _1:05:35_ — É, mas tem muito plugin, né?

**Matheus Henrique Pereira Vaz** · _1:05:36_ — É a mesma que ganha café sem açúcar.

**Sandy da Silva Santos** · _1:05:40_ — Mas eu gosto do inteligente também, acho que sei porque eu sei uma ferramenta antiga né, mas eu sou dessa época.

**Antonio David Breno Souza Lima** · _1:05:46_ — O que eu estou curtindo no VS Code é abrir vários projetos relacionados no mesmo workspace. E aí o agente tem acesso a todos os projetos desse workspace.

**Jessé Haniel** · _1:05:56_ — Olha aí, olha aí. Boa gente, boa gente.

**Andre Felipe Corradi Botelho** · _1:05:58_ — Isso é bom, o problema é que eu faço uma pergunta e acaba o

**Alexandre Machado Rosa Filho** · _1:06:00_ — Demo.

**Jessé Haniel** · _1:06:04_ — Então, mas isso aí entra as partes de isso aí entra a questão de gerenciamento de contexto, de otimização de tokens. Vamos trocar umas figurinhas nisso aí, beleza, gente?

**Antonio David Breno Souza Lima** · _1:06:04_ — Infelizmente.

**Andre Felipe Corradi Botelho** · _1:06:13_ — Okay.

**Antonio David Breno Souza Lima** · _1:06:13_ — E chato também que caparam nossos modelos aqui, né? Tirar o o Sonet.

**Andre Felipe Corradi Botelho** · _1:06:16_ — Yeah, OK.

**Matheus Henrique Pereira Vaz** · _1:06:17_ — Para abrir, para abrir vários projetos no WLJ, é só adicionar como módulo.

**Jessé Haniel** · _1:06:19_ — Ohh, combinados.

**Andre Felipe Corradi Botelho** · _1:06:24_ — Thank you.

**Jessé Haniel** · _1:06:24_ — Eita, é, dá para fazer, é aquela coisa meio assim do é, dá para fazer.

**Relder Maia da Silva Batista** · _1:06:28_ — Emma, the verse could you be dorm.

**Jessé Haniel** · _1:06:32_ — E aí vamos lá vamos lá a gente vai ter a gente vai conversar bastante sobre essas coisas aí estão combinados início início sempre começa pontualmente às 15horas 15 horas eu estarei aqui enquanto os demais vão chegando a gente durante cinco minutinhos a gente conversa ali alguma coisa a gente revisa pode tirar dúvidas pode falarCopa, enfim. Mas a gente não passa mais do que 5 minutos de tolerância. E daí a gente já começa realmente no conteúdo previsto para o dia, tá?

**Sandy da Silva Santos** · _1:07:01_ — Você está falando da pausa para hidratação?

**Jessé Haniel** · _1:07:04_ — Pausa para hidratação, às 4h20, 4h40, certo? Então fica aí exatamente 3 horas de duração, 20 minutinhos de pausa para hidratação e a gente encerra nunca passando das 18 horas, tá?

**Sandy da Silva Santos** · _1:07:06_ — Yes.Entendi, beleza.

**Jessé Haniel** · _1:07:20_ — Yes.

**Antonio David Breno Souza Lima** · _1:07:20_ — Sem chance de ser 30 minutos de intervalo, Jassan?

**Jessé Haniel** · _1:07:25_ — Então, eu ia perguntar isso, Dora, aqui não é o módulo de ágil, mas a gente continua praticando o ágil e a gente faz de tudo para se adequar ao que vocês pedem.

**Antonio David Breno Souza Lima** · _1:07:38_ — Então é que às vezes eu chego mais tarde, e aí se eu precisar bater o ponto durante a aula, eu perco acesso ao times. E como a gente só pode bater o ponto, no mínimo 2 horas depois do ponto de intervalo do mínimo 2 horas depois da entrada.

**Jessé Haniel** · _1:07:41_ — Mm.Mm.

**Antonio David Breno Souza Lima** · _1:07:53_ — Se a gente tiver 30 minutos durante a aula de intervalo, se acontecesse o caso, daria para bater esse intervalo com o intervalo da área.

**Jessé Haniel** · _1:08:02_ — So.

**Alexandre Machado Rosa Filho** · _1:08:04_ — É porque o intervalo tem que ser no mínimo 30 minutos, não pode ser menos, não da ocorrência.

**Jessé Haniel** · _1:08:12_ — Mhm.É que é possível, com certeza é possível.Sendo que aí assim a gente de novo a gente entra naquela questão das nossas negociações, se a gente de 3 horas a gente coloca 30 minutos de intervalo.Quer dizer que a gente tem ainda o mesmo conteúdo pra duas horas e meia?Se todo mundo topar, eu topo.

**Renan Sarto Gregorio** · _1:08:45_ — Para mim é uma boa também, estou com o mesmo problema.

**Alexandre Machado Rosa Filho** · _1:08:46_ — Por mim, tranquilo, é.

**Sandy da Silva Santos** · _1:08:46_ — Eu por mim, ok pessoal, por mim pode ser até uns 40 minutos aí, tá tranquilo também, tranquilo.

**Jessé Haniel** · _1:08:53_ — Pega esse horário aí das 4 às 5.

**Antonio David Breno Souza Lima** · _1:08:55_ — Quando o cara é bom, é outra coisa.

**Sandy da Silva Santos** · _1:08:56_ — Think I did it.

**Jessé Haniel** · _1:09:00_ — Beleza, gente, então vou ajustar aqui, então 4:15 até as 4:45, 30 minutinhos, daqui a pouco, beleza?E aí o segundo ponto e que ele é tão importante quanto o primeiro. Se o primeiro a gente negocia aqui os nossos horários, os nossos polos de hidratação, então também o segundo ponto ele é fundamental, principalmente.Porque eu entendo que todo mundo está aqui.Afim, né? Então, para que a gente tenha essa troca da melhor forma, para que ele possa estar acompanhando realmente se vocês estão com dúvidas, se vocês estão interagindo, se vocês estão conseguindo colocar tudo em prática, se vocês estão.realmente absorvendo e dominando tudo isso que a gente está trazendo aqui, então é fundamental que vocês não só estejam online, mas que vocês estejam realmente presentes, que vocês estejam participando aqui, tá? Então, sempre que possível,Câmera, participem, tá? Interajam, respondam, perguntem, certo? Façam parte das discussões. Por que que isso é tão importante?eu digo sempre que possível porque porque tem dia que a gente não tá assim tem dia que sei lá a câmera deu problema tem dia que a gente tá ali no deadline do prazo e a gente tá aqui realmente porque senão a coisa complica mais mas hoje não vai dar para prestar atençãoem nada porque vocês têm um projeto a entregar. Entendo, todo mundo aqui é adulto, todo mundo é profissional, não vou pegar no pé de ninguém, sendo que essas situações, elas têm que ser a exceção.Dos 9 encontros que a gente tem aqui, se isso acontecer uma vez, sem problema nenhum, faço vista grossa, entendo que foi ali um ponto fora da curva, tá tudo certo. Agora, se isso começa a ficar recorrente,Aí isso compromete diretamente a qualidade do nosso curso.Aí vocês vão ficar, caramba, eu tenho que entrar lá, hoje tem aula, nossa, demora demais, três horas, ai, não sei o que lá. Começa a ficar aquela coisa e a gente perde a oportunidade que era o objetivo central.Tá? Então, por isso que tem toda a sequência do que a gente está trazendo aqui, avaliação de satisfação, questão da gente combinar os horários. Ah, eu quero fazer 2 pausas, 30 minutos, ah, não sei o que lá. Beleza, tudo isso a gente vai ajustando.mas é fundamental que a gente esteja, a gente tenha aqui um compromisso alto. A gente realmente não, eu quero tirar o melhor dessa oportunidade. Beleza? Então conto aí comParticipação de vocês, por favor, ninguém suma, ninguém desapareça, ninguém use capa de invisibilidade.Todo mundo já interagiu, já sei que os microfones estão funcionando, as câmeras né, ainda tá aí meio tímido, mas vamos trabalhar nisso para que a gente possa estar interagindo da melhor forma, tá? Ambiente, vocês vão estar usando aí o Nexus corporativo, então a genteVai tentar fugir da dependência do Docker e de outros sites externos. Sempre eu vou estar aqui com o plano ABC para a gente ir testando o que vocês têm disponível. Dúvidas são sempre bem-vindas, mesmo durante os momentos de live encoding que a gente estiver fazendo.Set.É, Alexandre, também.

**Alexandre Machado Rosa Filho** · _1:12:45_ — Opa, eu só queria perguntar, conferir com a galera, todo mundo, vocês sabem se o Docker, ele é realmente banido ou se ele só não tem muito acesso? Porque talvez a gente consiga abrir um.

**Jessé Haniel** · _1:12:55_ — É provável que o Docker desktop não esteja disponível, tá? Porque tem as questões de licença, mas talvez o se ela esteja.

**Andre Felipe Corradi Botelho** · _1:12:56_ — Mm.Não, então é que não está liberado o da USL.

**Alexandre Machado Rosa Filho** · _1:13:02_ — Hi.No of it.

**Jessé Haniel** · _1:13:07_ — Thank you.

**Andre Felipe Corradi Botelho** · _1:13:09_ — O Windows Subsystem for Linux não está liberado, pelo que eu sei.

**Alexandre Machado Rosa Filho** · _1:13:13_ — Não tem uma versão para o Windows, né?

**Andre Felipe Corradi Botelho** · _1:13:17_ — Tem, mas pelo que eu sei, precisa também precisa.

**Relder Maia da Silva Batista** · _1:13:20_ — Ele não é homologado pela.

**Alexandre Machado Rosa Filho** · _1:13:20_ — Indy.

**Andre Felipe Corradi Botelho** · _1:13:22_ — Yeah.

**Alexandre Machado Rosa Filho** · _1:13:23_ — Entendi, mas o.

**Leonardo Garcia Melo** · _1:13:23_ — É, eu acho que não é uma alugada.

**Relder Maia da Silva Batista** · _1:13:24_ — pelas áreas da caixa.

**Alexandre Machado Rosa Filho** · _1:13:25_ — Eu acho que o WSL é homologado, eu sei que tem alguns colegas de crédito que usam.

**Andre Felipe Corradi Botelho** · _1:13:32_ — Privilegiados.

**Alexandre Machado Rosa Filho** · _1:13:34_ — É, então, muito mesmo, mas às vezes talvez a gente tentar abrir um chamado, não sei.

**Jessé Haniel** · _1:13:41_ — Faz acesso remoto na máquina deles.

**Relder Maia da Silva Batista** · _1:13:43_ — Na verdade, é só consultar lá o caderno de TI. Lá no caderno de TI tem os sistemas homologados lá. Aí, se ele tiver homologado, é só solicitar a instalação por lá.

**Alexandre Machado Rosa Filho** · _1:13:58_ — Bem, é só deixando questionamento.

**Figura Carrijo Viana Figur** · _1:13:59_ — Onde que fica esse cadernos de TI?

**Relder Maia da Silva Batista** · _1:14:02_ — Lá no serviços.caixa.

**Figura Carrijo Viana Figur** · _1:14:07_ — Uh, you got

**Relder Maia da Silva Batista** · _1:14:07_ — É, depois eu mando um print aqui.Some new tinta.

**Alexandre Machado Rosa Filho** · _1:14:13_ — So pra questioner mesmo.


### Bloco 8 — Intervalo, compartilhamento (WhatsApp) e teste de dependências no Nexus

**Jessé Haniel** · _1:14:14_ — Boa.ótimo questionamento as provocações aí de repente a gente vai descobrindo aí né que existe uma luzinha no fim do túnel beleza gente então já são já são aí 4:16 vamos fazer o nosso intervalo e quando a gente voltar a gente inicia aquie com a parte do DDD, Dome and Drigger Design. Beleza?Vamos lá, 30 minutos.E aí pessoal, tamo de volta!

**Antonio David Breno Souza Lima** · _1:45:38_ — Yeah, it.

**Figura Carrijo Viana Figur** · _1:45:45_ — Olá.

**Jessé Haniel** · _1:45:50_ — Gente, me passaram uma relação aqui provisória.para eu lançar as presenças e aí deixa eu só confirmar direitinho aqui com vocêsQuem está presente ou não.Eu não localizei aqui Gabriel.e Rodrigo. Estão aí?No, beleza.

**Figura Carrijo Viana Figur** · _1:46:25_ — Acho que eles não se apresentaram também, acho que eles não estiveram.

**Jessé Haniel** · _1:46:31_ — Boa.Maravilha, gente, então vamos seguir. Eu vou compartilhar com vocês alguns conteúdos lá no WhatsApp, porque é o canal que nós temos por enquanto, tá?E aí para a gente ter um melhor acompanhamentoNão ficar só em cima do que eu estou projetando aqui.E aí, caso vocês queiram enviar para o e-mail pessoal para ficar mais fácil de abrir no computador.Aí eu acho que cada um enviando fica mais fácil do que se eu for tentar enviar para todos os e-mails.E aí, rapidinho, eu queria fazer aqui um teste de ambiente em cima desse arquivo checklist.md.colocar ele na tela aquiQue aí, caso alguém não queira abrir, pelo menos consegue acompanhar.Então é esse conteúdo aqui, tá gente? O checklist. A gente fez a verificação do Java, vocês disseram que tem pelo menos 17, mas eu queria saber se alguém tem disponível aí o 21nem que seja ainda para pedir o suporte ao atendimento para fazer a instalação.

**Antonio David Breno Souza Lima** · _1:48:25_ — No.

**Alexandre Machado Rosa Filho** · _1:48:26_ — Eu tenho.Eu tenho o zip dele aqui, se vocês precisarem, eu mando pra todo mundo o Stand Alone.

**Antonio David Breno Souza Lima** · _1:48:29_ — No.

**Jessé Haniel** · _1:48:32_ — Maravilha!

**Antonio David Breno Souza Lima** · _1:48:32_ — Qualquer dia é, então eu sempre baixo pelo site do Java mesmo, o ZIP, aí eu só coloco no na ideia, porque aqui no PF do sistema ele está configurado no de sistema e aí o de usuário não sobrescreve.

**Jessé Haniel** · _1:48:42_ — Mhm.Boa.maravilha versão do Maven que que vocês tem aí

**Antonio David Breno Souza Lima** · _1:48:54_ — Faço a mesma coisa com o zip.

**Alexandre Machado Rosa Filho** · _1:48:56_ — Acho que é a última, acho que é a 3 ponta alguma coisa, não é a última.

**Andre Felipe Corradi Botelho** · _1:49:00_ — É, eu baixei, eu baixo do site.

**Jessé Haniel** · _1:49:04_ — Tá, acho que é 396398, alguma coisa assim, se bem que já tem a 4 aí, não é?

**Antonio David Breno Souza Lima** · _1:49:07_ — 396 mesmo longe.

**Alexandre Machado Rosa Filho** · _1:49:09_ — A gente está na 39, mas não sei exatos, mas é por aí.

**Jessé Haniel** · _1:49:10_ — Yeah.Show.

**Figura Carrijo Viana Figur** · _1:49:14_ — 3.99

**Jessé Haniel** · _1:49:18_ — Boa do settings XML configurado lá para o Nexus, está o que isso aí?

**Alexandre Machado Rosa Filho** · _1:49:25_ — In.

**Andre Felipe Corradi Botelho** · _1:49:26_ — Same.

**Antonio David Breno Souza Lima** · _1:49:27_ — Thank you.

**Figura Carrijo Viana Figur** · _1:49:30_ — See?

**Jessé Haniel** · _1:49:30_ — Be Lisa.Então, OK, vamos pedir a Deus que dê certo, não é?

**Andre Felipe Corradi Botelho** · _1:49:37_ — Impossible.

**Jessé Haniel** · _1:49:37_ — Existe uma remotíssima possibilidade aí ou totalmente descartado.

**Leonardo Garcia Melo** · _1:49:40_ — É, eu acho que.

**Andre Felipe Corradi Botelho** · _1:49:43_ — Não, esse aí eu acho totalmente secreta.

**Leonardo Garcia Melo** · _1:49:45_ — Pode botar uns 99.9% descartado.

**Jessé Haniel** · _1:49:46_ — Elisa.Certo, está é. Talvez vocês conseguiriam aí um ambiente sandbox.Oh no.

**Alexandre Machado Rosa Filho** · _1:49:57_ — My fast conseguiu docker.

**Leonardo Garcia Melo** · _1:49:58_ — Chief his stomach.

**Alexandre Machado Rosa Filho** · _1:50:00_ — Porque conseguiu o ambiente, é.

**Andre Felipe Corradi Botelho** · _1:50:01_ — E eu dó falei, é, não consigo um ambiente para o meu, para o sistema novo.

**Jessé Haniel** · _1:50:03_ — That.Então vamos o seguinte.O nosso, o mais provável aí vai ser essa, esse plano B aqui, tá? Então por isso que eu compartilhei o arquivo com vocês, que aí fica mais fácil de vocês copiarem do que tentar digitar tudo isso. Então eu preciso que vocês.abram aí um terminal, IDE, qualquer coisa, e aí façam essas execuções aqui chamando de PNSGET para ver se ele consegue resolver todas essas dependências. Porque aí se der certo, aí a gente pelo menos vai ter algum ambiente embarcado aí dentro do projeto.

**Antonio David Breno Souza Lima** · _1:50:49_ — Isso não consegue ser enviado. Esses comandos não consegue ser enviados aqui pelo chat não, professor.

**Jessé Haniel** · _1:50:56_ — Enviar como arquivo não, no máximo eu consigo enviar aqui como texto, sendo que aí ele fica meio quebrado.

**Antonio David Breno Souza Lima** · _1:51:00_ — Já ajuda?

**Andre Felipe Corradi Botelho** · _1:51:01_ — Não, o ponto md não pode enviar.

**Jessé Haniel** · _1:51:04_ — Eu não consigo enviar nenhum arquivo, porque para enviar arquivos pelo Teams, é, tem que estar configurado aqui um.Um drive virtual tem que ter um ambiente de OneDrive, aí pelo lado da ADA não está configurado, aí eu não consigo enviar, só vocês conseguem enviar para mim.

**Andre Felipe Corradi Botelho** · _1:51:18_ — Mm.

**Jessé Haniel** · _1:51:24_ — no máximo conseguir enviar a imagem.

**Figura Carrijo Viana Figur** · _1:51:33_ — Eu pedi para o Jet GPT só transcrever a imagem. Não sei se está certo os comandos mesmo.

**Jessé Haniel** · _1:51:36_ — How to read it?

**Figura Carrijo Viana Figur** · _1:51:40_ — Está meio quebrado aí as coisas, mas.

**Jessé Haniel** · _1:51:49_ — Deixa eu ver se eu consigo aqui.Talvez isso aqui.Chuck these.

**Leonardo Garcia Melo** · _1:52:01_ — Professor, não era mais fácil só mandar por e-mail de uma de um dos participantes e daí ele envia aqui?

**Jessé Haniel** · _1:52:03_ — Oi.

**Andre Felipe Corradi Botelho** · _1:52:15_ — Manda como no chat do Teams dá para só copiar e colar, você cola como é.Cold block, tá ligado? Tipo com os 3 assent.

**Antonio David Breno Souza Lima** · _1:52:25_ — Sete do Teams aceita Markdown, né?

**Andre Felipe Corradi Botelho** · _1:52:28_ — Yeah.Dá pra só dar um copiar tudo mesmo.

**Jessé Haniel** · _1:52:45_ — Tá quase, tá quase.

**Figura Carrijo Viana Figur** · _1:53:01_ — Manda aquele primeiro comando do Maven, ali também, que a gente fala com o gavi.

**Andre Felipe Corradi Botelho** · _1:53:22_ — Dá bom andar em?Um code block, só tipo.É exatamente.muito mais bonitinho.

**Jessé Haniel** · _1:53:35_ — One.

**Leonardo Garcia Melo** · _1:54:08_ — Alguém consegue ver se consegue baixar o arquivo AMD que eu mandei?

**Alexandre Machado Rosa Filho** · _1:54:18_ — Tá de boa aqui.

**Andre Felipe Corradi Botelho** · _1:54:18_ — Da, you both.

**Jessé Haniel** · _1:54:23_ — É só avisando para vocês, tudo que vocês compartilham aí pelo SharePoint é só para vocês, tá? Eu não consigo acessar porque ele vai pedir, vai bater lá no SSO da caixa.

**Antonio David Breno Souza Lima** · _1:54:49_ — Recebi um erro de plugin not found.Any plucking hip full story?

**Jessé Haniel** · _1:55:04_ — Mesquapuli.

**Antonio David Breno Souza Lima** · _1:55:06_ — No primeiro, eu não sei se é por conta de versão específica que está fixado versão aí.

**Jessé Haniel** · _1:55:14_ — Será que é porque ele está com esse 33x?

**Antonio David Breno Souza Lima** · _1:55:20_ — É, então acho que é realmente porque tá fixado a versão, vou tentar deixar a versão genérica aqui.

**Jessé Haniel** · _1:55:24_ — Uh-huh.

**Matheus Henrique Pereira Vaz** · _1:55:26_ — mano cria um projeto no na IDE e só joga naNaia, pede para adicionar para tu.

**Antonio David Breno Souza Lima** · _1:55:44_ — Mas aqui é só para testar mesmo se consegue pegar esses artefatos.

**Jessé Haniel** · _1:55:47_ — Mm-hmm. Yeah.33 aqui seria o 3313.Porque como vocês estão usando aí umUm repositório dentro da rede caixa, né? Como ele não vai bater aqui no Maven repositório, no Maven Central, então tem que ver se tem essas dependências aí.

**Antonio David Breno Souza Lima** · _1:56:26_ — Eu estou dando uma olhada aqui no nexus, se eu acho.

**Jessé Haniel** · _1:56:31_ — Pode ser também.

**Leonardo Garcia Melo** · _1:56:59_ — Porque diz, pelo que eu estou vendo aqui, tem a versão 3.7.0, eu acho.

**Jessé Haniel** · _1:57:13_ — Is do already client.

**Leonardo Garcia Melo** · _1:57:18_ — Yes.

**Jessé Haniel** · _1:57:19_ — Sim.

**Leonardo Garcia Melo** · _1:57:20_ — I still beauty failure.

**Jessé Haniel** · _1:57:28_ — Os principais vão ser esses embarcados, tá?Embarcado o.E em keeping embarcado.

**Matheus Henrique Pereira Vaz** · _1:57:41_ — Aí a gente faz a configuração depois.

**Jessé Haniel** · _1:57:45_ — Mhm.

**Matheus Henrique Pereira Vaz** · _1:57:45_ — Se fosse por se tivesse Doca, a gente faria ele via.Via Docker file, via Docker file do compose.

**Jessé Haniel** · _1:57:55_ — Sim, a gente montaria a rede lá, mas aí você vai precisar só dos clients, porque aí.A ferramenta em si, o provedor em si, seja aqui do Rabbit, do Kafka ou do Reddit, tudo estaria lá dentro do Container Docker.

**Matheus Henrique Pereira Vaz** · _1:58:10_ — Everyone.Yeah.

**Figura Carrijo Viana Figur** · _1:58:25_ — É tipo capaz de gerar um arquivo.bet para baixar esses arquivos.

**Jessé Haniel** · _1:58:32_ — Boa.

**Figura Carrijo Viana Figur** · _1:58:33_ — Eu vou mandar aqui para vocês.

**Antonio David Breno Souza Lima** · _1:58:39_ — Acho que o meu Maven está com algum problema aqui. Realmente no Nexus tem a versão 3.3.13, mas o comando não está conseguindo encontrar.

**Figura Carrijo Viana Figur** · _1:58:40_ — Eu não consigo mandar.

**Antonio David Breno Souza Lima** · _1:58:50_ — Try.

**Matheus Henrique Pereira Vaz** · _1:58:56_ — Toma sem comando.

**Antonio David Breno Souza Lima** · _1:58:58_ — O comando de fazer o dependence resolve.Let's see the pen sketch.Ele não está encontrando o Springboard starter da internet.

**Matheus Henrique Pereira Vaz** · _1:59:06_ — Right.Aí teria que olhar dentro do Lexus pra ver qual é a última versão que a gente tem desse cara.

**Antonio David Breno Souza Lima** · _1:59:14_ — Então eu já vi aqui é o 3.3.13 e mesmo fixando essa versão ele não está conseguindo pegar.O que é estranho porque nos meus projetos aqui quando eu dou umClean digital, ele consegue baixar os repositórios.

**Matheus Henrique Pereira Vaz** · _1:59:37_ — A gente.

**Jessé Haniel** · _1:59:42_ — Talvez se você tentar apontar para um M2 diferente, ele pode estar dando conflito aí.

**Antonio David Breno Souza Lima** · _1:59:43_ — Acho que eu sei, é, eu estou pegando do repositório caixa group. Ele está na no repositório Maven central.

**Matheus Henrique Pereira Vaz** · _1:59:52_ — Não tem que ver o daqui, pô, tu olhou no tem que olhar o binário daqui.

**Antonio David Breno Souza Lima** · _1:59:58_ — Sim, eu tô no binário daqui, só que eu tô no repositório caixa group. E aí dentro do binário da caixa tem o caixa group, tem o maving central, tem o central, caixa optação, tem vários repositórios.

**Jessé Haniel** · _2:00:12_ — Tem um clone do Maven Central aí.

**Matheus Henrique Pereira Vaz** · _2:00:13_ — Okay.

**Antonio David Breno Souza Lima** · _2:00:16_ — Na teoria.

**Matheus Henrique Pereira Vaz** · _2:00:17_ — Okay.Eu acho que não, que é só com o nível. Acho que o senhor está com o nível.

**Figura Carrijo Viana Figur** · _2:00:23_ — eu acabei de cair nesse erro do caixa group aqui

**Leonardo Garcia Melo** · _2:00:32_ — O meu setting está apontando para o meio centro, se alguém quiser.

**Matheus Henrique Pereira Vaz** · _2:00:42_ — Voltamos a era do colão binário dentro do barra vinho.I see.

**Jessé Haniel** · _2:00:47_ — Alguém tem um pendrive aí para passar as dependências?

**Matheus Henrique Pereira Vaz** · _2:00:49_ — Barra, Libby, John.At some of the place, please.

**Antonio David Breno Souza Lima** · _2:00:54_ — Se pá aqui, se pá que os pendrive aqui são bloqueados também, é.

**Figura Carrijo Viana Figur** · _2:00:55_ — O pior é que pendrive é bloqueado aqui também.

**Matheus Henrique Pereira Vaz** · _2:01:10_ — Tell Gloom special.

**Antonio David Breno Souza Lima** · _2:01:16_ — Okay.

**Matheus Henrique Pereira Vaz** · _2:01:17_ — Tem algum nome específico o projeto?

**Antonio David Breno Souza Lima** · _2:01:20_ — Não, não estou criando projeto, estou só tentando dar um dependence resolve para ele tentar bater lá no repositório.

**Matheus Henrique Pereira Vaz** · _2:01:26_ — Então, já vou criar já um projeto já com as coisas já para facilitar a vida.Vai ser aquele lá de compra hotpix, né?

**Jessé Haniel** · _2:01:36_ — Okay.

**Matheus Henrique Pereira Vaz** · _2:01:37_ — Yeah.

**Jessé Haniel** · _2:01:41_ — Então, mas para hoje a gente não vai precisar de nenhum desses, tá?

**Matheus Henrique Pereira Vaz** · _2:01:42_ — Okay.What?

**Jessé Haniel** · _2:01:48_ — Acho que pela terceira, quarta aula que a gente vai precisar. Não, estou só entendendo aqui como é que estamos de ambiente.

**Matheus Henrique Pereira Vaz** · _2:01:54_ — Ah, tá, não tranquilo, deixa eu vou fazer logo.

**Jessé Haniel** · _2:01:55_ — Porque aí, dependendo do que vocês tiverem disponíveis, aí eu vou ter que adaptar a aula.

**Matheus Henrique Pereira Vaz** · _2:02:03_ — Já tava aqui.

**Jessé Haniel** · _2:02:03_ — Mas vamos fazer o seguinte, é, já passei aqui para vocês, tá? A gente não vai precisar disso para hoje. Aí depois vocês vão vendo aí, talvez amanhã ou quarta-feira, tá?que a gente aproveita para seguir agoraBeleza.

**Matheus Henrique Pereira Vaz** · _2:02:25_ — Yes.

**Jessé Haniel** · _2:02:27_ — Show.Gente, então vamos rapidinho aqui conversar, oi?

**Matheus Henrique Pereira Vaz** · _2:02:30_ — Esse projeto, qual seria o escopo dele?Pra qual finalidade mais ou menos seria?

**Jessé Haniel** · _2:02:38_ — Então, vamos entrar nisso.

**Matheus Henrique Pereira Vaz** · _2:02:40_ — Ah.


### Bloco 9 — DDD na prática (2ª metade)

**Jessé Haniel** · _2:02:43_ — Beleza, antes da gente ir para o projeto, vamos falar um pouquinho aqui sobre DDD, tá? Primeiro, quem aqui já conhece sobre DDD?

**Antonio David Breno Souza Lima** · _2:02:55_ — Só conceitos.

**Jessé Haniel** · _2:02:55_ — Levanta a mão aí.Same.Like wanting.Só para ter uma noção aqui.

**Figura Carrijo Viana Figur** · _2:03:07_ — Por DDD você quer dizer domain driven design?

**Jessé Haniel** · _2:03:07_ — 23.Exatamente.

**Figura Carrijo Viana Figur** · _2:03:12_ — Yeah.

**Sandy da Silva Santos** · _2:03:12_ — Eu fiz um curso, eu acho, sobre esse cara aí. Na verdade, esses cursos que a gente faz nada, tinha um módulo desse cara aí, mas assim, na prática, nada, não fica mais conceitual.

**Jessé Haniel** · _2:03:21_ — Boa, beleza.Bom, estamos aqui quase meio a meio.

**Figura Carrijo Viana Figur** · _2:03:24_ — A prática eu sempre acho difícil de aplicar 100% assim, mas ele é um guia.

**Jessé Haniel** · _2:03:31_ — Legal, vamos ver então se a gente consegue desmistificar um pouco nesse módulo agora.Beleza, Jane? Então, DDD ferramenta para decidir onde cortar o sistema. Imagina o seguinte, antes da gente entrar aqui no problema do corte, deixa eu trazer para vocês aqui qual que é.O nosso problema, a gente falou que PBL a gente tem sempre um problema.Então.Deixa eu trazer aqui qual é o nosso problema gerador. Vou jogar aqui no chat, fica mais fácil.Andrea, quer falar alguma coisa?

**Andre Felipe Corradi Botelho** · _2:04:08_ — Opa, não, só deixa levantar.

**Jessé Haniel** · _2:04:11_ — Beleza de boa.Olha só, gente, mandei no chat aqui, tá? Então, qual que é o nosso problema de hoje? Os comprovantes de PIX hoje vivem dentro do core bancário. Precisamos extrair um serviço que a emite grava e consulta comprovante sem acoplar ao core. Onde ficam as fronteiras?Então, a gente poderia fazer um breakout rápido aqui, mas vamos tentar seguir. Então, cada grupo propõe um corte de domínio ou não entregar a resposta remete do Vent Storm, que é o bloco 3. Essa parte aqui desses blocos, está diretamente ligado com o PDF que eu compartilhei com vocês, mandei lá no grupo doDo WhatsApp. Então, se vocês quiserem abrir e acompanhar lá, mas também eu vou projetar já primeiro, passar aqui primeiro pelos slides, que está mais resumido e depois a gente vai lá mais no detalhe. Então, esse que é o nosso problema de hoje, a parte de comprovantes de Pix.Então a gente tem que fazer a emissão, gravação e a consulta, considerando que isso já existe dentro do código da aplicação.Então quando a gente vai fazer, eu tenho aqui um modelista, eu tenho aqui um sistema grandão legado e eu vou extrair aqui alguns micro serviços. Qual é o problema disso? O ponto de corte que se a gente cortar demais, se a gente não souber como a gente vai.fazer essa definição para transformar em microsserviços, extrair os microsserviços, a gente pode acabar caindo no pior cenário possível, que é o monolito distribuído, onde a gente tem só aqui os problemas que vem do microsserviço e a gente não tem nenhum dos benefícios. Então, a gente tem aqui aumento de comunicação de rede,potência, toda a problemática para fazer deploy e manutenção disso aqui. Então a gente precisa entender como a gente faz para identificar os domínios para a gente poder fazer essas separações. Então a gente vai separarPor domínios, não é por camadas, não é por tecnologia, por nada disso, é por domínio, tá?O DDD, ele funciona em 2 níveis, o estratégico e o tático. O estratégico é onde estão as Fronteiras. A gente vai entender como fazer essa, como identificar essas separações e o tático é o como modelar essas Fronteiras. Lembrando que aqui a gente não está falando nada de.Arquitetura a gente não está falando de framework, a gente está falando em como a gente modelar o domínio de um negócio, como a gente trazer as discussões de negócio para apresentações do nosso código.em termos de blocos táticos para fazer a construção disso a gente tem entidades a gente tem ver ossos velho objetos e a gente tem os agregados então a entidade é o que é tudo aquilo que tem um identificador é tudo aquilo que tem um ciclo próprio é tudo aquilo que éMesmo que a gente mude o estado, mesmo que a gente mude todos os atributos, todos os valores, ainda assim continua sendo o mesmo objeto.Então, normalmente a gente representa isso com a questão do ID. Normalmente isso está associado com uma questão do representado no banco de dados.Mas a forma como isso é traduzido aqui para o DDD é que uma entidade é tudo aquilo que tem uma identificação própria, tá? Os VOS são o quê? É tudo aquilo que é imutável. A gente cria uma única vez e que ele é identificado exatamente.Pela representação do seu estado, pela soma dos valores em cada um dos atributos. Se a gente fizer qualquer alteração, aquilo ali já é outro VO, então 2 VOS que tem exatamente todos os valores idênticos.Eles são o mesmo VO.So, essa aqui é a idea.central doda parte tática do DDD e o agregado ele é uma entidade com vários é ver os e as alterações que a gente faz é sempre através da raiz que essa entidade então não vai ter aqui várias entidades eu não vou ter aquiÉ nenhuma entidade, apenas VOS. Eu vou ter uma entidade que é raiz, eu vou ter vários VOS que são alterados a partir dessa entidade raiz.Esse que é o ponto chave aqui do DDD e depois desse slide, eu vou alterar aqui, vou alternar lá para o conteúdo PDF que vocês têm que a gente vai mais no passo a passo. Então o nosso objetivo é a gente identificar quais são os contextos, quais são os bound de context.E como que esses diferentes contextos eles se comunicam através do contexto? Então o mesmo termo significados diferentes por contexto dos clientes de cada área, então cada contexto é exatamente.Como que a gente está comunicando com determinada área? Eu tenho um determinado contexto, quando eu vou falar com o pessoal do financeiro, por exemplo, folha de pagamento, e aí para eles, uma fatura é o que está relacionado lá ao faturamento de uma folha de pagamento.You nice.Aí a gente vai falar com o pessoal de contratos, aí eles dizem, não, uma fatura é o que a gente está pagando para o prestador de serviço, para um agente externo. Então vejam que a mesma palavra fatura, ela muda de entendimento.De acordo com o contexto da nossa conversa, então é em cima disso que a gente vai montar os nossos pound context. Então, por exemplo, aqui o cliente dentro de cada área.Então, cada um desses bound context, cada um desses contextos delimitados vão ser as fronteiras do modelo e para a nossa linguagem ubíqua. Essa linguagem ubíqua é aquela que é o que o negócio fala e é o que também a gente traz para.o nosso código para os nossos serviços então não pode o negócio fala lá sobre fatura o negócio fala sobre clientes e o nosso código ele tá escrito de maneira diferente então por exemplo eu vou ter lá clientes eu vou ter person eu vou ter user eu vou tersei lá qualquer palavra que seja diferente daquilo que o negócio trouxe Qual é o problema disso a gente tá assumindo riscos a gente tá desalinhando a gente tá desconectando os nossos sistemas os nossos códigos daquilo que o negócio fala então fica mais difícilgente traduzir para código aquilo que o negócio está pedindo, está trazendo ali como problema, certo?Vou compartilhar esses slides com vocês. Tinha apenas mais aqui a parte de event storming e dos cenários, mas isso vai ficar melhor compreendido aqui no PDF que eu compartilhei com vocês. Então deixa eu trazer para cá.que agora que a gente vai destrinchando melhor aquilo ali foi primeiro uma visão geral é e bem superficialBeleza.Então, esse aqui é o material de vocês. Vocês vão ler com calma depois e na próxima aula, vendo dúvidas, vocês podem trazer sem problema nenhum, certo?Então, o que a gente tem aqui? Qual é o problema que o DDD resolve quebrar um monólito em microsserviços, evitando que a gente caia, como estava lá no slide, na questão do monólito distribuído. Então, a pergunta certa nunca é, em quantos serviços eu divido?Deve ser quais são as fronteiras de negócio que mudam por razões diferentes, em ritmos diferentes, sob responsabilidade de pessoas diferentes. Então esse aqui é o ponto chave do DDD.Então, como que a gente vai fazer as separações? Então sempre vai ser.Quais são as fronteiras que mudam por razões diferentes Então por que que eu vou mudar o meu objeto fatura ou a regra de criar uma fatura para a gente está falando de fatura com o pessoal de folha de pagamento a gente está falando fatura com o pessoal de contrato a gente está falando fatura comAquisição de material de serviço, o que que é?Quando eu falo cliente, eu estou falando cliente do ponto de vista de quem? De RH? Esse cliente é do pessoal de agências. Quem é esse cliente?Então, mudam por razões diferentes, em ritmos diferentes. Um tem sempre novidade, é o que está ali inovando, é o que a parte de novos produtos, por exemplo, que está tentando trazer mais clientes para a caixa. O outro é uma coisa consolidada, que não deve.Ficar mudando porque cada mudança tem.O risco aí de quebrar retrocompatibilidade.então mudam em ritmos diferentes isso vai causar atrito sobre responsabilidade de pessoas diferentes então eu falei mais de uma vez setores diferentes tem entendimentos diferentes então se há responsabilidades diferentes provavelmente aquilo ali deve sercom textos diferentes.Então DDD não é Controller Service Repository?DDD não é o framework, nem uma tecnologia. A gente não instala DDD, não tem um starter DDD, certo? É uma forma de modelar software guiada pelo negócio.E o DDD não são só os blocos táticos, não é porque está usando a entidade VO e aggregates que está aplicando DDD.Então a gente tem aqui o nível estratégico, onde estão as fronteiras e a gente tem o tático de como modelar o que existe. E aí o DDD, o Domain Driven Design, ele tem um princípio muito parecido lá com as metodologias de gestão de projetos ágeis.Aqui a gente precisa da participação do negócio, não tão ativa, mas a gente precisa ter uma participação, principalmente no momento de a gente entender e a gente desenhar esses domínios e definir o.Os contextos, então?Existem várias sessões de entrevistas, existem vários momentos de estar conversando com esse cliente até fazer essa validação, inclusive na maioria das vezes, quando é definido a linguagem ubíqua, isso facilita principalmente não só o pessoal de desenvolvimento, não só a área técnica, não só.a parte de sistemas e os desenvolvedores mas também pessoal de negócio da área que eles entendem que por esse processo de fazer a discussão de fazer as reflexões de fazer as definições dos termos isso traz mais clareza e até ajudaSe dentro da equipe existem entendimentos diferentes, naquela hora ali sai todo mundo com um dicionário único, que é exatamente a linguagem ubíqua, tá?Então, a linguagem, o bico, ela é exatamente esse vocabulário único, rigoroso, compartilhado entre o negócio e o time técnico.Regra prática, se você lendo o código, não consegue ter uma conversa com o especialista de negócio usando os nomes que estão lá, então a linguagem ubico, ela falhou, tá?Onde que mora tudo isso? Onde que está o domínio? Quando a gente está falando sobre domínio, a gente está falando aqui sobre o núcleo, sobre o core domain, que é exatamente onde está a vantagem competitiva e o que diferencia o negócio. Então, quando a gente está falando de negócio, a gente falando de domínio, a gente está falando principalmente desses termos aqui.que estão no núcleo do negócio, da operação da empresa ou de um setor ou do sistema que vocês estão desenvolvendo ou dando manutenção para uma determinada área.Temos a o subdomínio de suporte, que é aquilo que está relacionado ou derivado.Núcleo central do domínio e a gente tem aqui o genérico, que normalmente é onde a empresa acaba contratando uma solução de terceiro, porque não é aquilo que é o centro do seu negócio, então acaba que sai mais barato.Contratar isso pronto, inclusive com a parte de suporte e manutenção, do que ficar mantendo isso internamente.Os blocos entidade VO.E o agregado.entidade eu pulei porque acredito que vocês todo mundo aqui já tem experiência com programação Em algum momento já tiveram contato com isso e o agregado ele vai dar aqui uma boa visão do que que são os outros dois então a gente estava falando aqui por exemplo sobreFatura.Então, a classe fatura, ela é a raiz de um agregado, ela protege a invariante, ninguém mexe nos lançamentos por fora, tá? Então a gente tem aqui quem é o agregado raiz fatura, vai ter um ID, uma determinada fatura.E eu vou ter aqui uma lista de lançamentos e eu tenho aqui o dinheiro que é o total.Então vejam que lançamento ele é um V.O.Da mesma forma que esse dinheiro, que é o valor total, ele também é um VO.Então, em vez de a gente ficar tratando com tipos primitivos, ou mesmo que a gente já sabe que não deve usar float e double para valores monetários, a gente deve usar big décimo, mas ainda assim no big décimo eu tenho lá toda a precisão.Que é necessário, mas eu não tenho uma informação, por exemplo, de qual é a moeda.Então a gente poderia criar aqui um VO que encapsula exatamente isso.qual é o valor e qual é a moeda. Então eu tenho aqui esse VO que é dinheiro. Lançamento da mesma forma, posso ter ele aqui como um VO. E aí para eu adicionar qualquer valor na minha fatura, que é o agregado, eu tenho que fazer através de um método aqui.Entra também a questão do encapsulamento, que a gente está protegendo aqui, é um encapsulamento. Relembrando lá de IPO, não é só a questão de get set, é a gente realmente proteger os acessos e modificações.Para os atributos da nossa classe, então aqui dessa forma, esse é a única porta para eu adicionar.Lançamentos aqui na minha fatura, tomando esse método adicionar e passando um lançamento e fazendo aqui todo o cálculo de como isso deve ser feito.Já que eu mencionei encapsulamento, como é que a gente poderia fazer aqui para quebrar o encapsulamento e quebrar essa ideia que a gente está fazendo aqui de proteger esse agregado?

**Alexandre Machado Rosa Filho** · _2:19:40_ — Esperança.

**Sandy da Silva Santos** · _2:19:42_ — É o private ali.

**Jessé Haniel** · _2:19:46_ — boa a gente não vai mexer nessa visibilidade tá a gente vai manter todos aqui como private a parte de herança é uma tá por mais aqui que ele tá como presente talvez a gente poderia achar alguma forma aqui mas uma coisa que é muito comumE a gente fazer quando a gente diz que está praticando encapsulamento e que, na verdade, iria quebrar esse princípio aqui.

**Renan Sarto Gregorio** · _2:20:12_ — Atch in the remote.

**Jessé Haniel** · _2:20:14_ — Como?

**Renan Sarto Gregorio** · _2:20:15_ — Um set sem nenhuma validação, sem comportamento previsível.

**Jessé Haniel** · _2:20:20_ — 17 mas aqui, por exemplo, do lançamentos, ele está definido como final, se bem que o total ele está em aberto, então se realmente colocar um total aqui de dinheiro, quebrou tudo.

**Renan Sarto Gregorio** · _2:20:34_ — Mas mesmo sendo final, também dá para modificar a lista. Não seria um set, mas um Edge sem validação ou um POP também daria para fazer.

**Jessé Haniel** · _2:20:45_ — Me conta mais aí sobre como que a gente faria essa modificação dessa lista aí?

**Matheus Henrique Pereira Vaz** · _2:20:50_ — Cara, agora ele está, agora tu se complicou.

**Relder Maia da Silva Batista** · _2:20:52_ — É o que acontece é o seguinte, mesmo sendo final, isso é por referência, então aí você pode modificar qualquer elemento da lista, independente do final.

**Renan Sarto Gregorio** · _2:20:54_ — So.É por referência. Os valores são mutáveis.

**Andre Felipe Corradi Botelho** · _2:21:06_ — É, mas a lista tá private.

**Renan Sarto Gregorio** · _2:21:08_ — Sim, mas você põe um get sem nenhum controle, sem passar uma lista imutável, você modifica a lista diretamente.

**Matheus Henrique Pereira Vaz** · _2:21:11_ — A única forma de você mudar um private file?Nunca falei movie.

**Jessé Haniel** · _2:21:15_ — Boa, Renan, exatamente. Renan ganhou aí a terceira estrelinha do dia. Então, normalmente, quando a gente fala de encapsulamento, a gente pensa em get set. Então, mas aqui ele é final. Eu não posso ter um set para o lançamento. Eu não posso modificar, beleza.

**Relder Maia da Silva Batista** · _2:21:17_ — Você tem que devolver.

**Jessé Haniel** · _2:21:30_ — Aí a gente faz o quê? Põe só o get para a gente relistar esses lançamentos, sendo que, como é por referência, se a gente fizer aqui um lance, faço get lançamentos, tá? Vai visualizando aí o código get lançamentos. Se eu fizer ponto Ed.Eu estou adicionando um elemento novo nessa lista de lançamentos sem ser pelo método adicionar.Quebrei, ferrei tudo, já era. Eu adicionei um elemento lá no lançamentos sem atualizar o meu total.A consistência foi embora, foi para o saco.Yeah.Então, realmente, quando a gente fala em encapsulamento, não é definitivamente, não é definir como private aqui o atributo, apenas definir como private e colocar de headset e pronto e acabou, principalmente quando a gente está falando de listas e tipos de referência.Tá, então tem que ter esse cuidado. Nesse caso aqui, a única forma de você manipular a lista de lançamentos é através do método adicionar.E na hora de listar os lançamentos, a melhor forma de fazer isso seria exibir a fatura, seria uma forma aqui de print fatura ou de ler fatura, algum método assim que ele sequer, em momento nenhum, joga essa lista de lançamentos para fora.Certo? Ou se por uma necessidade extrema, absurda, precisar passar essa lista para fora, joga ela como a modifiable.So.Coloca ele dentro de uma coleção de uma lista modifiableTá garantido que é somente leitura.

**Figura Carrijo Viana Figur** · _2:23:16_ — Só uma dúvida, é porque a gente está usando array list aí nessa definição. E aí, como o Renan falou, o array list, ele é mutável, mas o Java, ele não tem uma lista.

**Jessé Haniel** · _2:23:18_ — Sim.Mhm.

**Figura Carrijo Viana Figur** · _2:23:32_ — Imutável.

**Jessé Haniel** · _2:23:34_ — Sim, a modifiable list. Isso que a gente estava falando. A modifiable list.

**Figura Carrijo Viana Figur** · _2:23:38_ — Tá.

**Jessé Haniel** · _2:23:46_ — Ah, já vai dar aqui, baby.Então dá para fazer aqui, tem o list off, tem o list copy off.

**Andre Felipe Corradi Botelho** · _2:23:54_ — Você pode devolver cópia também, né?

**Figura Carrijo Viana Figur** · _2:24:00_ — Amor de verbolish.

**Jessé Haniel** · _2:24:02_ — É o melhor que eu acho é esse aqui.Collections of Multifilable List.

**Figura Carrijo Viana Figur** · _2:24:07_ — O tipo objeto de polist, ele não é imutável?

**Relder Maia da Silva Batista** · _2:24:10_ — É, mas isso aí não é um tipo de lista, tá?

**Jessé Haniel** · _2:24:16_ — Desculpa, figura, pode dizer.

**Relder Maia da Silva Batista** · _2:24:16_ — Okay.

**Figura Carrijo Viana Figur** · _2:24:17_ — O list, ele não é imutável, só o list, since there are a list.

**Andre Felipe Corradi Botelho** · _2:24:21_ — Alicia interfacen.

**Jessé Haniel** · _2:24:23_ — O list é a interface, a array list é a implementação da lista.

**Figura Carrijo Viana Figur** · _2:24:25_ — Tá.Entendi.

**Jessé Haniel** · _2:24:31_ — Okay.Beleza.

**Andre Felipe Corradi Botelho** · _2:24:36_ — Vou devolver cópia, né, no get também, né? Aí não vai ser eu mesmo, né?

**Figura Carrijo Viana Figur** · _2:24:40_ — No Kotlin, no Kotlin azul.

**Jessé Haniel** · _2:24:42_ — Então, mas se você devolver a cópia, aí isso pode parecer estranho, porque você está fazendo as alterações, principalmente se for uma cópia mutável. Você está lá adicionando e aí depois, quando você for fazer uma chamada,Aqui do fatura, por exemplo, get lançamentos, ele vai trazer com valores diferentes e isso vai gerar aí uma inconsistência. Ué, estava adicionando, mas eu estava adicionando numa cópia.

**Andre Felipe Corradi Botelho** · _2:25:08_ — Não, não, não.Aí não também, pô. Você pega um linguagem funcional, por exemplo, você ficar devolvendo lista para lá e para cá. Às vezes você quer modificar a lista mesmo. Você quer pegar aquela lista, dar um get, brincar com aquilo lá, multiplicar tudo por 2 e brincar com aquilo.

**Jessé Haniel** · _2:25:22_ — Mhm.

**Andre Felipe Corradi Botelho** · _2:25:25_ — So that.

**Jessé Haniel** · _2:25:25_ — Então, o ideal é que, assim, fique claro que a lista que ela foi retornada, mesmo que por cópia, ela é imutável. Se você quiser criar uma nova coleção a partir daqueles elementos, beleza, mas está explícito ali que é uma nova coleção, que ela não tem nenhuma relação com a outra.Apenas ela foi criada a partir daquela outra.Yeah.Beleza, então por que isso decide microsserviços? Porque o agregado é a menor unidade de consistência transacional. A Fronteira de microsserviço, ele precisa conter agregados inteiros. Você nunca quebra um agregado entre 2 serviços, tá?Então, essa ideia aqui do agregado é que a gente vai ter ele sempre dentro de um microsserviço. Ele vai estar ali espalhado entre vários microsserviços, tá? Então, o microsserviço, ele vai ter no mínimo.Um agregado.Sendo que aí a gente não normalmente não cria apenas um micro serviço para um agregado, a gente cria para um determinado contexto.Certo? Então.Seguindo aquele mesmo cenário que a gente falou há pouco de fatura e de cliente, então para o atendimento, o cliente ele tem telefone, histórico de chamadas, canal preferido para um setor de crédito. Vai ter lá qual é o score, a renda, o limite, o comprometimento, pagamentos, vários outros.Então, cada um desses aqui de novo, eu tenho motivos diferentes para mudar, eu tenho ritmos diferentes para mudar, tá? Então isso aqui, provavelmente cada um desses aqui seria um contexto.Então a gente tem que o contexto delimitado, o bounded contexto, ele é a fronteira dentro da qual o modelo e a linguagem umbico são consistentes e válidos.Check.Bom, para a gente não ficar só na questão da teoria mais uma vez e como esse material já está compartilhado com vocês, então eu vou avançar um pouco aqui para a gente entender como que a gente faz a descoberta desses bounded context e como que a gente vai então definir qual que é o escopo.tipo de um micro serviço ou seja respondendo aquela pergunta Inicial onde é que a gente corta onde é que eu passo a faca ali no nosso monolito para eu poder criar os meus micro serviços uma das formas da gente fazer essa descobertaÉ através do EventStorm.Hoje não vai dar mais tempo para a gente praticar isso, mas a gente faz na próxima aula, principalmente, isso aqui fica bacana da gente fazer uma demonstração juntos em cima do nosso projeto em comum, que a gente vai desenvolver aqui nas aulas e logo em seguida vocês aplicam.Para o projeto que vocês vão fazer em grupos, então formem os grupos até as até a próxima aula, de preferência aí já definindo qual que vai ser o tema, tá bom?Assim que a gente tiver o LMS, eu compartilho com vocês a descrição do quais são os critérios de avaliação.E também quais são os temas que eu posso sugerir para vocês?Então, o event storm, ele é uma técnica para a gente fazer a descoberta dos bounded contexts. Então, normalmente isso é feito em um quadro ou em algum ambiente. Eu gosto bastante de usar aqui o scalidraw, não sei se vocês conhecem.que ele é, olha só, tá até um projeto aberto aqui, ele é um board praticamente infinito, tá?Então, dá para trabalhar à vontade aqui.E dá para ir criando os elementos.If you need the key.

**Sandy da Silva Santos** · _2:29:29_ — Eu já acho que eu já usei ele até dá pra fazer umas animações, não dá?Fazemos com os.

**Jessé Haniel** · _2:29:35_ — Cara, animação? Acho que não. Eu acho que esse é o miro.

**Sandy da Silva Santos** · _2:29:39_ — É, eu sei que tem um, acho que parece com esse aí, ele dá até fazer umas animaçõeszinhas assim dos você faz as ligações e depois ele anima, né?

**Jessé Haniel** · _2:29:48_ — É um miro. Esse aqui ele é. Ele é mais peduro. Ele lembra mais a ideia de você estar escrevendo num quadro mesmo, tá?

**Sandy da Silva Santos** · _2:29:56_ — Thank you.

**Jessé Haniel** · _2:29:56_ — Então esse calidrol eu vou jogar aqui no chat só para vocês terem a referência, mas a gente não vai usar agora.Então, qual que é a ideia?Normalmente, isso aqui é um padrão que se usa, mas que poderia ser o que vocês preferirem. Laranja, evento de domínio, sempre um fato no passado. Então, se a gente está falando sobre geração de comprovantes, então o evento que aconteceu foi o comprovante gerado. Sempre.No passado, então a gente vai marcar lá quais são os elementos a tem aqui que um comprovante ele foi gerado, certo? Qual foi o comando que foi executado para chegar naquele estado?Emitir comprovante.Tá, então tem até aqui as perguntas, o que aconteceu? O comprovante foi gerado. O que foi pedido emitir comprovante? Quem é o responsável? Então tem um agregado aqui onde o comando age e o evento nasce.A política, então, sempre que X então Y, então que reação automática existe. Então, daqui a pouco a gente vai ver que quando a gente emite um comprovante, eu preciso fazer a gravação dele na nossa base. Então isso é uma política, sempre que um comprovante for emitido.Então eu tenho que gravar ele na base.Esse reading model aqui, alguns textos vão trazer como sendo a tela onde a gente interage. Então, o que eu preciso enxergar? A informação que alguém precisa ver para decidir. Então, eu tenho uma determinada tela onde eu tô visualizando algo, né? Eu fiz uma determinada transação, por exemplo.e eu vou lá e faço a emissão do meu comprovante, vou lá e faço a emitir comprovante, tá? Oi Leanderson, diz aí.

**Leanderson Freire Ficagna** · _2:31:46_ — Você está compartilhando algumas coisas no chat, né? É só queria dizer que eu estou sem acesso, né? Se você puder me adicionar.

**Jessé Haniel** · _2:31:56_ — Ah, beleza, no WhatsApp.

**Leanderson Freire Ficagna** · _2:31:58_ — Não, aqui no no mesmo.

**Jessé Haniel** · _2:32:02_ — Não entendi, você não está acessando o chat aqui?

**Leanderson Freire Ficagna** · _2:32:03_ — No.Não, eu não estou com permissão para mandar mensagem nem visualizar as mensagens aqui do chat do teams.

**Jessé Haniel** · _2:32:11_ — Nossa.Eu nunca fiz isso, deixa eu ver se eu descubro aqui como fazer.Olha, realmente não sei. Para mim você aparece normal.

**Leanderson Freire Ficagna** · _2:32:31_ — É, mas aqui está escrito, você não pode ver a mensagem porque não é membro do chat, mas está bom, mas vocês compartilharam outra coisa aí no chat ou não?

**Jessé Haniel** · _2:32:39_ — TheNão teve muita coisa, não. A gente está colocando principalmente lá no grupo do WhatsApp, está? Você conseguiu entrar?

**Leanderson Freire Ficagna** · _2:32:41_ — Ah, tá.Ah, ah, beleza. No no do WhatsApp, sim. Obrigado.

**Jessé Haniel** · _2:32:49_ — Tá, beleza, eu compartilhei lá.alguns arquivos então tem o checklist então a outra a outra mensagem que eu mandei aqui foi do mesmo conteúdo que tem lá no checklist o que eu tô compartilhando na tela agora é o pdf que eu compartilhei para vocês tá então tem aula 01 alunoÉ o que eu estou compartilhando aqui na tela. E o outro que eu tinha mandado no chat foi o link do Scalidraw que eu mostrei agora há pouco para vocês. Mandei agora lá no WhatsApp também.

**Matheus Henrique Pereira Vaz** · _2:33:28_ — Manda o grupo de novo.

**Jessé Haniel** · _2:33:29_ — Beleza! Oi?

**Matheus Henrique Pereira Vaz** · _2:33:31_ — mandou bem no início manda um grupo de novo

**Jessé Haniel** · _2:33:34_ — Do grupo? Tá.Pronto, mandei aqui no chat, é um QR code.Dealay Zagent.Um.Bom, e aí finalmente a gente chega aqui então no cenário prático.Vamos ver até onde a gente consegue ler aqui, tá? Então vamos aplicar tudo no único caso, o relato de alto nível. A gente precisa de um sistema que comprove os PIX que os clientes já fizeram. O PIX em si já é efetivo, já é efetivado pelo Core. A gente não processa o pagamento, só emite e guarda o comprovante e deixa o clienteconsultar depois Inclusive a segunda via no pico dia de pagamento décimo terceiro etc entra muita emissão de uma vez e a consulta é ainda mais frequente todo mundo querendo ver o comprovante não pode perder comprovanteE o cliente reclama quando faz o Pix e o comprovante demora a aparecer.Parece com algum de vocês aqui? Será que reclama se se demorar emitir o comprovante?Yeah.Eu já fiz bastante, então o que a gente tem aqui de event storm? Ou seja, quais são os eventos que a gente tem comprovante emitido?Comprovante aceito, comprovante gravado.Comprovante consultado, cliente notificado. Lembrando sempre no passado.Então esses que são os eventos que acontecem dentro desse fluxo.Comprovante emitido, comprovante aceito, comprovante gravado, comprovante consultado, cliente notificado.Tá? Um notificado aqui no caso do comprovante gravado. Quais são os comandos e atores?Então, quais são os comandos que geraram cada um desses eventos? Emitir comprovante disparado pelo.

**Relder Maia da Silva Batista** · _2:35:48_ — Comprovante aceita é o quê? Desculpa.

**Jessé Haniel** · _2:35:51_ — Oi.

**Relder Maia da Silva Batista** · _2:35:52_ — Comprovante aceito seria o quê? Seria a gravação dele?

**Jessé Haniel** · _2:35:57_ — Comprovante aceito quer dizer que é um comprovante válido.Daqui a pouco a gente vai destrinchando melhor quais são os elementos aí do comprovante, tá?Mas aqui, no caso, eu estou dizendo que é emitido o comprovante aceito, então é porque ele tem todos os campos que são necessários, tem todas as informações que são necessárias.

**Relder Maia da Silva Batista** · _2:36:19_ — Então o nosso sistema, pelo que eu vi na descrição, ali não tem essa validação. Eu acho que teria que ter essa validação não ali na descrição negocial.

**Jessé Haniel** · _2:36:30_ — Então, isso aqui foi o alto nível, foi o que o demandante pediu ali em poucas frases. Tem mais um conteúdo que eu compartilhei com vocês? Deixa eu ver o requisitos.zip.

**Relder Maia da Silva Batista** · _2:36:39_ — Mhm.

**Jessé Haniel** · _2:36:46_ — que aí ele é bem mais rico e a gente vai explorar lá, principalmente entendendo quais são as capacidades que a IA proporciona para a gente, né? Então a gente vai simular aqui todo um ambiente onde aconteceram várias reuniões, enfim.Tudo isso foi gerado.Mas ainda que só se atendo aos pontos aqui do DDD em si, então a gente tem aqui os eventos, a gente tem os comandos, então emitir comprovantes parado pelo sistema de canais após o Pix efetivar.Então, gera comprovante emitido aceito.consultar comprovante disparado pelo cliente ou pelo atendimento gera comprovante gera comprovante consultado Então veja que cada um dos Comandos ele tá dizendo quem faz e ele tá dizendo qual é o evento que é geradoEntão aqui tem uma relação direta entre os comandos e o que a gente identificou antes como sendo os eventos.identificando entidades voos e agregados Então a gente tem que o entidade raiz é o comprovante ele vai ter uma identificação né então cada comprovante ele é ele tem um identificador e ele tem um ciclo de vida que é de aceito para gravadoEntão o comprovante, ele nasce quando ele é aceito e ele muda para gravado depois que ele realmente foi ali persistido. Quais são os voos que a gente tem dentro dessa entidade raiz? Eu tenho a chave pix, a gente estáÉ especificamente discutindo sobre o contexto de Pix. Então, qual é o tipo, qual foi o valor?enfim dinheiro também aqui valor e qual foi a moeda conta bancária agência conta dígito Isso é o que forma o VO de conta bancária e o documento invariante do agregado um comprovante só é válido com todos os elementos obrigatórios consistentesa validação que roda na emissão certo então aqui a gente já entendeu o que que é o comprovante aceito é quando ele passa essa validação que roda na emissão e o comprovante é a raiz ninguém cria um comprovante pela metadeAnd.Então, dessa forma, a gente está desenhando aqui qual é o contexto desse nosso serviço de emissão de comprovantes.Políticas, sempre que um comprovante é aceito, então gravá-lo de forma assíncrona, vira A Fila que a gente vai trabalhar lá nas aulas 4 e 5.Sempre que um comprovante é gravado, então deve notificar o cliente e avisar a antifraude e alimentar a BI.Então isso aqui é parte de tópicos que a gente vai tratar lá na aula 6. Hotspots. Hotspots é o quê? Lembrando aqui em cima. São os pontos de dúvida. Dúvida, conflito ou risco, tá?O que a gente tem de hotspot? O cliente faz o Pix e quer o comprovante na hora, mas a gravação é assíncrona. Então aqui eu tenho um conflito real da consistência imediata versus a volumetria. Resolução na consulta retentar algumas vezes antes de declarar 404 ou não encontrado.E esse hotspot é exatamente um requisito que a gente vai ver nas transcrições do PO. Antes da gente ir para as transcrições, vamos só ver como então que a gente agrupa aqui, então os nossos contextos delimitados, os nossos bounded contexts.Os coisas que mudam juntos e falam a mesma língua, então a gente tem aqui o contexto de emissão, onde a gente tem as responsabilidades de receber, validar, aceitar, publicar a gravação.Então eu tenho essas responsabilidades aqui dentro da emissão. Vira o serviço, comprovante e emissor. Tem uma base própria? Tem uma base de dados própria? Sim.Gravação é outro contexto, então eu tenho que persistir. O comprovante tem que ser idipotente, ou seja, se eu chamar isso aqui várias vezes para persistir o comprovante, eu não vou ter várias entradas na minha base de dados. Isso aqui tem que ser uma chamada única.publicar o eventoPublicar o evento de gravado, dizer, disparar esse evento aqui dizendo o comprovante, ele foi gravado.Tenho aqui um micro serviço de comprovante gravador.Um terceiro microsserviço de consulta ler por ID com cache de fallback. Então, se eu fiz ali determinado, se eu fiz várias chamadas, o que que isso acontece? O que que isso provoca? Então, por isso aqui é a questão do retry e fallback.Fiz uma chamada, OPA, ainda não está disponível. Fiz de novo, fiz de novo, fiz de novo. Depois de quantas tentativas que eu digo que realmente o comprovante não está disponível? Então a gente precisa desse fullback aqui, porque, afinal de contas.Essa parte da emissão e a gravação do banco e a disponibilização, isso é assíncrona.Então, no momento da consulta, pode ser que acabou de ser emitido. Ah, eu já quero acessar aqui. Cadê que não está disponível? Está chegando. Então, é preciso ter essa estratégia aqui de retentativas. Certo?E depois tem um segundo cenário também para vocês lerem com calma, entenderem aí e fazer as comparações em relação ao cenário 1.Para a gente finalizar, heurísticos de corte. Então, como traçar fronteiras, corte por capacidade de negócio, então emissão de comprovante, não por camada técnica, serviço de DAO. Então a gente identificou ali que existe um determinado contexto.parte de emissão de comprovantes.Eu tenho as minhas mudanças de estado, eu tenho as minhas ações, eu tenho razões diferentes para fazer alterações, eu tenho ritmos diferentes de alteração. Então, sempre a gente vai aqui pela capacidade de negócio e não por uma motivação técnica.As costuras organizacionais, então times que mudam coisas por razões diferentes provavelmente são contextos diferentes.Um agregado, ele nunca atravessa uma fronteira de serviço, então o agregado ele sempre está dentro de um mesmo serviço, senão a gente vai ter ali um excesso de comunicação distribuída sem sentido.E por último, aqui, os perfis não funcionais. Então, escala, latência, criticidade. Se a gente tiver uma coisa que, putz, aqui é uma transação que envolve vários contextos e eu tenho aqui uma latência muito alta, então pode ser um motivo para eu manter.Aqueles 2 contextos, ainda que diferentes, mas juntos por causa da relação entre eles, muito forte e por causa aqui das questões não funcionais.Huh?Deixa eu navegar aqui antes da gente finalizar lá na parte das transcrições.que ele menciona aquiCadê sobre as transcrições do PO?Foi aqui no cenário 7, na parte 6.Bom, enfim, não tô conseguindo visualizar agora.Certo, that foi aqui nos hotsports.Então deixa eu mostrar para vocês o que eu já compartilhei láO.No Zip.Então aqui.Tudo isso aqui eu vou compartilhar com vocês, tá? Assim que a gente tiver o LMS, mas.tem aqui a parte do início e vocês pediram para já compartilhar o conteúdo a evolução a cada aula tem aqui então o gabarito tudo que a gente vai evoluir a cada aula táA breve descrição aqui do projeto guiado, do que ele trata e o que a gente vai evoluir a cada aula.Lembrando que a gente está tentando trabalhar com esse cenário B aqui, com esse plano B, que vai depender de vocês validarem aí se vocês conseguem resolver cada uma daquelas dependências que foi no arquivinho deChecklist.cada um dos serviços que a gente vai desenvolver, então comprovante emissor, comprovante gravador, comprovante consulta e aqui a parte compartilhada, onde a gente vai ter os GTOs, os eventos, enfim, tá?ah e também uma pastinha Docs onde vai ter aqui as decisões de arquitetura onde vai ter aqui os ADR beleza no final na qual eu dispondo quando eu disponibilizar para vocês as pastinhas de cada aula sempre vai ter também um arquivo estado MD onde ele vai dizer aquiEstado após a aula um, o que que foi evoluído no conteúdo daquela aula, certo? Então, três serviços com base em H2 segregadas, o emissor tem aqui um post comprovantes que ele retorna o 202.o gravador como que ele vai fazer isso é chamado aqui ao banco tá e como fazer a execução e o principal que eu queria trazer para vocês aqui que eu queria mostrarÉ exatamente aqui essa parte do requisitos.então pacote de licitação de requisitos do projeto guia do módulo que ele simula uma coleta de requisitos de um sistema real e serve de entrada para o projeto que nós vamos desenvolver durante a aula então vocês vão ter um arquivo chamado personasÉ a ficha de cada participante das reuniões. Qual é o papel, o objetivo, o sujeito de falar ou quais são os vieses? Então tem esse arquivinho aqui, personas.Dentro dos requisitos.E aí vocês vão dar uma olhada lá para conhecer quem são os membros dessa equipe.Então a gente tem aqui a Marcela, que é uma PO, a gente tem o Roberto, que é o gerente de produtos de pagamento, tem um especialista em compliance, regulação e por aí vai.em cada uma dessas personas.E aí nós temos aqui a transcrição de algumas sessões contexto problema é escopo tem aqui o kickoff tem o detalhamento da emissão da emissão de comprovante Campos formatosQual é o aceite assíncrono? Então, aqui é, foi o Euder, né? Você tinha perguntado lá sobre os Campos, sobre os dados lá da como é que eu faço a validação disso aqui? O que significa que o comprovante emitido foi aceito?Onde que estão essas informações? Estão aqui nas transcrições, certo?Então tem as transcrições aqui, das 5 sessões e por último, nossa queridíssima piou aqui, no caso era a uma cela.So.Ela foi lá e criou a lista de todas as user stories.Então define aqui o gonsário, define aqui o épico, tem todas as meus stories, quais são os campos que são necessários, tem tudo isso.Qual é o problema?Já estou aqui adiantando para vocês, vocês acham que dá para confiar 100%?De que primeiro?Cada um dos entrevistados, cada um dos stakeholders, não se contradisse.Vocês acham que o PO conseguiu captar fielmente aquilo que os stakeholders trouxeram?Pode ser que sim, pode ser que não. Então por isso que tem aqui essa pastinha de requisitos.Para vocês se divertirem, descobrindo se o sistema ele foi devido, se os requisitos foram devidamente levantados, corretamente levantados e se a gente está pronto para seguir com o nosso desenvolvimento.Ou seja, será que a gente vai se deparar?Com alguma situação aí de hotspot?É, será que a gente vai se deparar com uma situação aí de dúvida, conflito ou risco?Done.Então, lembrando, o foco desse módulo não é desenvolvimento, não é código, não é escrever, não é digitar. Se vocês tiverem alguma dúvida, se vocês quiserem ver alguma parte em específico, se vocês quiserem fazer alguma variação.Do código que a gente trouxe, podemos fazer isso. Estamos aqui, estou compartilhando minha tela inteira, então vocês estão vendo que temos alguns opções aqui de IDE.That.então se for para a gente abrir e escrever aqui alguma coisa tamo junto tá tudo certo mas o foco é principalmente a gente trabalhar nesse nível mais alto que é onde dá problema a gente entender quais foram ali os requisitos queOs stakeholders trouxeram para a gente. E como é que a gente transforma isso em código e principalmente um código que seja ágil, que seja resiliente, que aguente o tranco realmente e que entregue aquilo que foi pedido, certo?Sandy, mandei.

**Sandy da Silva Santos** · _2:51:27_ — Sobre essa questão do hotspot, professor, no caso, na minha opinião e acho que na minha vivência com o desenvolvimento, eu nunca vi uma situação de que não houvesse o hotspot, porque muitas vezes nós iniciar um software, né?É até uma história do pastel, mas assim, mesmo que a gente detalhe, é o máximo aqui tudo e tente pensar todos os cenários durante aí a questão da condução, do desenvolvimento.Surge muitas coisas depois.

**Jessé Haniel** · _2:52:04_ — perfeito e aí assim nesse cenário já tava até recentemente conversando com o o diretor de DTI aqui do tribunal e a gente tentando aplicar em alguns cenários e dizendo para isso né a gente olha para ir às vezes com a visão muito romântica de que éde resolver muito problema mas ela só resolve aquilo que a gente consegue descrever quando ele nem entre a gente tem consenso que que ia vai ajudar como é que ela pode resolver isso E aí a gente tava conversando exatamente que nesses cenários a gente pode usar por exemplo uma uma análisedireito a gente vai no cenário lá do 80/20 Beleza então vamos tentar chegar num consenso aqui pelo menos nesses oitenta por cento a gente consegue né é entrar num consenso belezaEntão, vamos nisso, vamos atacar nesse ponto. E o melhor, vamos deixar registrado que estamos assumindo algo que não foi um consenso, que não é uma unanimidade. Então, da mesma forma que foi feito aqui, o cliente faz o Pix e quer o comprovante na hora, mas a gravação é assíncrona.Então.Um disse, eu quero comprovante na hora, o outro falou, olha, a gravação tem que ser assíncrona. E aí, como é que eu resolvo esse conflito? Vamos fazer o seguinte, a gente vai entregar a gravação assíncrona e esse comprovante na hora, ele vai ser com um pequeno delay, mas que a gente não vai deixar nem transparecer.ser isso para o usuário a gente faz algumas retentativas aqui no momento da consulta então ele pode até demorar alguns instantes mas ele vai chegar de maneira quePara o usuário é transparente, se foi uma chamada só ou se ele ficou tentando ali, insistindo durante um tempo, como podemos seguir assim? Ah, beleza.Então, acordamos em resolver esse conflito dessa forma. Então, a gente precisa fazer esses registros dessas decisões difíceis, porque se em determinado momento chegar alguém e disser, ah, mas você não está fazendo a.A gravação assíncrona ou por que que você está fazendo assíncrona? Eu pedi que fosse emitido aqui a consulta na hora. Então isso aqui tem que ser uma gravação síncrona. Não. Então aconteceu essa situação aqui e a gente resolveu decidir assim.Beleza.

**Sandy da Silva Santos** · _2:54:36_ — Verdade.

**Jessé Haniel** · _2:54:39_ — Set.Perfeito, gente.

**Relder Maia da Silva Batista** · _2:54:44_ — Eu tenho um livro aqui do DDD doGood day.não dá para ver aqui mas é

**Jessé Haniel** · _2:54:54_ — Estou vendo, estou vendo, estou vendo. Às vezes some, mas é.

**Relder Maia da Silva Batista** · _2:54:56_ — É porque corta aqui, é.

**Jessé Haniel** · _2:55:00_ — Bacana, o meu também está aqui.

**Relder Maia da Silva Batista** · _2:55:03_ — Pois é, eu fico ele aqui na cabeceira também.

**Jessé Haniel** · _2:55:07_ — não alcance que ele tá na prateleira lá para cima agora se não ia pegar aqui mas tô nessas leituras aí e com essa parte daí a agora né com essa questão do dos agentes da escrita de código e tal caraEsse é o tipo de material que a gente tem que ler, que a gente tem que estudar mesmo. É esse tipo de coisa. Como que a gente vai fazer para?É entender quais são os requisitos, quais são os contextos, quais são, qual é a linguagem no bico, como é que a gente vai fazer com que os nossos sistemas realmente resolvam lá a as dores de negócio e não a gente querer ficar.impondo as coisas. Para mim, eu acho que uma das coisas mais absurdas que tem é quando a gente chega num lugar para ser atendido, vou estudar aqui uma situação, e alguém fala, ah, mas a gente tem que fazer isso porque o sistema funciona assim.Eu já fico com vergonha alheia, mas cara, o sistema foi muito mal feito. O sistema que está determinando como é que você trabalha.É muito estranho isso.

**Sandy da Silva Santos** · _2:56:16_ — Esse ponto aí, professor, acho que do DDD, hoje eu vejo de forma assim, bem essencial para o nosso trabalho, enquanto desenvolvedor e tudo mais. Mas eu confesso que quando tive o primeiro contato,assim mais profundo eu tenho muitas dificuldades tá de entender todos os termos e tudo que todos esses domínios né que trata aí essa matéria é eu assim eu confesso que eu fiz a matéria lá até decorei algumas coisas e respondi lá fiz as provas néMas assim, não entrou na caixola não. Acho que estou esperançoso aí para aprender um pouquinho mais aí com vocês aí para dominar melhor.

**Jessé Haniel** · _2:56:59_ — Boa, boa maravilha. Então assim, hoje a gente tinha realmente bastante coisa para fazer aqui. Não deu para a gente se prender só ao DDD, então a gente na próxima aula continua. Daí a gente entra na parte de código que hoje a gente não entrou.eu apenas compartilhei aí os requisitos com vocês o ideal seria a gente destinar pelo menos 20 minutinhos para vocês fazerem a leitura ali do dos requisitos entender o que foi pedido né Realmente entender essa contextualização e não simplesmente fazer uma coisa do tipo ahFaz um sistema aí que emite comprovantes e que permite consulta. É isso aí. A gente foi em 30 segundos. É 2 palitos para fazer isso. Faz uma endpoint de gravação de consulta. Acabou.Tá mas aí quando a gente vai para um cenário desse de tá é dia de pagamento acabou de sair PLR acabou de sair não sei o que lá tá todo mundo lá consultando todo mundo consumindo E aí como é que eu faço para aguentar o trancoSerá que eu posso fazer um monolito que ele simplesmente tem um endpoint lá de post um get?Posso fazer só isso? Será que a gente vai fazer escalonamento apenas vertical? Põe mais memória, aí põe mais processamento, põe numa VM melhor. Cria um Monte de instância aí, beleza. Aí uma que gravou, ela não transmitiu corretamente, não sincronizou.e aí quando a pessoa vai consultar lá o comprovante não está disponível então assim são alguns cenários que se a gente olhar de maneira muito apressada parecem muito simples mas se a gente for olhar principalmente a parte dos requisitos não funcionais isso pode ir demandando que a gente tenhaQue a gente precise usar estratégias mais complexas, tá? Então a gente vai entender melhor essa parte do DDD, vai escrever ali, evoluir o.a nossa aplicação e, em seguida, vocês já se reúnem aí nos grupos para aplicar tudo isso ao contexto de vocês. E aí, normalmente, é aí que as dúvidas vão surgir. Quando está todo mundo fazendo junto, é lindo, é maravilhoso. E quando a gente separa e se deparaPágina em branco lá. E agora? Vamos para onde? Tá? Mas vamos fazer tudo isso aí quarta-feira. Pessoal, 18 horas, um minuto. Hora a gente encerrar. A gente vai ficando por aqui.Mais alguma dúvida? Pergunta, comentário?Não, beleza. Se de repente lembrarem de algo depois, quiserem mandar pelo WhatsApp. Oi.

**Alexandre Machado Rosa Filho** · _2:59:41_ — Não, tranquilo.

**Sandy da Silva Santos** · _2:59:44_ — Thank you so.Então tranquilo.

**Jessé Haniel** · _2:59:50_ — Beleza, então se quiserem mandar depois alguma coisa pelo WhatsApp, fica à vontade, tá?

**Relder Maia da Silva Batista** · _2:59:51_ — Onde you, onde you?Onde que você compartilhou isso aí do dos requisitos mesmo?

**Jessé Haniel** · _3:00:01_ — Tem um ZIP lá no grupo do WhatsApp e para entrar no grupo, se você ainda não tiver aqui no chat, tem um QR Code.

**Relder Maia da Silva Batista** · _3:00:05_ — Eu não estou nesse grupo não.

**Andre Felipe Corradi Botelho** · _3:00:07_ — Pode mandar de novo?

**Relder Maia da Silva Batista** · _3:00:10_ — Ah, tá, beleza.

**Andre Felipe Corradi Botelho** · _3:00:12_ — Pode mandar de novo que eu entrei depois?

**Sandy da Silva Santos** · _3:00:12_ — eu sou só uma coisa é que isso falou que vai ter vai depois vai liberar né o LMS para gente não é isso que parece que já liberou aqui um ADA é umas aulas gravadas né eu já tô até acompanhando lá

**Jessé Haniel** · _3:00:20_ — Sim.

**Sandy da Silva Santos** · _3:00:27_ — Parece que é o é a mesma, não sei se é da do mesmo, mesmo curso, né?

**Jessé Haniel** · _3:00:33_ — O que eu preciso que vocês acessem é esse ambiente aqui, Oh.É esse aqui que ele vai aparecer aqui, conteúdo didático, arquivos, exercícios, tá? É esse aqui que eu vou precisar que vocês acessem.

**Figura Carrijo Viana Figur** · _3:00:50_ — Não estamos usando sua tela só para lá.

**Sandy da Silva Santos** · _3:00:50_ — Beleza.

**Jessé Haniel** · _3:00:51_ — Eu acho que esse das aulas gravadas é diferente.

**Sandy da Silva Santos** · _3:00:54_ — Entendi.Beleza.

**Jessé Haniel** · _3:00:58_ — Beleza gente.

**Sandy da Silva Santos** · _3:01:00_ — Beleza, acho que na hora que você falou que tava mostrando aí não tava compartilhando tal professor.

**Jessé Haniel** · _3:01:04_ — Ah foi tá bom deixa eu ver se agora foi

**Sandy da Silva Santos** · _3:01:08_ — Sim, uh-huh, I see, né?

**Jessé Haniel** · _3:01:09_ — Chegou aí?Beleza, então aqui vai ter aqui arquivos, conteúdo didático, tal, e aí em cada uma das pastinhas das aulas, aí eu vou compartilhando aqui com vocês, tá?

**Andre Felipe Corradi Botelho** · _3:01:22_ — É, ainda não tenho um.

**Sandy da Silva Santos** · _3:01:24_ — Maravilha.

**Jessé Haniel** · _3:01:26_ — Acho que daqui para quarta-feira vocês já vão estar com acesso.Beleza gente, muito obrigado pela presença hoje, pela interação, por tudo. Valeu, boa noite, bom descanso, até quarta.

**Andre Felipe Corradi Botelho** · _3:01:38_ — Pode ir só mandar de novo no WhatsApp?

**Renan Sarto Gregorio** · _3:01:39_ — And.Yeah.

**Sandy da Silva Santos** · _3:01:41_ — Other pessoal.

**Andre Felipe Corradi Botelho** · _3:01:43_ — O que você mandou? Não o WhatsApp, não. O pessoal entrou hoje. Não, QR code pessoa que entrou agora, né? Você não tinha mandando negócio no WhatsApp?

**Renan Sarto Gregorio** · _3:01:43_ — Well, OK.

**Matheus Henrique Pereira Vaz** · _3:01:43_ — Tell me someone I should love.

**Leonardo Oliveira Faria** · _3:01:46_ — Okay, good.

**Jessé Haniel** · _3:01:50_ — Deixa eu ver se eu consigo deixar como fixado?Aí eu acho que você consegue acessar.

**Leonardo Oliveira Faria** · _3:01:58_ — I got it.

**Jessé Haniel** · _3:01:59_ — Valeu.

**Leonardo Garcia Melo** · _3:02:01_ — So.

**Lucas Guimaraes Gassert** · _3:02:02_ — Valeu, gente. Tchau, tchau.

**Renan Sarto Gregorio** · _3:02:04_ — Valeu.

**Figura Carrijo Viana Figur** · _3:02:06_ — Hello.

**Andre Felipe Corradi Botelho** · _3:02:10_ — É, não aparece nome.

**Jessé Haniel** · _3:02:15_ — I will incomine novamente.

**Andre Felipe Corradi Botelho** · _3:02:17_ — Tá bom?

**Jessé Haniel** · _3:02:18_ — Value, tchau, tchau.

**Andre Felipe Corradi Botelho** · _3:02:19_ — Tchau.

