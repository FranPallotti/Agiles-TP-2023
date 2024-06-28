package isi.agiles.entidad;

public enum TipoSexo {
    MASCULINO ("Masculino"),
    FEMENINO ("Femenino"),
    X("No Binario");

    private final String nombre;

    private TipoSexo(String nombre){
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return this.nombre;
    }

    public static boolean isEqual(TipoSexo tipo1, TipoSexo tipo2){
        return tipo1==tipo2;
    }
}
