# Estado após a Aula 3 — Cache com Redis (Caffeine no Plano B)

**Foco:** aliviar leituras repetidas sem servir dado inconsistente.

## O que funciona
- **consulta**: `GET /comprovantes/{id}` busca **primeiro no cache** (`@Cacheable`);
  no miss, lê o **gravador** (`GET /interno/comprovantes/{id}`) com até **3 retentativas**,
  **popula o cache** e retorna; ausência real → **404** (não cacheado, via `unless`).
- **gravador**: novo endpoint de leitura + projeção `ComprovanteView`.
- Cache configurável (TTL 5min, Caffeine no Plano B; trocar por Redis no Plano A sem mexer no serviço).

## Como rodar
```bash
mvn -Pplano-b-jvm -pl comprovante-gravador spring-boot:run   # 8082
mvn -Pplano-b-jvm -pl comprovante-consulta spring-boot:run   # 8083
```

## Próxima aula
Desacoplar a gravação: a emissão publica numa fila e o gravador consome (async).
