# Módulo de Sistema de Calidad

El proyecto implementa el sistema de calidad con autenticación de usuarios. El archivo de usuarios, donde se tienen las credenciales de lo mismos, se guarda localmente en la ruta `usuarios.json`.

## Pre-requisitos

La ejecución se realiza mediante la terminal (Linux/MacOS) o el PowerShell (Windows).

- Java SDK 8 o superior
- Maven 3.5.0 o superior

Los comandos de Java y Maven deben estar en el PATH del sistema. Para verificar si están instalados, ejecute el siguiente comando:

```bash
# Debe arrojar un versión 1.8 o superior
java -version
```

```bash
# Debe arrojar un versión 3.5.0 o superior
mvn -version
```

## Compilación

Asegúrese de ubicarse en el directorio del módulo `path/to/proyecto-distribuidos-2210/SistemaCalidad`.

```bash
mvn clean compile assembly:single
```

El anterior comando debe generar un nuevo directorio `target/` con el archivo jar `target/SistemaCalidad-1.0-SNAPSHOT.jar`.

## Ejecución

Para ejecutar el sistema de calidad:

```bash
java -jar target/SistemaCalidad-1.0-SNAPSHOT.jar
```

Después entrará a un menú con las opciones de:
1. Iniciar sesión
2. Crear usuario
3. Salir

Una vez haya iniciado sesión o creado un usuario, el sistema estará listo para recibir alertas.