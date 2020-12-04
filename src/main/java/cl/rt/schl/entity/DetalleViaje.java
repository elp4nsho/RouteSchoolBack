// Generated with g9.

package cl.rt.schl.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "detalle_viaje")
public class DetalleViaje implements Serializable {

	private String asistio;
	@Id
	@GeneratedValue
	private Long id;
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_viaje", nullable = false)
	private Viaje viaje;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "rut_n")
	private Nino nino;

	/** Default constructor. */
	public DetalleViaje() {
		super();
	}

	/**
	 * Access method for asistio.
	 *
	 * @return the current value of asistio
	 */
	public String getAsistio() {
		return asistio;
	}

	/**
	 * Setter method for asistio.
	 *
	 * @param aAsistio the new value for asistio
	 */
	public void setAsistio(String aAsistio) {
		asistio = aAsistio;
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
	 * Access method for viaje.
	 *
	 * @return the current value of viaje
	 */
	public Viaje getViaje() {
		return viaje;
	}

	/**
	 * Setter method for viaje.
	 *
	 * @param aViaje the new value for viaje
	 */
	public void setViaje(Viaje aViaje) {
		viaje = aViaje;
	}

	/**
	 * Access method for nino.
	 *
	 * @return the current value of nino
	 */
	public Nino getNino() {
		return nino;
	}

	/**
	 * Setter method for nino.
	 *
	 * @param aNino the new value for nino
	 */
	public void setNino(Nino aNino) {
		nino = aNino;
	}

	

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[DetalleViaje |");
		sb.append(" id=").append(getId());
		sb.append("]");
		return sb.toString();
	}

	

}
