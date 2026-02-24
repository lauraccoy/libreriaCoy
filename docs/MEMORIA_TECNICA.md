# Memoria técnica — Librería Coy

## 1. Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot
- Spring MVC (web con Thymeleaf)
- API REST
- Spring Security (login + JWT para API)
- Spring Data JPA / Hibernate
- MySQL
- Maven

### Frontend Web
- Thymeleaf (plantillas)
- Bootstrap (estilos y layout)
- JavaScript (funcionalidad ligera)

### App móvil
- Flutter (Dart)
- Consumo de API REST (JSON)

### Herramientas
- IDE: Eclipse / Spring Tool Suite
- Control de versiones: Git (si lo usas)
- Cliente BD: MySQL Workbench
- Postman (pruebas de endpoints REST)


## 2. Arquitectura del sistema

El sistema sigue una arquitectura multicapa basada en el patrón:

Controller → Service → Repository → Base de datos

Se diferencian claramente dos formas de acceso al backend:

### Aplicación web
El cliente accede a vistas renderizadas en el servidor mediante Thymeleaf.

Flujo:

Usuario → Controller → Service → Repository → MySQL

---

### Aplicación móvil
La app Flutter consume la API REST expuesta por el backend.

Flujo:

App → REST Controller → Service → Repository → MySQL

---

### Gestión de recursos estáticos
El backend sirve directamente contenido estático como:

- Imágenes de productos
- Archivos HTML del blog
- Recursos CSS y JavaScript

Este enfoque mejora el rendimiento y reduce consultas innecesarias a la base de datos.


## 3. Patrón arquitectónico

El proyecto sigue el patrón de arquitectura MVC (Modelo-Vista-Controlador).

- Modelo → entidades JPA que representan la estructura de la base de datos.
- Vista → plantillas Thymeleaf renderizadas en el servidor.
- Controlador → gestionan las peticiones HTTP y coordinan la lógica de la aplicación.

Además, se aplica una arquitectura multicapa:

Controller → Service → Repository

Este enfoque mejora la mantenibilidad del sistema y facilita su escalabilidad.


## 4. Módulos implementados

### Controladores Web (Thymeleaf)
- HomeController
- CatalogoController
- DetalleController
- CarritoController
- CheckoutController
- AuthWebController
- BlogController

### Controladores REST (API)
La aplicación dispone de controladores REST que permiten la comunicación con la aplicación móvil mediante respuestas en formato JSON.

Entre ellos destacan:

- BlogRestController → expone los artículos del blog para su consumo desde la app móvil.

### Servicios
La capa de servicios implementa la lógica de negocio de la aplicación y actúa como intermediaria entre los controladores y la base de datos.

Servicios principales:

- CatalogoService → gestión del catálogo de productos.
- DetalleService → recuperación de información detallada de productos.
- AuthService → autenticación y gestión de usuarios.
- BlogContentService → carga del contenido HTML de los artículos del blog desde recursos estáticos.

Esta separación evita que los controladores contengan lógica de negocio,
siguiendo buenas prácticas de diseño de software.

### Repositorios
La persistencia se gestiona mediante Spring Data JPA.

Repositorios implementados:

- AppUserRepository
- BlogPostRepository
- LibroRepository
- ComicRepository
- PapeleriaRepository
- ProductoGeekRepository

El uso de repositorios permite desacoplar el acceso a datos de la lógica de negocio.

La arquitectura aplicada sigue el principio de responsabilidad única,
facilitando la escalabilidad del sistema y permitiendo futuras ampliaciones
sin afectar a los módulos existentes.
