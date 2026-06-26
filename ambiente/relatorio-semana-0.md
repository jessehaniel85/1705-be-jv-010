# Relatório Semana 0 — Descoberta do Ambiente (BE-JV-010 · Caixa · turma 1705)

**Status:** 🟡 **Parcial.** A sondagem não rodou como "Semana 0" antes da Aula 1 — foi iniciada **ao vivo na própria Aula 1 (22/06)** e, por consumir muito tempo, o restante **virou tarefa de casa dos alunos**. Este relatório consolida o que já se sabe e o que falta.
**Fonte:** Aula 1 (22/06) — verificações dos alunos na máquina real da rede Caixa + relatos.
**Atualizar quando:** os alunos devolverem o resultado de `mvn dependency:get` dos artefatos de fallback (seção 3).

---

## Veredito por tema (vivo)

| Tema | Plano vigente | Confiança | Observação |
|---|---|---|---|
| DDD / bounded context | **B** | ✅ Alta | Só H2 in-memory; não depende de infra externa. Sem risco. |
| Redis / cache | **B** (provável) | 🟡 Média | embedded-redis/Caffeine — depende de o Nexus resolver. Caffeine é o fallback mais provável de existir. |
| RabbitMQ / filas | **B?** → risco **C** | 🟠 Baixa | Qpid Broker-J + dependência `com.sleepycat:je` (licença restrita) é o ponto mais frágil. **Maior risco de cair em C.** |
| Kafka / tópicos | **B?** → risco **C** | 🟠 Baixa | `EmbeddedKafkaBroker` (`spring-kafka-test`) — confirmar no Nexus. |
| Contract test (PACT) | **B** | 🟢 Alta | Pact-JVM com pactfiles em disco já é Docker-free; só confirmar `au.com.dius.pact*` no Nexus. |
| Projeto em grupo (Teams) | a confirmar | 🟡 | Breakout rooms e versionamento interno ainda não confirmados. |

> **Regra de operação enquanto a seção 3 não fecha:** assumir **Plano B**, mas levar **Plano C pronto** para filas e tópicos (Aulas 4–7). Cada `aula-0X.md` já traz B **e** C.

---

## 1. Infra base

| Item | Resultado | Fonte |
|---|---|---|
| Java | ⬜ versões variam por máquina (instalam o ZIP do site, pois o de sistema não sobrescreve) | Aula 1 |
| Maven presente | ✅ sim (Antonio testou) | Aula 1 |
| `settings.xml` → Nexus (`caixa-group`) | ✅ configurado | Aula 1 |
| **`dependency:get` resolve do Nexus** | 🔴 **FALHOU ao vivo:** Antonio rodou e o `caixa-group` **não encontrou o `spring-boot-starter`** (mesmo fixando 3.3.13), embora os projetos dele baixem normalmente em `clean install`. Inconclusivo/preocupante. | Aula 1 |

> **Achado crítico (Aula 1):** se nem o `spring-boot-starter` resolveu via `dependency:get` no `caixa-group`, a resolução dos **fallbacks in-JVM** (Qpid/EmbeddedKafka/Pact) é **incerta** → **probabilidade real de Plano C** em mensageria. Reexecutar o teste com calma (tarefa de casa) e, se confirmar, abrir chamado. **Liaison: Antonio David** (centraliza chamados de infra/segurança).

## 2. Docker / contêineres — **RESOLVIDO**

| Item | Resultado |
|---|---|
| Docker existe e roda | ❌ **NÃO** — sem Docker e **sem sandbox** na rede Caixa (confirmado na Aula 1). |
| **Decisão** | **Plano A descartado. Plano B (pura-JVM) é o piso.** Docker fica só como referência didática de produção (ponte da modernização). |

## 3. Resolução dos artefatos de fallback no Nexus — **TAREFA DE CASA (bloqueante p/ Aulas 4–7)**

Pendente. Pedir a cada aluno (ou ao aluno-piloto) que rode, na máquina Caixa, e devolva o resultado:

```
mvn -s settings.xml dependency:get -Dartifact=<GAV>
```

| Artefato (fallback de) | GAV | Resolve? |
|---|---|---|
| Redis embarcado | `com.github.codemonstur:embedded-redis:1.4.3` | ⬜ |
| Cache local (alt. Redis) | `com.github.ben-manes.caffeine:caffeine:3.1.8` | ⬜ |
| Broker AMQP embarcável | `org.apache.qpid:qpid-broker:9.x` (+ resolver/excluir `com.sleepycat:je`) | ⬜ |
| Kafka broker in-JVM | `org.springframework.kafka:spring-kafka-test:3.x` | ⬜ |
| Contract test | `au.com.dius.pact.consumer:junit5:4.6.x` / `...provider:junit5:4.6.x` | ⬜ |

> **Se Qpid (filas) e/ou `spring-kafka-test` (tópicos) NÃO resolverem:** abrir chamado para publicação no Nexus **e** conduzir as Aulas 4–7 no **Plano C** (event bus in-process + design), sem perda do aprendizado do padrão.

## 4. Rede e processos locais

| Item | Resultado |
|---|---|
| Abrir porta em `localhost` (broker/cache embarcado) | ⬜ a confirmar |
| Sites externos (start.spring.io / Maven Central / GitHub) bloqueados | provável SIM → reforça "tudo via Nexus" + material offline |

## 5. Colaboração e didática no Teams

| Item | Resultado |
|---|---|
| Breakout rooms liberados | ⬜ a confirmar (define blocos 4–5 da aula: breakout × mob guiado) |
| Aluno compartilha tela / recebe controle | ⬜ a confirmar |
| Onde os grupos versionam (GitLab/Bitbucket interno) | ⬜ a confirmar |
| IDE na máquina Caixa + plugins | ⬜ a confirmar — **GitHub Copilot com agentes está disponível** (confirmado) |
| Gravação da aula permitida | ⬜ a confirmar |

## 6. Outros achados da Aula 1

- **Plataforma Ada (LMS): disponível desde ~23/06.** O que foi compartilhado por WhatsApp na Aula 1 (`requisitos/` zip, `plano-de-aulas/aula-01-aluno.md` em PDF, este checklist) está sendo subido ao LMS; **exercícios disponíveis a partir da Aula 2**. A distribuição offline (`../distribuicao-offline/`) fica como **backup** de rede.
