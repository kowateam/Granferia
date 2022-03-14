package com.app.grantienda.entidades;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.app.grantienda.enums.Provincias;



@Entity
public class Direccion {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String direccionVisibilidad;
	private String dirCalle;
	private String dirNum;
	private String dirPiso;
	private String dirDepto;
	
	@Enumerated(EnumType.STRING)
	private Provincias provincia;
	
	private String localidadEmp;
	private String codPostal;
	private String direcciondetrabajo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDireccionVisibilidad() {
		return direccionVisibilidad;
	}
	public void setDireccionVisibilidad(String direccionVisibilidad) {
		this.direccionVisibilidad = direccionVisibilidad;
	}
	public String getDirCalle() {
		return dirCalle;
	}
	public void setDirCalle(String dirCalle) {
		this.dirCalle = dirCalle;
	}
	public String getDirNum() {
		return dirNum;
	}
	public void setDirNum(String dirNum) {
		this.dirNum = dirNum;
	}
	public String getDirPiso() {
		return dirPiso;
	}
	public void setDirPiso(String dirPiso) {
		this.dirPiso = dirPiso;
	}
	public String getDirDepto() {
		return dirDepto;
	}
	public void setDirDepto(String dirDepto) {
		this.dirDepto = dirDepto;
	}
	public String getLocalidadEmp() {
		return localidadEmp;
	}
	public void setLocalidadEmp(String localidadEmp) {
		this.localidadEmp = localidadEmp;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getDirecciondetrabajo() {
		return direcciondetrabajo;
	}
	public void setDirecciondetrabajo(String direcciondetrabajo) {
		this.direcciondetrabajo = direcciondetrabajo;
	}
	public Provincias getProvincia() {
		return provincia;
	}
	public void setProvinciaEmp(Provincias provincia) {
		this.provincia = provincia;
	}
	
}
