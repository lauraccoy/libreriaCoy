# Casos de uso — Librería Coy

## Diagrama de casos de uso

![Diagrama de casos de uso](imagenes/casos_de_uso.png)

---

## Actores

- **Usuario (cliente):** visitante o usuario registrado que navega por el catálogo, consulta productos, gestiona el carrito y realiza el proceso de compra mediante pago simulado.

- **App móvil (Flutter):** aplicación cliente que consume la API REST para obtener información del catálogo y del blog.

- **Administrador:** responsable de la gestión del sistema. Puede administrar productos y publicaciones del blog. Este actor se contempla como parte de la escalabilidad del sistema, permitiendo futuras ampliaciones sin modificar la arquitectura principal.

> **Nota:** En esta versión del proyecto, el proceso de pago se implementa como **simulado**, permitiendo representar el flujo completo de compra sin integrar una pasarela de pago real.

---

## Casos de uso del cliente

### CU-01 — Consultar catálogo por categorías
**Actor:** Usuario (cliente)  
**Descripción:** Permite al usuario navegar por el catálogo filtrando productos por categorías como libros, cómics, papelería o productos geek.  
**Precondiciones:** Ninguna.  
**Flujo principal:**
1. El usuario accede al catálogo.
2. Selecciona una categoría.
3. El sistema muestra los productos disponibles.
**Postcondiciones:** El catálogo filtrado se muestra correctamente.

---

### CU-02 — Ver detalle de un producto
**Actor:** Usuario (cliente)  
**Descripción:** El usuario puede visualizar la información completa de un producto.  
**Precondiciones:** El producto debe existir en la base de datos.  
**Flujo principal:**
1. El usuario selecciona un producto.
2. El sistema muestra su información detallada (precio, descripción, imagen, etc.).
**Postcondiciones:** El usuario obtiene información completa del producto.

---

### CU-03 — Gestionar carrito de compra
**Actor:** Usuario (cliente)  
**Descripción:** Permite añadir, eliminar o modificar productos dentro del carrito.  
**Precondiciones:** Ninguna.  
**Flujo principal:**
1. El usuario añade un producto.
2. El sistema actualiza el carrito.
3. El usuario puede modificar cantidades o eliminar productos.
**Postcondiciones:** El carrito refleja los cambios realizados.

---

### CU-04 — Registro de usuario
**Actor:** Usuario (cliente)  
**Descripción:** Permite a un visitante crear una cuenta en la aplicación.  
**Precondiciones:** El correo electrónico no debe estar registrado previamente.  
**Flujo principal:**
1. El usuario accede al formulario de registro.
2. Introduce sus datos personales.
3. El sistema valida la información y crea la cuenta.
**Postcondiciones:** El usuario queda registrado en el sistema.

---

### CU-05 — Inicio de sesión
**Actor:** Usuario (cliente)  
**Descripción:** Permite a un usuario autenticarse en el sistema.  
**Precondiciones:** El usuario debe estar registrado.  
**Flujo principal:**
1. El usuario introduce sus credenciales.
2. El sistema valida los datos.
3. Se inicia la sesión.
**Postcondiciones:** El usuario accede a funcionalidades privadas.

---

### CU-06 — Realizar checkout (pago simulado)
**Actor:** Usuario (cliente)  
**Descripción:** Permite completar el proceso de compra mediante un pago simulado.  
**Precondiciones:**  
- El carrito debe contener al menos un producto.  
- El usuario debe haber iniciado sesión.  

**Flujo principal:**
1. El usuario accede al checkout.
2. Revisa el resumen del pedido.
3. Confirma la compra.
4. El sistema procesa el pago simulado.
5. Se muestra una confirmación de compra.

**Postcondiciones:** La compra queda registrada en el sistema de forma simulada.

---

### CU-07 — Consultar blog (listado)
**Actor:** Usuario (cliente)  
**Descripción:** Permite visualizar todas las publicaciones disponibles.  
**Precondiciones:** Deben existir entradas publicadas.  
**Flujo principal:**
1. El usuario accede al blog.
2. El sistema muestra el listado de artículos.
**Postcondiciones:** El usuario puede seleccionar una publicación.

---

### CU-08 — Consultar entrada del blog
**Actor:** Usuario (cliente)  
**Descripción:** Permite leer el contenido completo de un artículo.  
**Precondiciones:** La publicación debe estar disponible.  
**Flujo principal:**
1. El usuario selecciona una entrada.
2. El sistema carga el contenido.
3. Se muestra el artículo completo.
**Postcondiciones:** El usuario accede a la información del blog.

---

## Casos de uso de la aplicación móvil

### CU-09 — Consultar catálogo
**Actor:** App móvil  
**Descripción:** La aplicación solicita el catálogo al backend mediante la API REST.  
**Precondiciones:** La API debe estar operativa.  
**Flujo principal:**
1. La app envía una petición al endpoint correspondiente.
2. El sistema devuelve los productos en formato JSON.
**Postcondiciones:** La app muestra el catálogo.

---

### CU-10 — Consultar blog
**Actor:** App móvil  
**Descripción:** Permite obtener las publicaciones del blog desde la API.  
**Precondiciones:** La API debe estar disponible.  
**Flujo principal:**
1. La app realiza una petición REST.
2. El sistema responde con las entradas publicadas.
**Postcondiciones:** Las publicaciones se visualizan en la app.

---

## Casos de uso del administrador (ampliación futura)

### CU-11 — Gestionar productos
**Actor:** Administrador  
**Descripción:** Permite crear, modificar o eliminar productos del catálogo.  
**Precondiciones:** El administrador debe estar autenticado.  
**Postcondiciones:** El catálogo queda actualizado.

---

### CU-12 — Gestionar publicaciones del blog
**Actor:** Administrador  
**Descripción:** Permite administrar el contenido del blog.  
**Precondiciones:** El administrador debe tener permisos.  
**Postcondiciones:** Las publicaciones quedan actualizadas.

Los casos de uso definidos han servido como base para el diseño funcional del sistema, permitiendo identificar las interacciones clave entre los actores y la aplicación antes de la fase de desarrollo.
