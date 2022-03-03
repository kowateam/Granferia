package com.app.grantienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.ItemVariablePedido;
import com.app.grantienda.repositorio.ItemProductoPedidoRepository;

@Service
public class ItemProductoService {
	@Autowired
	private ItemProductoPedidoRepository ir;

	public void guardarItem(ItemVariablePedido item) {
		ir.save(item);
		
	}

	
	
}
