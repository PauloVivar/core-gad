## Configuración del entorno

Para configurar las variables de entorno necesarias:

1. Copia el archivo `.env.example` y renómbralo a `.env`
2. Edita `.env` y añade tus valores reales para cada variable
3. No compartas tu archivo `.env`, está en .gitignore por razones de seguridad

Levantar aplicacion con docker para el despliegue.
1. Compilación: 
.\mvnw.cmd clean package -DskipTests
2. Construir la imagen de Docker: 
docker build -t msvc-users-back:latest .
3. Validar imagen docker creada: 
docker images
4. Levantar docker desde Dockerfile: 
docker run -p 8080:8080 msvc-users-back "o utilizar" docker run -p 8080:8080 afa6fd21178c(ejem)
5. Eliminar imagen (OPCIONAL): 
docker rmi -f f052f6eda49f 
6. Volver nuevamente al ambiente de desarrollo para compilarlo desde IDE(OPCIONAL):
mvn clean install
mvn spring-boot:run
7. Modificar el archivo application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5433/db_msvc_users_bck
#spring.datasource.url=jdbc:postgresql://host.docker.internal:5433/db_msvc_users_bck