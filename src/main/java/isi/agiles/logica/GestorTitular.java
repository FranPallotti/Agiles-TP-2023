package isi.agiles.logica;

import java.util.*;
import java.time.LocalDate;
import java.time.Period;

import isi.agiles.dao.TitularDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.*;
import isi.agiles.excepcion.ObjetoNoEncontradoException;


public class GestorTitular {

    public static TitularDTO getTitularDTO(Titular titular){
        TitularDTO dto = new TitularDTO();
        dto.setIdTitular(titular.getIdTitular());
        dto.setNombre(titular.getNombre());
        dto.setApellido(titular.getApellido());
        dto.setNroDoc(titular.getNroDoc());
        dto.setTipoDoc(titular.getTipoDoc());
        dto.setFechaNacimiento(titular.getFechaNacimiento());
        return dto;
    }

    public static Titular getTitular(TitularDTO dto)
    throws ObjetoNoEncontradoException{
        TitularDAO dao = new TitularDAO();
        Titular titular = dao.getById(dto.getIdTitular()).orElseThrow(() -> new ObjetoNoEncontradoException());
        return titular;
    }

    public static TitularDTO getTitularDTOByDocumento(String nroDoc, TipoDoc tipoDoc)
    throws ObjetoNoEncontradoException{
        return getTitularDTO(getTitularByDocumento(nroDoc, tipoDoc));
    }

    public static Titular getTitularByDocumento(String nroDoc, TipoDoc tipoDoc)
    throws ObjetoNoEncontradoException{
        TitularDAO dao = new TitularDAO();
        Titular titular = dao.getByDocumento(nroDoc, tipoDoc).orElseThrow(() -> new ObjetoNoEncontradoException());
        return titular;
    }
    
    private static Integer getEdad(LocalDate fechaNacimiento){
        LocalDate hoy = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, hoy);
        return edad.getYears();
    }

    public static Integer getEdadTitular(TitularDTO dto)
    throws ObjetoNoEncontradoException{
        return getEdad(dto.getFechaNacimiento());
    }

    public static Integer getEdadTitular(Titular titular){
        return getEdad(titular.getFechaNacimiento());
    }

    public static Boolean haTenidoLicenciaProf(Titular titular){
        return titular.getLicencias().stream().
                map(l -> l.getClaseLicencia()).
                anyMatch(c -> c.getEsProfesional());
    }

    /*Valida una de las condiciones para tener licencia profesional:
     *  "Para que se les pueda emitir una licencia [profesional], debe habérseles otorgado
     *  una licencia de clase B al menos un año antes"
     * 
     *  Nota: No se ha especificado si tiene que ser vigente hace un año, o si solo debe haber
     *  tenido hace un año o mas. El metodo usa el segundo supuesto.
     */
    public static Boolean tieneClaseBHaceUnAnio(Titular titular){
        List<Licencia> licencias = titular.getLicencias();
        Boolean validar = licencias.stream().
            filter(l -> Period.between(l.getInicioVigencia(), LocalDate.now()).getYears() >= 1).
            map(l -> l.getClaseLicencia()).
            anyMatch(c -> GestorClaseLicencia.esClase(c, 'B'));
        return validar;
    }

    /*Valida una de las condiciones para tener licencia profesional:
     *  "No puede otorgarseles licencia profesional por primera vez a personas de más de 65 años."
     */
    public static Boolean tieneEdadParaLicenciaProf(Titular titular){
        if(haTenidoLicenciaProf(titular)) {
            return true;
        }else{
            return getEdadTitular(titular) <= 65;
        }
    }

    /*Valida ambas condiciones que deben cumplirse para que un titular pueda tener licencia
     * profesional.
     */
    public static Boolean puedeTenerLicenciaProfesional(Titular titular){
        return tieneEdadParaLicenciaProf(titular) && tieneClaseBHaceUnAnio(titular);
    }

    public static Boolean puedeTenerLicenciaProfesional(TitularDTO titularDto)
    throws ObjetoNoEncontradoException{
        return puedeTenerLicenciaProfesional(getTitular(titularDto));
    }

    public static Boolean tieneEdadParaLicencia(Titular titular, ClaseLicencia claseLic){
        return getEdadTitular(titular) >= claseLic.getEdadMinima();
    }

    public static Boolean tieneEdadParaLicencia(TitularDTO titularDto, ClaseLicenciaDTO claseLicDto)
    throws ObjetoNoEncontradoException{
        return tieneEdadParaLicencia(getTitular(titularDto), GestorClaseLicencia.getClaseLicencia(claseLicDto));
    }

    public static Boolean puedeTenerLicencia(Titular titular, ClaseLicencia claseLic){
        if(!tieneEdadParaLicencia(titular, claseLic)){
            return false;
        }

        if(claseLic.getEsProfesional()){
            return puedeTenerLicenciaProfesional(titular);
        }

        return true;
    }

    public static Boolean puedeTenerLicencia(TitularDTO titularDto, ClaseLicenciaDTO claseLicDto)
    throws ObjetoNoEncontradoException{
        return puedeTenerLicencia(getTitular(titularDto), GestorClaseLicencia.getClaseLicencia(claseLicDto));
    }
   
}
