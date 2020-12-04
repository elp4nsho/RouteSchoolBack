
package cl.rt.schl.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
public class Transportista{

    @Id
    @Column(unique=true, nullable=false)
    private String rut;
    private String uid;
    private String nombre;
    private String email;
    private String direccion;

    /** Default constructor. */
    public Transportista() {
        
    }

    /**
     * Access method for rut.
     *
     * @return the current value of rut
     */
    public String getRut() {
        return rut;
    }

    /**
     * Setter method for rut.
     *
     * @param aRut the new value for rut
     */
    public void setRut(String aRut) {
        rut = aRut;
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
     * Access method for email.
     *
     * @return the current value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email.
     *
     * @param aEmail the new value for email
     */
    public void setEmail(String aEmail) {
        email = aEmail;
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
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
 

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
    	   StringBuffer sb = new StringBuffer("{");
    	   sb.append(" \"rut\": \"").append(getRut()+"\",");
    	   sb.append(" \"nombre\": \"").append(getNombre()+"\",");
    	   sb.append(" \"email\": \"").append(getEmail()+"\",");
    	   sb.append(" \"uid\": \"").append(getUid()+"\",");

    	   sb.append(" \"direccion\": \"").append(getDireccion()+"\"");
           sb.append("}");
           return sb.toString();
    }

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

   

}
