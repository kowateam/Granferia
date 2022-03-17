package com.app.grantienda.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Producto {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String tipoDeProducto;
	private String digitalAnalogo;
	private String nombreProductoServicio;
	private String descripcion;
	private String 	oferta;
	private String 	aCotizar;
	private Integer precio;
	private Integer precioOferta;
	private String stock;
	private Integer unidProducto;
	private String tiempoEnvio;
	private String mercadopago;
	private String efectivo;
	private String tarjetas;
	private Date alta;
	
	@ManyToMany
	private List<User>usuarios;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Variable> variables=new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Foto>foto=new ArrayList<>();
	
	@ManyToOne()
	@JoinColumn(name = "categoria_producto_id")
	private CategoriaProducto categoria_producto;
	

	public List<Variable> getVariables() {
		return variables;
	}
	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}
	public List<Foto> getFoto() {
		return foto;
	}
	public void setFoto(List<Foto> foto) {
		this.foto = foto;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombreProductoServicio() {
		return nombreProductoServicio;
	}
	public void setNombreProductoServicio(String nombreProductoServicio) {
		this.nombreProductoServicio = nombreProductoServicio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getPrecio() {
		return precio;
	}
	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
	public String getOferta() {
		return oferta;
	}
	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	public String getTipoDeProducto() {
		return tipoDeProducto;
	}
	public void setTipoDeProducto(String tipoDeProducto) {
		this.tipoDeProducto = tipoDeProducto;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public Integer getUnidProducto() {
		return unidProducto;
	}
	public void setUnidProducto(Integer unidProducto) {
		this.unidProducto = unidProducto;
	}
	public String getTiempoEnvio() {
		return tiempoEnvio;
	}
	public void setTiempoEnvio(String tiempoEnvio) {
		this.tiempoEnvio = tiempoEnvio;
	}
	public String getMercadopago() {
		return mercadopago;
	}
	public void setMercadopago(String mercadopago) {
		this.mercadopago = mercadopago;
	}
	public String getEfectivo() {
		return efectivo;
	}
	public void setEfectivo(String efectivo) {
		this.efectivo = efectivo;
	}
	public String getTarjetas() {
		return tarjetas;
	}
	public void setTarjetas(String tarjetas) {
		this.tarjetas = tarjetas;
	}
	public String getDigitalAnalogo() {
		return digitalAnalogo;
	}
	public void setDigitalAnalogo(String digitalAnalogo) {
		this.digitalAnalogo = digitalAnalogo;
	}
	public String getaCotizar() {
		return aCotizar;
	}
	public void setaCotizar(String aCotizar) {
		this.aCotizar = aCotizar;
	}
	public Integer getPrecioOferta() {
		return precioOferta;
	}
	public void setPrecioOferta(Integer precioOferta) {
		this.precioOferta = precioOferta;
	}

	public CategoriaProducto getCategoria_producto() {
		return categoria_producto;
	}
	public void setCategoria_producto(CategoriaProducto categoria_producto) {
		this.categoria_producto = categoria_producto;
	}
	public List<User> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}
	public Date getAlta() {
		return alta;
	}
	public void setAlta(Date alta) {
		this.alta = alta;
	}
	
	

	
	
}
