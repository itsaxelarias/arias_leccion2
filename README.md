Arias Lección 2 – Purchase Orders API

API REST desarrollada con Spring Boot 3, JPA / Hibernate y MySQL, completamente dockerizada y publicada en Docker Hub para facilitar su ejecución y evaluación.

La aplicación permite la gestión de órdenes de compra (Purchase Orders), incluyendo creación, listado y filtros avanzados.

Autor

Nombre: Anthony Alexis Arias Hurtado

GitHub: https://github.com/itsaxelarias

Docker Hub: https://hub.docker.com/repository/docker/aaarias6/arias_leccion2-api

Tecnologías utilizadas

Java 17

Spring Boot 3.3.5

Spring Data JPA

Hibernate

MySQL 8

Maven

Docker

Docker Compose

Postman

Arquitectura del proyecto
API Spring Boot (arias_leccion2-api)
        |
        |-- Docker
        |
MySQL 8 (arias_db)


La base de datos y la API se ejecutan en contenedores independientes, comunicándose mediante Docker Compose.

Imagen en Docker Hub

La imagen de la API se encuentra publicada en Docker Hub:

aaarias6/arias_leccion2-api:latest

Ejecución del proyecto (FORMA RECOMENDADA)
Requisitos previos

Docker

Docker Compose

No se requiere Java, Maven ni MySQL instalados localmente.

docker-compose.yml para evaluación

El siguiente archivo docker-compose.yml permite levantar toda la solución completa usando la imagen publicada en Docker Hub.

services:
  mysql:
    image: mysql:8.0
    container_name: arias_mysql
    environment:
      MYSQL_ROOT_PASSWORD: abcd
      MYSQL_DATABASE: arias_db
    ports:
      - "3307:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-pabcd"]
      interval: 5s
      timeout: 3s
      retries: 20

  api:
    image: aaarias6/arias_leccion2-api:latest
    container_name: arias_api
    depends_on:
      mysql:
        condition: service_healthy
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      DB_URL: jdbc:mysql://arias_mysql:3306/arias_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      DB_USER: root
      DB_PASSWORD: abcd

volumes:
  mysql_data:

Pasos para ejecutar
1. Crear el archivo docker-compose.yml

Copiar el contenido anterior en un archivo llamado docker-compose.yml.

2. Levantar los contenedores
docker compose up


Esto descargará automáticamente:

MySQL 8

La imagen aaarias6/arias_leccion2-api desde Docker Hub

Creación automática de la base de datos

La base de datos se crea automáticamente al iniciar los contenedores:

Nombre de la base: arias_db

Creación gestionada por MySQL y Hibernate

Tablas generadas automáticamente con spring.jpa.hibernate.ddl-auto=update

No se requiere crear tablas manualmente.

Endpoints disponibles
Crear una orden de compra

POST

http://localhost:8080/api/v1/purchase-orders

{
  "orderNumber": "PO-2025-000001",
  "supplierName": "ACME Tools",
  "status": "DRAFT",
  "totalAmount": 1500.75,
  "currency": "USD",
  "expectedDeliveryDate": "2025-02-10"
}

Listar todas las órdenes

GET

http://localhost:8080/api/v1/purchase-orders

Filtros avanzados

GET

http://localhost:8080/api/v1/purchase-orders?q=acme&from=2025-01-01T00:00:00&to=2025-06-30T23:59:59


Parámetros soportados:

Parámetro	Descripción
q	Búsqueda por orderNumber y supplierName
status	Estado de la orden
currency	Moneda
minTotal	Monto mínimo
maxTotal	Monto máximo
from	Fecha de inicio (createdAt)
to	Fecha de fin (createdAt)
Pruebas con Postman

Método: POST / GET

Body: raw → JSON

URL base: http://localhost:8080

La API fue probada correctamente en entorno Docker.

Apagar y limpiar contenedores
docker compose down -v


Esto elimina contenedores, volúmenes y datos persistidos.
