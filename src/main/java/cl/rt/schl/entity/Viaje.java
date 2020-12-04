// Generated with g9.

package cl.rt.schl.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity(name="viaje")
public class Viaje implements Serializable {

  

    @Id
    @GeneratedValue
    private Long idViaje;
    @Column(name="rut_t")
    private String rutT;

    @Column(name="dia")
    private LocalDate dia;
    @Column(name="fecha_inicio")
    private OffsetDateTime fechaInicio;
    @Column(name="fecha_termino")
    private OffsetDateTime fechaTermino;
    private String estado;
    
    @OneToMany(mappedBy="viaje")
    private Set<DetalleViaje> detalleViaje;

    /** Default constructor. */
    public Viaje() {
        super();
    }

    /**
     * Access method for idViaje.
     *
     * @return the current value of idViaje
     */
    public Long getIdViaje() {
        return idViaje;
    }

    /**
     * Setter method for idViaje.
     *
     * @param aIdViaje the new value for idViaje
     */
    public void setIdViaje(Long aIdViaje) {
        idViaje = aIdViaje;
    }

    /**
     * Access method for rutT.
     *
     * @return the current value of rutT
     */
    public String getRutT() {
        return rutT;
    }

    /**
     * Setter method for rutT.
     *
     * @param aRutT the new value for rutT
     */
    public void setRutT(String aRutT) {
        rutT = aRutT;
    }

    /**
     * Access method for fechaInicio.
     *
     * @return the current value of fechaInicio
     */
    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Setter method for fechaInicio.
     *
     * @param aFechaInicio the new value for fechaInicio
     */
    public void setFechaInicio(OffsetDateTime aFechaInicio) {
        fechaInicio = aFechaInicio;
    }

    /**
     * Access method for fechaTermino.
     *
     * @return the current value of fechaTermino
     */
    public OffsetDateTime getFechaTermino() {
        return fechaTermino;
    }

    /**
     * Setter method for fechaTermino.
     *
     * @param aFechaTermino the new value for fechaTermino
     */
    public void setFechaTermino(OffsetDateTime aFechaTermino) {
        fechaTermino = aFechaTermino;
    }

    /**
     * Access method for estado.
     *
     * @return the current value of estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Setter method for estado.
     *
     * @param aEstado the new value for estado
     */
    public void setEstado(String aEstado) {
        estado = aEstado;
    }
    public LocalDate getDia() {
        return dia;
    }

    /**
     * Setter method for estado.
     *
     * @param aEstado the new value for estado
     */
    public void setDia(LocalDate aEstado) {
        dia = aEstado;
    }

    /**
     * Access method for detalleViaje.
     *
     * @return the current value of detalleViaje
     */
    public Set<DetalleViaje> getDetalleViaje() {
        return detalleViaje;
    }

    /**
     * Setter method for detalleViaje.
     *
     * @param aDetalleViaje the new value for detalleViaje
     */
    public void setDetalleViaje(Set<DetalleViaje> aDetalleViaje) {
        detalleViaje = aDetalleViaje;
    }

    
 

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Viaje |");
        sb.append(" idViaje=").append(getIdViaje());
        sb.append("]");
        return sb.toString();
    }

  

}
