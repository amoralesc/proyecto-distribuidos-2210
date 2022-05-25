package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ControladorMonitor {
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

		Monitor monitor = new Monitor(args[0], args[1], args[2], args[3]);
		monitor.ejecutar();
	}
}
