# Etapa de construcción
FROM eclipse-temurin:21.0.3_9-jdk-alpine as builder

# Instalar herramientas necesarias
RUN apk add --no-cache curl tar bash

# Configurar el directorio de trabajo
WORKDIR /app/msvc-users-prod

# Copiar archivos del proyecto
COPY ./pom.xml /app/
COPY ./msvc-users-prod/.mvn ./.mvn
COPY ./msvc-users-prod/mvnw .
COPY ./msvc-users-prod/pom.xml .

# Asegurar que mvnw tenga permisos de ejecución y convertir a LF si es necesario
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Descargar dependencias y compilar (sin tests)
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip

# Copiar el código fuente y compilar la aplicación
COPY ./msvc-users-prod/src ./src
RUN ./mvnw clean package -DskipTests

# Etapa de producción
FROM eclipse-temurin:21.0.3_9-jdk-alpine

WORKDIR /app

# Copiar el jar compilado desde la etapa de construcción
COPY --from=builder /app/msvc-users-prod/target/*.jar ./app.jar

EXPOSE 8001

ENV SPRING_MAIL_PASSWORD=eqwyqgalkzsykgvi

ENTRYPOINT ["java", "-jar", "app.jar"]
