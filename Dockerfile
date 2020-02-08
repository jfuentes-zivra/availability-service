FROM docker.james.lab.zivra.com:9500/zivra-ubi8-jre11:latest 
VOLUME /tmp
ARG JAR_FILE

RUN mkdir /app && chown 1000:1000 /app

COPY target/$JAR_FILE /app/app.jar


USER 1000:1000

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]