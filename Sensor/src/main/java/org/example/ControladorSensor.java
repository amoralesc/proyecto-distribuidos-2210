package org.example;

public class ControladorSensor {
	 /**
	 * args[0]: tipo de sensor en mayúsculas,
	 * args[1]: ruta del archivo de configuración del sensor,
	 * args[2]: intervalo t de generación de medidas en milisegundos.
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Tipo de sensor: " + args[0]);
		System.out.println("Ruta archivo configuración: " + args[1]);
		System.out.println("Intervalo t de medidas: " + args[2]);

		Sensor sensor = new Sensor(args[0], args[1], Long.parseLong(args[2]));
		sensor.ejecutar();
	}
}
