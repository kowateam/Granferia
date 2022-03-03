package com.app.grantienda.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class CantidadVistas {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private Integer cantidadVistas;
	private Boolean principal;
	
	@OneToMany
	private List<User>usuarios;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCantidadVistas() {
		return cantidadVistas;
	}
	public void setCantidadVistas(Integer cantidadVistas) {
		this.cantidadVistas = cantidadVistas;
	}
	public Boolean getPrincipal() {
		return principal;
	}
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}
	public List<User> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
