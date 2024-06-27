package isi.agiles.entidad;

public enum TipoClasesLicencia {
    A, B, C, D, E, F, G;

    public static boolean isEqual(TipoClasesLicencia tipo1, TipoClasesLicencia tipo2){
        return tipo1==tipo2;
    }
}
