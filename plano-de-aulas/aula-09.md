# Aula 9 — Bancas dos projetos + devolutiva por rubrica

**Data:** Sex 10/07 · **3h** · **Competência:** Síntese / defesa de arquitetura (Nível III)
**Sem incremento no PIX** — aula de avaliação e fechamento.
**Leitura do aluno:** `aula-09-aluno.md` · **Slides:** `aula-09-slides.pptx`

> Esta aula realiza a **banca de defesa de arquitetura** (alinhada às 21h complementares do Nível III) e a **devolutiva por rubrica/auto-avaliação** prevista no módulo.

## 1. Abertura (0–15)
- Combinar formato das bancas: tempo por grupo, ordem, critérios (mesmos do `projetos/projeto-final-grupo/brief.md`).
- Reforçar que a banca avalia **decisão arquitetural e domínio dos padrões**, não a infra que coube ao time.

## 2. Bancas de defesa (15–150)
> Tempo por grupo = ~`130 / nº de grupos` min (apresentação + arguição). Se forem muitos grupos, dividir em rodadas e usar parte do bloco como avaliação cruzada entre pares.

Cada grupo (8–12 min + arguição):
- **Demo** do que roda (declarando o **perfil de execução B/C** em que rodou e os fallbacks usados — Plano A/Docker não se aplica neste ambiente).
- **Defesa das decisões:** por que estes bounded contexts, onde SAGA × evento, estratégia de cache, garantias de entrega, política de resiliência, contratos.
- **Arguição** do docente + perguntas dos pares (avaliação entre pares conta para participação).
- **Uso crítico de IA:** como o time usou IA no design/dev e o que validou à mão.

## 3. Intervalo (150–160)

## 4. Devolutiva por rubrica + auto-avaliação (160–180)
- Cada aluno faz a **auto-avaliação** nas competências do módulo (Arquitetura de Microsserviços, Cache, Producer/Consumer, Assíncrono filas/tópicos, Arquitetura testável).
- Devolutiva coletiva: padrões fortes e fracos observados nas bancas.
- **Discussão de fechamento — IA e o papel do arquiteto:** o que a IA acelera, o que ela **não** decide, e por que o domínio de arquitetura distribuída fica **mais** valioso, não menos, na era dos agentes.

## 5. Fechamento + NPS (último ato)
- Frase-síntese do módulo: *"Sistemas distribuídos são a arte de transformar falha inevitável em comportamento previsível."*
- Abrir a **pesquisa de NPS** com contexto: o que levaram, o que faltou.

## Pós-aula (docente)
- Avaliar os repositórios dos grupos **via Claude**, contra a rubrica (ver `projetos/projeto-final-grupo/brief.md` §rubrica e §avaliação-via-claude).
- Consolidar notas + auto-avaliações + observações da banca.
