ARG MSVC_NAME=msvc-avaluos-catastros

# Etapa de construcción
FROM eclipse-temurin:21.0.3_9-jdk-alpine as builder

ARG MSVC_NAME

# Instalar herramientas necesarias
RUN apk add --no-cache curl tar bash

# Configurar el directorio de trabajo: se copia msvc-avaluos-catastros(pom hijo y demas proyecto) en /app
WORKDIR /app/$MSVC_NAME

# Copiar archivos del proyecto: copiar el pom parent en /app
COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn  ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

# Asegurar que mvnw tenga permisos de ejecución y convertir a LF si es necesario
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# se ejecuta solo cuando me manda a crear la imagen -> optimizado
# RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
# otra forma
# RUN ./mvnw dependency:go-offline

# Descargar dependencias y compilar (sin tests)
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip

# Copiar el código fuente y compilar la aplicación: solo se ejecuta cuando hay cambios en el codigo y lo anterior se mantiene en cache -> optimizado
COPY ./$MSVC_NAME/src ./src
RUN ./mvnw clean package -DskipTests

# Etapa de producción: optimización para solo compilar (a partir de la imagen as builder creamos otra imagen)
FROM eclipse-temurin:21.0.3_9-jdk-alpine

ARG MSVC_NAME

WORKDIR /app

# Copiar el jar compilado desde la etapa de construcción
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/msvc-avaluos-catastros-0.0.1-SNAPSHOT.jar .

ENV PORT 8002
EXPOSE $PORT

# es mas fijo y no permite iteractividad
ENTRYPOINT ["java", "-jar", "msvc-avaluos-catastros-0.0.1-SNAPSHOT.jar"]

# permite iteractividad, nos deja ingresar al SO del contenedor
# CMD ["java", "-jar", "msvc-avaluos-catastros-0.0.1-SNAPSHOT.jar"]
