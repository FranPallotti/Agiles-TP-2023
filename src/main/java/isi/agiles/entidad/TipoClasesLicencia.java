package isi.agiles.entidad;

public enum TipoClasesLicencia {
    A, B, C, D, E, F, G;

    public static boolean isEqual(TipoDoc tipo1, TipoDoc tipo2){
        return tipo1==tipo2;
    }
}
