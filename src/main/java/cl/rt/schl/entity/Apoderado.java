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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="apoderado")
public class Apoderado implements Serializable {


    public Apoderado(String rutT, String rutAp, String nombre, String email, String direccion, Set<Nino> nino) {
        this.rutT = rutT;
        this.rutAp = rutAp;
        this.nombre = nombre;

        this.email = email;
        this.direccion = direccion;
        this.nino = nino;
    }

    
    @Column(name="rut_t", nullable=false)
    private String rutT;
    @Id
    @Column(name="rut_ap", nullable=false)
    @Size(min = 8)
    private String rutAp;
    private String nombre;
    private String apellidoPaterno;

    private String apellidoMaterno;

    private String telefono;

    private String clave;
    private String email;

    private String direccion;
    @OneToMany(mappedBy="apoderado")
    private Set<Nino> nino;

    /** Default constructor. */
    public Apoderado() {
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
     * Access method for rutAp.
     *
     * @return the current value of rutAp
     */
    public String getRutAp() {
        return rutAp;
    }

    /**
     * Setter method for rutAp.
     *
     * @param aRutAp the new value for rutAp
     */
    public void setRutAp(String aRutAp) {
        rutAp = aRutAp;
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
        StringBuffer sb = new StringBuffer("[Apoderado |");
        sb.append(" rutAp=").append(getRutAp());
        sb.append("]");
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
        ret.put("rutAp", getRutAp());
        return ret;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
