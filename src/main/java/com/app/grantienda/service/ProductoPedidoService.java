package com.app.grantienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.ProductoPedido;
import com.app.grantienda.repositorio.ProductoPedidoRepository;

@Service
public class ProductoPedidoService {
	
	@Autowired
	private ProductoPedidoRepository ppr;

	public void guardarProductoPedido(ProductoPedido pp) {
		ppr.save(pp);
		
	}
	

}
