package com.app.grantienda.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.Valores;
import com.app.grantienda.entidades.Variable;
import com.app.grantienda.repositorio.ValoresRepository;

@Service
public class ValoresService {

	@Autowired
	private ValoresRepository vr;
	@Autowired
	private VariableService vs;
	
	@Transactional
	public void guardar (Valores valores) {
		
		vr.save(valores);
	}
	
	public Valores buscarValor(String id) {
		Optional<Valores> valor = vr.findById(id);
		Valores valores = null;
		if(valor.isPresent()) {
			valores= valor.get();
		}
		return valores;
	}
	
	@Transactional
	public void borrarValor(String idValor,String idVariable) {
		Valores val ;
		Variable var = vs.buscarVariable(idVariable);
			List<Valores> valores=var.getValores();
			for (Valores valores2 : valores) {
				 val = buscarValor(idValor);
				if(valores2 == val) {
					valores.remove(valores2);
					vr.delete(val);	
				}
				vs.guardarEntidad(var);
			}
	}
}
