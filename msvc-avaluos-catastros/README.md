## Configuración del entorno

Para levantar aplicacion con docker para el despliegue.
x. ( Este paso es solo documentación: Sentencia optimizada y resumida más abajo (optimización en Dockerfile):
Compilación: 
`.\mvnw.cmd clean package -DskipTests`
Validar que levante el servicio una ves compilado .jar: 
`java -jar .\target\msvc-avaluos-catastros-0.0.1-SNAPSHOT.jar`
Construir la imagen de Docker a partir del Dockerfile: 
`docker build -t avaluos-catastros:latest .` )

1. Compilación y construcción de imagen Docker (optimizado en Dockerfile) desde carpeta raiz (Se debe ejecutar cada ves que haya cambios en el código para su actualización):
`docker build -t avaluos-catastros:latest . -f .\msvc-avaluos-catastros\Dockerfile`
2. Validar imagen docker creada: 
`docker images`
5. Levantar el contenedor de Docker a partir de imagen generada: 
`docker run -p 8002:8002 avaluos-catastros` "o utilizar" 
`docker run -p 8002:8002 afa6fd21178c` "o utilizar"
`docker run -p 8002:8002 -d --name msvc-avaluos-catastros avaluos-catastros:latest` "o utilizar"
`docker run -p 8002:8002 -d --rm --name msvc-avaluos-catastros --network spring avaluos-catastros`
6. Puede eliminar contenedor (OPCIONAL): 
`docker rm -f f052f6eda49f`
7. Puede eliminar imagen (OPCIONAL): 
`docker rmi -f f052f6eda49f`
8. Levantar el contenedor de Docker postgres:16-alpine:
`docker run -p 5435:5432 -d --name postgres16ac --network spring -e POSTGRES_USER=paulov -e POSTGRES_PASSWORD=paulovroot -e POSTGRES_DB=db_msvc_avaluos_catastros -v data-postgres-ac:/var/lib/postgresql/data postgres:16-alpine`

9. Volver nuevamente al ambiente de desarrollo para compilarlo desde IDE(OPCIONAL):
`chmod +x mvnw`
`./mvnw clean`
`mvn clean install`
mvn spring-boot:run
10. Modificar el archivo application.properties:
`spring.datasource.url=jdbc:postgresql://localhost:5432/db_msvc_avaluos_catastros`
`#spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/db_msvc_avaluos_catastros`