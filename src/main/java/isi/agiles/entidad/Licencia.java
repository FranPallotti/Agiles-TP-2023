package isi.agiles.entidad;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import java.util.*;

@Entity
@Table(name = "licencia")
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_licencia")
    private Long idLicencia;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "inicio_vigencia", nullable = false)
    private LocalDate inicioVigencia;

    @Column(name = "fin_vigencia", nullable = false)
    private LocalDate finVigencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoLicencia estado;

    

    @Column(name = "costo", nullable = false)
    private Float costo;
        
    /*Asociaciones */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clase_licencia",
                nullable = false,
                referencedColumnName = "clase",
                foreignKey = @ForeignKey(name ="fk_licencia_clase_licencia"))
    private ClaseLicencia claseLic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "titular",
                nullable = false,
                referencedColumnName = "id_titular",
                foreignKey = @ForeignKey(name = "fk_licencia_titular"))
    private Titular titular;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "realizo_tramite",
                nullable = false,
                referencedColumnName = "id_usuario",
                foreignKey = @ForeignKey(name = "fk_licencia_usuario"))
    private Usuario realizoTramite;

    @OneToMany(fetch = FetchType.LAZY,
               cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
               mappedBy = "licencia")
    private List<CopiaLicencia> copias;

    @PostLoad
    private void modificarEstadoSegunFecha(){
        if(this.estado.equals(EstadoLicencia.EXPIRADA)){
            return;
        }
        LocalDate hoy = LocalDate.now();
        /*Si la fecha de hoy es posterior o igual a la fecha de fin de vigencia.
        El estado deberia ser "EXPIRADA"*/
        if(hoy.isAfter(this.finVigencia.minusDays(1))){
            this.estado = EstadoLicencia.EXPIRADA;
        }
        return;
    }

    /*Getters y setters */

    public Long getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(Long idLicencia) {
        this.idLicencia = idLicencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(LocalDate inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public LocalDate getFinVigencia() {
        return finVigencia;
    }

    public void setFinVigencia(LocalDate finVigencia) {
        this.finVigencia = finVigencia;
    }

    public ClaseLicencia getClaseLicencia() {
        return claseLic;
    }

    public void setClaseLicencia(ClaseLicencia claseLic) {
        this.claseLic = claseLic;
    }

    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public Usuario getRealizoTramite() {
        return realizoTramite;
    }

    public void setRealizoTramite(Usuario realizoTramite) {
        this.realizoTramite = realizoTramite;
    }

    public EstadoLicencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoLicencia estado) {
        this.estado = estado;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }
    


    /*Lei que no se recomienda usar IDs auto generados para equals() y hashCode()
     * por ahora lo dejo asi, pero buscar alguna mejor alternativa como una @NaturalId
     */

    public List<CopiaLicencia> getCopias() {
        return copias;
    }

    public void setCopias(List<CopiaLicencia> copias) {
        this.copias = copias;
    }
    public void agregarCopia(CopiaLicencia c){
        this.copias.add(c);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idLicencia == null) ? 0 : idLicencia.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Licencia other = (Licencia) obj;
        if (idLicencia == null) {
            if (other.idLicencia != null)
                return false;
        } else if (!idLicencia.equals(other.idLicencia))
            return false;
        return true;
    }

    
}
