package com.app.grantienda.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class CoordenadasEmp {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String latitud;
	private String longitud;
	private String emp;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public CoordenadasEmp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CoordenadasEmp(String id, String latitud, String longitud, String emp) {
		super();
		this.id = id;
		this.latitud = latitud;
		this.longitud = longitud;
		this.emp = emp;
	}
	
	
}
