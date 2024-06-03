package isi.agiles.entidad;

public enum TipoGrupoS {
    AP, AN, BP, BN, ABP, ABN, OP, ON;
    
    public static boolean isEqual(TipoGrupoS tipo1, TipoGrupoS tipo2){
        return tipo1==tipo2;
    }
}
