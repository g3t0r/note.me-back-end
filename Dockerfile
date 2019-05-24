FROM openjdk:11.0.3-jre-slim-stretch
VOLUME /tmp
COPY ./build/libs/noteme-0.0.1-SNAPSHOT.jar noteme.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "noteme.jar", "--spring.profiles.active=prod" ]
