package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorUsuario {
	private static final String RUTA_USUARIOS = "usuarios.json";
	private final ControladorSeguridad controladorSeguridad;
	private final Gson gson;
	private final List<Usuario> usuarios;

	public ControladorUsuario() {
		this.controladorSeguridad = new ControladorSeguridad();
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		usuarios = leerUsuarios();
	}

	/**
	 * Crea un nuevo usuario en el sistema y lo persiste en
	 * la base de datos
	 *
	 * @param email correo del usuario, debe ser único
	 * @param password contraseña del usuario
	 * @param nombre nombre del usuario
	 * @return creación correcta o fallida
	 */
	public Resultado crearUsuario(String email, String password, String nombre) {
		// Determinar si el correo no se encuentra registrado ya en la aplicación
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().equals(email)) {
				return new Resultado(
						false,
						"Ya existe un usuario registrado con ese email"
				);
			}
		}

		// Generar salt y encriptar contraseña en hash
		String salt, hash;
		try {
			byte[] saltByte = controladorSeguridad.generarSalt();
			salt = controladorSeguridad.toHex(saltByte);
			hash = controladorSeguridad.generarHash(password, saltByte);
		} catch (Exception e) {
			return new Resultado(
					false,
					"Ocurrió un error creando el usuario"
			);
		}

		Usuario usuario = new Usuario(
				email, hash, salt, nombre
		);
		try {
			persistirUsuario(usuario);
		} catch (IOException exception) {
			return new Resultado(
					false,
					"Ocurrió un error creando el usuario"
			);
		}
		return new Resultado(
				true,
				"Bienvenido, " + nombre
		);
	}

	/**
	 * Autentica un usuario en el sistema, buscando si sus
	 * datos coinciden con los almacenados en la base de datos.
	 *
	 * @param email correo del usuario
	 * @param password contraseña del usuario
	 * @return autenticación correcta o fallida
	 */
	public Resultado autenticar(String email, String password) {
		// Buscar el usuario según su email
		Usuario usuario = null;
		for (Usuario _usuario : usuarios) {
			if (_usuario.getEmail().equals(email)) {
				usuario = _usuario;
				break;
			}
		}
		if (usuario == null) {
			return new Resultado(
					false,
					"Email / contraseña incorrectos"
			);
		}

		// Autenticar el usuario validando su contraseña
		boolean autenticado;
		try {
			autenticado = controladorSeguridad.validarPassword(
					password, usuario.getSalt(), usuario.getHash()
			);
		} catch (Exception e) {
			return new Resultado(
					false,
					"Ocurrió un error autenticando el usuario"
			);
		}

		if (autenticado) {
			return new Resultado(
					true,
					"Bienvenido, " + usuario.getNombre()
			);
		}
		return new Resultado(
				false,
				"Email / contraseña incorrectos"
		);
	}

	/**
	 * Persiste un usuario en la base de datos de usuarios
	 *
	 * @param usuario el usuario a persistir
	 */
	private void persistirUsuario(Usuario usuario) throws IOException {
		usuarios.add(usuario);
		Type usuariosType = new TypeToken<List<Usuario>>() {}.getType();
		String json = gson.toJson(usuarios, usuariosType);

		File file = new File(ControladorUsuario.RUTA_USUARIOS);
		if (!file.exists()) {
			//noinspection ResultOfMethodCallIgnored
			if (!file.createNewFile()) {
				throw new IOException("No se pudo crear el archivo de usuarios");
			}
		}
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(json);
		bufferedWriter.close();
	}

	/**
	 * Lee una lista de usuarios alojados en un archivo JSON
	 *
	 * @return lista de usuarios leídos
	 */
	private List<Usuario> leerUsuarios() throws JsonSyntaxException {
		List<Usuario> usuarios = new ArrayList<>();
		String json;
		try {
			File file = new File(ControladorUsuario.RUTA_USUARIOS);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			json = bufferedReader.lines().collect(Collectors.joining());
			bufferedReader.close();
		} catch (Exception exception) {
			return usuarios;
		}

		usuarios = gson.fromJson(json, new TypeToken<List<Usuario>>(){}.getType());
		return usuarios;
	}
}
