FROM openjdk:22-slim
WORKDIR /app
LABEL maintainer="adeputrans@gmail.com"
LABEL company="hehe"
COPY ./target/*.jar app.jar
COPY ./src/main/resources/certs certs
EXPOSE ${APP_PORT}
ENTRYPOINT ["java","-jar","app.jar"]