// Generated with g9.

package cl.rt.schl.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="nino")
public class Nino implements Serializable {

  

    @Id
    @Column(name="rut_n", unique=true, nullable=false)
    private String rutN;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int edad;
    private String curso;

    private String reglas;
    private String direccion;
    @ManyToOne(optional=false)
    @JoinColumn(name="id_colegio", nullable=false)
    private Colegio colegio;
    @JsonIgnore(value = true)
    @ManyToOne(optional=false)
    @JoinColumn(name="rut_ap", nullable=false)
    private Apoderado apoderado;
    @JsonIgnore
    @OneToMany(mappedBy="nino")
    private Set<DetalleViaje> detalleViaje;
    @JsonIgnore
    @OneToMany(mappedBy="nino")
    private Set<Asistencia> asistencia;

    /** Default constructor. */
    public Nino() {
        super();
    }

    /**
     * Access method for rutN.
     *
     * @return the current value of rutN
     */
    public String getRutN() {
        return rutN;
    }

    /**
     * Setter method for rutN.
     *
     * @param aRutN the new value for rutN
     */
    public void setRutN(String aRutN) {
        rutN = aRutN;
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
     * Access method for reglas.
     *
     * @return the current value of reglas
     */
    public String getReglas() {
        return reglas;
    }

    /**
     * Setter method for reglas.
     *
     * @param aReglas the new value for reglas
     */
    public void setReglas(String aReglas) {
        reglas = aReglas;
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
     * Access method for colegio.
     *
     * @return the current value of colegio
     */
    public Colegio getColegio() {
        return colegio;
    }

    /**
     * Setter method for colegio.
     *
     * @param aColegio the new value for colegio
     */
    public void setColegio(Colegio aColegio) {
        colegio = aColegio;
    }

    /**
     * Access method for apoderado.
     *
     * @return the current value of apoderado
     */
    public Apoderado getApoderado() {
        return apoderado;
    }

    /**
     * Setter method for apoderado.
     *
     * @param aApoderado the new value for apoderado
     */
    public void setApoderado(Apoderado aApoderado) {
        apoderado = aApoderado;
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
     * Access method for asistencia.
     *
     * @return the current value of asistencia
     */
    public Set<Asistencia> getAsistencia() {
        return asistencia;
    }

    /**
     * Setter method for asistencia.
     *
     * @param aAsistencia the new value for asistencia
     */
    public void setAsistencia(Set<Asistencia> aAsistencia) {
        asistencia = aAsistencia;
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
        if (getRutN() == null) {
            i = 0;
        } else {
            i = getRutN().hashCode();
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
        StringBuffer sb = new StringBuffer("[Nino |");
        sb.append(" rutN=").append(getRutN());
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
        ret.put("rutN", getRutN());
        return ret;
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}
