package isi.agiles.logica;

import java.util.*;

import java.time.LocalDate;
import java.time.Period;

import isi.agiles.dao.TitularDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.entidad.EstadoLicencia;
import isi.agiles.entidad.Licencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.Titular;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.excepcion.TitularYaCargadoException;

public class GestorTitular {

    private TitularDAO titularDao = new TitularDAO();
    private GestorClaseLicencia gestorClaseLic = new GestorClaseLicencia();

    public TitularDTO getTitularDTO(Titular titular){
        TitularDTO dto = new TitularDTO();
        dto.setIdTitular(titular.getIdTitular());
        dto.setNombre(titular.getNombre());
        dto.setApellido(titular.getApellido());
        dto.setNroDoc(titular.getNroDoc());
        dto.setTipoDoc(titular.getTipoDoc());
        dto.setFechaNacimiento(titular.getFechaNacimiento());
        dto.setFactorRH(titular.getFactorRH());
        dto.setEsDonante(titular.getEsDonante());
        dto.setGrupoSanguineo(titular.getGrupoSanguineo());
        dto.setSexo(titular.getSexo());
        dto.setDireccion(titular.getDireccion());
        dto.setClaseSol(titular.getClaseSol());
        return dto;
    }

    public Titular getTitular(TitularDTO dto)
    throws ObjetoNoEncontradoException{
        Titular titular = titularDao.getById(dto.getIdTitular()).orElseThrow(() -> new ObjetoNoEncontradoException());
        return titular;
    }

    public TitularDTO getTitularDTOByDocumento(String nroDoc, TipoDoc tipoDoc)
    throws ObjetoNoEncontradoException{
        return this.getTitularDTO(this.getTitularByDocumento(nroDoc, tipoDoc));
    }

    public Titular getTitularByDocumento(String nroDoc, TipoDoc tipoDoc)
    throws ObjetoNoEncontradoException{
        Titular titular = titularDao.getByDocumento(nroDoc, tipoDoc).orElseThrow(() -> new ObjetoNoEncontradoException());
        return titular;
    }

    private Integer getEdad(LocalDate fechaNacimiento){
        LocalDate hoy = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, hoy);
        return edad.getYears();
    }

    public Integer getEdadTitular(TitularDTO dto)
    throws ObjetoNoEncontradoException{
        return this.getEdad(dto.getFechaNacimiento());
    }

    public void persistir(TitularDTO titular) throws TitularYaCargadoException{
        if(!this.dniUnico(titular.getNroDoc(), titular.getTipoDoc())){
            throw new TitularYaCargadoException();
        } 
        Titular entidad = new Titular();
        entidad.setApellido(titular.getApellido());
        entidad.setClaseSol(titular.getClaseSol());
        entidad.setDireccion(titular.getDireccion());
        entidad.setEsDonante(titular.getEsDonante());
        entidad.setFactorRH(titular.getFactorRH());
        entidad.setFechaNacimiento(titular.getFechaNacimiento());
        entidad.setSexo(titular.getSexo());
        entidad.setGrupoSanguineo(titular.getGrupoSanguineo());
        entidad.setNombre(titular.getNombre());
        entidad.setNroDoc(titular.getNroDoc());
        entidad.setTipoDoc(titular.getTipoDoc());
        TitularDAO t = new TitularDAO();
        t.saveInstance(entidad);
    }

    private boolean dniUnico(String documento, TipoDoc tipoDoc) {
        Boolean esUnico = false;
        TitularDAO t = new TitularDAO();
        if(t.getByDocumento(documento, tipoDoc).isEmpty()){
            esUnico = true;
        }
        return esUnico;
    }

    public Integer getEdadTitular(Titular titular){
        return this.getEdad(titular.getFechaNacimiento());
    }

    public Boolean haTenidoLicenciaProf(Titular titular){
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
    public Boolean tieneClaseBHaceUnAnio(Titular titular){
        List<Licencia> licencias = titular.getLicencias();
        Boolean validar = licencias.stream().
            filter(l -> Period.between(l.getInicioVigencia(), LocalDate.now()).getYears() >= 1).
            map(l -> l.getClaseLicencia()).
            anyMatch(c -> gestorClaseLic.esClase(c, 'B'));
        return validar;
    }

    /*Valida una de las condiciones para tener licencia profesional:
     *  "No puede otorgarseles licencia profesional por primera vez a personas de más de 65 años."
     */
    public Boolean tieneEdadParaLicenciaProf(Titular titular){
        if(this.haTenidoLicenciaProf(titular)) {
            return true;
        }else{
            return this.getEdadTitular(titular) <= 65;
        }
    }

    /*Valida ambas condiciones que deben cumplirse para que un titular pueda tener licencia
     * profesional.
     */
    public Boolean puedeTenerLicenciaProfesional(Titular titular){
        return this.tieneEdadParaLicenciaProf(titular) && this.tieneClaseBHaceUnAnio(titular);
    }

    public Boolean puedeTenerLicenciaProfesional(TitularDTO titularDto)
    throws ObjetoNoEncontradoException{
        return this.puedeTenerLicenciaProfesional(getTitular(titularDto));
    }

    public Boolean tieneEdadParaLicencia(Titular titular, ClaseLicencia claseLic){
        return this.getEdadTitular(titular) >= claseLic.getEdadMinima();
    }

    public Boolean tieneEdadParaLicencia(TitularDTO titularDto, ClaseLicenciaDTO claseLicDto)
    throws ObjetoNoEncontradoException{
        return this.tieneEdadParaLicencia(this.getTitular(titularDto), gestorClaseLic.getClaseLicencia(claseLicDto));
    }

    public Boolean puedeTenerLicencia(Titular titular, ClaseLicencia claseLic){
        if(!this.tieneEdadParaLicencia(titular, claseLic)){
            return false;
        }
        if(claseLic.getEsProfesional()){
            return this.puedeTenerLicenciaProfesional(titular);
        }
        /*Si tiene edad para licencia, y licencia no es profesional (valdrian condiciones adicionales)
         * entonces puede obtener la licencia.
         */
        return true;
    }

    public Boolean puedeTenerLicencia(TitularDTO titularDto, ClaseLicenciaDTO claseLicDto)
    throws ObjetoNoEncontradoException{
        return puedeTenerLicencia(this.getTitular(titularDto), gestorClaseLic.getClaseLicencia(claseLicDto));
    }

    public Integer aniosDeVigenciaLicencia(Titular titular){
        Integer edadTitular = this.getEdadTitular(titular);
        if(edadTitular <= 20 /*Menor de 21*/){
            if(titular.getLicencias().isEmpty()){
                return 1;
            }
            //Si ya tiene licencias:
            return 3;
        }
        if(edadTitular <= 46){
            return 5;
        }
        if (edadTitular <= 60) {
            return 4;
        }
        if (edadTitular <= 70) {
            return 3;
        }
        //Si mayor de 70 años:
        return 1;
    }

    public void actualizarTitular(TitularDTO titular) throws ObjetoNoEncontradoException{
        Titular titularBD = titularDao.getById(titular.getIdTitular()).orElseThrow(() -> new ObjetoNoEncontradoException());
        if(!titular.getNombre().equals(titularBD.getNombre())){
            titularBD.setNombre(titular.getNombre());
        }
        if(!titular.getApellido().equals(titularBD.getApellido())){
            titularBD.setApellido((titular.getApellido()));
        }
        if(!titular.getNroDoc().equals(titularBD.getNroDoc())){
            titularBD.setNroDoc((titular.getNroDoc()));
        }
        if(!titular.getTipoDoc().equals(titularBD.getTipoDoc())){
            titularBD.setTipoDoc((titular.getTipoDoc()));
        }
        if(!titular.getFechaNacimiento().equals(titularBD.getFechaNacimiento())){
            titularBD.setFechaNacimiento((titular.getFechaNacimiento()));
        }
        if(!titular.getFactorRH().equals(titularBD.getFactorRH())){
            titularBD.setFactorRH((titular.getFactorRH()));
        }
        if(!titular.getEsDonante().equals(titularBD.getEsDonante())){
            titularBD.setEsDonante((titular.getEsDonante()));
        }
        if(!titular.getGrupoSanguineo().equals(titularBD.getGrupoSanguineo())){
            titularBD.setGrupoSanguineo((titular.getGrupoSanguineo()));
        }
        if(!titular.getSexo().equals(titularBD.getSexo())){
            titularBD.setSexo((titular.getSexo()));
        }
        if(!titular.getDireccion().equals(titularBD.getDireccion())){
            titularBD.setDireccion((titular.getDireccion()));
        }
        if(titularBD.getClaseSol() == null ||!titular.getClaseSol().equals(titularBD.getClaseSol())){
            titularBD.setClaseSol((titular.getClaseSol()));
        }
        titularDao.updateInstance(titularBD);
    }

    public boolean tieneLicenciasVigentes(TitularDTO titularDto, ClaseLicenciaDTO claseLic)
    throws ObjetoNoEncontradoException{
        Titular titular = this.getTitular(titularDto);
        List<Licencia> licenciasTitular = titular.getLicencias();
        Character clase = claseLic.getClase();
        return licenciasTitular.stream().
            filter(l -> l.getEstado().equals(EstadoLicencia.VIGENTE)).
            anyMatch(l -> gestorClaseLic.esClase(l.getClaseLicencia(),clase));
    }
}