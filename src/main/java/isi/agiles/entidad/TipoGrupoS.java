package isi.agiles.entidad;

public enum TipoGrupoS {
    AP ("A+"),
    AN ("A-"),
    BP ("B+"),
    BN ("B-"),
    ABP ("AB+"),
    ABN ("AB-"),
    OP ("0+"),
    ON ("0-");
    
    private final String nombre;

    private TipoGrupoS(String nombre){
        this.nombre = nombre;
    }

    public static boolean isEqual(TipoGrupoS tipo1, TipoGrupoS tipo2){
        return tipo1==tipo2;
    }

    @Override
    public String toString(){
        return this.nombre;
    }
}
