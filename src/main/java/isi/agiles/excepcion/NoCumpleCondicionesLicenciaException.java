package isi.agiles.excepcion;

public class NoCumpleCondicionesLicenciaException extends Exception {
    @Override
    public String getMessage(){
        String message = "El titular no cumple con las condiciones necesarias para obtener esta licencia.";
        return message;
    }
}
