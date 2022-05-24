package org.example;

@SuppressWarnings("unused")
public class Resultado {
	private boolean estado;
	private String mensaje;

	public Resultado(boolean estado, String mensaje) {
		this.estado = estado;
		this.mensaje = mensaje;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
