# Stage 1: Build
FROM gradle:8.5-jdk21-alpine AS builder

WORKDIR /app

# Copiar archivos de configuración de gradle
COPY build.gradle.kts settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

# Copiar código fuente
COPY src ./src

# Dar permisos de ejecución y compilar
RUN chmod +x ./gradlew && ./gradlew clean build -x test

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variable de entorno para los logs
ENV LOG_LEVEL=INFO

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
