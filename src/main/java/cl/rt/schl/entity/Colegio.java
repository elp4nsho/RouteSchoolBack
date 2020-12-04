// Generated with g9.

package cl.rt.schl.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="colegio")
public class Colegio implements Serializable {

  
    @Id
    @GeneratedValue
    private Long idColegio;
    @Column(name="rut_t", nullable=false)
    @JsonIgnore

    private String rutT;
    private String nombre;
    private String direccion;
    @JsonIgnore(value = true)
    @OneToMany(mappedBy="colegio")
    private Set<Nino> nino;

    /** Default constructor. */
    public Colegio() {
        super();
    }

    public Colegio(Long id,String nombre) {
        super();

    }

    
    
    /**
     * Access method for idColegio.
     *
     * @return the current value of idColegio
     */
    public Long getIdColegio() {
        return idColegio;
    }

    /**
     * Setter method for idColegio.
     *
     * @param aIdColegio the new value for idColegio
     */
    public void setIdColegio(Long aIdColegio) {
        idColegio = aIdColegio;
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
     * Access method for nombre.
     *
     * @return the current value of nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter method for nombre.
     *
     * @param aNombre the new value for nombre
     */
    public void setNombre(String aNombre) {
        nombre = aNombre;
    }

    /**
     * Access method for direccion.
     *
     * @return the current value of direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Setter method for direccion.
     *
     * @param aDireccion the new value for direccion
     */
    public void setDireccion(String aDireccion) {
        direccion = aDireccion;
    }

    /**
     * Access method for nino.
     *
     * @return the current value of nino
     */
    public Set<Nino> getNino() {
        return nino;
    }

    /**
     * Setter method for nino.
     *
     * @param aNino the new value for nino
     */
    public void setNino(Set<Nino> aNino) {
        nino = aNino;
    }


    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{");
        sb.append(" \"idColegio\": \"").append(getIdColegio()+"\",");
        sb.append(" \"rutTransportista\": \"").append(getRutT()+"\",");
        sb.append(" \"nombre\": \"").append(getNombre()+"\",");
        sb.append(" \"direccion\": \"").append(getDireccion()+"\"");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    @JsonIgnore

    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("idColegio", getIdColegio());
        return ret;
    }

}
