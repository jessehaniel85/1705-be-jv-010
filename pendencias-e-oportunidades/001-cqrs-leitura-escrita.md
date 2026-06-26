# 001 — CQRS / separação leitura-escrita

- **Tipo:** Oportunidade (conteúdo)
- **Status:** Backlog — fora do escopo da Aula 1 por decisão de tempo
- **Origem:** discussão de 22/06/2026 — pergunta "existe relação entre CQRS e DDD?"
- **Decisão:** **não** incluir na Aula 1. A aula já está densa (DDD completo: blocos táticos, bounded context, context map, event storming, dois cenários); incluir CQRS arriscaria estourar o horário ou deixar a aula corrida. Retomar em **aula futura deste módulo** ou em oferta posterior.

## Resumo do tema
CQRS (*Command Query Responsibility Segregation*) separa o **modelo de escrita** (comandos, que alteram estado e protegem invariantes) do **modelo de leitura** (consultas, otimizadas para exibir dados). É a elevação, ao nível de arquitetura, do **CQS** de Bertrand Meyer (um método ou é comando ou é query).

**Relação com DDD** (o ponto que motivou o registro): CQRS resolve uma **tensão real do DDD**. O agregado é a fronteira de consistência da escrita — desenhado para proteger invariantes, referenciar outros agregados só por id e proibir "carregar a vista inteira". Isso é ótimo para escrever e ruim para ler (telas/relatórios querem dados desnormalizados, cruzando agregados). CQRS deixa o agregado **puro na escrita** e dá à leitura um **modelo próprio** (projection). Pontos de precisão: aplica-se **dentro de um bounded context** (não entre), **não em todo lugar** (só quando leitura e escrita divergem de verdade), e é **ortogonal a Event Sourcing** (um não exige o outro).

## Por que é uma boa oportunidade aqui
O projeto-guia já é um **CQRS-lite** — dá para ensinar o conceito sobre o que a turma já construiu, sem código novo:
- **Escrita (comando):** `comprovante-emissor` + `comprovante-gravador` — o agregado `Comprovante`, invariantes, gravação como fonte da verdade.
- **Leitura (query):** `comprovante-consulta` — modelo próprio (`ComprovanteView`, projeção achatada) + cache.
- **Costura:** o evento `comprovante-gravado` (Kafka, Aula 6) é exatamente o mecanismo com que um CQRS "cheio" manteria um *read model* materializado sincronizado.

> Ressalva honesta a manter na explicação: hoje a consulta lê do gravador (REST) + cache, **não** de um read store independente alimentado por eventos — então é separação leitura/escrita, **não** CQRS completo. O evento da Aula 6 é a emenda por onde se evoluiria.

## Onde poderia encaixar (opções)
1. **Aprofundamento na Aula 6** (Kafka/eventos) — quando o `comprovante-gravado` aparece, é a costura natural para introduzir read model materializado. Risco: a Aula 6 também é cheia.
2. **Gancho do projeto final** — oferecer CQRS como item opcional de "ir além" para grupos avançados, sem custo de aula.
3. **Aula/oferta futura** — se o módulo ganhar uma aula extra de padrões arquiteturais.

## Esforço estimado
Baixo se for boxe conceitual (1 slide + 1 subseção de material, reusando o split do PIX). Médio-alto se virar hands-on de read model materializado via evento.

## Referências
- Greg Young — CQRS (documento original) e palestras.
- Udi Dahan — *Clarified CQRS*.
- Martin Fowler — verbete *CQRS* (martinfowler.com/bliki/CQRS.html).
- Bertrand Meyer — *Command-Query Separation* (origem do CQS).
