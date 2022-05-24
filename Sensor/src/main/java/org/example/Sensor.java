package org.example;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Sensor {
	private TipoSensor tipoSensor;
	private float[] probabilidadMedida = {0, 0, 0};
	private long intervalo;

	public Sensor(String sensor, String rutaArchivo, long intervalo) {
		this.tipoSensor = TipoSensor.buscar(sensor);
		this.intervalo = intervalo;
		this.leerArchivoConfiguracion(rutaArchivo);
	}

	public TipoSensor getTipoSensor() {
		return tipoSensor;
	}

	public void setTipoSensor(TipoSensor tipoSensor) {
		this.tipoSensor = tipoSensor;
	}

	public float[] getProbabilidadMedida() {
		return probabilidadMedida;
	}

	public void setProbabilidadMedida(float[] probabilidadMedida) {
		this.probabilidadMedida = probabilidadMedida;
	}

	public long getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(long intervalo) {
		this.intervalo = intervalo;
	}

	/**
	 * Lee un archivo .txt de configuración y carga las probabilidades
	 * de crear cada una de las medidas (CORRECTO, INCORRECTO, FUERA DE RANGO)
	 * en el arreglo de probabilidadMedida
	 *
	 * @param rutaArchivo ruta donde se aloja el archivo de las probabilidades
	 */
	private void leerArchivoConfiguracion(String rutaArchivo) {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaArchivo);
		assert inputStream != null;
		Scanner scanner = new Scanner(inputStream);
		int i = 0;
		float medida = 0;
		while (scanner.hasNextLine()) {
			medida += Float.parseFloat(scanner.nextLine());
			if (i == 0) {
				this.probabilidadMedida[i] = medida;
			} else if (i == 1) {
				this.probabilidadMedida[i] = medida;
			} else if (i == 2) {
				this.probabilidadMedida[i] = medida;
			}
			i++;

			if (i == 3) {
				break;
			}
		}
		scanner.close();
	}

	/**
	 * Genera una medida aleatoria que puede ubicarse dentro
	 * del rango CORRECTO, INCORRECTO, o FUERA DE RANGO
	 *
	 * @return una org.example.Medida aleatoria
	 */
	public Medida generarMedida() {
		Random rand = new Random();

		// Determinar el rango donde se va a generar la medida aleatoria
		float rangoProbAleatorio = rand.nextFloat();
		int rangoGeneracion = 2;    // 0 -> correcto, 1 -> fuera de rango, 2 -> incorrecto
		for (int i = 0; i < 3; i++) {
			if (rangoProbAleatorio <= this.probabilidadMedida[i]) {
				rangoGeneracion = i;
				break;
			}
		}

		// Generar la medida aleatoria a partir del rango de generación
		float valor;
		if (rangoGeneracion == 0) {            // CORRECTO
			// Generar un valor aleatorio dentro del rango (min, max) del sensor
			valor = this.tipoSensor.min + rand.nextFloat() * (this.tipoSensor.max - this.tipoSensor.min);
		} else if (rangoGeneracion == 1) {    // FUERA DE RANGO
			// Generar un valor aleatorio positivo fuera del rango (min, max) del sensor
			if (rand.nextFloat() < 0.5) {    // Entre (0, min)
				valor = rand.nextFloat() * this.tipoSensor.min;
			} else {    // Entre (max, max + min)
				valor = this.tipoSensor.max + rand.nextFloat() * (this.tipoSensor.min);
			}
		} else {    // INCORRECTO
			// Generar un valor aleatorio negativo entre (-max, 0)
			valor = (rand.nextFloat() * this.tipoSensor.max) * -1;
		}

		// Determinar el tiempo actual
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss.SSS");
		LocalDateTime now = LocalDateTime.now();
		return new Medida(this.tipoSensor.sensor, dtf.format(now), valor, this.tipoSensor.unidadMedida);
	}
}
