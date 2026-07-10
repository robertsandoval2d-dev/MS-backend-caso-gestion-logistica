# Fase 1: Compilación
# FROM maven:3.9-eclipse-temurin-21 AS build
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests

# Fase 2: Ejecución
# FROM eclipse-temurin:21-jre-alpine
# WORKDIR /app
# VOLUME /tmp
# COPY --from=build /app/target/*.jar app.jar
# ENV JAVA_OPTS="-Xmx300m -Xss512k"
# ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS="-Xmx300m -Xss512k"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]