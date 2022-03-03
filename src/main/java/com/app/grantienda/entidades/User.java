package com.app.grantienda.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.app.grantienda.enums.Provider;
import com.app.grantienda.enums.Roles;




@Entity
public class User {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String mail;
	private String password;
	private String celular;
	private String regNombre;
	
	private Boolean mostrarCrearEmp;
	private Boolean userActivo;
	@OneToMany
	private List<Emprendimiento>emprendimientos;
	
	@OneToMany
	private List<ProductoGuardado>productosGuardados;

	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@Column(name="codigoDeVerificacion", length = 64)
	private String codigoDeVerificacion;
	
	@Column(name="codigoDeContrasena", length = 64)
	private String codigoDeContrasena;
	
	private Boolean crearEmpNotifi;
	
	private String avatar;
	
	@Enumerated(EnumType.STRING)
	private Provider provider;

	public Provider getProvider() {
		return provider;
	}


	public void setProvider(Provider provider) {
		this.provider = provider;
	}


	public String getCodigoDeContrasena() {
		return codigoDeContrasena;
	}


	public void setCodigoDeContrasena(String codigoDeContrasena) {
		this.codigoDeContrasena = codigoDeContrasena;
	}


	public Boolean getUserActivo() {
		return userActivo;
	}


	public void setUserActivo(Boolean usuarioActivo) {
		this.userActivo = usuarioActivo;
	}


	public String getCodigoDeVerificacion() {
		return codigoDeVerificacion;
	}


	public void setCodigoDeVerificacion(String codigoDeVerificacion) {
		this.codigoDeVerificacion = codigoDeVerificacion;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRegNombre() {
		return regNombre;
	}


	public void setRegNombre(String regNombre) {
		this.regNombre = regNombre;
	}


	public Date getAlta() {
		return alta;
	}


	public void setAlta(Date alta) {
		this.alta = alta;
	}


	public Roles getRole() {
		return role;
	}


	public void setRole(Roles role) {
		this.role = role;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", mail=" + mail + ", password=" + password + ", regNombre=" + regNombre
				+ ", userActivo=" + userActivo + ", alta=" + alta + ", role=" + role + ", codigoDeVerificacion="
				+ codigoDeVerificacion + ", codigoDeContrasena=" + codigoDeContrasena + ", avatar=" + avatar
				+ ", provider=" + provider + "]";
	}


	public Boolean getCrearEmpNotifi() {
		return crearEmpNotifi;
	}


	public void setCrearEmpNotifi(Boolean crearEmpNotifi) {
		this.crearEmpNotifi = crearEmpNotifi;
	}


	public String getCelular() {
		return celular;
	}


	public void setCelular(String celular) {
		this.celular = celular;
	}


	public Boolean getMostrarCrearEmp() {
		return mostrarCrearEmp;
	}


	public void setMostrarCrearEmp(Boolean mostrarCrearEmp) {
		this.mostrarCrearEmp = mostrarCrearEmp;
	}


	public List<ProductoGuardado> getProductosGuardados() {
		return productosGuardados;
	}


	public void setProductosGuardados(List<ProductoGuardado> productosGuardados) {
		this.productosGuardados = productosGuardados;
	}


	public List<Emprendimiento> getEmprendimientos() {
		return emprendimientos;
	}


	public void setEmprendimientos(List<Emprendimiento> emprendimientos) {
		this.emprendimientos = emprendimientos;
	}


	
	
	
}
