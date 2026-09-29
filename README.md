# HotelSpringBoot

API REST de reservas de hotel con Spring Boot, JPA, PostgreSQL y MapStruct.

## Ejecución

El perfil por defecto usa PostgreSQL, configurado en `src/main/resources/application.properties`.

Para desarrollo local sin acceso a PostgreSQL, usa el perfil `dev`, que persiste los datos en H2 mediante un archivo local:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

El perfil `dev` habilita la consola H2 en `http://localhost:8080/h2-console` y crea de forma idempotente un cliente, una habitación estándar, una suite y dos reservas. Los UUID y los totales se imprimen con el prefijo `[DEV-SEED]` al iniciar.

## Endpoints principales

- `GET /api/habitaciones`: lista habitaciones estándar y suites con el campo discriminador `tipo`.
- `POST /api/habitaciones/suites`: crea una suite y responde `201 Created` con `Location`.
- `GET /api/clientes/{id}/resumen`: devuelve el resumen agregado y sus reservas sin ciclos de serialización.
- `PATCH /api/clientes/{id}`: actualiza únicamente `nombre` y `email`, ignorando los campos internos del cliente.

Las cuatro solicitudes del taller están documentadas en `solicitudes-avanzadas.http`. Sustituye `clienteId` por el UUID que imprime el seed del perfil `dev`.
