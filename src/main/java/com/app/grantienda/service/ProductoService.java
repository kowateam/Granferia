package com.app.grantienda.service;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.CategoriaProducto;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.repositorio.ProductoRepositorio;

@Service
public class ProductoService {


	@Autowired
	private ProductoRepositorio pr;
	@Autowired
	private CategoriaProductoService cps;
	@Autowired
	private ProductoGuardadoService pgs;
	@Autowired
	private EmprendimientoService es;
	
	@Transactional
	public Producto subirProducto(String idEmprendimiento,String productoServicio,String digitalAnalogo,String nombreProducto,String descProducto,String esOferta,String aCotizar,Integer precioProducto,Integer precioOferta,String stock,Integer unidProducto,String tiempoenvio,String efectivo,String tarjetas,String mercadopago){
		
		Producto producto = new Producto();
		
		producto.setTipoDeProducto(productoServicio);
		producto.setDigitalAnalogo(digitalAnalogo);
		producto.setNombreProductoServicio(nombreProducto);
		producto.setDescripcion(descProducto);
		producto.setOferta(esOferta);
		producto.setaCotizar(aCotizar);
		producto.setPrecio(precioProducto);
		producto.setPrecioOferta(precioOferta);
		producto.setStock(stock);
		producto.setUnidProducto(unidProducto);
		producto.setTiempoEnvio(tiempoenvio);
		producto.setEfectivo(efectivo);
		producto.setMercadopago(mercadopago);
		producto.setTarjetas(tarjetas);
		producto.setAlta(new Date());
		pr.save(producto);
		
		Emprendimiento emp = es.buscarEmprendimiento(idEmprendimiento);
		List<Producto>productos=emp.getProductos();
		productos.add(producto);
		es.guardarEmprendimiento(emp);
		List<CategoriaProducto> cp = cps.buscarCategorias(idEmprendimiento);
		CategoriaProducto cp1 = null;
		for (CategoriaProducto categoriaProducto : cp) {
			if(categoriaProducto.getPrimero() != null) {
				
				if (categoriaProducto.getPrimero() == true) {
					cp1 = categoriaProducto;
				}
			}
		}
		List<Producto>productos2=cp1.getProductos();
		productos2.add(producto);
		cp1.setProductos(productos2);
		cps.guardar(cp1);
		cps.subirCategoriaGeneral(idEmprendimiento, cp1.getId(),producto.getId());
		return producto;
		
	}
	
	@Transactional
	public Producto buscarProducto(String id) {
		
		Producto product = pr.buscarProductoPorId(id);
		return product;
	}
	
	@Transactional
	public void guardarProducto(Producto producto){
		
		pr.save(producto);
	}
	@Transactional
	public void guardarProductoyFlush(Producto producto){
		
		pr.saveAndFlush(producto);
	}


	public List<Producto> buscarProductoPorEmprendimiento(String id) {
		List<Producto>productos = pr.buscarProductosPorId(id);
		return productos;
	}

	public Producto modificarProducto(String idProducto,String idEmprendimiento, String productoServicio, String digitalAnalogo,
			String nombreProducto, String descProducto, String esOferta, String aCotizar, Integer precioProducto,
			Integer precioOferta, String stock, Integer unidProducto, String stockTime, String efectivo, String tarjeta,
			String mercadopago) {
		
		Producto producto = buscarProducto(idProducto);
		
		producto.setTipoDeProducto(productoServicio);
		producto.setDigitalAnalogo(digitalAnalogo);
		producto.setNombreProductoServicio(nombreProducto);
		producto.setDescripcion(descProducto);
		producto.setOferta(esOferta);
		producto.setaCotizar(aCotizar);
		producto.setPrecio(precioProducto);
		producto.setPrecioOferta(precioOferta);
		producto.setStock(stock);
		producto.setUnidProducto(unidProducto);
		producto.setTiempoEnvio(stockTime);
		producto.setEfectivo(efectivo);
		producto.setMercadopago(mercadopago);
		producto.setTarjetas(tarjeta);
		pr.save(producto);
		return producto;
	}
	
	@Transactional
	public void borrarProducto(String idProducto,String idEmprendimiento) {
		Producto producto = buscarProducto(idProducto);
		
		
		es.modificarProductosEmprendimiento(idEmprendimiento,idProducto);
		cps.modificarCategoriaProductos(idProducto);
		pr.delete(producto);
	
	}

	private void borrarRelacionesProducto(String id) {
		pr.borrarRelacionesProducto(id);
		
	}

	@Transactional
	public void borrarProductoSinFoto(String id) {
		Emprendimiento emp = es.buscarEmprendimiento(id);
		List<Producto>productos=emp.getProductos();	
		for (Producto producto2 : productos) {
			
			if(producto2.getFoto().isEmpty()) {
				pr.borrarProductoEnEmprendimiento(producto2.getId());
				pr.delete(producto2);
			
			}
			
		}
	}

	public void borrarEmprendimiento(String id) {
		pr.borrarRelacionesProducto(id);
		pgs.borrarPedidosEmprendimiento(id);
		List<Producto>productos= pr.buscarProductosPorEmprendimiento(id);
		if(!productos.isEmpty() || productos != null) {
			for (Producto producto : productos) {
				borrarProducto(producto.getId(), id);
			}
		}
	}

}

