package com.app.grantienda.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class HistorialPedidoEmprendimiento {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date historial;
	private String estado;
	private String nota;
	private String Total;
	
	@OneToMany
	private List<ProductoPedido>productos;
	
	@OneToOne
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getHistorial() {
		return historial;
	}

	public void setHistorial(Date historial) {
		this.historial = historial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	public List<ProductoPedido> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoPedido> productos) {
		this.productos = productos;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
