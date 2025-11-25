# Proyecto: Plataforma de Gestión de Cursos (Provisorio)

Este proyecto es una plataforma web que permite gestionar cursos
internos dentro de una organización.\
Incluye registro de usuarios, asignación de roles, inscripción de
empleados a cursos y panel administrativo para la gestión de la oferta
académica.

> ⚠ **Este README es provisorio** y se actualizará a medida que se
> completen los objetivos del proyecto.

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

## Pendientes

### Mejoras

Existen varios aspectos que podrían optimizarse para mejorar la experiencia de usuario y la calidad del sistema:
- En la vista de empleados, se podría mejorar la lógica para mostrar la opción “Inscribirse” sólo en aquellos cursos que se encuentren vigentes según su fecha de inicio y término.
- Separar de forma más clara los roles de ADMIN e INSTRUCTOR, definiendo permisos y vistas específicas para cada uno.
- Implementar un manejo de excepciones centralizado en los controladores para entregar respuestas más claras y controladas ante errores.
- Agregar paginación en las vistas de cursos, tanto en el panel de administrador como en el de empleados, para mejorar la usabilidad cuando existen muchos registros.
- Incluir un navbar común en todas las vistas, asegurando una experiencia de navegación consistente en toda la aplicación.

------------------------------------------------------------------------

## Autor

-   Laura Duhalde
