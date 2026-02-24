# LibreriaCoy Flutter (skeleton)

Este es un esqueleto mínimo para una app Flutter que consume la API de `libreriaCoy`.

Requisitos:
- Flutter SDK instalado (https://flutter.dev)
- Emulador Android o dispositivo físico

Instrucciones rápidas:
1. Abre una terminal en `flutter_app/`.
2. Ejecuta `flutter pub get`.
3. Para emuladores Android use `flutter run`.

Notas importantes:
- En el emulador Android, `localhost` del servidor Spring Boot es `10.0.2.2`. La app por defecto apunta a `http://10.0.2.2:8080`.
- En dispositivo físico o iOS ajusta la `baseUrl` en `lib/services/api_service.dart` a la IP de tu equipo.
