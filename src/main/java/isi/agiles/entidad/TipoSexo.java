package isi.agiles.entidad;

public enum TipoSexo {
    MASCULINO, FEMENINO, X;

    public static boolean isEqual(TipoSexo tipo1, TipoSexo tipo2){
        return tipo1==tipo2;
    }
}
