// Generated with g9.

package cl.rt.schl.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
/**
 * 
 * @author Francisco
 *
 */
@Entity(name="link")
public class Link implements Serializable {

 
    @Column(name="rut_t")
    private String rutT;
    private LocalDate fecha;
    @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;
    private String estado;
    private String link;

    /** Default constructor. */
    public Link() {
        super();
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
     * Access method for fecha.
     *
     * @return the current value of fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Setter method for fecha.
     *
     * @param aFecha the new value for fecha
     */
    public void setFecha(LocalDate aFecha) {
        fecha = aFecha;
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(Long aId) {
        id = aId;
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

    /**
     * Access method for link.
     *
     * @return the current value of link
     */
    public String getLink() {
        return link;
    }

    /**
     * Setter method for link.
     *
     * @param aLink the new value for link
     */
    public void setLink(String aLink) {
        link = aLink;
    }

  
    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Link |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

  

}
