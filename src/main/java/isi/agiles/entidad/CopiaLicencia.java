package isi.agiles.entidad;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class CopiaLicencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_copia")
    private Long idCopia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "es_copia",
                nullable = false,
                referencedColumnName = "id_licencia",
                foreignKey = @ForeignKey(name = "fk_licencia_copia"))
    private Licencia licencia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emitida_por",
                nullable = false,
                referencedColumnName = "id_usuario",
                foreignKey = @ForeignKey(name = "fk_copia_usuario"))
    private Usuario emisor;


    @Column(nullable = false)
    private LocalDate fechaEmision;


    public Licencia getLicencia() {
        return licencia;
    }
    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }
    public Usuario getEmisor() {
        return emisor;
    }
    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }
    public LocalDate getFechaEmision() {
        return fechaEmision;
    }
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    

    

}
