package org.example;

@SuppressWarnings("unused")
public class Monitor {
	private TipoSensor tipoSensor;
	private String archivoBD;

	public Monitor(String sensor, String archivoBD) {
		this.tipoSensor = TipoSensor.buscar(sensor);
		this.archivoBD = archivoBD;
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
}
