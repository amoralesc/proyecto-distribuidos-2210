package org.example;

@SuppressWarnings("unused")
public class Usuario {
	private String email;
	private String hash;
	private String salt;
	private String nombre;

	public Usuario(String email, String hash, String salt, String nombre) {
		this.email = email;
		this.hash = hash;
		this.salt = salt;
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
