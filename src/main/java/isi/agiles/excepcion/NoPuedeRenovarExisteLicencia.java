package isi.agiles.excepcion;

public class NoPuedeRenovarExisteLicencia extends Exception{
    public String getMessage() {
		String message = "La licencia seleccionada no puede ser renovada porque el titular ya tiene una licencia vigente.";
		return message;
	}
}
