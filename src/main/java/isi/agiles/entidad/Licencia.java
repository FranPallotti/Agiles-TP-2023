package isi.agiles.entidad;

import java.time.LocalDate;

public class Licencia {
    private Long idLicencia;
    private ClaseLicencia claseLicencia;
    private String observaciones;
    private Titular titular;
    private LocalDate inicioVigencia;
    private LocalDate finVigencia;
    private Usuario realizoTramite;
}
