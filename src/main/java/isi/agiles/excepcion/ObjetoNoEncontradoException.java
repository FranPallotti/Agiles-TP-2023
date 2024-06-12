package isi.agiles.excepcion;

public class ObjetoNoEncontradoException extends Exception {
	public String getMessage() {
		String message = "El objeto solicitado no ha sido encontrado en nuestra base de datos.";
		return message;
	}
}
