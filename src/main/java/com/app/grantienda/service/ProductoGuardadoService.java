package com.app.grantienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.ProductoGuardado;
import com.app.grantienda.repositorio.ProductoGuardadoRepository;

@Service
public class ProductoGuardadoService {

	@Autowired
	private ProductoGuardadoRepository pgr;
	public void guardarProducto(ProductoGuardado pg) {
		pgr.save(pg);
		
	}
	public ProductoGuardado buscarProducto(String idProducto) {
		ProductoGuardado pg = pgr.buscarProductod(idProducto); 
		return pg;
	}
	public void borrarProductoGuardado(ProductoGuardado pg) {
		pgr.borrarRelacionesProductoGuardados(pg.getId());
		pgr.delete(pg);
	}
	public List<ProductoGuardado> buscarProductosPorUsuario(String id) {
		System.out.println("el id del usuario es "+id);
		List<ProductoGuardado>guardados=pgr.buscarProductoPorUsuario(id);
		for (ProductoGuardado productoGuardado : guardados) {
			
			System.out.println("los productos guardados en producto guardado :"+productoGuardado);
		}
		return guardados;
	}
	
	public void borrarPedidosEmprendimiento(String id) {
	 pgr.borrarRelacionesProductoGuardados(id);
	}

}
