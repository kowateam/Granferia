package com.app.grantienda.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ProductoPedidoHistorial {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private String cantidad;
	private String precio;
	private String variableHistorial;
	private String itemHistorial;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getVariableHistorial() {
		return variableHistorial;
	}
	public void setVariableHistorial(String variableHistorial) {
		this.variableHistorial = variableHistorial;
	}
	public String getItemHistorial() {
		return itemHistorial;
	}
	public void setItemHistorial(String itemHistorial) {
		this.itemHistorial = itemHistorial;
	}
	
}
