# User Stories — API de Comprovantes de PIX

> **Documento compilado por:** Marcela Tavares (Product Owner, Pagamentos Instantâneos)
> **Data:** 06/05/2026
> **Base:** cinco sessões de elicitação (kickoff, emissão, consulta, confiabilidade, compliance)
> **Status:** primeira fonte de verdade para o time de desenvolvimento.

Este documento é o ponto de partida do desenvolvimento. As transcrições das cinco reuniões estão no mesmo diretório e devem ser consultadas para entender o contexto e dirimir dúvidas. Compilei o mais rápido que consegui para não travar o time; em caso de divergência, recorram às transcrições.

## Glossário (linguagem ubíqua)

- **Comprovante de PIX:** documento de uma transação PIX já efetivada por outro sistema.
- **2ª via:** consulta/reexibição de um comprovante existente.
- **Emissão:** registro de um novo comprovante (POST sistema-a-sistema).
- **Consulta:** recuperação de um comprovante por identificador.
- **Identificador:** UUID v4 devolvido na emissão; chave da consulta.
- **Cache:** memória rápida na frente do banco para acelerar consultas.
- **Evento "comprovante gravado":** aviso publicado quando um comprovante é persistido, para outras áreas reagirem.

---

## Épico 1 — Emissão de comprovante

### US-01 — Receber e aceitar a emissão de um comprovante

**Como** sistema de origem (core/intermediário de PIX),
**quero** enviar os dados de um PIX já efetivado para a API de comprovantes,
**para** que o comprovante fique disponível para consulta posterior.

**Notas de domínio**
- A emissão é **sistema-a-sistema** (POST). O cliente final nunca chama a emissão.
- O sistema **não efetiva PIX** — apenas registra o comprovante de um PIX já ocorrido.

**Campos do comprovante**

| Campo | Obrigatório | Formato |
|---|---|---|
| nome | Sim | texto |
| tipo_documento | Sim | CPF ou CNPJ |
| numero_documento | Sim | texto |
| numero_agencia | Sim | 4 caracteres |
| numero_conta | Sim | 6 caracteres |
| digito_verificador_conta | Sim | 1 caractere |
| valor_transacao | Sim | valor monetário |
| tipo_chave_pix_destino | Sim | celular, e-mail, CPF, CNPJ, aleatória |
| chave_pix_destino | Sim | texto |
| nome_cliente_destino | Sim | texto |
| identificacao_pix | Não | texto livre |
| data_hora_transacao | Sim | data/hora do PIX |

**Critérios de aceite**

- **Dado** uma requisição de emissão com todos os campos obrigatórios válidos,
  **Quando** o POST é recebido,
  **Então** o sistema responde **`201 Created`** com um **identificador (UUID v4)** e a **data/hora da requisição**.

- **Dado** uma requisição de emissão com algum campo obrigatório ausente,
  **Quando** o POST é recebido,
  **Então** o sistema responde erro de validação indicando o campo que faltou.

- **Dado** que `numero_agencia`, `numero_conta` ou `digito_verificador_conta` não respeitam o tamanho esperado,
  **Quando** o POST é recebido,
  **Então** o sistema rejeita a requisição com erro de validação.

### US-02 — Gravar o comprovante de forma assíncrona

**Como** time de plataforma,
**quero** que a gravação do comprovante no banco aconteça de forma assíncrona após o aceite,
**para** que a API aguente picos de volumetria sem engasgar.

**Critérios de aceite**

- **Dado** que uma emissão foi aceita,
  **Quando** o aceite é respondido,
  **Então** a gravação no banco é processada por um consumidor de forma assíncrona, e a resposta ao chamador não espera a gravação concluir.

---

## Épico 2 — Consulta e 2ª via

### US-03 — Consultar um comprovante por identificador

**Como** atendente (ou cliente, via app),
**quero** consultar um comprovante pelo seu identificador,
**para** obter a 2ª via rapidamente.

**Critérios de aceite**

- **Dado** um identificador de comprovante existente,
  **Quando** a consulta é feita,
  **Então** o sistema busca **primeiro no cache** e, em caso de ausência, **no banco**, **populando o cache** quando encontra, e retorna o comprovante.

- **Dado** um identificador que não corresponde a nenhum comprovante,
  **Quando** a consulta é feita,
  **Então** o sistema retorna **`404`**.

### US-04 — Desempenho da consulta

**Como** área de produto,
**quero** que a consulta seja rápida mesmo em picos,
**para** entregar a 2ª via "na hora" e reduzir o tempo de atendimento.

**Critérios de aceite**

- **Dado** que a consulta é a operação mais frequente do sistema,
  **Quando** o volume cresce em datas de pico,
  **Então** a latência da 2ª via permanece baixa (apoiada no cache).

---

## Épico 3 — Confiabilidade

### US-05 — Não perder comprovante

**Como** área de produto e compliance,
**quero** que nenhum comprovante seja perdido no fluxo assíncrono,
**para** garantir o direito do cliente à 2ª via e evitar risco regulatório.

**Critérios de aceite**

- **Dado** que a gravação de um comprovante falha por indisponibilidade temporária (ex.: banco fora),
  **Quando** a falha ocorre,
  **Então** o sistema **re-tenta** a gravação até concluir, sem descartar a mensagem.

- **Dado** o fluxo assíncrono de gravação,
  **Quando** ocorrem falhas,
  **Então** o sistema deve **garantir a entrega** da gravação, sem perda de comprovantes.

---

## Épico 4 — Integrações por evento

### US-06 — Publicar evento quando um comprovante é gravado

**Como** áreas consumidoras (antifraude, BI, notificação),
**quero** ser avisada quando um comprovante é gravado,
**para** reagir sem que o gravador precise me conhecer.

**Critérios de aceite**

- **Dado** que um comprovante foi gravado com sucesso,
  **Quando** a gravação conclui,
  **Então** o sistema **publica um evento "comprovante gravado"** em um tópico, e o gravador **não conhece** os assinantes.

### US-07 — Notificar o cliente que o comprovante está disponível

**Como** cliente,
**quero** ser avisado quando meu comprovante estiver pronto,
**para** não precisar ficar consultando.

**Critérios de aceite**

- **Dado** que o evento "comprovante gravado" foi publicado,
  **Quando** a área de notificação o consome,
  **Então** o cliente é notificado de que o comprovante está disponível.

> Escopo: **MVP**.

---

## Épico 5 — Compliance e retenção

### US-08 — Reter o comprovante pelo prazo regulatório

**Como** compliance,
**quero** que os comprovantes sejam retidos pelo prazo regulatório,
**para** cumprir a exigência do Bacen.

**Critérios de aceite**

- **Dado** um comprovante gravado,
  **Quando** o tempo passa,
  **Então** o comprovante é retido por **10 anos** a partir da data da transação, não sendo descartado antes.

### US-09 — Trilha de auditoria de acesso

**Como** compliance e segurança,
**quero** registrar todo acesso a um comprovante,
**para** responder a auditorias sobre quem consultou o quê e quando.

**Critérios de aceite**

- **Dado** uma consulta a um comprovante,
  **Quando** a consulta é feita,
  **Então** o sistema registra **quem** consultou, **qual** comprovante e **quando**.

### US-10 — Tratar dado pessoal sensível

**Como** compliance,
**quero** que os dados do comprovante sejam tratados como dado pessoal sensível em todos os canais,
**para** cumprir a LGPD.

**Critérios de aceite**

- **Dado** que o comprovante contém dado pessoal (nome, documento, conta, chave),
  **Quando** o dado trafega pelo banco, fila, tópico ou consulta,
  **Então** é tratado conforme a LGPD, com base legal de obrigação de guarda documentada.

---

## Fora do escopo do MVP (catálogo / fase 2)

- Efetivação, liquidação ou cancelamento de PIX (é o core bancário — fora do escopo, sempre).
- Consulta por período / listagem de comprovantes.
- Anonimização e política de descarte pós-prazo de retenção.
- Relatórios de BI sobre comprovantes.
