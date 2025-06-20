
# 📱 SocioApp - Gestión de Peña Sevillista

**SocioApp** es una aplicación multiplataforma (Android y Web) desarrollada como proyecto final del ciclo superior de Desarrollo de Aplicaciones Multiplataforma (FP DAM). Su objetivo principal es facilitar la gestión de los socios de una peña sevillista mediante una API RESTful con base de datos integrada.

## 🧩 Características Principales

- 🔐 Autenticación JWT con roles (Administrador / Usuario)
- 👤 Gestión completa de usuarios y socios
- 📆 Reasignación de número de socio según antigüedad
- 🌍 Soporte multilenguaje (español e inglés)
- 🌗 Soporte para modo claro/oscuro
- 🖼️ Carga de imágenes de perfil
- 🧭 Interfaz intuitiva construida con Jetpack Compose y Kotlin Multiplatform
- 🔗 Comunicación HTTP con seguridad (APIKEY + JWT)

## 🛠️ Tecnologías Utilizadas

### Backend (API RESTful)

- **Node.js + Express**
- **MySQL2 + Sequelize (ORM)**
- **JWT (JsonWebToken)**
- **Multer** (gestión de imágenes)
- **AJV** (validación JSON)
- **I18N** (internacionalización)
- **Dotenv** (variables de entorno)
- **Cors**

### Cliente (Web y Android)

- **Kotlin Multiplatform (KMP)**
- **Jetpack Compose**
- **Ktor Client / Ktorfit**
- **Kotlinx Serialization / Kotlinx Datetime**
- **Multiplatform Settings** (persistencia local)
- **Coil** (carga de imágenes)
- **FileKit** (gestión de archivos)

## 🔐 Roles y Permisos

| Funcionalidad                            | Usuario | Administrador |
|-----------------------------------------|:-------:|:-------------:|
| Iniciar sesión                          | ✅      | ✅            |
| Ver información personal                | ✅      | ✅            |
| Alta / Modificación / Eliminación       | ❌      | ✅            |
| Ver y filtrar socios                    | ❌      | ✅            |
| Reasignar numeración por antigüedad     | ❌      | ✅            |
| Cambiar idioma o tema visual            | ✅      | ✅            |

## 🧪 Requisitos del Sistema

- **Servidor:**
  - Node.js v18+
  - MySQL
  - Entorno de desarrollo: Visual Studio Code
- **Cliente Android/Web:**
  - Android Studio (para Android)
  - Navegador moderno (para Web)
  - Kotlin Multiplatform

## 🧮 Modelo de Datos

### Tabla `usuario`
- `id`: INT, PK, autoincrement
- `avatar`: STRING (opcional)
- `nombre`: STRING
- `apellidos`: STRING (opcional)
- `telefono`: STRING (opcional)
- `email`: STRING, único
- `contraseña`: STRING
- `rol`: ENUM (`usuario`, `administrador`)

### Tabla `socio`
- `id`: INT, PK, autoincrement
- `numero_socio`: INT
- `fecha_antigüedad`: STRING
- `categoría`: ENUM (`Infantil`, `Juvenil`, `Adulto`, `Senior`)
- `abonado`: BOOLEAN
- `usuario_id`: FK → `usuario.id`

## 🧑‍💻 Autor

**Desarrollado por: Álvaro Moyano Vila**  
Proyecto final individual realizado como cierre del ciclo formativo de Grado Superio DAM.
