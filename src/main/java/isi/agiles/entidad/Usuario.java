package isi.agiles.entidad;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    private String nombre;
    
    private String apellido;
    
    private LocalDate fechaNacimiento;
    
    private String mail;
    
    private TipoSexo sexo;
    
    private TipoDoc tipoDoc;
    
    private String nmoDoc;
    
    private String nombreUsuario;

    private TipoRol rol;

    public TipoRol getRol() {
        return rol;
    }
    public void setRol(TipoRol rol) {
        this.rol = rol;
    }
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
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }
    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }
    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
    public String getNmoDoc() {
        return nmoDoc;
    }
    public void setNmoDoc(String nmoDoc) {
        this.nmoDoc = nmoDoc;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
}
