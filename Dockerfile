## Jenkins doesn't use this file. it pulls the Dockerfile from teh sahred library

FROM docker.james.lab.zivra.com/zivra-ubi8-jre11:latest 


VOLUME /tmp
ARG JAR_FILE

USER root

RUN mkdir /app && chmod g=u /app

COPY target/$JAR_FILE /app/app.jar

RUN chown -R root:root /app && \
    chmod -R g=u /app

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]