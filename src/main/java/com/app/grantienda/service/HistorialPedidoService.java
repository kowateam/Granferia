package com.app.grantienda.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.HistorialPedidoEmprendimiento;
import com.app.grantienda.entidades.Pedido;
import com.app.grantienda.entidades.ProductoPedido;
import com.app.grantienda.repositorio.HistorialPedidoEmprendedorRepository;

@Service
public class HistorialPedidoService {
	
	@Autowired
	private HistorialPedidoEmprendedorRepository hpr;

	
	public void guardarHistorial(Pedido pedido,String estadoPedido,String nota) {
		HistorialPedidoEmprendimiento hist = new HistorialPedidoEmprendimiento();
		hist.setHistorial(new Date());
		hist.setNota(pedido.getNota());
		hist.setEstado(estadoPedido);
		hist.setTotal(pedido.getTotal());
		List<ProductoPedido>productos= new ArrayList<>();
		List<ProductoPedido>productos1=pedido.getProductos();
		for (ProductoPedido productoPedido : productos1) {
			productos.add(productoPedido);
		}
		hist.setProductos(productos);
		hist.setNota(nota);
		hist.setUser(pedido.getUser());
		
		hpr.save(hist);
		
		
		
	}
}
