package isi.agiles.entidad;

public enum TipoRol {
    ADMINISTRADOR,OPERADOR;

    public static boolean isEqual(TipoRol tipo1, TipoRol tipo2){
        return tipo1==tipo2;
    }
}
