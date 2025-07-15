
# 📱 SociosApp - Aplicación Multiplataforma (Android/Web)

Aplicación cliente multiplataforma para la gestión de socios de una peña sevillista. Desarrollada con **Kotlin Multiplatform (KMP)** y **Jetpack Compose**, permite su ejecución tanto en Android como en Web. Consume la API REST del backend para ofrecer funcionalidades como login, gestión de usuarios y socios, internacionalización y personalización de interfaz.

## ⚙️ Tecnologías utilizadas

- Kotlin Multiplatform
- Jetpack Compose (Android & Web)
- Ktor Client
- Ktorfit
- Kotlin Serialization
- Multiplatform Settings
- Kotlinx Datetime
- Coil (carga de imágenes)
- FileKit (gestión de archivos)
- UI BackHandler

## ✨ Características

- Iniciar sesión como Usuario o Administrador.
- Ver/editar perfil personal (Usuario).
- Gestionar socios y usuarios (Administrador).
- Reasignar numeración de socios automáticamente.
- Cambiar idioma (Español/Inglés).
- Modo claro/oscuro/sistema.
- Sesiones con expiración y validación por token.
- Notificaciones (snackbars) dinámicas.
- Carga de imágenes de perfil (avatar).

## 📲 Instalación y ejecución

### Android

1. Abre el proyecto en Android Studio.
2. Elige un emulador o conecta un dispositivo.
3. Ejecuta `Run` ▶️.

### Web

1. Usa la configuración WASMJs incluida en el proyecto.
2. Ejecuta:
   ```bash
   ./gradlew wasmJsBrowserRun
   ```

> Asegúrate de tener la API REST ejecutándose localmente o en el servidor.

## 🧑‍💼 Roles

- **Administrador:** Puede crear, modificar, eliminar socios y usuarios.
- **Usuario:** Solo puede ver su información personal.

## 🧾 Licencia

Este proyecto ha sido desarrollado como parte del **Proyecto Final de Grado Superior de Desarrollo de Aplicaciones Multiplataforma (FP DAM)** – IES Torre del Rey (Curso 24-25).

## 📂 Repositorio API Backend

👉 [apirest-sociosapp](https://github.com/alvaromyv/apirest-sociosapp)
