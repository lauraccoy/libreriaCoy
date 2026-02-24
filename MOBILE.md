# App móvil para LibreríaCoy 🚀

## Objetivo
Aprovechar la lógica y datos ya existentes (servicios `CatalogoService`, `DetalleService`, DTOs y recursos estáticos) para exponer una **API REST** y crear una app móvil (nativa o cross-platform) que consuma esa API.

---

## Endpoints ya añadidos ✅
- GET `/api/catalogo` → Lista de `ItemCatalogo`. Cada elemento incluye la URL absoluta de la imagen en el campo `imagen`.
- GET `/api/catalogo/{tipo}/{id}` → Un `ItemCatalogo` concreto.
- GET `/api/detalle?tipo={tipo}&id={id}` → `DetalleViewDTO` con `img` convertido a URL absoluta.

> Las imágenes se sirven desde las rutas estáticas del servidor (ej.: `/portadas/xxx.jpg`, `/papeleria/yyy.jpg`).

---

## Opciones para la app móvil (resumen) 💡
1. Android nativo (Kotlin)
   - Ventajas: rendimiento, integración nativa.
   - Recomendación: crear un módulo/rep separado `android-app/` que consuma `/api`.
2. Flutter (Dart)
   - Ventajas: UI consistente en iOS y Android; buena productividad.
   - Recomendación: crear `flutter-app/` en la raíz o repo separado.
3. React Native / Expo (JS)
   - Ventajas: rápido para equipos web; mucho ecosistema.
4. PWA (Progressive Web App)
   - Ventajas: usar la misma base web; se instala como app en móviles.

---

## Recomendaciones técnicas 🔧
- Usar HTTPS en producción.
- En `WebConfig` ya permitimos CORS en `/api/**`. En producción restringe `allowedOrigins` al dominio de la app o la IP.
- Para imágenes, la API devuelve URLs completas; la app solo hace GET a esas URLs.
- Autenticación: si necesitas pedidos, agrega JWT + endpoints seguros (`/api/auth`, `/api/orders`).
- Documentación: considera `springdoc-openapi` para generar OpenAPI/Swagger para la API.

---

## Siguientes pasos sugeridos (elige 1 para avanzar) 🔢
1. ¿Qué plataforma quieres: **Android**, **Flutter**, **React Native**, o **PWA**? (Responde el nombre)
2. Quieres que: a) yo cree una *skeleton app* mínima que consuma `/api/catalogo`, o b) solo que te dé ejemplos de llamadas y componentes.

---

## Flutter (instrucciones y notas específicas) 🚀
- He creado un *skeleton* en `flutter_app/` con los archivos principales:
  - `pubspec.yaml` (dependencias: `http`, `cached_network_image`)
  - `lib/services/api_service.dart` (consulta `GET /api/catalogo`)
  - `lib/models/item_catalogo.dart`
  - `lib/screens/catalog_page.dart` (lista con imágenes)
  - `lib/main.dart`

- Ejecutar localmente:
  1. Arranca la app Spring Boot en `localhost:8080`.
  2. Abre `flutter_app/`, ejecuta `flutter pub get`.
  3. Para emulador Android usa `flutter run`. **Nota:** en el emulador Android la máquina host es `10.0.2.2` (la app ya apunta a `http://10.0.2.2:8080`).
  4. En dispositivo físico cambia `baseUrl` en `api_service.dart` por la IP de tu equipo (ej. `http://192.168.1.100:8080`) o usa `ngrok`.

- Siguientes mejoras sugeridas:
  - Pantalla de detalle que use `/api/detalle` (ya implementada en el skeleton Flutter).
  - Paginar el catálogo y manejar carga incremental.
  - Campos adicionales (carrito, login con JWT, endpoints protegidos).

Cómo probar la pantalla de detalle:
1. Arranca la aplicación Spring Boot localmente (`localhost:8080`).
2. Ejecuta la app Flutter en emulador Android (`flutter run`).
3. En la lista del catálogo pulsa sobre cualquier elemento: se abrirá la pantalla de detalle (usa `/api/detalle` para obtener todos los campos).

Si quieres, creo el skeleton completo con navegación detalle/carrito y autenticación JWT para la app Flutter. ✨
