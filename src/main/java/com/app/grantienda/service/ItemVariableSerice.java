package com.app.grantienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.ItemVariablePedido;
import com.app.grantienda.repositorio.ItemVariableRepository;

@Service
public class ItemVariableSerice {

	@Autowired
	private ItemVariableRepository ivr;
	
	public void guardarItem(ItemVariablePedido ivp) {
		ivr.save(ivp);
	}
}
