FROM eclipse-temurin:21.0.3_9-jdk-alpine as builder

# directorio de trabajo donde se copia msvc-users-prod(pom hijo y demas proyecto) en /app
WORKDIR /app/msvc-users-prod

# copiar el pom parent en /app
COPY ./pom.xml /app
COPY ./msvc-users-prod/.mvn  ./.mvn
COPY ./msvc-users-prod/mvnw .
COPY ./msvc-users-prod/pom.xml .

# se ejecuta solo cuando me manda a crear la imagen -> optimizado
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
# RUN ./mvnw dependency:go-offline  #---> otra forma

# solo se ejecuta cuando hay cambios en el codigo y lo anterior se mantiene en cache -> optimizado
COPY ./msvc-users-prod/src ./src

RUN ./mvnw clean package -DskipTests

# optimización para solo compilar (a partor de la imagen as builder creamos otra imagen)
FROM eclipse-temurin:21.0.3_9-jdk-alpine

WORKDIR /app

COPY --from=builder /app/msvc-users-prod/target/msvc-users-prod-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

# Define una variable de entorno
ENV SPRING_MAIL_PASSWORD=eqwyqgalkzsykgvi

ENTRYPOINT ["java", "-jar", "msvc-users-prod-0.0.1-SNAPSHOT.jar"]
