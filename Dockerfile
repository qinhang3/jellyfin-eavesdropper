FROM azul/zulu-openjdk:11
WORKDIR /data
ADD target/eavesdropper.jar /data/app.jar
ENTRYPOINT java -Xmx256m -jar /data/app.jar
