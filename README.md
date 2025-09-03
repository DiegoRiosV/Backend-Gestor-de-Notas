# Gestor de Notas - Backend 📝

Este repositorio contiene el código fuente del backend para la aplicación de Gestor de Notas. La API RESTful está construida para manejar la lógica de negocio, la persistencia de datos y la autenticación de la aplicación, permitiendo a los usuarios crear, organizar y buscar sus notas de manera eficiente.

---
## Características Principales ✨

* **Gestión Completa de Notas (CRUD):** Crear, leer, actualizar y eliminar notas.
* **Gestión de Categorías:** Permite crear, obtener y eliminar categorías para organizar las notas.
* **Posicionamiento de Notas:** Guarda y devuelve las coordenadas X/Y de cada nota para mantener su posición en el tablero.
* **Búsqueda y Filtrado:** La API permite buscar notas por palabra clave en su contenido y/o filtrarlas por su categoría.
* **Documentación Interactiva:** La API está completamente documentada con Swagger (SpringDoc) para facilitar su uso y prueba.

---
## Tecnologías Utilizadas 🛠️

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3
* **Gestor de Dependencias:** Maven
* **Base de Datos:** MySQL (con soporte para H2 en el entorno de pruebas)
* **Persistencia:** Spring Data JPA / Hibernate
* **Documentación:** SpringDoc (Swagger / OpenAPI 3)

---
## Instalación y Puesta en Marcha 🚀

Sigue estos pasos para clonar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

* [Git](https://git-scm.com/)
* Java Development Kit (JDK) 17 o superior
* [Apache Maven](https://maven.apache.org/download.cgi)
* Una instancia de base de datos MySQL (puede ser local o en la nube).

### Pasos

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/darvgg/Backend-Gestor-de-Notas.git](https://github.com/darvgg/Backend-Gestor-de-Notas.git)
    cd Backend-Gestor-de-Notas
    ```

2.  **Configurar las variables de entorno:**
    Este proyecto utiliza un archivo `.env` para manejar las credenciales de la base de datos de forma segura. Crea un archivo llamado `.env` en la raíz del proyecto y añade las siguientes variables con tus datos:

    ```env
    # Credenciales de la Base de Datos MySQL
    DB_URL=jdbc:mysql://TU_HOST:TU_PUERTO/TU_BASE_DE_DATOS
    DB_USERNAME=TU_USUARIO
    DB_PASSWORD=TU_CONTRASEÑA
    ```

3.  **Construir y ejecutar la aplicación:**
    Puedes ejecutar la aplicación directamente usando el plugin de Maven para Spring Boot.

    ```bash
    mvn spring-boot:run
    ```
    La aplicación se iniciará y estará disponible en `http://localhost:8080`.

---
## Documentación de la API 📚

Este proyecto utiliza **SpringDoc** para generar documentación interactiva de la API en tiempo real. Una vez que la aplicación esté en ejecución, puedes acceder a la interfaz de Swagger UI para ver, probar y entender todos los endpoints disponibles.

* **URL de Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

La documentación incluye:
* Endpoints para **Gestión de Notas** (`/api/notes`).
* Endpoints para **Gestión de Categorías** (`/api/categories`).
* Descripciones detalladas de cada operación, parámetros y posibles respuestas.
* Esquemas de los modelos `Note` y `Category`.

---
## Equipo de Trabajo 🤝

Este proyecto fue desarrollado por **ALPACAS TEAM**:

* **Product Owner:** Diego Canedo
* **Scrum Master:** Salma Torres
* **Development Team:**
    * Andre Prudencio
    * Adrian Rojas
    * Diego Rios
    * Mariana Mercado
