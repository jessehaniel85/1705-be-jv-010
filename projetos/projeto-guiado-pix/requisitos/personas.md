# Personas — Projeto Comprovantes PIX

Fichas das pessoas que participam das reuniões de elicitação de requisitos. Use-as para entender de onde vem cada fala nas transcrições e por que cada uma defende o que defende. Mantêm voz consistente ao longo das cinco sessões.

> Contexto institucional: todos são colaboradores (ou prestadores) da Caixa Econômica Federal, lotados na Diretoria de Meios de Pagamento e áreas correlatas. As reuniões ocorrem em abril de 2026, semanais, via Microsoft Teams.

---

## Marcela Tavares — Product Owner, Pagamentos Instantâneos

- **Papel:** facilita as reuniões, conduz a pauta, traduz negócio ↔ técnico e, ao final, **compila as user stories** que viram a primeira fonte de verdade para o time de desenvolvimento.
- **Objetivos:** entregar um MVP de comprovantes de PIX que resolva a dor de atendimento sem estourar prazo; manter as conversas focadas; sair de cada reunião com decisões e *action items* claros.
- **Jeito de falar:** organizada, faz boas perguntas de fechamento ("então, deixa eu confirmar..."), recapitula. Usa muito "perfeito", "fechado", "anotei aqui". Cria glossário enquanto a reunião acontece.
- **Vieses (importante):** sob pressão de prazo, **simplifica demais** quando transcreve. Tende a transformar nuances técnicas em frases curtas e redondas, perdendo exceções, números exatos e fases. É uma profissional competente — os deslizes vêm da pressa de compilar, não de incompetência. **É a fonte natural das discrepâncias entre as user stories e as transcrições.**

## Roberto Khoury — Gerente de Produto de Meios de Pagamento (dono do negócio)

- **Papel:** patrocinador / dono do produto. É quem responde pelo resultado de negócio e pela priorização.
- **Objetivos:** reduzir a "dor do cliente", melhorar métricas de atendimento (TMA, recontato), dar visibilidade executiva. Quer o MVP no ar rápido.
- **Jeito de falar:** direto, orientado a impacto e número. Fala em "dor do cliente", "experiência", "isso vira NPS", "quanto isso custa de atendimento por mês?". Tem urgência, às vezes corta digressão técnica: "isso a gente resolve depois, foca no que o cliente sente".
- **Vieses:** otimiza para o que é visível ao cliente e ao board; pode subestimar trabalho de infraestrutura/confiabilidade que "o cliente não vê". Defende empurrar para fase 2 o que não é essencial ao MVP.

## Dra. Helena Sasaki — Especialista em Compliance e Regulação (Bacen / LGPD)

- **Papel:** guardiã regulatória. Valida que o sistema respeita exigências do Banco Central, LGPD e políticas internas de auditoria.
- **Objetivos:** garantir retenção correta, trilha de auditoria, tratamento adequado de dado pessoal sensível, base legal/consentimento. Evitar exposição da Caixa a sanção regulatória.
- **Jeito de falar:** cautelosa, precisa, cita normas e prazos com exatidão. "Pela nossa política de retenção...", "isso é dado pessoal, então...", "preciso que isso fique registrado em ata". Não tem pressa: prefere travar agora a remediar depois.
- **Vieses:** conservadora; na dúvida, exige o controle mais rígido. Pode parecer freio para Roberto. Insiste em números exatos de retenção e em registro de quem acessou o quê.

## Daniel Prado — Arquiteto de Soluções

- **Papel:** desenha a solução técnica. Introduz na conversa os conceitos de **aceite assíncrono (202)**, **fila**, **fila morta (DLQ)**, **cache**, **eventos/tópico**, **idempotência** — sempre em linguagem de conceito, **sem citar marcas de fornecedor**.
- **Objetivos:** uma solução que aguente **alta volumetria** e **picos** (datas de pagamento, 13º), não perca comprovante, tenha baixa latência na 2ª via e se integre a outras áreas sem acoplamento.
- **Jeito de falar:** pensa em voz alta sobre cenários de falha ("e se o banco estiver lento na hora do pico?", "o que acontece se isso cair no meio?"). Usa analogias para explicar conceito técnico a quem é de negócio. Pondera trade-offs.
- **Vieses:** otimiza para robustez e desacoplamento; às vezes entra em detalhe técnico que Roberto corta. É a **voz canônica dos fatos técnicos** — quando a user story diverge do que Daniel disse, geralmente o erro está na story.

## Sandra Lima — Coordenadora de Atendimento / Canais

- **Papel:** representa a operação de atendimento (call center, app, agências). Traz a dor concreta do dia a dia.
- **Objetivos:** que o atendente (e o próprio cliente no app) encontre o comprovante **rápido**, inclusive logo após o PIX. Reduzir reclamação de "fiz o PIX e o comprovante não aparece".
- **Jeito de falar:** concreta, anedótica, fala pelo cliente e pelo atendente. "Ontem mesmo uma cliente ligou...", "o atendente fica com o cliente na linha esperando", "tem que aparecer na hora". Tradutora da realidade de ponta.
- **Vieses:** foca no caso de uso imediato do atendimento; pode não enxergar o custo técnico do "instantâneo". É quem **levanta o caso crítico** do comprovante recém-emitido que ainda não gravou.

## Téo Mendonça — Analista de Segurança / Antifraude *(entra nas Sessões 4 e 5)*

- **Papel:** representa segurança e prevenção a fraude.
- **Objetivos:** ser **avisado quando comprovantes são gerados** (para cruzar com modelos antifraude), sem que o time de gravação precise conhecê-lo. Garantir auditabilidade.
- **Jeito de falar:** desconfiado no bom sentido, pensa em abuso e rastreabilidade. "E se alguém gerar comprovante em massa?", "eu não quero atrasar o fluxo de vocês, só quero ser notificado". Pragmático sobre desacoplamento.
- **Vieses:** quer dados e eventos "para ontem", mas entende que não pode acoplar nem travar o fluxo principal. Bom defensor do modelo de **eventos/tópico** (publica-assina).
