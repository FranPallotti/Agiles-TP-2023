package isi.agiles.excepcion;

public class TitularYaCargadoException extends Exception{
    public String getMessage() {
		String message = "Ya existe un titular con ese dni en la base de datos.";
		return message;
	}
}
