package org.example;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Medida implements Serializable {
	private String sensor;
	private String nombreSensor;
	private String fecha;
	private float valor;
	private String unidadMedida;

	public Medida(String sensor, String nombreSensor, String fecha, float valor, String unidadMedida) {
		this.sensor = sensor;
		this.nombreSensor = nombreSensor;
		this.fecha = fecha;
		this.valor = valor;
		this.unidadMedida = unidadMedida;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getNombreSensor() {
		return nombreSensor;
	}

	public void setNombreSensor(String nombreSensor) {
		this.nombreSensor = nombreSensor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	@Override
	public String toString() {
		return this.sensor + " " + this.nombreSensor + " " + this.fecha + " " + this.valor + " " + this.unidadMedida;
	}

	/**
	 * Crea una medida a partir de su representacion en String
	 *
	 * @param medida el resultado de llamar toString() sobre una medida
	 * @return una medida
	 */
	public static Medida fromString(String medida) {
		String[] lista = medida.split(" ");
		return new Medida(lista[0], lista[1], lista[2], Float.parseFloat(lista[3]), lista[4]);
	}

	/**
	 * Retornar la medida en formato String específico
	 *
	 * @param modo tipo de formato:
	 *              <ul>
	 *                     <li>1: tipoSensor, nombreSensor, fecha, valor, unidadMedida</li>
	 *                     <li>2: fecha, tipoSensor, nombreSensor, valor, unidadMedida</li>
	 *             		   <li>3: fecha, tipoSensor, nombreSensor</li>
	 *                     <li>4: fecha, valor, unidadMedida</li>
	 *             </ul>
	 * @return la medida en formato String específico
	 */
	public String tabular(int modo) {
		// TIPO_SENSOR NOMBRE_SENSOR FECHA VALOR UNIDAD_MEDIDA
		if (modo == 1) {
			return this.toString();
		}
		// FECHA TIPO_SENSOR NOMBRE_SENSOR VALOR UNIDAD_MEDIDA
		else if (modo == 2) {
			return this.fecha + " " + this.sensor + " " + this.nombreSensor + " " + this.valor + " " + this.unidadMedida;
		}
		// FECHA TIPO_SENSOR NOMBRE_SENSOR
		else if (modo == 3) {
			return this.fecha + " " + this.sensor + " " + this.nombreSensor;
		}
		// FECHA VALOR UNIDAD_MEDIDA
		else if (modo == 4) {
			return this.fecha + " " + this.valor + " " + this.unidadMedida;
		} else {
			return "";
		}
	}
}
