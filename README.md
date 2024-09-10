## Configuración del entorno

Para configurar las variables de entorno necesarias:

1. Copia el archivo `.env.example` y renómbralo a `.env`
2. Edita `.env` y añade tus valores reales para cada variable
3. No compartas tu archivo `.env`, está en .gitignore por razones de seguridad

Levantar aplicacion con docker-compose para el despliegue.
1. Siguiendo la configuración del archivo docker-compose.yml basado en las imagenes creadas con Dockerfile, utilizar: 
2. Levantar contenedores Docker, construye (desde imagen existente) y levanta contenedores: 
`docker-compose up -d`
3. Bajar contenedores Docker, parar y eliminar contenedores: 
`docker-compose down`
4. Arrancar contenedores Docker cuando esten detenidos:
`docker-compose start`
5. Levantar contenedores Docker, utilizar --build cuando exista cambios en el codigo: 
`docker-compose up --build -d`

