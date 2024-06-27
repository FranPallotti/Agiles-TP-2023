package isi.agiles.excepcion;

public class NoPuedeRenovarVigenciaTemprana extends Exception{
    public String getMessage() {
		String message = "La licencia seleccionada no puede ser renovada porque excede el plazo de renovación temprana (3 meses).";
		return message;
	}
}
