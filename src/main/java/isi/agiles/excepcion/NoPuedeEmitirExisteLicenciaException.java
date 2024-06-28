package isi.agiles.excepcion;

public class NoPuedeEmitirExisteLicenciaException extends Exception{
    @Override
    public String getMessage(){
        String message = "La licencia no puede ser emitida porque el titular ya tiene una licencia vigente de esa clase.";
        return message;
    }
}
