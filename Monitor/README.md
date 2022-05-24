# Módulo de monitores

El proyecto implementa los tres tipos de monitores:
- Oxígeno
- Temperatura
- PH

Se espera ejecutar un monitor por cada tipo disponible.

## Pre-requisitos

La ejecución se realiza mediante la terminal (Linux/MacOS) o el PowerShell (Windows).

- Java SDK 11 o superior
- Maven 3.5.0 o superior

Los comandos de Java y Maven deben estar en el PATH del sistema. Para verificar si están instalados, ejecute el siguiente comando:

```bash
# Debe arrojar un versión 11 o superior
java -version
```

```bash
# Debe arrojar un versión 3.5.0 o superior
mvn -version
```

## Compilación

Asegúrese de ubicarse en el directorio del módulo `path/to/proyecto-distribuidos-2210/Monitor`.

```bash
mvn clean compile assembly:single
```


El anterior comando debe generar un nuevo directorio `target/` con el archivo jar `target/Monitor-1.0-SNAPSHOT.jar`.

## Ejecución

Los argumentos de un sensor son:
- Dirección IP de los sensores
- Dirección IP del sistema de control de calidad
- Tipo de sensor a monitorear ('OXIGENO', 'TEMPERATURA', 'PH')
- Ruta del archivo de base de datos (es un archivo de texto)

Así se ejecutaría un monitor de OXIGENO que se conecta mediante localhost y escribe en el archivo de base de datos `bd_oxigeno.txt`:

```bash
java -jar target/Monitor-1.0-SNAPSHOT.jar localhost localhost OXIGENO bd_oxigeno.txt
```
