package com.app.grantienda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.CategoriaProducto;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.repositorio.CategoriaProductoRepository;

@Service
public class CategoriaProductoService {
	
	@Autowired
	private CategoriaProductoRepository cpr;
	@Autowired
	private ProductoService ps;
	@Autowired
	private EmprendimientoService es;

	
	@Transactional
	public CategoriaProducto subirCategoria(String nombre,String id) {
		Emprendimiento emp = es.buscarEmprendimiento(id);
		CategoriaProducto cp = new CategoriaProducto();
		cp.setNombre(nombre);
		cp.setEmprendimiento(emp);
	
		cpr.save(cp);
		
		
		return cp;
	}
	public void primero(String idCategoria) {
		CategoriaProducto cp = buscarCategoria(idCategoria);
		cp.setPrimero(true);
	}
	private CategoriaProducto buscarCategoria(String idCategoria) {
		CategoriaProducto cate = cpr.getById(idCategoria);
		return cate;
	}
	@Transactional
	public void crearCategoria(String nombre) {
		CategoriaProducto cp = new CategoriaProducto();
		cp.setNombre(nombre);
		cpr.save(cp);
	}
	
	@Transactional
	public void subirProductoAcategoria(String idProducto,String idCategoria) {
		
		System.out.println("el id del producto " + idProducto);
		Producto producto = ps.buscarProducto(idProducto);
		System.out.println(producto.getNombreProductoServicio()+" "+producto.getCategoria().getNombre());
		System.out.println("el producto con el id " +producto.getNombreProductoServicio());
		
		System.out.println("el nombre de la categoria que tiene el producto: "+producto.getCategoria().getNombre());
		CategoriaProducto cpcambiar= cpr.buscarCategoriaDelProducto(idProducto);
		System.out.println("el nombre de la categoria del producto " + cpcambiar.getNombre());
		
		for (Producto i : cpcambiar.getProductos()) {
			System.out.println("los productos de la categoria "+i.getNombreProductoServicio() );
		}
		CategoriaProducto categoriaproducto=null;
		List<Producto>prod=cpcambiar.getProductos();
		prod.remove(producto);
		cpcambiar.setProductos(prod);
		cpr.save(cpcambiar);
		System.out.println("los productos"+prod);
		Optional<CategoriaProducto> cp = cpr.findById(idCategoria);
		if(cp.isPresent()) {
			categoriaproducto=cp.get();		
			System.out.println("la categoria q puede ser null " +categoriaproducto.getNombre());
		}
		
		producto.setCategoria(categoriaproducto);
		ps.guardarProductoyFlush(producto);
		System.out.println(producto.getCategoria().getNombre());
		List<Producto>productos1=categoriaproducto.getProductos();
		for (Producto producto2 : productos1) {
			System.out.println("el producto es :" +producto2.getNombreProductoServicio() + "la categoria es:"+producto2.getCategoria().getNombre());
		}
		productos1.add(producto);
		categoriaproducto.setProductos(productos1);
		cpr.save(categoriaproducto);
	
	}
	
	@Transactional
	public void modificarCategoria(String id,String nombre) {
		Optional<CategoriaProducto> cp=cpr.findById(id);
		if(cp.isPresent()) {
			CategoriaProducto catego= cp.get();
			catego.setNombre(nombre);
			cpr.save(catego);
		}
		
	}

	public List<CategoriaProducto> buscarCategorias(String id) {
        List<CategoriaProducto>categorias=cpr.buscarCategoriasSegunEmprendimiento(id);
        return categorias;
    }
	
	@Transactional
	public void BorrarCategorias(String id) {
		CategoriaProducto cp = null;
		System.out.print("en borrar categorias ");
		 List<CategoriaProducto>categorias=buscarCategorias(id);
		System.out.println("las categorias : "+categorias);
		 for (CategoriaProducto xxx : categorias) {
			 System.out.println("la categoria es: " +xxx.getNombre());
			 cp= xxx;
			// borrarCategoriasRelaciones(cp.getId());
			 cpr.delete(cp);
		}
		 
	}
	@Transactional
	public void borrarCategoriasRelacionesEmprendimiento(String id) {
		
		 List<CategoriaProducto>categorias=buscarCategorias(id);
		 
		 for (CategoriaProducto categoriaProducto : categorias) {
			System.out.println("el id de la categoria " +categoriaProducto.getId());
			 cpr.borrarRelacionesProducto(categoriaProducto.getId());
			 cpr.delete(categoriaProducto);
		}
		 borrarRelacionesEmprendimiento(id);
	}
	
	@Transactional
	public void borrarCategoriasRelaciones(String id) {
		//cpr.borrarRelaciones(id);
	
	}
	
	@Transactional
	public void borrarRelacionesEmprendimiento(String id) {
		cpr.borrarRelacionesEmprendimiento(id);
		
	}
	
	@Transactional
	public void subirCategoriaGeneral(String idEmprendimiento, String id,String idProducto) {
		Emprendimiento emp = es.buscarEmprendimiento(idEmprendimiento);
		Producto prod = ps.buscarProducto(idProducto);
		CategoriaProducto cp = cpr.buscarPrincipal(idEmprendimiento);
		cp.setEmprendimiento(emp);
		prod.setCategoria(cp);
		List<Producto>productos=cp.getProductos();
		productos.add(prod);
		cp.setProductos(productos);
		ps.guardarProducto(prod);
		cpr.save(cp);
		

	}
	//Metodo para bajar de categorias
	@Transactional
	public void crearNuevaYCargarLosProductos(String idEmprendimiento) {
		CategoriaProducto cp = new CategoriaProducto();
		Emprendimiento emp = es.buscarEmprendimiento(idEmprendimiento);
		cp.setNombre("Todos");
		cp.setEmprendimiento(emp);
		cp.setPrimero(true);
		List<Producto>productos=emp.getProductos();
		List<Producto>productos2=new ArrayList<>();
		
		cpr.save(cp);
		for (Producto producto2 : productos) {
			producto2.setCategoria(cp);
			productos2.add(producto2);
		}
		cp.setProductos(productos2);
		
		es.guardarEmprendimiento(emp);
	}
	
	@Transactional
	public void borrarCategoriaSola(String id) {
		CategoriaProducto cp = buscarCategoria(id);
		cpr.delete(cp);
		
	}
	
	public void modificarCategoriaProductos(String idProducto) {
		CategoriaProducto CategoriaDelProducto = buscarCategoriaDelProducto(idProducto);
		System.out.println("la categoria del producto es: "+CategoriaDelProducto);
		Producto prod = ps.buscarProducto(idProducto);
		List<Producto>productoss= CategoriaDelProducto.getProductos();
		Producto pro = null;
		for (Producto producto : productoss) {
			if(producto.getId().equals(prod.getId())) {
				pro= producto;
			}
		}
		productoss.remove(pro);
		CategoriaDelProducto.setProductos(productoss);
		cpr.save(CategoriaDelProducto);
		
	}
	public CategoriaProducto buscarCategoriaDelProducto(String idProducto) {
		CategoriaProducto cp =cpr.buscarCategoriaDelProducto(idProducto);
		return cp;
	}
	public void guardar(CategoriaProducto nueva) {
		cpr.save(nueva);
		
	}


		
}
