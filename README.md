# TaskFlow - ProyectoDAW135 

Repositorio de pruebas y desarrollo para *TaskFlow*, un proyecto de DAW orientado a la gestión, simulación y seguimiento de proyectos utilizando diferentes metodologías ágiles como Scrum.

---

## Características

- *Backend:* Java con Spring Boot.
- *Modelado de entidades:* Proyecto, Usuario, Metodología, Simulación, Tarea y Decisión de Simulación.
- *Repositorios JPA* para acceso eficiente a la base de datos.
- *Servicios* para la gestión y lógica de negocio de proyectos, simulaciones y usuarios.
- *Métodos personalizados* para filtrar por metodología, simulación o usuario.
- *Integración y despliegue mediante Docker.*

- *Autenticación con Auth0:*  
  Autenticación mediante correo electrónico y Google. Los roles se gestionan desde el dashboard de Auth0. La lógica de la aplicación aplica control de acceso según el rol del usuario autenticado (por ejemplo, admin, user, etc.).

- *Protección de rutas:*  
  Las rutas del backend están protegidas por Auth0 utilizando tokens JWT. El frontend muestra u oculta funcionalidades según el rol del usuario autenticado.

- *Despliegue en Render:*  
  Existe una rama llamada staging en el repositorio, utilizada para los despliegues automáticos en Render. Tanto el backend como el frontend están desplegados y funcionando correctamente desde internet.

- *Conexión con base de datos en Neon:*  
  La aplicación se conecta a una base de datos PostgreSQL alojada en Neon.tech. Se utilizan variables de entorno para mantener seguras las credenciales de conexión.

- *Manejo seguro de configuración:*  
  Ninguna contraseña o dato sensible está expuesto en el código fuente. La configuración se mantiene mediante las herramientas que proporcionan Render y Auth0 para ambientes seguros.

---

## Estructura del Proyecto

- *Java:* Lógica de negocio (servicios), repositorios y modelos.
- *HTML/CSS/JS:* Interfaces y recursos estáticos.
- *Dockerfile:* Para construir y ejecutar el backend fácilmente en contenedores.

---

## Instalación y Ejecución

1. *Requisitos previos*:
   - Java 17+
   - Maven 3.9+
   - Docker (opcional, para despliegue en contenedor)

2. *Construcción local*:

   bash
   mvn clean package
   java -jar target/*.jar
   

3. *Despliegue con Docker*:

   bash
   docker build -t taskflow-testing .
   docker run -p 8080:8080 taskflow-testing
   

---

## Uso

- El backend expone endpoints RESTful para la gestión de proyectos, usuarios, simulaciones, tareas y decisiones.
- Permite crear proyectos asociados a metodologías y simular escenarios según la metodología seleccionada.
- El acceso y visibilidad de funcionalidades está controlado por los roles de Auth0.

---

## Licencia

Este proyecto es solo para fines educativos y de prueba.

---

### Autoría

Repositorio gestionado por
  -[Ricardo Antonio Mora Morales](https://github.com/MM23084).
  -[Moises Isaac Molina Corado](https://github.com/moisescorado91)
  -[Carlos Manuel Solis Flores](https://github.com/SF10020)
