package cl.rt.schl.entity;

public class Credencial {

	private String name;
	private String clave;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Credencial(String name, String clave) {
		super();
		this.name = name;
		this.clave = clave;
	}
	public Credencial() {
	}
	
}
