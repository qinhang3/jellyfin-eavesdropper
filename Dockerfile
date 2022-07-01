FROM maven:3-openjdk-11-slim as builder
WORKDIR /data
ADD . /data
RUN mvn clean package -Dmaven.test.skip

FROM azul/zulu-openjdk:11
WORKDIR /app
COPY --from=builder /data/target/eavesdropper.jar /app/app.jar
ENTRYPOINT java -Xmx256m -jar /app/app.jar
