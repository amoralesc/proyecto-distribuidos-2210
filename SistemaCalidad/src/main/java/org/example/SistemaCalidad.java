package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Scanner;

public class SistemaCalidad {
	@SuppressWarnings("FieldCanBeLocal")
	private static ControladorUsuario controlador;

	public static void main(String[] args) {
		controlador = new ControladorUsuario();
		Scanner in = new Scanner(System.in);

		String email, password, nombre;
		Resultado resultado = new Resultado(false, "");

		int opcion;
		do {
			System.out.println("\nSISTEMA DE CALIDAD");
			System.out.println("1. Iniciar sesión");
			System.out.println("2. Crear usuario");
			System.out.println("3. Salir");

			// Solicitar opción al usuario
			System.out.print("Opción: ");
			opcion = in.nextInt();

			switch (opcion) {
				case 1:
					System.out.print("Ingrese el email: ");
					email = in.next();
					System.out.print("Ingrese la contraseña: ");
					password = in.next();
					resultado = controlador.autenticar(
							email, password
					);
					break;
				case 2:
					System.out.print("Ingrese el email: ");
					email = in.next();
					System.out.print("Ingrese la contraseña: ");
					password = in.next();
					System.out.print("Nombre: ");
					nombre = in.next();
					resultado = controlador.crearUsuario(
							email, password, nombre
					);
					break;
				case 3:
					System.exit(0);
				default:
					System.out.println("Opcion inválida");
					break;
			}

			System.out.println("\n" + resultado.getMensaje());
		} while (!resultado.isEstado());

		try (ZContext context = new ZContext()) {
			//  CONEXIÓN CON LOS MONITORES
			// El monitor actúa como pull
			ZMQ.Socket pull = context.createSocket(SocketType.PULL);
			pull.bind("tcp://*:5559");

			String mensaje;
			while (!Thread.currentThread().isInterrupted()) {
				mensaje = pull.recvStr();
				System.out.println("ALERTA: " + mensaje);
			}
			pull.close();
		}
	}
}
