FROM openjdk:21-jdk-slim
WORKDIR /app

# Maven Wrapper dosyasının çalıştırılabilir olduğundan emin olun
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# Bağımlılıkları yükleyin ve JAR dosyasını oluşturun
RUN ./mvnw clean package

# JAR dosyasını Docker konteynerine kopyalayın
COPY target/balance_management-0.0.1-SNAPSHOT.jar app.jar

# Uygulamanızı başlatın
ENTRYPOINT ["java", "-jar", "/app/app.jar"]