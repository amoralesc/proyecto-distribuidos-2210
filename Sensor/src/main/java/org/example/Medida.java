package org.example;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Medida implements Serializable {
	private String sensor;
	private String fecha;
	private float valor;
	private String unidadMedida;

	public Medida(String sensor, String fecha, float valor, String unidadMedida) {
		this.sensor = sensor;
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
		return this.sensor + " " + this.fecha + " " + this.valor + " " + this.unidadMedida;
	}

	/**
	 * Crea una medida a partir de su representacion en String
	 *
	 * @param medida el resultado de llamar toString() sobre una medida
	 * @return una medida
	 */
	public static Medida fromString(String medida) {
		String[] lista = medida.split(" ");
		return new Medida(lista[0], lista[1], Float.parseFloat(lista[2]), lista[3]);
	}
}
