## Configuración del entorno

Para configurar las variables de entorno necesarias:

1. Copia el archivo `.env.example` y renómbralo a `.env`
2. Edita `.env` y añade tus valores reales para cada variable
3. No compartas tu archivo `.env`, está en .gitignore por razones de seguridad

Levantar aplicacion con docker para el despliegue.
x. ( No usar este paso, solo documentación: Sentencia optimizada y resumida más abajo:
Compilación: 
`.\mvnw.cmd clean package -DskipTests`
Validar que levante el servicio una ves compilado la app msvc-users-prod con tomcat embebido de spring-boot: `java -jar .\target\msvc-users-prod-0.0.1-SNAPSHOT.jar`
Construir la imagen de Docker a partir de Dockerfile: 
`docker build -t msvc-users-prod:latest .` )

1. Copilación y construcción de la imagen Docker optimizado:
`docker build -t msvc-users-prod:latest . -f .\msvc-users-prod\Dockerfile`
2. Validar imagen docker creada: 
`docker images`
5. Levantar el contenedor de Docker a partir de imagen generada: 
`docker run -p 8001:8001 msvc-users-prod` "o utilizar" `docker run -p 8001:8001 afa6fd21178c` (ejem)
6. Eliminar imagen (OPCIONAL): 
`docker rmi -f f052f6eda49f`
7. Volver nuevamente al ambiente de desarrollo para compilarlo desde IDE(OPCIONAL):
`mvn clean install`
mvn spring-boot:run
8. Modificar el archivo application.properties:
`spring.datasource.url=jdbc:postgresql://localhost:5433/db_msvc_users_prod`
`#spring.datasource.url=jdbc:postgresql://host.docker.internal:5433/db_msvc_users_prod`