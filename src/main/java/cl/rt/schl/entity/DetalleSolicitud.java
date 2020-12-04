// Generated with g9.

package cl.rt.schl.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity(name="detalle_solicitud")
public class DetalleSolicitud implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

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

    private String doc;
    @Id
    @Column(unique=true, nullable=false)
    private String id;
    @ManyToOne(optional=false)
    @JoinColumn(name="id_solicitud", nullable=false)
    private Solicitud solicitud;

    /** Default constructor. */
    public DetalleSolicitud() {
        super();
    }

    /**
     * Access method for doc.
     *
     * @return the current value of doc
     */
    public String getDoc() {
        return doc;
    }

    /**
     * Setter method for doc.
     *
     * @param aDoc the new value for doc
     */
    public void setDoc(String aDoc) {
        doc = aDoc;
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(String aId) {
        id = aId;
    }

    /**
     * Access method for solicitud.
     *
     * @return the current value of solicitud
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * Setter method for solicitud.
     *
     * @param aSolicitud the new value for solicitud
     */
    public void setSolicitud(Solicitud aSolicitud) {
        solicitud = aSolicitud;
    }

    /**
     * Compares the key for this instance with another DetalleSolicitud.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class DetalleSolicitud and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof DetalleSolicitud)) {
            return false;
        }
        DetalleSolicitud that = (DetalleSolicitud) other;
        Object myId = this.getId();
        Object yourId = that.getId();
        if (myId==null ? yourId!=null : !myId.equals(yourId)) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another DetalleSolicitud.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DetalleSolicitud)) return false;
        return this.equalKeys(other) && ((DetalleSolicitud)other).equalKeys(this);
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
        if (getId() == null) {
            i = 0;
        } else {
            i = getId().hashCode();
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
        StringBuffer sb = new StringBuffer("[DetalleSolicitud |");
        sb.append(" id=").append(getId());
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
        ret.put("id", getId());
        return ret;
    }

}
