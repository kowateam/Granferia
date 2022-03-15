package com.app.grantienda.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.app.grantienda.enums.Categorias;


@Entity
public class Emprendimiento {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombreRazonSocial;
	private String dniCuit;
	private String regEmpNombre;
	private String regEmpDescripcion;
	private String regEmpDir;
	private String empActividad;
	private String empTel;
	private Boolean esWhatsapp;
	private Boolean aceptado;
	private String membresia;
	private String empLayout;
	private String empTheme;
	
	@Enumerated(EnumType.STRING)
	private Categorias categoria;
	
	private String subCatergoria;
	private String marcaRegistrada;
	private String tieneLogo;
	private String estadoEmp;
	private String reachazoText;
	private String instagram;
	private Boolean haceEnvios;
	
	private Integer contadorPedidos;
	
	@OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
	private CantidadVistas cantidadVistasEmp;

	@OneToOne(cascade=CascadeType.REMOVE,orphanRemoval = true)
	private Foto foto;
	
	@OneToMany(cascade=CascadeType.REMOVE,orphanRemoval = true)
	private List<Producto> productos = new ArrayList<>();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;
	
	@OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private Direccion direccion;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CategoriaProducto> categorias;

	public String getMembresia() {
		return membresia;
	}
	public void setMembresia(String membresia) {
		this.membresia = membresia;
	}
	public Boolean getEsWhatsapp() {
		return esWhatsapp;
	}
	public void setEsWhatsapp(Boolean esWhatsapp) {
		this.esWhatsapp = esWhatsapp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getEstadoEmp() {
		return estadoEmp;
	}
	public void setEstadoEmp(String estadoEmp) {
		this.estadoEmp = estadoEmp;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegEmpNombre() {
		return regEmpNombre;
	}
	public void setRegEmpNombre(String regEmpNombre) {
		this.regEmpNombre = regEmpNombre;
	}
	public String getRegEmpDescripcion() {
		return regEmpDescripcion;
	}
	public void setRegEmpDescripcion(String regEmpDescripcion) {
		this.regEmpDescripcion = regEmpDescripcion;
	}
	public String getRegEmpDir() {
		return regEmpDir;
	}
	public void setRegEmpDir(String regEmpDir) {
		this.regEmpDir = regEmpDir;
	}
	public String getEmpTel() {
		return empTel;
	}
	public void setEmpTel(String empTel) {
		this.empTel = empTel;
	}
	public Categorias getCategoria() {
		return categoria;
	}
	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}
	public String getSubCatergoria() {
		return subCatergoria;
	}
	public void setSubCatergoria(String subCatergoria) {
		this.subCatergoria = subCatergoria;
	}
	public String getMarcaRegistrada() {
		return marcaRegistrada;
	}
	public void setMarcaRegistrada(String marcaRegistrada) {
		this.marcaRegistrada = marcaRegistrada;
	}
	public String getTieneLogo() {
		return tieneLogo;
	}
	public void setTieneLogo(String tieneLogo) {
		this.tieneLogo = tieneLogo;
	}
	public Foto getFoto() {
		return foto;
	}
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	public Date getAlta() {
		return alta;
	}
	public void setAlta(Date alta) {
		this.alta = alta;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	public Boolean getAceptado() {
		return aceptado;
	}
	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
	}
	public String getEmpActividad() {
		return empActividad;
	}
	public void setEmpActividad(String empActividad) {
		this.empActividad = empActividad;
	}
	public String getReachazoText() {
		return reachazoText;
	}
	public void setReachazoText(String reachazoText) {
		this.reachazoText = reachazoText;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getDniCuit() {
		return dniCuit;
	}
	public void setDniCuit(String dniCuit) {
		this.dniCuit = dniCuit;
	}
	public Boolean getHaceEnvios() {
		return haceEnvios;
	}
	public void setHaceEnvios(Boolean haceEnvios) {
		this.haceEnvios = haceEnvios;
	}
	public CantidadVistas getCantidadVistasEmp() {
		return cantidadVistasEmp;
	}
	public void setCantidadVistasEmp(CantidadVistas cantidadVistasEmp) {
		this.cantidadVistasEmp = cantidadVistasEmp;
	}
	public String getEmpLayout() {
		return empLayout;
	}
	public void setEmpLayout(String empLayout) {
		this.empLayout = empLayout;
	}
	public String getEmpTheme() {
		return empTheme;
	}
	public void setEmpTheme(String empTheme) {
		this.empTheme = empTheme;
	}
	public Integer getContadorPedidos() {
		return contadorPedidos;
	}
	public void setContadorPedidos(Integer contadorPedidos) {
		this.contadorPedidos = contadorPedidos;
	}
	
	
	

}

