# Proyecto: Plataforma de Gestión de Cursos

Este proyecto es una plataforma web que permite gestionar cursos
internos dentro de una organización.\
Incluye registro de usuarios, asignación de roles, inscripción de
empleados a cursos y panel administrativo para la gestión de la oferta
académica.

------------------------------------------------------------------------

## Tecnologías Utilizadas

### Backend

-   Java 21
-   Spring Boot 3.5.7
-   Spring MVC
-   Spring Data JPA
-   Spring Security
-   Hibernate Validator
-   MySQL
-   Lombok

### Frontend

-   Thymeleaf
-   Bootstrap 5.3

------------------------------------------------------------------------

## Funcionalidades Actuales

### Autenticación

-   Login con Spring Security.
-   Redirección según rol:
    -   `ADMIN` → `/admin/cursos`
    -   `EMPLEADO` → `/empleado/cursos`

### Registro de Usuarios

-   Formulario con Thymeleaf.
-   Creación de usuario + empleado/instructor según rol.
-   Contraseña encriptada con BCrypt.

### Gestión de Cursos (Admin)

-   Listado de cursos con nombre del instructor.
-   Botón para asignar instructor si falta.

### Panel Empleado

-   Listado de cursos disponibles.
-   Inscripción con validación y mensajes flash.

------------------------------------------------------------------------

## Estructura simplificada del Proyecto

    src/
     └─ main/
         ├─ java/cl/web/
         │   ├─ controllers/
         │   ├─ dto/
         │   ├─ entities/
         │   ├─ mappers/
         │   ├─ repositories/
         │   ├─ restControllers/
         │   ├─ security/
         │   └─ services/
         └─ resources/
             ├─ templates/
             │   ├─ index.html
             │   ├─ login.html
             │   ├─ registro.html
             │   ├─ admin/
             │   └─ empleado/
             └─ application.properties

------------------------------------------------------------------------

## Cómo Ejecutar el Proyecto

### 1. Clonar el repositorio

### 2. Configurar Base de Datos

Editar `application.properties`:

    spring.datasource.url=jdbc:mysql://localhost:3306/cursos
    spring.datasource.username=root
    spring.datasource.password=tu_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

### 3. Ejecutar la aplicación

    mvn spring-boot:run

### 4. Abrir en el navegador

    http://localhost:8081

------------------------------------------------------------------------

## Autor

-   Laura Duhalde
