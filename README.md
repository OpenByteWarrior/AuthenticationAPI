# Microservicio de Autenticación

Este microservicio proporciona funcionalidades de autenticación y autorización, utilizando una arquitectura basada en Java con Spring Boot. Incluye manejo de autorizacion por rol, cacheo, generación de JWT y resiliencia para la integración con otros microservicios.

---

## 🚀 Tecnologías y Librerías

- **Java 17**: Lenguaje principal.
- **Spring Boot**: Framework para simplificar el desarrollo de aplicaciones.
- **Spring Security**: Implementación de autenticación y autorización.
- **JSON Web Tokens (JWT)**: Manejo de tokens para la autenticación.
- **Caffeine**: Cache en memoria para roles.
- **Resilience4j**: Circuit breaker y mecanismos de resiliencia para la integración con el microservicio de gestión de roles.
- **ModelMapper**: Mapeo entre objetos de dominio y DTOs.
- **Lombok**: Reducción de código boilerplate con anotaciones.
- **PostgreSQL**: Base de datos relacional para almacenar usuarios y permisos.

---

## ✨ Funcionalidades Principales

1. **Autenticación**

   - 🔑 Generación y validación de tokens JWT.
   - 👤 Login de usuarios.

2. **Autorización**

   - 🛡️ Autorización de endpoints según los roles de los usuarios.
   - ⚡ Cacheo de roles para mejorar el rendimiento.

3. **Gestión de Resiliencia**

   - ⚙️ Uso de circuit breaker (Resilience4j) para gestionar la comunicación con el microservicio de gestión de roles.
   - 🛠️ Fallback seguro en caso de fallos.

4. **Cache de Roles**

   - 🗄️ Implementación de Caffeine para almacenar en memoria roles y permisos consultados frecuentemente.

5. **Interoperabilidad**

   - 🔄 Comunicación con el microservicio de gestión de roles.

---

## 🗂️ Estructura del Proyecto

```plaintext
└── 📁src
    ├── 📁main
    │   ├── 📁java
    │   │   └── 📁com
    │   │       └── 📁authentication_api
    │   │           ├── 📁application
    │   │           |    ├── 📁dto
    │   │           |    │   ├── RequestChangePasswordDTO.java
    │   │           |    │   ├── RequestUserUpdateDTO.java
    │   │           |    │   ├── ResponseHttpDTO.java
    │   │           |    │   ├── UserDTO.java
    │   │           |    │   └── UserLoginDTO.java
    │   │           |    ├── 📁service
    │   │           |    │   ├── JwtService.java
    │   │           |    │   ├── RoleService.java
    │   │           |    │   └── UserService.java
    │   │           |    └── 📁usecase
    │   │           |        ├── AuthUseCase.java
    │   │           |        └── UserUseCase.java
    │   │           ├── 📁config
    │   │           │   ├── ApplicationConfig.java
    │   │           │   ├── AuthorizationJwt.java
    │   │           │   ├── CacheConfig.java
    │   │           │   ├── JwtFilter.java
    │   │           │   └── ModelMapperConfig.java
    │   │           ├── 📁domain
    │   │           │   └── UserGateway.java
    │   │           ├── 📁infrastructure
    │   │           │   ├── 📁client
    │   │           │   │   └── RoleClient.java
    │   │           │   ├── 📁dto
    │   │           │   │   ├── RoleDTO.java
    │   │           │   │   └── RoleResponseDTO.java
    │   │           │   ├── 📁exception
    │   │           │   │   └── GlobalExceptionHandler.java
    │   │           │   ├── 📁persistence
    │   │           │   │   ├── 📁entity
    │   │           │   │   │   └── User.java
    │   │           │   │   ├── 📁repository
    │   │           │   │   │   ├── UserAdapter.java
    │   │           │   │   │   └── UserRepository.java
    │   │           │   ├── 📁rest
    │   │           │   │   ├── AdminController.java
    │   │           │   │   ├── AuthController.java
    │   │           │   │   ├── MainController.java
    │   │           │   │   └── UserController.java
    │   │           │   └── 📁security
    │   │           │       ├── CustomAccessDeniedHandler.java
    │   │           │       └── CustomUserDetails.java
    │   │           └── AuthenticationApiApplication.java
    │   └── 📁resources
    │       ├── 📁static
    │       ├── 📁templates
    │       ├── application.yaml
    │       └── banner.txt
    └── 📁test
        └── 📁java
            └── 📁com
                └── 📁authentication_api
                    └── AuthenticationApiApplicationTests.java
```

El proyecto sigue una arquitectura **hexagonal**:

- **Core**: Contiene las entidades de dominio y los casos de uso.
- **Adaptadores**:
  - Entrada: Controladores REST para manejar solicitudes HTTP.
  - Salida: Integración con la base de datos (Spring Data JPA) y con otros microservicios.
- **Configuración**: Beans y configuración de las librerías utilizadas.

---

## 🔧 Requisitos Previos

- **Java 17** o superior.
- **Gradle** como herramienta de construcción.
- **PostgreSQL** instalado y configurado.

---

## ⚙️ Configuración del Entorno

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/JuanCamilo-FVXOU/AuthenticationAPI.git
   cd AuthenticationAPI
   ```

2. **Configurar las variables de entorno**:
   Crear un archivo `.env` en la raíz del proyecto con las siguientes variables:

   ```env
   DB_URL=jdbc:postgresql://localhost:5432/nombre_base_datos
   DB_USERNAME=usuario
   DB_PASSWORD=contraseña
   ROLE_SERVER_URL=http://url-del-microservicio-de-roles
   JWT_EXPIRATION_MS=3600000
   ```

   ### Ejemplo explicativo:

   - `DB_URL`: URL de conexión a la base de datos PostgreSQL. Por ejemplo:
     ```env
     DB_URL=jdbc:postgresql://localhost:5432/mi_base_de_datos
     ```
     Cambia `mi_base_de_datos` por el nombre de tu base de datos.

   - `DB_USERNAME`: Nombre de usuario para conectarse a la base de datos. Ejemplo:
     ```env
     DB_USERNAME=admin
     ```

   - `DB_PASSWORD`: Contraseña asociada al usuario de la base de datos. Ejemplo:
     ```env
     DB_PASSWORD=admin123
     ```

   - `ROLE_SERVER_URL`: URL del microservicio de gestión de roles. Ejemplo:
     ```env
     ROLE_SERVER_URL=http://localhost:8081/api/roles
     ```

   - `JWT_EXPIRATION_MS`: Tiempo de expiración de los tokens JWT en milisegundos. Ejemplo:
     ```env
     JWT_EXPIRATION_MS=3600000
     ```
     En este caso, el token expirará en una hora (3600000 ms = 1 hora).

3. **Construir y ejecutar**:

   ```bash
   ./gradlew bootRun
   ```

---

## 📋 Endpoints Principales

### 🔑 Autenticación

 - **POST** `/api/auth/login`: Autenticación de usuarios y generación de token JWT.
 - **POST** `/api/auth/register`: Registro de nuevos usuarios.

### 🛡️ Gestión de Usuarios

 - **GET** `/api/users`: Obtener información de usuarios.
 - **PUT** `/api/users/changepassword/{id}`: Cambiar contraseña de un usuario.
 - **PUT** `/api/users/{id}`: Actualizar información de un usuario.
 - **DELETE** `/api/users/{id}`: Eliminar un usuario.

---

## 🧪 Tests

Para ejecutar los tests unitarios y de integración

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o un pull request para cualquier mejora o corrección.
