# WellPlayed: Backend

![Java Edition](edition.png)

> Backend API para la aplicación WellPlayed
>
> [Repositorio índice del proyecto](https://github.com/Maig0l/proyecto-dsw)

## Setup

Antes de iniciar la aplicación, es importante definir las siguientes variables de entorno:
- `DB_CONN_STRING`: Es la string de conexión, con host, puerto y nombre del schema a conectar. Ejemplo: `jdbc:mysql://localhost:3306/wellplayed-spring`
- `DB_USER`: Es el usuario de la DB con acceso a este schema.
- `DB_PASS`: Es la contraseña del usuario
- `API_SECRET`: Es es la clave con la que se van a hashear las contraseñas para el JWT

Estas variables de entorno pueden guardarse en Eclipse en: `Run -> Run configurations... -> (pestaña) Environments`

![Env config](envconfig.png)