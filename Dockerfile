FROM maven
EXPOSE 1337
ADD / game-log
WORKDIR game-log
ENV SENTRY_DSN=""
RUN mvn clean install
ENTRYPOINT ["java", "-jar", "target/game-logs.jar"]