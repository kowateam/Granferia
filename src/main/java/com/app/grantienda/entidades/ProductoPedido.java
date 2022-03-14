package com.app.grantienda.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ProductoPedido {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private Integer cantidad;
	private String precio;
	private String variablePedido;
	private String itemPedido;
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
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

	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getVariablePedido() {
		return variablePedido;
	}
	public void setVariablePedido(String variablePedido) {
		this.variablePedido = variablePedido;
	}
	public String getItemPedido() {
		return itemPedido;
	}
	public void setItemPedido(String itemPedido) {
		this.itemPedido = itemPedido;
	}

	



	
}
