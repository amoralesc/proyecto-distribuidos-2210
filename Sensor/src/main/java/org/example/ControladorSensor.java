package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

public class ControladorSensor {
	@SuppressWarnings("FieldCanBeLocal")
	private static Sensor sensor;

	 /**
	 * args[0]: tipo de sensor en mayúsculas,
	 * args[1]: ruta del archivo de configuración del sensor,
	 * args[2]: intervalo t de generación de medidas en milisegundos.
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Tipo de sensor: " + args[0]);
		System.out.println("Ruta archivo configuración: " + args[1]);
		System.out.println("Intervalo t de medidas: " + args[2]);

		sensor = new Sensor(args[0], args[1], Long.parseLong(args[2]));

		try (ZContext context = new ZContext()) {
			// CONEXIÓN CON LOS MONITORES (PUB - SUB)
			// El sensor actúa como publisher
			ZMQ.Socket publisher = context.createSocket(SocketType.PUB);

			// Buscar un puerto disponible y hacer un bind a este
			boolean encuentraPuerto = false;
			for (int i = sensor.getTipoSensor().primerPuerto; i < sensor.getTipoSensor().ultimoPuerto; i++) {
				try {
					publisher.bind(String.format("tcp://*:%d", i));
					System.out.printf("\nEl sensor ha hecho bind al puerto: %d%n", i);
					encuentraPuerto = true;
					break;
				} catch (ZMQException addressAlreadyInUseException) {
					// Puerto está siendo utilizado
				}
			}
			// Si no encuentra ningún puerto, el sensor no puede funcionar
			if (!encuentraPuerto) {
				System.out.println("\nTodos los puertos están ocupados y el sensor no puede ejecutarse");
				System.exit(1);
			}

			System.out.println("El sensor se está ejecutando y enviando medidas...");
			System.out.println("Ingrese CTRL + C para terminar el proceso...");

			// El sensor empieza a enviar medidas mientras no se interrumpa el proceso
			while (!Thread.currentThread().isInterrupted()) {
				publisher.sendMore(sensor.getTipoSensor().tema);    // Tema (tipo de medida)
				publisher.send(sensor.generarMedida().toString());  // Mensaje (medida)

				//noinspection BusyWait
				Thread.sleep(sensor.getIntervalo());    // El sensor envía medidas cada intervalo t
			}
			publisher.close();
		}
	}
}
