FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8081
ADD target/*.jar app.jar
ADD docker-files/beacon.cer beacon.cer
ADD docker-files/beacon-priv.pem beacon-priv.pem
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]