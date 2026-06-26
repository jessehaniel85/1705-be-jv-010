# Sessão 2 — Emissão do comprovante

- **Projeto:** API de Comprovantes de PIX — 2ª via
- **Data:** 13/04/2026 (segunda-feira), 14h00–14h30
- **Duração:** 30 min
- **Plataforma:** Microsoft Teams
- **Participantes:** Marcela Tavares (PO, facilitadora), Daniel Prado (Arquiteto de Soluções), Roberto Khoury (Gerente de Produto), Sandra Lima (Coordenadora de Atendimento)

---

**Marcela:** Oi, pessoal. Sessão 2. Hoje é a emissão do comprovante. Eu quero sair daqui com três coisas: a lista de dados que entram, o que é obrigatório, e o que acontece quando alguém manda emitir. Daniel prometeu explicar o "aceite assíncrono" pra gente de negócio. Mas antes — quem chama a nossa emissão? Quem é que manda os dados pra cá?

**Daniel:** Boa pergunta de fronteira. Quem chama a emissão é um sistema, não uma pessoa. O fluxo é: o PIX é efetivado lá no core; o core, ou um sistema intermediário, dispara pra gente uma requisição com os dados daquele PIX, e a gente registra o comprovante. Então a emissão é **sistema-para-sistema**, é uma API que recebe um POST. O cliente final nunca chama a emissão direto — ele só consulta, depois.

**Roberto:** Então a emissão é invisível pro cliente. Ele nem sabe que ela existe.

**Daniel:** Exato. A emissão é a porta de entrada do dado. A consulta é a porta de saída pro cliente.

**Marcela:** Anotei. Emissão = POST sistema-a-sistema. Agora os dados. Daniel, você fez o levantamento dos campos. Manda.

**Daniel:** Fiz. Vou ler e a gente discute cada um. Os campos do comprovante são: **nome** do titular; **tipo de documento** — CPF ou CNPJ; **número do documento**; **número da agência**; **número da conta**; **dígito verificador da conta**; **valor da transação**; **tipo da chave PIX de destino**; **chave PIX de destino**; **nome do cliente de destino**; **identificação do PIX**; e **data e hora da transação**.

**Marcela:** Calma que eu vou por partes. Nome, tipo de documento, número do documento — isso é do **pagador**, certo? De quem fez o PIX?

**Daniel:** Isso. Nome, tipo e número do documento são do titular da conta que pagou. Tipo de documento é um dos dois: **CPF** ou **CNPJ**. Não tem um terceiro.

**Roberto:** Pessoa física e pessoa jurídica. Faz sentido, empresa também faz PIX.

**Daniel:** Depois vêm os dados da conta de origem: **agência**, **conta** e **dígito**. E aqui eu preciso ser chato com formato, porque é onde dá ruim. A **agência tem 4 caracteres**. A **conta tem 5 caracteres**. E o **dígito verificador tem 1 caractere**. Quatro, cinco, um.

**Marcela:** Deixa eu repetir pra não errar: agência **quatro**, conta **cinco**, dígito **um**.

**Daniel:** Isso. E eu falo "caracteres" de propósito, não "números", porque a gente trata como string. Zero à esquerda importa. Agência "0341" é diferente de "341". Se você guardar como número inteiro, perde o zero da frente e o comprovante fica errado. Então: string, com o tamanho exato.

**Sandra:** Isso aí é dor real, viu. Atendente já me trouxe caso de agência aparecendo sem o zero e o cliente achando que era outra conta.

**Daniel:** Pois é. Por isso eu quero validação de tamanho na emissão. Agência que não tem 4, conta que não tem 5, dígito que não tem 1 — a gente rejeita na entrada. Não deixa entrar dado torto.

**Marcela:** Anotando: **agência 4 chars, conta 5 chars, dígito 1 char, todos string, validar tamanho na emissão.** Continua, Daniel. Valor.

**Daniel:** **Valor da transação** — o valor do PIX. Obrigatório, claro. Não faz sentido comprovante sem valor.

**Roberto:** Óbvio. Comprovante sem valor não é comprovante.

**Daniel:** Depois vêm os dados do **destino** — pra quem foi o PIX. **Tipo da chave PIX de destino**, **chave PIX de destino** e **nome do cliente de destino**. O tipo da chave é importante porque define o que vem no campo da chave.

**Marcela:** Quais são os tipos de chave?

**Daniel:** Cinco: **celular**, **e-mail**, **CPF**, **CNPJ** e **aleatória**. A chave aleatória é aquele código comprido que o banco gera. Dependendo do tipo, o conteúdo da chave muda — celular é um telefone, e-mail é um e-mail, e por aí vai.

**Sandra:** E o nome do cliente de destino é o nome de quem recebeu? Isso aparece no comprovante que o cliente vê?

**Daniel:** Sim, é o nome de quem recebeu, e sim, aparece. O cliente quer ver "paguei pra fulano". Faz parte da 2ª via.

**Marcela:** Anotado: destino = tipo de chave (celular, e-mail, CPF, CNPJ, aleatória), a chave em si, e o nome do recebedor. Faltam dois.

**Daniel:** **Identificação do PIX** — é um campo de **mensagem livre**, aquele textinho que a pessoa escreve no PIX, tipo "aluguel março" ou "racha do almoço". É livre, opcional no sentido de que pode vir vazio, mas o campo existe. E por último **data e hora da transação** — quando o PIX foi efetivado. Isso é a data do PIX em si, não a data em que a gente recebeu.

**Marcela:** Essa distinção é importante. **data_hora_transacao = quando o PIX aconteceu.** Tá. Agora, dos campos todos, o que é **obrigatório** mesmo? Porque eu quero saber o que a gente rejeita se faltar.

**Daniel:** Obrigatórios pra fazer sentido um comprovante: nome, tipo e número do documento do pagador, agência, conta, dígito, valor, tipo e chave PIX de destino, nome do destino, e data e hora da transação. O único que eu trataria como **opcional** é a **identificação do PIX**, a mensagem livre — porque tem PIX que a pessoa não escreve nada. Esse pode vir vazio. Todos os outros são obrigatórios.

**Marcela:** Então só a mensagem livre é opcional. O resto, faltou, rejeita.

**Daniel:** Isso. Faltou obrigatório, a gente não aceita a requisição e responde um erro de validação dizendo o que faltou. Bem na cara, pra quem chamou corrigir.

**Roberto:** Beleza. E quando dá tudo certo, o que acontece? O sistema responde o quê?

**Daniel:** Aqui entra o "aceite assíncrono" que eu prometi. Segura que vou explicar com calma porque é contraintuitivo pra quem é de negócio. Quando o POST chega com os dados válidos, a gente **não grava no banco na hora** e só então responde. A gente faz diferente: a gente **valida os obrigatórios, dá um "aceitei", e responde já** — devolvendo um **identificador**, um UUID, e a **data e hora da requisição**. A gravação de verdade no banco acontece **depois**, de forma assíncrona.

**Roberto:** Peraí. Você responde "aceitei" antes de gravar? E se a gravação falhar depois?

**Daniel:** Essa é a pergunta certa, e a gente vai cuidar disso com muito carinho na Sessão 4, que é a de confiabilidade. Mas deixa eu explicar **por que** a gente faz assim. Lembra do pico do 13º? Se a cada POST eu tiver que gravar no banco na hora, abrir transação, esperar o banco confirmar, e só então responder, no pico o banco vira gargalo e o sistema engasga. Então a gente desacopla: recebe, valida o básico, **aceita**, devolve o identificador, e **enfileira** a gravação pra um consumidor processar no ritmo dele. Isso é o **aceite assíncrono**.

**Marcela:** Então a resposta não é "criei", é "aceitei pra processar".

**Daniel:** Exatamente, e isso tem até um código HTTP próprio. Não é o "criado com sucesso" tradicional, o 201. É o **202**, que em HTTP significa literalmente **"aceito"** — "recebi, validei, vou processar, aqui está seu identificador pra você acompanhar". A semântica é diferente: 201 diz "já está gravado, pronto". **202 diz "aceitei, mas a gravação vai acontecer logo depois".** No nosso caso é 202, porque a gravação é assíncrona. Isso é importante, não confunde os dois.

**Marcela:** Deixa eu cravar isso porque é sutil: a emissão responde **202 Accepted**, não 201. Porque a gente aceita e grava depois, não grava na hora.

**Daniel:** Isso, perfeito. 202. E na resposta vão duas coisas: o **identificador do comprovante** — um UUID versão 4, que é como o cliente e o atendente vão consultar depois — e a **data e hora da requisição**, que é quando a gente recebeu, diferente da data e hora da transação que é quando o PIX aconteceu.

**Sandra:** Espera, então esse identificador, esse UUID, é o que o atendente vai usar pra puxar a 2ª via?

**Daniel:** É a chave de consulta. Ele identifica unicamente aquele comprovante no nosso sistema. Guarda esse fio porque ele amarra com a Sessão 3.

**Marcela:** E esse atraso entre o "aceitei" e o "gravei" — é esse atraso que explica o caso da Sandra, do comprovante recém-emitido que ainda não aparece?

**Daniel:** É exatamente esse. Bingo. Como a gravação acontece um tiquinho depois do aceite, existe uma janela curta em que o comprovante já tem identificador, já foi aceito, mas ainda não está no banco pra ser consultado. É essa janela que a Sandra sente lá na ponta. A gente resolve isso na consulta, Sessão 3.

**Roberto:** Tá ficando claro o quebra-cabeça. Aceita rápido, grava depois, e a consulta tem que ser esperta pra cobrir a janelinha. Tô dentro. Só não quero perder comprovante por causa desse "depois".

**Daniel:** E não vamos. "Não perder comprovante" é o tema central da Sessão 4. Aqui na 2 a mensagem é só: emissão valida, aceita com 202 e identificador, e a gravação real é assíncrona.

**Marcela:** Fechando a Sessão 2 então. Recapitulando os campos obrigatórios: nome, tipo de documento (CPF ou CNPJ), número do documento, agência (4 chars), conta (5 chars), dígito (1 char), valor da transação, tipo da chave de destino (celular, e-mail, CPF, CNPJ ou aleatória), chave de destino, nome do destino, e data/hora da transação. **Opcional só a identificação do PIX**, a mensagem livre. Faltou obrigatório → erro de validação. Deu certo → **202 Accepted** com **UUID v4** e **data/hora da requisição**. Gravação assíncrona. É isso?

**Daniel:** É isso. Caprichou.

**Roberto:** Bom pra mim. Tenho que correr. Valeu, pessoal.

**Sandra:** Eu só fico com a curiosidade do "ainda não aparece", mas a Marcela já marcou pra semana que vem.

**Marcela:** Marquei. Sessão 3, consulta e desempenho, é toda sua, Sandra.

---

## Decisões da Sessão 2

1. A **emissão** é uma API **sistema-a-sistema** (POST). O cliente final nunca chama a emissão; só consulta depois.
2. **Campos do comprovante:** nome, tipo_documento (CPF/CNPJ), numero_documento, numero_agencia, numero_conta, digito_verificador_conta, valor_transacao, tipo_chave_pix_destino, chave_pix_destino, nome_cliente_destino, identificacao_pix, data_hora_transacao.
3. **Formatos validados na entrada:** agência = **4 caracteres**, conta = **5 caracteres**, dígito = **1 caractere**. Tratados como **string** (zero à esquerda importa).
4. **Tipos de chave PIX de destino:** celular, e-mail, CPF, CNPJ, aleatória.
5. **Único campo opcional:** identificacao_pix (mensagem livre). **Todos os demais são obrigatórios.** Falta de obrigatório → erro de validação.
6. Resposta de sucesso da emissão = **202 Accepted** (aceite assíncrono), contendo **identificador (UUID v4)** e **data/hora da requisição**.
7. A **gravação no banco é assíncrona** (acontece depois do aceite). Isso cria a janela do "comprovante recém-emitido" — a tratar na consulta (Sessão 3).
8. `data_hora_transacao` (quando o PIX ocorreu) é **diferente** da data/hora da requisição (quando o sistema recebeu).

## Action items

- **Daniel:** trazer para a Sessão 4 o desenho de "não perder comprovante" no fluxo assíncrono.
- **Sandra:** abrir a Sessão 3 com o caso do comprovante recém-emitido.
- **Marcela:** acrescentar ao glossário "aceite assíncrono", "202 Accepted", "identificador (UUID v4)".

## Glossário acrescentado

- **Aceite assíncrono:** o sistema valida e aceita a emissão respondendo já, e grava no banco depois (de forma desacoplada), para aguentar picos.
- **202 Accepted:** código de resposta da emissão — "aceitei e vou gravar depois" (diferente de 201, que seria "já gravado").
- **Identificador do comprovante:** UUID v4 devolvido na emissão; é a chave usada na consulta.
