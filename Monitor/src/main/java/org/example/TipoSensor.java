package org.example;

public enum TipoSensor {
	OXIGENO("OXIGENO"),
	TEMPERATURA("TEMPERATURA"),
	PH("PH");

	public final String sensor;
	public final String unidadMedida;
	public final String tema;
	public final float min;
	public final float max;
	public final int primerPuerto;
	public final int ultimoPuerto;

	TipoSensor(String sensor) {
		this.sensor = sensor;
		switch (sensor) {
			case "OXIGENO":
				this.unidadMedida = "mg/L";
				this.min = 2.0F;
				this.max = 11.0F;
				this.tema = "oxigeno";
				this.primerPuerto = 5560;
				this.ultimoPuerto = 5579;
				break;
			case "TEMPERATURA":
				this.unidadMedida = "F";
				this.min = 68.0F;
				this.max = 89.0F;
				this.tema = "temperatura";
				this.primerPuerto = 5580;
				this.ultimoPuerto = 5599;
				break;
			case "PH":
				this.unidadMedida = "ph";
				this.min = 6.0F;
				this.max = 8.0F;
				this.tema = "ph";
				this.primerPuerto = 5600;
				this.ultimoPuerto = 5619;
				break;
			default:
				throw new IllegalArgumentException("Tipo de sensor no reconocido");
		}
	}

	/**
	 * Busca el tipo de sensor a partir del nombre del sensor
	 *
	 * @param sensor nombre del sensor
	 * @return el tipo de sensor
	 */
	public static TipoSensor buscar(String sensor) {
		switch (sensor) {
			case "OXIGENO":
				return TipoSensor.OXIGENO;
			case "TEMPERATURA":
				return TipoSensor.TEMPERATURA;
			case "PH":
				return TipoSensor.PH;
			default:
				return null;
		}
	}
}
