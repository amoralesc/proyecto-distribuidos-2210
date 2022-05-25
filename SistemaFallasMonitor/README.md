# Módulo de Tolerancia a Fallas de los monitores

El proyecto implementa un sistema con tolerancia a fallas de los monitores, donde si un monitor deja de funcionar, otro monitor sube a tomar su lugar.

**Nota:** Este módulo está hecho para correr los tres monitores disponibles, y cada uno de ellos con tolerancia a fallas. No se deben correr los monitores por si solos si se está utilizando este módulo.

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

### El módulo de monitores debe haber sido compilado previamente

## Compilación

Asegúrese de ubicarse en el directorio del módulo `path/to/proyecto-distribuidos-2210/SistemaFallasMonitor`.

```bash
mvn clean compile assembly:single
```

El anterior comando debe generar un nuevo directorio `target/` con el archivo jar `target/SistemaFallasMonitor-1.0-SNAPSHOT.jar`.

## Ejecución

Los argumentos del sistema con tolerancia a fallas de monitores son:
- Ruta al jar del módulo de monitores (suele ser `../Monitor/target/Monitor-1.0-SNAPSHOT.jar`) 
- Dirección IP de los sensores
- Dirección IP del sistema de control de calidad
- Ruta del archivo de base de datos del monitor de OXIGENO
- Ruta del archivo de base de datos del monitor de TEMPERATURA
- Ruta del archivo de base de datos del monitor de PH

Así se ejecutaría el sistema que utiliza el jar de monitores en la ruta default, se conecta mediante localhost y cuyas base de datos son `bd_oxigeno.txt`, `bd_temperatura.txt` y `bd_ph.txt`:

```bash
java -jar target/SistemaFallasMonitor-1.0-SNAPSHOT.jar ../Monitor/target/Monitor-1.0-SNAPSHOT.jar localhost localhost bd_oxigeno.txt bd_temperatura.txt bd_ph.txt
```