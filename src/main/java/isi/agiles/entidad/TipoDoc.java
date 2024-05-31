package isi.agiles.entidad;

public enum TipoDoc {
    DNI,PASAPORTE;

    public static boolean isEqual(TipoDoc tipo1, TipoDoc tipo2){
        return tipo1==tipo2;
    }
}
