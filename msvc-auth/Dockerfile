ARG MSVC_NAME=msvc-auth

# Etapa de construcción
FROM eclipse-temurin:21.0.3_9-jdk-alpine as builder

ARG MSVC_NAME

# Instalar herramientas necesarias
RUN apk add --no-cache curl tar bash

# Configurar el directorio de trabajo
WORKDIR /app/$MSVC_NAME

# Copiar archivos del proyecto
COPY ./pom.xml /app/
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

# Asegurar que mvnw tenga permisos de ejecución y convertir a LF si es necesario
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Descargar dependencias y compilar (sin tests)
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip

# Copiar el código fuente y compilar la aplicación
COPY ./$MSVC_NAME/src ./src
COPY ./$MSVC_NAME/.env ./src/main/resources/.env
RUN ./mvnw clean package -DskipTests

# Etapa de producción
FROM eclipse-temurin:21.0.3_9-jdk-alpine

ARG MSVC_NAME

WORKDIR /app

# Copiar el jar compilado desde la etapa de construcción
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/msvc-auth-0.0.1-SNAPSHOT.jar .
COPY --from=builder /app/$MSVC_NAME/src/main/resources/.env .

ENV PORT 9000
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "msvc-auth-0.0.1-SNAPSHOT.jar"]
