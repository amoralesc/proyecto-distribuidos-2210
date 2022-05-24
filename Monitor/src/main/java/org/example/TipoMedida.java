package org.example;

@SuppressWarnings("unused")
public enum TipoMedida {
	CORRECTO(0),
	FUERA(1),
	INCORRECTO(2);

	public final int valor;
	public final String clasificacion;

	TipoMedida(int valor) {
		this.valor = valor;
		if (valor == 0) {
			this.clasificacion = "DENTRO DEL RANGO";
		} else if (valor == 1) {
			this.clasificacion = "FUERA DEL RANGO";
		} else {
			this.clasificacion = "INCORRECTA";
		}
	}

	@Override
	public String toString() {
		return this.clasificacion;
	}
}
