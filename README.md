# Proyecto de Introducción a los Sistemas Distruibuidos

## Instalación

Para obtener el código fuente de este proyecto

```bash
git clone https://github.com/amoralesc/proyecto-distribuidos-2210.git proyecto-distribuidos-2210
cd proyecto-distribuidos-2210
```

Cada carpeta representa un módulo del proyecto:

- [`Sensor`](/Sensor) contiene el código fuente del subsistema de sensores que representa un sensor
- [`Monitor`](/Monitor) contiene el código fuente del subsistema de monitores que representa un monitor
- [`SistemaFallasMonitor`](/SistemaFallasMonitor) contiene el código fuente del subsistema de monitores que implementa la tolerancia a fallas. Este módulo se utiliza si se espera que el sistema siga funcionando en caso de que un monitor falle
- [`SistemaCalidad`](/SistemaCalidad) contiene el código fuente del subsistema que representa el sistema de calidad

## Ejecución

Cada módulo tiene instrucciones de ejecución en su respectivo README.md. El proyecto puede ser ejecutado en una misma máquina (utilizando localhost como direcciones IP). No obstante, el proyecto está pensado para ser ejecutado en tres máquinas distintas, que se puedan conectar mediante una red. Esta red puede ser LAN o estar sobrepuesta mediante Hamachi.

El orden de ejecución recomendado es:
1. Sistema de Calidad
2. Sensores
3. Monitores

Para el subsistema de monitores, se recomienda solo ejecutar o los monitores standalone, o el sistema de fallas que los autoejecuta. **No se recomienda ejecutar ambos módulos al mismo tiempo en la misma máquina**.