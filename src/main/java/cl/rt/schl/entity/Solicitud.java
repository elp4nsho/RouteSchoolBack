// Generated with g9.

package cl.rt.schl.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity(name="solicitud")
public class Solicitud implements Serializable {

    /** Primary key. */
    protected static final String PK = "idSolicitud";

    /**
     * The optimistic lock. Available via standard bean get/set operations.
     */
    @Version
    @Column(name="LOCK_FLAG")
    private Integer lockFlag;

    /**
     * Access method for the lockFlag property.
     *
     * @return the current value of the lockFlag property
     */
    public Integer getLockFlag() {
        return lockFlag;
    }

    /**
     * Sets the value of the lockFlag property.
     *
     * @param aLockFlag the new value of the lockFlag property
     */
    public void setLockFlag(Integer aLockFlag) {
        lockFlag = aLockFlag;
    }

    @Id
    @Column(name="id_solicitud", unique=true, nullable=false)
    private String idSolicitud;
    @Column(name="rut_t", nullable=false)
    private String rutT;
    private String estado;
    @OneToMany(mappedBy="solicitud")
    private Set<DetalleSolicitud> detalleSolicitud;

    /** Default constructor. */
    public Solicitud() {
        super();
    }

    /**
     * Access method for idSolicitud.
     *
     * @return the current value of idSolicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Setter method for idSolicitud.
     *
     * @param aIdSolicitud the new value for idSolicitud
     */
    public void setIdSolicitud(String aIdSolicitud) {
        idSolicitud = aIdSolicitud;
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
     * Access method for detalleSolicitud.
     *
     * @return the current value of detalleSolicitud
     */
    public Set<DetalleSolicitud> getDetalleSolicitud() {
        return detalleSolicitud;
    }

    /**
     * Setter method for detalleSolicitud.
     *
     * @param aDetalleSolicitud the new value for detalleSolicitud
     */
    public void setDetalleSolicitud(Set<DetalleSolicitud> aDetalleSolicitud) {
        detalleSolicitud = aDetalleSolicitud;
    }

    /**
     * Compares the key for this instance with another Solicitud.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Solicitud and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Solicitud)) {
            return false;
        }
        Solicitud that = (Solicitud) other;
        Object myIdSolicitud = this.getIdSolicitud();
        Object yourIdSolicitud = that.getIdSolicitud();
        if (myIdSolicitud==null ? yourIdSolicitud!=null : !myIdSolicitud.equals(yourIdSolicitud)) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Solicitud.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Solicitud)) return false;
        return this.equalKeys(other) && ((Solicitud)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        if (getIdSolicitud() == null) {
            i = 0;
        } else {
            i = getIdSolicitud().hashCode();
        }
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Solicitud |");
        sb.append(" idSolicitud=").append(getIdSolicitud());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("idSolicitud", getIdSolicitud());
        return ret;
    }

}
