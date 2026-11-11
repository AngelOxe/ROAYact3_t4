# Actividad 3 — CRUD de materias con Spring Boot y MySQL

**Alumno:** Ángel Yibán Ruiz Osante  
**Materia:** Programación Web

## Descripción

Aplicación web para registrar, consultar, actualizar y eliminar materias.
Cada materia pertenece a un área y sus datos se almacenan en MySQL.

La interfaz utiliza Thymeleaf y CSS dentro del mismo proyecto Spring Boot.
Las pruebas del CRUD se realizaron desde las vistas web.

## Enlaces

- Repositorio: https://github.com/AngelOxe/ROAYact3_t4
- Aplicación en el VPS: http://138.68.234.30:8085/materias
- Actividad 1: http://138.68.234.30:8081/proyecto
- Actividad 2: http://138.68.234.30:8083/catalogo

La actividad 3 utiliza una carpeta, base de datos y puerto independientes
de las actividades anteriores.

## Tecnologías

- Java 21.
- Spring Boot 4.1.2.
- Spring Web MVC.
- Spring Data JPA y Hibernate.
- MySQL.
- Thymeleaf.
- Maven.
- HTML y CSS.

## Entidades y relación

Se implementaron dos entidades:

- **Area:** contiene un identificador y un nombre único.
- **Materia:** contiene un identificador, un nombre y el área a la que pertenece.

La relación es muchos a uno: varias materias pueden pertenecer a una misma
área. Se implementó con `@ManyToOne` y `@JoinColumn(name = "area_id")`
en la entidad `Materia`.

La columna `materias.area_id` es una llave foránea que referencia
`areas.id`. En el listado se muestra el nombre del área, no solamente su ID.

![Tablas y llave foránea](capturas/01-tablas-relacion.png)

## Organización del proyecto

| Capa | Archivos | Responsabilidad |
|---|---|---|
| Entidades | Area y Materia | Representar las tablas y su relación. |
| Repositorios | AreaRepository y MateriaRepository | Acceder a los datos mediante JpaRepository. |
| Servicios | AreaService y MateriaService | Validar datos y realizar las operaciones del CRUD. |
| Controlador | MateriaController | Recibir peticiones y preparar las vistas. |
| Vistas | lista.html y formulario.html | Mostrar las materias y capturar datos. |

La lógica de validación se encuentra en los servicios. El controlador
utiliza esos servicios y no accede directamente a los repositorios.

## Rutas

| Método | Ruta | Función |
|---|---|---|
| GET | `/` | Redirigir al listado. |
| GET | `/materias` | Consultar las materias y sus áreas. |
| GET | `/materias/nueva` | Mostrar el formulario de registro. |
| GET | `/materias/{id}/editar` | Mostrar el formulario de edición. |
| POST | `/materias/guardar` | Crear o actualizar según la presencia del ID. |
| POST | `/materias/{id}/eliminar` | Eliminar una materia. |

La eliminación se realiza mediante un formulario POST con confirmación.
Los formularios muestran los mensajes de éxito o los errores de validación.

## Configuración y ejecución local

Se requiere Java 21 y una instancia de MySQL activa.

1. Crear la base `roayact3_t4` con el juego de caracteres `utf8mb4`.
2. Crear un usuario de MySQL con permisos sobre esa base.
3. Configurar las variables de entorno de conexión.
4. Ejecutar el proyecto desde la carpeta que contiene `pom.xml`.

El archivo `application.properties` utiliza:

- `DB_URL`: dirección JDBC de la base.
- `DB_USER`: usuario de MySQL.
- `DB_PASSWORD`: contraseña de MySQL.

Las contraseñas reales no se incluyen en el repositorio.

En PowerShell, usando el usuario local `roay_app`:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/roayact3_t4"
$env:DB_USER = "roay_app"

$credencialBD = Get-Credential -UserName "roay_app" -Message "Contraseña MySQL local"
$env:DB_PASSWORD = $credencialBD.GetNetworkCredential().Password

$env:SERVER_PORT = "8085"
.\mvnw.cmd spring-boot:run