# 📚 LibreriaCoy - Aplicación de Gestión de Librería

Una aplicación móvil y web completa para la gestión de una librería, incluyendo catálogo de productos, carrito de compras, autenticación de usuarios y blog con artículos.

## 🎯 Descripción del Proyecto

LibreriaCoy es una solución integral de e-commerce diseñada para una librería, que permite a los usuarios:
- Navegar por el catálogo de productos disponibles
- Gestionar un carrito de compras
- Autenticarse y crear cuentas de usuario
- Leer un blog with artículos y noticias
- Acceder desde dispositivos móviles (Android, iOS) o web

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java** con Spring Boot
- **Base de datos**: SQL (configurada con schema.sql y data.sql)
- **API REST**: Comunicación con frontend

### Frontend Mobile
- **Flutter** para desarrollo multiplataforma
- **Dart** como lenguaje de programación
- Soporta **Android**, **iOS**, **Web** y **Windows**

### Frontend Web
- **Angular/HTML5** con CSS
- Interfaz responsiva

### DevOps
- **Maven** para gestión de dependencias y build
- **.gitignore** configurado para ambientes de desarrollo

## 📁 Estructura del Proyecto

```
├── src/                          # Código backend Java
│   ├── main/
│   │   ├── java/                # Clases Java principales
│   │   └── resources/           # Configuración (application.properties)
│   └── test/                    # Tests unitarios
├── flutter_app/                 # Aplicación móvil Flutter
│   ├── lib/
│   │   ├── screens/             # Pantallas de la app
│   │   ├── widgets/             # Componentes reutilizables
│   │   ├── models/              # Modelos de datos
│   │   ├── services/            # Servicios (API, Auth, etc)
│   │   └── state/               # Gestión de estado
│   ├── android/                 # Configuración Android
│   ├── ios/                     # Configuración iOS
│   └── web/                     # Compilación web
├── docs/                        # Documentación del proyecto
├── pom.xml                      # Configuración Maven
└── .gitignore                   # Exclusiones de Git
```

## 📝 Documentación

- [REQUISITOS.md](docs/REQUISITOS.md) - Especificación de requisitos
- [CASOS_DE_USO.md](docs/CASOS_DE_USO.md) - Diagrama de casos de uso
- [MEMORIA_TECNICA.md](docs/MEMORIA_TECNICA.md) - Especificación técnica
- [README.md de Flutter](flutter_app/README.md) - Instrucciones de la app móvil
- [HELP.md](HELP.md) - Ayuda general del proyecto

## 🚀 Instalación

### Backend (Java/Spring Boot)

```bash
# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run
```

### Frontend (Flutter)

```bash
# Acceder al directorio
cd flutter_app

# Obtener dependencias
flutter pub get

# Ejecutar en dispositivo/emulador
flutter run

# Ejecutar en web
flutter run -d chrome

# Build para Android
flutter build apk

# Build para iOS
flutter build ios
```

## 🔧 Configuración

1. Configurar la base de datos en `src/main/resources/application.properties`
2. Actualizar URLs de API en los servicios de Flutter
3. Configurar autenticación (JWT o Similar) en backend y mobile

## 📊 Características Principales

- ✅ Autenticación de usuarios (registro/login)
- ✅ Catálogo de productos dinámico
- ✅ Carrito de compras persistente
- ✅ Blog con artículos
- ✅ Interfaz intuitiva y responsive
- ✅ API REST funcional
- ✅ Compatibilidad multiplataforma

## 👥 Contribuidores

- [Laura Coy Casado](https://github.com/lauraccoy)
- [Axel Belbrun](https://github.com/AxelBelbrun)

## 📄 Licencia

Este proyecto está disponible bajo licencia abierta para fines educativos.

## 📞 Contacto

Para más información sobre el proyecto, contacta a través de GitHub o LinkedIn.

---

**Desarrollado como proyecto integral de gestión de desarrollo de aplicaciones**