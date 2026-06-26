# Estado após a Aula 2 — Consistência distribuída e SAGA

**Foco:** garantir atomicidade emissão→gravação sem transação distribuída.

## O que funciona
- **SAGA orquestrada (síncrona):** o `EmissaoService` aceita, chama o **gravador via REST**
  (`POST /interno/comprovantes`) e, se a gravação falhar, executa a **compensação**
  (marca a emissão como `FALHOU` no `EmissaoRegistro`) e devolve **502**.
- **Idempotência** no gravador: reprocessar o mesmo `id` não duplica (`existsById`).
- Sucesso → **202** com o id.

## Por que síncrono aqui
A orquestração é o conceito da aula; o **desacoplamento por fila** vem na Aula 4 — então
ainda há acoplamento temporal (emissor e gravador precisam estar de pé juntos).

## Como rodar (2 serviços)
```bash
mvn -Pplano-b-jvm -pl comprovante-gravador spring-boot:run   # 8082
mvn -Pplano-b-jvm -pl comprovante-emissor  spring-boot:run   # 8081
```

## Próxima aula
Cache na consulta (Redis/Caffeine) com fallback no gravador e 3 retentativas.
