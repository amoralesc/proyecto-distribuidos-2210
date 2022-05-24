package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ControladorMonitor {
	@SuppressWarnings("FieldCanBeLocal")
	private static Monitor monitor;

	/**
	 * args[0]: dirección IP de los sensores,
	 * args[1]: dirección IP del sistema de control de calidad,
	 * args[2]: tipo de sensor a monitorear,
	 * args[3]: ruta del archivo de base de datos.
	 */
	public static void main(String[] args) {
		System.out.println("Dirección IP sensores: " + args[0]);
		System.out.println("Dirección IP sistema de calidad: " + args[1]);
		System.out.println("Tipo de sensor a monitorear: " + args[2]);
		System.out.println("Ruta archivo base de datos: " + args[3]);

		monitor = new Monitor(args[2], args[3]);

		try (ZContext context = new ZContext()) {
			// CONEXIÓN CON LOS SENSORES (PUB - SUB)
			// El monitor actúa como subscriber
			ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);

			// Conectarse a todos los puertos del tipo de sensor
			for (int i = monitor.getTipoSensor().primerPuerto; i < monitor.getTipoSensor().ultimoPuerto; i++) {
				subscriber.connect(String.format("tcp://%s:%d", args[0], i));
			}
			// Suscribirse al tema
			subscriber.subscribe((monitor.getTipoSensor().tema).getBytes(ZMQ.CHARSET));

			// CONEXIÓN CON EL SISTEMA DE CALIDAD (PUSH - PULL)
			// El monitor actúa como push
			ZMQ.Socket push = context.createSocket(SocketType.PUSH);
			push.connect(String.format("tcp://%s:5559", args[1]));

			// Abrir la base de datos
			PrintWriter bd = null;
			try {
				FileWriter fw = new FileWriter(args[3], true);
				BufferedWriter bw = new BufferedWriter(fw);
				bd = new PrintWriter(bw);
			} catch (IOException e) {
				System.out.println("No se pudo abrir el archivo de base de datos");
				System.exit(1);
			}

			System.out.println("\nEl monitor se está ejecutando y esperando medidas...");
			System.out.println("Ingrese CTRL + C para terminar el proceso...");

			// El monitor empieza a esperar medidas de los sensores mientras no se interrumpa el proceso
			String mensaje;
			while (!Thread.currentThread().isInterrupted()) {
				subscriber.recvStr();           // Tema (tipo de medida)
				mensaje = subscriber.recvStr(); // Mensaje (medida)

				Medida medida = Medida.fromString(mensaje);
				TipoMedida tipoMedida = monitor.clasificarMedida(medida);

				System.out.println(mensaje);
				System.out.println("La medida es: " + tipoMedida.toString());

				// Enviar la medida al sistema de calidad si está fuera de rango
				if (tipoMedida == TipoMedida.FUERA) {
					push.send(mensaje);
				}
				// Registrar la medida en la base de datos si no es incorrecta
				if (tipoMedida != TipoMedida.INCORRECTO) {
					bd.println(mensaje);
					bd.flush();
				}
			}
			subscriber.close();
			push.close();
			bd.close();
		}
	}
}
