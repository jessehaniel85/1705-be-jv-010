# Dockerfile genérico p/ containerizar UM serviço do gabarito PIX (uso do docente, local).
# A turma NÃO usa isto — roda em plano-b-jvm. Aqui é só para você rodar/demonstrar com Docker.
#
# Build (a partir de uma pasta de aula, que é o contexto Maven):
#   cd aula-06
#   docker build -f ../Dockerfile --build-arg MODULE=comprovante-gravador -t pix-gravador:a06 .
#
# Rodar conectando aos brokers do docker-compose (rede gabarito-pix):
#   docker run --rm --network gabarito-pix_default \
#     -e SPRING_RABBITMQ_HOST=rabbitmq -e SPRING_DATA_REDIS_HOST=redis \
#     -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 -p 8082:8082 pix-gravador:a06
#
# (Para Kafka a partir de container, ver nota no README: o listener anunciado precisa de ajuste.
#  O caminho mais simples continua sendo rodar os serviços no HOST, via Maven/IDE.)

# ---- build ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY . .
ARG MODULE
# Ambiente local do docente resolve do Maven Central (sem o settings.xml do Nexus Caixa).
RUN mvn -q -Pplano-a-docker -pl ${MODULE} -am clean package -DskipTests

# ---- runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app
ARG MODULE
COPY --from=build /src/${MODULE}/target/*.jar /app/app.jar
# Porta varia por serviço (emissor 8081 / gravador 8082 / consulta 8083 / notificacao 8084).
# Publique a porta certa no `docker run -p`.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
