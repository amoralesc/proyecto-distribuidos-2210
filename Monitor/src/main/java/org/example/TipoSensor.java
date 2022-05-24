package org.example;

@SuppressWarnings("unused")
public enum TipoSensor {
	OXIGENO("OXIGENO"),
	TEMPERATURA("TEMPERATURA"),
	PH("PH");

	public final String sensor;
	public String unidadMedida;
	public String tema;
	public float min;
	public float max;
	public int primerPuerto;
	public int ultimoPuerto;

	TipoSensor(String sensor) {
		this.sensor = sensor;
		switch (sensor) {
			case "OXIGENO":
				this.unidadMedida = "mg/L";
				this.min = 2.0F;
				this.max = 11.0F;
				this.tema = "oxigeno";
				this.primerPuerto = 5560;
				this.ultimoPuerto = 5569;
				break;
			case "TEMPERATURA":
				this.unidadMedida = "F";
				this.min = 68.0F;
				this.max = 89.0F;
				this.tema = "temperatura";
				this.primerPuerto = 5570;
				this.ultimoPuerto = 5579;
				break;
			case "PH":
				this.unidadMedida = "ph";
				this.min = 6.0F;
				this.max = 8.0F;
				this.tema = "ph";
				this.primerPuerto = 5580;
				this.ultimoPuerto = 5589;
				break;
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
