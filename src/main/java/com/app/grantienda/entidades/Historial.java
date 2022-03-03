package com.app.grantienda.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Historial {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombreEmprendimiento;
	private String estadoEmp;
	private String rechazoText;
	private String quienRechazo;
	
	
	@Temporal(TemporalType.DATE)
	private Date alta;
	@Temporal(TemporalType.DATE)
	private Date baja;
	
	
	
	public String getQuienRechazo() {
		return quienRechazo;
	}
	public void setQuienRechazo(String quienRechazo) {
		this.quienRechazo = quienRechazo;
	}
	public String getEstadoEmp() {
		return estadoEmp;
	}
	public void setEstadoEmp(String estadoEmp) {
		this.estadoEmp = estadoEmp;
	}
	public String getRechazoText() {
		return rechazoText;
	}
	public void setRechazoText(String rechazoText) {
		this.rechazoText = rechazoText;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombreEmprendimiento() {
		return nombreEmprendimiento;
	}
	public void setNombreEmprendimiento(String nombreEmprendimiento) {
		this.nombreEmprendimiento = nombreEmprendimiento;
	}
	public Date getAlta() {
		return alta;
	}
	public void setAlta(Date alta) {
		this.alta = alta;
	}
	public Date getBaja() {
		return baja;
	}
	public void setBaja(Date baja) {
		this.baja = baja;
	}
	
	
	
	
}
