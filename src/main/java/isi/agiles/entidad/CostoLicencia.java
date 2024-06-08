package isi.agiles.entidad;




import jakarta.persistence.*;

@Entity
@Table(name = "costo_licencia",uniqueConstraints = {@UniqueConstraint(columnNames={"clase","duracion"})})
public class CostoLicencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCostoClase")
    private Integer id;


    @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
    @JoinColumn(name = "clase", nullable = false, referencedColumnName = "clase",foreignKey= @ForeignKey(name="FK_id_clase_en_costo"))
    private ClaseLicencia clase;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @Column(name= "costo", nullable = false)
    private Float costo;


    public Float getCosto() {
        return costo;
    }
    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Integer getId() {
        return id;
    }
    public void setClase(ClaseLicencia clase) {
        this.clase = clase;
    }
    public ClaseLicencia getClase() {
        return clase;
    }
    public Integer getDuracion() {
        return duracion;
    }
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}
