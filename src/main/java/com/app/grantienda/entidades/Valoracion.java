package com.app.grantienda.entidades;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class Valoracion {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String tiempoV;
	private String servicioV;
	private String productoV;
	private String comentario = null;
	
	public Valoracion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Valoracion(String id, String tiempoV, String servicioV, String productoV, String comentario,
			Producto producto, User user) {
		super();
		this.id = id;
		this.tiempoV = tiempoV;
		this.servicioV = servicioV;
		this.productoV = productoV;
		this.comentario = comentario;
		this.producto = producto;
		this.user = user;
	}

	
	@OneToOne
	private Producto producto;
	
	@OneToOne
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTiempoV() {
		return tiempoV;
	}

	public void setTiempoV(String tiempoV) {
		this.tiempoV = tiempoV;
	}

	public String getServicioV() {
		return servicioV;
	}

	public void setServicioV(String servicioV) {
		this.servicioV = servicioV;
	}

	public String getProductoV() {
		return productoV;
	}

	public void setProductoV(String productoV) {
		this.productoV = productoV;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
