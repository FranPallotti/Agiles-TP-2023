package isi.agiles.dto;

import java.time.LocalDate;

import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoRol;
import isi.agiles.entidad.TipoSexo;

public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNaciemiento;
    private String mail;
    private TipoSexo sexo;
    private TipoDoc tipoDoc;
    private String numDoc;
    private String nombreUsuario;
    private TipoRol rol;

    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public LocalDate getFechaNaciemiento() {
        return fechaNaciemiento;
    }
    public void setFechaNacimiento(LocalDate fechaNaciemiento) {
        this.fechaNaciemiento = fechaNaciemiento;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public TipoSexo getSexo() {
        return sexo;
    }
    public void setSexo(TipoSexo s) {
        this.sexo = s;
    }
    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }
    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
    public String getNumDoc() {
        return numDoc;
    }
    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public TipoRol getRol() {
        return rol;
    }
    public void setRol(TipoRol rol) {
        this.rol = rol;
    }

    


}
