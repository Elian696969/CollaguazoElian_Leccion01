# Etapa de construcción
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Crear usuario no root para seguridad
RUN addgroup --system appgroup && adduser --system --group appuser

WORKDIR /app

# Copiar primero pom.xml para caché Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (se caché si pom.xml no cambia)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Construir aplicación
RUN ./mvnw clean package -DskipTests -B

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine

# Crear usuario no root para seguridad
RUN addgroup -S appgroup && adduser -S -G appgroup appuser

# Instalar herramientas necesarias
RUN apk add --no-cache curl

WORKDIR /app

# Copiar JAR desde etapa de construcción
COPY --from=builder /app/target/support-ticket-api-0.0.1-SNAPSHOT.jar app.jar

# Cambiar propietario de archivos
RUN chown -R appuser:appgroup /app

USER appuser

# Exponer puerto
EXPOSE 8080

# Health check mejorado
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Variables de entorno optimizadas
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENV SPRING_PROFILES_ACTIVE=docker
ENV SERVER_PORT=8080

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
