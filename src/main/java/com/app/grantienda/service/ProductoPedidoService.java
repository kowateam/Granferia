package com.app.grantienda.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.ProductoPedido;
import com.app.grantienda.repositorio.ProductoPedidoRepository;
import com.app.grantienda.repositorio.ValoracionRepository;

@Service
public class ProductoPedidoService {
	
	@Autowired
	private ProductoPedidoRepository ppr;
	
	@Autowired
	private ValoracionRepository valoracionRepository;
	
	
	@Transactional
	public void guardarProductoPedido(ProductoPedido pp) {
		ppr.save(pp);
		
	}
	@Transactional
	public String contadorDePedido(String idEmprendimiento) {
		
			String pedidos=ppr.contadorDepedidos(idEmprendimiento);
		
		return pedidos;
	}
	
	

}
