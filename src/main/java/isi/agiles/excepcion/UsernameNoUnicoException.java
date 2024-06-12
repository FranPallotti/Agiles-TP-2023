package isi.agiles.excepcion;

public class UsernameNoUnicoException extends Exception{
    public String getMessage() {
		String message = "Ya existe un usuario con este nombre de usuario en la base de datos.";
		return message;
	}
}
