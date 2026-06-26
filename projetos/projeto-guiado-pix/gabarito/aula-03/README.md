# Comprovantes PIX — Projeto Guiado (BE-JV-010)

Esqueleto multi-módulo construído **ao vivo, aula a aula**. Já **compila**; cada aula preenche os `TODO`.
Não é avaliado — é a demonstração canônica dos padrões que o **projeto final** exige.

## Módulos
| Módulo | Bounded context | Porta | Papel |
|---|---|---|---|
| `shared-contracts` | — | — | DTOs, comandos, eventos e pacts compartilhados |
| `comprovante-emissor` | Emissão | 8081 | `POST /comprovantes` → 202 → publica gravação |
| `comprovante-gravador` | Gravação | 8082 | consome a fila, persiste (idempotente), publica evento |
| `comprovante-consulta` | Consulta | 8083 | `GET /comprovantes/{id}` → cache → banco → 3 retries → 404 |

## Perfis de execução (ambiente confirmado: Plano B)
A Aula 1 confirmou **sem Docker e sem sandbox** → **`plano-b-jvm` é o perfil desta turma** (ativo por padrão). `plano-a-docker` fica só como **referência de produção** (não usado aqui). Se o Nexus não publicar algum fallback in-JVM (Qpid/EmbeddedKafka), o tema cai em `plano-c-conceitual` — confirmação pendente, ver `ambiente/relatorio-semana-0.md` (na raiz do módulo).

| Perfil | Quando | Cache | Fila | Tópico |
|---|---|---|---|---|
| `plano-b-jvm` **(padrão — esta turma)** | Java 21+Maven+Nexus, sem Docker | Caffeine | Qpid embarcado | EmbeddedKafka |
| `plano-c-conceitual` | se faltar artefato no Nexus | ConcurrentHashMap | `ApplicationEventPublisher` / `BlockingQueue` | idem |
| `plano-a-docker` | *referência de produção (não usado nesta turma)* | Redis | RabbitMQ | Kafka |

> **Piso desta turma:** `plano-b-jvm` é ativo por padrão e **não exige Docker nem internet** — só o Nexus Caixa para baixar artefatos.

## Como rodar
```bash
# sempre com o settings.xml do Nexus (../../ambiente/settings.xml)
mvn -s ../../ambiente/settings.xml -Pplano-b-jvm clean compile      # build do esqueleto

# subir um serviço (exemplo: emissor)
mvn -s ../../ambiente/settings.xml -Pplano-b-jvm -pl comprovante-emissor spring-boot:run

# testar o emissor (responde 202 já no esqueleto)
curl -i -X POST localhost:8081/comprovantes -H 'Content-Type: application/json' \
  -d '{"nome":"Giovanni","tipo_documento":"CPF","numero_documento":"50329291076",
       "numero_agencia":"2022","numero_conta":"00276","digito_verificador_conta":"0",
       "valor_transacao":23.99,"tipo_chave_pix_destino":"CELULAR","chave_pix_destino":"11948755536",
       "nome_cliente_destino":"Fernando","identificacao_pix":"churrasco",
       "data_hora_transacao":"2022-04-10T20:03:57"}'
```

## Estratégia didática
- **Programe contra as abstrações** (Spring Data Redis / Spring AMQP / Spring Kafka). O perfil troca a infra; o código de domínio não muda.
- **Um branch/tag por aula** (`aula-01`, `aula-02`, …) para colar o incremento se a demo ao vivo travar (rede do Teams).
- Procure os `// TODO (Aula N)` — eles marcam exatamente o que se constrói em cada encontro.

## Estrutura
```
comprovantes-pix/
├── pom.xml                    # parent + perfis A/B/C
├── shared-contracts/          # contratos (sem dependências pesadas) + pacts
├── comprovante-emissor/       # REST, 202, publica
├── comprovante-gravador/      # consome, persiste (base própria), publica evento
├── comprovante-consulta/      # GET com cache + fallback
└── docs/
    ├── arquitetura.md         # diagrama + mapa de incrementos
    └── adr/                   # decisões (modelo p/ os grupos do projeto final)
```
