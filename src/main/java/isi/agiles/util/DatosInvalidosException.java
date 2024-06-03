package isi.agiles.util;

public class DatosInvalidosException extends Exception {
        

    public DatosInvalidosException() {
        super("Datos inválidos");
    }

    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }

    public DatosInvalidosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public DatosInvalidosException(Throwable causa) {
        super(causa);
    }
}
