FROM docker.james.lab.zivra.com:9500/zivra-ubi8-jre11:latest 
VOLUME /tmp
ARG JAR_FILE
COPY target/$JAR_FILE /app.jar

USER 1000:1000

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]