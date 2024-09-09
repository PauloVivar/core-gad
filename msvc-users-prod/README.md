## Configuración del entorno

Para configurar las variables de entorno necesarias:

1. Copia el archivo `.env.example` y renómbralo a `.env`
2. Edita `.env` y añade tus valores reales para cada variable
3. No compartas tu archivo `.env`, está en .gitignore por razones de seguridad

Para levantar aplicacion con docker para el despliegue.
x. ( Este paso es solo documentación: Sentencia optimizada y resumida más abajo (optimización en Dockerfile):
Compilación: 
`.\mvnw.cmd clean package -DskipTests`
Validar que levante el servicio una ves compilado .jar: 
`java -jar .\target\msvc-users-prod-0.0.1-SNAPSHOT.jar`
Construir la imagen de Docker a partir del Dockerfile: 
`docker build -t users:latest .` )

1. Compilación y construcción de imagen Docker (optimizado en Dockerfile) desde carpeta raiz (Se debe ejecutar cada ves que haya cambios en el código para su actualización):
`docker build -t users:latest . -f .\msvc-users-prod\Dockerfile`
2. Validar imagen docker creada: 
`docker images`
5. Levantar el contenedor de Docker a partir de imagen generada: 
`docker run -p 8001:8001 users` "o utilizar" 
`docker run -p 8001:8001 afa6fd21178c` "o utilizar"
`docker run -p 8001:8001 -d --rm --name msvc-users-prod --network spring users:latest` "o utilizar"
`docker run -p 8001:8001 --env-file .\msvc-users-prod\.env -d --rm --name msvc-users-prod --network spring users`
6. Puede eliminar contenedor (OPCIONAL): 
`docker rm -f f052f6eda49f`
7. Puede eliminar imagen (OPCIONAL): 
`docker rmi -f f052f6eda49f`
8. Levantar el contenedor de Docker postgres:16-alpine:
`docker run -p 5434:5432 -d --name postgres16 --network spring -e POSTGRES_USER=paulov -e POSTGRES_PASSWORD=paulovroot -e POSTGRES_DB=db_msvc_users_prod -v data-postgres:/var/lib/postgresql/data postgres:16-alpine`

9. Volver nuevamente al ambiente de desarrollo para compilarlo desde IDE(OPCIONAL):
`chmod +x mvnw`
`./mvnw clean`
`mvn clean install`
`mvn spring-boot:run`
10. Modificar el archivo application.properties(OPCIONAL):
`spring.datasource.url=jdbc:postgresql://localhost:5432/db_msvc_users_prod`
`#spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/db_msvc_users_prod`