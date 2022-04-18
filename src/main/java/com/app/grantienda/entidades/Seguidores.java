package com.app.grantienda.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Seguidores {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private boolean seguidor;
	
	
	
	public Seguidores() {
		super();
		
	}
	
	@ManyToOne
	private Emprendimiento emprendimiento;
	
	public Emprendimiento getEmprendimiento() {
		return emprendimiento;
	}
	public void setEmprendimiento(Emprendimiento emprendimiento) {
		this.emprendimiento = emprendimiento;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne
	public User user;
	
	public Seguidores(String id, boolean seguidor) {
		super();
		this.id = id;
		this.seguidor = seguidor;
	}
	@Override
	public String toString() {
		return "Seguidores [seguidor=" + seguidor + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isSeguidor() {
		return seguidor;
	}
	public void setSeguidor(boolean seguidor) {
		this.seguidor = seguidor;
	}


	
}
