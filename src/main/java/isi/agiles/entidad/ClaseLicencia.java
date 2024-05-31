package isi.agiles.entidad;

import java.util.*;

/* (!!) Ver como agregar el dato de "edad maxima para pedir por primera vez"
 * sin que este en el codigo, que se pida de la base de datos.
 * (El problema es que es una constante global)
*/

public class ClaseLicencia {
    private Character clase;
    private String descripcion;
    private List<ClaseLicencia> incluye;
    private Boolean esProfesional;
    private Integer edadMinima;
    private static final Integer EDAD_MAX_PRIMERA_LICENCIA_PROF = 65; //(!)

    public static Integer getEdadMaxPrimeraLicenciaProf() {
        return EDAD_MAX_PRIMERA_LICENCIA_PROF;
    }


}
