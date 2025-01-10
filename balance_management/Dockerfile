FROM openjdk:21-jdk-slim
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw clean install

COPY src ./src
RUN ./mvnw package -DskipTests

ENTRYPOINT ["java", "-jar", "/app/target/balance-management-0.0.1-SNAPSHOT.jar"]