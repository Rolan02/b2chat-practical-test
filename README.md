# B2Chat API

B2Chat API es una aplicación Spring Boot que permite gestionar usuarios y consumir datos de APIs externas. Esta guía proporciona instrucciones paso a paso para ejecutar la aplicación.

## Requisitos Previos

Asegúrate de tener instalados los siguientes componentes en tu sistema:

- **Java 17** o superior
- **Maven** 3.6.3 o superior
- **Base de datos** (MySQL, PostgreSQL, etc.) - Configurado según tus necesidades.

## Configuración del Proyecto

1. **Clona el repositorio**

   ```bash
   git clone git@github.com:Rolan02/b2chat-practical-test.git
   cd b2chat
   ```

2. **Configura la base de datos**

   Edita el archivo `src/main/resources/application.properties` para establecer la configuración de la base de datos:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_de_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

   Asegúrate de crear la base de datos en tu gestor de base de datos antes de iniciar la aplicación.

3. **Agrega las credenciales de la API de Twitter (opcional)**

   Si decides habilitar la funcionalidad de Twitter, agrega tu Bearer Token en `application.properties`:

   ```properties
   twitter.api.bearer-token=TU_BEARER_TOKEN
   ```

## Ejecución de la Aplicación

### Con Maven

1. **Compila y ejecuta la aplicación**

   ```bash
   mvn spring-boot:run
   ```

   Esto iniciará el servidor en `http://localhost:8080`.

### Con el archivo JAR

1. **Compila el proyecto y genera el JAR**

   ```bash
   mvn clean package
   ```

   Esto generará un archivo JAR en la carpeta `target`.

2. **Ejecuta la aplicación**

   ```bash
   java -jar target/b2chat-0.0.1-SNAPSHOT.jar
   ```

   Asegúrate de reemplazar `b2chat-0.0.1-SNAPSHOT.jar` con el nombre real de tu archivo JAR.

## Uso de la API

Una vez que la aplicación esté en ejecución, puedes acceder a la API utilizando herramientas como Postman o cURL.

### Documentación de la API

La documentación interactiva de Swagger está disponible en:

```
http://localhost:8080/swagger-ui.html
```

Aquí podrás ver todos los endpoints disponibles, junto con ejemplos de cómo usarlos.

### Ejemplos de Endpoints

- **Crear un nuevo usuario:**

  ```http
  POST /b2chat/api/users/create
  Content-Type: application/json

  {
      "username": "nuevo_usuario",
      "password": "tu_contraseña",
      "email": "usuario@example.com"
  }
  ```

- **Obtener usuario por ID:**

  ```http
  GET /b2chat/api/users/{id}
  ```

- **Listar todos los usuarios:**

  ```http
  GET /b2chat/api/users
  ```

- **Actualizar un usuario:**

  ```http
  PUT /b2chat/api/users/update/{id}
  Content-Type: application/json

  {
      "username": "usuario_actualizado",
      "password": "nueva_contraseña",
      "email": "nuevo_email@example.com"
  }
  ```

- **Eliminar un usuario:**

  ```http
  DELETE /b2chat/api/users/delete/{id}
  ```

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -m 'Agregada nueva funcionalidad'`).
4. Haz push a la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## Contacto

Si tienes preguntas o comentarios, no dudes en contactar a:

- **Nombre:** Rolando Mamani Salas
- **Email:** rolan.salas02@gmail.com
