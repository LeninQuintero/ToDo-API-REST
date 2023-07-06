# ToDo API REST

## Descripción

La API REST de ToDo es una aplicación sencilla que permite a los usuarios crear, leer, actualizar y eliminar tareas. Está construida con Spring Boot 3.1 y proporciona una interfaz RESTful para interactuar con la aplicación.

## Características

- CRUD básico de tareas.
- Documentación de la API generada con Swagger.
- Pruebas unitarias escritas con JUnit 5 y Mockito.
- Dockerizada para facilitar la implementación.
- Soporte para conexión a bases de datos MySQL externas mediante variables de entorno.
- Demo desplegado en el servicio Container Apps de Azure.

## Requisitos

- Docker.

## Instalación

Para instalar la API REST de ToDo utilizando Docker, sigue estos pasos:

1. Clona el repositorio del proyecto en tu máquina local.
2. Navega hasta el directorio del proyecto y ejecuta el comando `./mvnw spring-boot:build-image` para construir una imagen Docker de la aplicación.
3. Ejecuta el comando `docker run -p 8080:8080 <nombre-de-la-imagen>` para iniciar un contenedor Docker con la aplicación.
4. Abre un navegador web y navega hasta `http://localhost:8080/swagger-ui.html` para ver la documentación de la API generada con Swagger.

Si deseas utilizar una base de datos MySQL externa en lugar de la base de datos en memoria H2 predeterminada, puedes configurar las siguientes variables de entorno antes de iniciar el contenedor Docker:

#### Cambia los valores por defecto de la DB embebida:
- `DB_CONNECTOR`: `com.mysql.cj.jdbc.Driver` para dejar de usar el conector por defecto de H2.
- `DB_DIALECT`: `org.hibernate.dialect.MySQLDialect` es el dielect adecuado para conectar con MySQL.

#### Conecta con tu base de datos:
- `DB_HOST`: La URL JDBC completa del servidor de base de datos MySQL, incluyendo el nombre del host, el puerto y el nombre de la base de datos. Por ejemplo: `jdbc:mysql://<host>:<port>/<dbname>`.
- `DB_USER`: El nombre de usuario para conectarse a la base de datos MySQL.
- `DB_PASSWORD`: La contraseña para conectarse a la base de datos MySQL.

Para configurar estas variables de entorno al iniciar el contenedor Docker, puedes utilizar la opción `-e` del comando `docker run`. Por ejemplo:
~~~
docker run -p 8080:8080 -e DB_HOST=jdbc:mysql://<host>:<dbport>/<dbname>-e DB_USER=<user> -e DB_PASSWORD=<password> -e DB_CONNECTOR=com.mysql.cj.jdbc.Driver -e DB_DIALECT=org.hibernate.dialect.MySQLDialect <nombre-de-la-imagen>
~~~

Reemplaza `<host>`, `<dbport>`, `<dbname>`, `<user>` y `<password>` con los valores apropiados para tu servidor de base de datos MySQL antes de ejecutar el comando.

## Uso

Puedes utilizar la API REST de ToDo para crear, leer, actualizar y eliminar tareas. Consulta la documentación de la API generada con Swagger en `http://localhost:8080/swagger-ui.html` para obtener más información sobre los endpoints disponibles y cómo hacer solicitudes a la API.

## Demo

Puedes probar la aplicación desplegada en [este enlace](<https://todoapp-cleversql.proudsand-5bd1a271.westus2.azurecontainerapps.io/swagger-ui/index.html>).

## Licencia

Este proyecto se distribuye bajo la licencia Apache 2.0. Consulta el archivo LICENSE para obtener más información.