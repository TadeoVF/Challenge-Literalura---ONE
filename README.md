# 📚 Literalura: Catálogo de Libros y Autores

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

## 📝 Descripción del Proyecto

Literalura es una aplicación de consola desarrollada con **Java** y **Spring Boot** que interactúa con la API de **GutenDex** para buscar, registrar y consultar libros y autores. El objetivo principal es ofrecer una interfaz sencilla para explorar la literatura mundial, almacenando los datos de forma persistente en una base de datos **PostgreSQL**.

Este proyecto fue desarrollado como parte del programa **Oracle Next Education (ONE)** de **Alura Latam**.

## 🚀 Funcionalidades

La aplicación ofrece un menú interactivo con las siguientes opciones:

- **Buscar libro por título:** Permite al usuario buscar un libro en la API de GutenDex por su título. Si el libro no existe en la base de datos local, se guarda junto con su autor.
- **Listar libros registrados:** Muestra todos los libros que han sido guardados en la base de datos.
- **Listar autores registrados:** Muestra todos los autores que han sido guardados.
- **Listar autores vivos en un año determinado:** Permite al usuario ingresar un año y la aplicación lista todos los autores que estaban vivos en ese período.
- **Listar libros por idioma:** Permite buscar y listar todos los libros registrados en la base de datos que pertenecen a un idioma específico (ej: `es` para español, `en` para inglés).

## 🛠️ Tecnologías Utilizadas

- **Lenguaje de Programación:** Java 21
- **Framework:** Spring Boot
- **Base de Datos:** PostgreSQL
- **Persistencia:** Spring Data JPA / Hibernate
- **Gestor de Dependencias:** Maven
- **Cliente HTTP:** `java.net.http` (integrado en Java)
- **Mapeo JSON:** Jackson Databind

## ⚙️ Cómo Ejecutar el Proyecto

Para correr la aplicación en tu máquina local, sigue estos pasos:

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/Literalura.git](https://github.com/tu-usuario/Literalura.git)
    cd Literalura
    ```
2.  **Configurar la base de datos:**
    * Instala **PostgreSQL** y **pgAdmin**.
    * Crea una base de datos llamada `literalura`.
    * Actualiza el archivo `src/main/resources/application.properties` con tus credenciales de PostgreSQL:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
        spring.datasource.username=tu_usuario
        spring.datasource.password=tu_contraseña
        ```
3.  **Ejecutar la aplicación:**
    * Puedes usar tu IDE favorito (como IntelliJ IDEA) o la línea de comandos de Maven.
    * Si usas Maven, ejecuta el siguiente comando en la terminal desde la raíz del proyecto:
        ```bash
        ./mvnw spring-boot:run
        ```
