# Módulo de sensores

El proyecto implementa los tres tipos de sensores:
- Oxígeno
- Temperatura
- PH

Se pueden ejecutar hasta 10 sensores de un mismo tipo, para un total de 30 posibles sensores en ejecución.

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

Asegúrese de ubicarse en el directorio del módulo `path/to/proyecto-distribuidos-2210/Sensor`.

```bash
mvn clean compile assembly:single
```

El anterior comando debe generar un nuevo directorio `target/` con el archivo jar `target/Sensor-1.0-SNAPSHOT.jar`.

## Ejecución

Los argumentos de un monitor son:
- Tipo de sensor ('OXIGENO', 'TEMPERATURA', 'PH')
- Ruta al archivo de configuración. El programa viene con tres configuraciones por defecto, ubicadas en `src/main/resources/`:
    - `configSensor1.txt` 0.6, 0.3, 0.1
    - `configSensor2.txt` 0.8, 0.1, 0.1
    - `configSensor3.txt` 0.4, 0.4, 0.2
  
    Es posible cambiar estas configuraciones, o crear nuevos archivos de configuración. Sin embargo, es necesario volver a compilar el módulo para que se hagan disponibles.
- Intervalo t de generación de medidas en milisegundos (t > 0)

Así se ejecutaría un sensor de OXIGENO con la configuración `configSensor1.txt` e intervalos de generación de 1 segundo:
```bash
java -jar target/Sensor-1.0-SNAPSHOT.jar OXIGENO configSensor1.txt 1000
```
