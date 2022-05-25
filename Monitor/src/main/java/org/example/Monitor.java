package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("unused")
public class Monitor {
	private String ipSensores;
	private String ipSistemaCalidad;
	private TipoSensor tipoSensor;
	private String archivoBD;

	public Monitor(String ipSensores, String ipSistemaCalidad, String tipoSensor, String archivoBD) {
		this.ipSensores = ipSensores;
		this.ipSistemaCalidad = ipSistemaCalidad;
		this.tipoSensor = TipoSensor.buscar(tipoSensor);
		this.archivoBD = archivoBD;
	}

	public String getIpSensores() {
		return ipSensores;
	}

	public void setIpSensores(String ipSensores) {
		this.ipSensores = ipSensores;
	}

	public String getIpSistemaCalidad() {
		return ipSistemaCalidad;
	}

	public void setIpSistemaCalidad(String ipSistemaCalidad) {
		this.ipSistemaCalidad = ipSistemaCalidad;
	}

	public TipoSensor getTipoSensor() {
		return tipoSensor;
	}

	public void setTipoSensor(TipoSensor tipoSensor) {
		this.tipoSensor = tipoSensor;
	}

	public String getArchivoBD() {
		return archivoBD;
	}

	public void setArchivoBD(String archivoBD) {
		this.archivoBD = archivoBD;
	}

	/**
	 * Clasifica una medida de acuerdo al sensor y los rangos de valores del mismo
	 *
	 * @param medida Medida a clasificar
	 * @return la clasificacion de la medida
	 */
	public TipoMedida clasificarMedida(Medida medida) {
		if (medida.getValor() < 0) {	// INCORRECTO
			return TipoMedida.INCORRECTO;
		} else if (medida.getValor() >= this.tipoSensor.min && medida.getValor() < this.tipoSensor.max) {	// CORRECTO
			return TipoMedida.CORRECTO;
		} else {	// FUERA DE RANGO
			return TipoMedida.FUERA;
		}
	}

	public void ejecutar() {
		try (ZContext context = new ZContext()) {
			// CONEXIÓN CON LOS SENSORES (PUB - SUB)
			// El monitor actúa como subscriber
			ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);

			// Conectarse a todos los puertos del tipo de sensor
			for (int i = this.getTipoSensor().primerPuerto; i <= this.getTipoSensor().ultimoPuerto; i++) {
				subscriber.connect(String.format("tcp://%s:%d", this.ipSensores, i));
			}
			// Suscribirse al tema
			subscriber.subscribe((this.getTipoSensor().tema).getBytes(ZMQ.CHARSET));

			// CONEXIÓN CON EL SISTEMA DE CALIDAD (PUSH - PULL)
			// El monitor actúa como push
			ZMQ.Socket push = context.createSocket(SocketType.PUSH);
			push.connect(String.format("tcp://%s:5559", this.ipSistemaCalidad));

			// Abrir la base de datos
			PrintWriter bd = null;
			try {
				FileWriter fw = new FileWriter(this.archivoBD, true);
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
				TipoMedida tipoMedida = this.clasificarMedida(medida);

				System.out.println(medida.tabular(2) + " -> " + tipoMedida.toString());

				// Enviar la medida al sistema de calidad si está fuera de rango
				if (tipoMedida == TipoMedida.FUERA) {
					push.send(mensaje);
				}
				// Registrar la medida en la base de datos si no es incorrecta
				if (tipoMedida != TipoMedida.INCORRECTO) {
					bd.println(medida.tabular(2));
					bd.flush();
				}
			}
			subscriber.close();
			push.close();
			bd.close();
		}
	}
}
