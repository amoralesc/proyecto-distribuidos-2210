package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SistemaFallasMonitor {
	private static final List<String> MONITOR_PARAMS = List.of(
			"java",
			"-jar"
	);

	/**
	 * args[0]: ruta al JAR del monitor,
	 * args[1]: dirección IP de los sensores,
	 * args[2]: dirección IP del sistema de calidad,
	 * args[3]: ruta del archivo bd del monitor de OXIGENO,
	 * args[4]: ruta del archivo bd del monitor de TEMPERATURA,
	 * args[5]: ruta del archivo bd del monitor de PH
	 */
	public static void main(String[] args) {
		// Obtener parámetros
		String jarMonitor = args[0];
		String ipSensores = args[1];
		String ipSistemaCalidad = args[2];
		String bdOxigeno = args[3];
		String bdTemperatura = args[4];
		String bdPh = args[5];

		// Crear parámetros base para la creación de los monitores
		List<String> paramsBase = new ArrayList<>();
		paramsBase.addAll(MONITOR_PARAMS);
		paramsBase.addAll(List.of(jarMonitor, ipSensores, ipSistemaCalidad));

		// Crear los runnables de los monitores
		Runnable runnableOxigeno = new RunnableMonitor(paramsBase, "OXIGENO", bdOxigeno);
		Runnable runnableTemperatura = new RunnableMonitor(paramsBase, "TEMPERATURA", bdTemperatura);
		Runnable runnablePh = new RunnableMonitor(paramsBase, "PH", bdPh);

		// Crear los threads de los monitores
		Thread threadMonitorOxigeno = new Thread(runnableOxigeno);
		Thread threadMonitorTemperatura = new Thread(runnableTemperatura);
		Thread threadMonitorPh = new Thread(runnablePh);

		// Iniciar los threads de los monitores
		threadMonitorOxigeno.start();
		threadMonitorTemperatura.start();
		threadMonitorPh.start();
	}

	/**
	 * Crea un builder de proceso para el monitor de un tipo de sensor
	 *
	 * @param paramsBase Parámetros base del monitor
	 * @param nombre     Nombre del tipo de sensor
	 * @param archivoBd  Ruta del archivo de base de datos del monitor
	 * @return Builder del proceso
	 */
	private static ProcessBuilder crearProcesoMonitor(List<String> paramsBase, String nombre, String archivoBd) {
		String[] params = new String[paramsBase.size() + 2];
		params = paramsBase.toArray(params);
		params[params.length - 2] = nombre;     // Tipo de sensor del monitor
		params[params.length - 1] = archivoBd;  // Ruta del archivo bd del monitor

		return new ProcessBuilder(params);
	}

	public static class RunnableMonitor implements Runnable {
		private final List<String> paramsBase;
		private final String nombre;
		private final String archivoBd;

		public RunnableMonitor(List<String> paramsBase, String nombre, String archivoBd) {
			this.paramsBase = paramsBase;
			this.nombre = nombre;
			this.archivoBd = archivoBd;
		}

		public void run() {
			ProcessBuilder pb = crearProcesoMonitor(paramsBase, nombre, archivoBd);

			System.out.printf("Iniciando monitor de %s...\n", nombre);
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Process monitor = pb.start();
					Long pid = monitor.pid();
					System.out.printf(
							"Monitor de %s iniciado con PID: %d\n", nombre, pid
					);
					monitor.waitFor();
					System.out.printf(
							"\nEl monitor de %s con PID: %d terminó inesperadamente\n", nombre, pid
					);
					System.out.printf("Reiniciando monitor de %s...\n", nombre);
				} catch (IOException | InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
