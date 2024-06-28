package isi.agiles.entidad;

import java.time.LocalDate;


import jakarta.persistence.*;

@Entity
@Table(name = "usuario",uniqueConstraints = {
    @UniqueConstraint(columnNames={"nroDoc","tipoDoc"})
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre",nullable = false)
    private String nombre;
    
    @Column(name = "apellido", nullable = false)
    private String apellido;
    
    @Column(name = "fechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column(name = "email", nullable = false)
    private String mail;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private TipoSexo sexo;
    
    @Enumerated(EnumType.STRING)
    @Column(name="tipoDoc", nullable = false)
    private TipoDoc tipoDoc;
    
    @Column(name= "nroDoc", nullable = false)
    private String numDoc;
    
    @Column(name= "nombreUsuario", unique = true)
    private String nombreUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
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
    public String getNumDoc() {
        return numDoc;
    }
    public void setNumDoc(String nmoDoc) {
        this.numDoc = nmoDoc;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
}
