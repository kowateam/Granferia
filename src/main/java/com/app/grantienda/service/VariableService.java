package com.app.grantienda.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.Producto;
import com.app.grantienda.entidades.Valores;
import com.app.grantienda.entidades.Variable;
import com.app.grantienda.repositorio.ValoresRepository;
import com.app.grantienda.repositorio.VariableRepository;

@Service
public class VariableService {

	@Autowired
	private VariableRepository vr;
	@Autowired
	private ValoresRepository vre;
	@Autowired
	private ProductoService ps;
	
	public Variable buscarVariable(String id) {
		Variable var = null;
		Optional<Variable> variable = vr.findById(id);
		if(variable.isPresent()) {
			var = variable.get();
		}
		return var;
	}
	@Transactional
	public Variable guardar (String nombre,String id) {
		Variable variable = new Variable();
		variable.setNombre(nombre);
		vr.save(variable);
		Producto producto = ps.buscarProducto(id);
		List<Variable>variables = producto.getVariables();
		variables.add(variable);
		producto.setVariables(variables);
		ps.guardarProducto(producto);
		return variable;
	}
	@Transactional
	public Valores guardarVariableValores(String idVariable, String nombre) {
		Valores valores = new Valores();
		valores.setNombre(nombre);
		Optional<Variable> variable = vr.findById(idVariable);
		Variable variab = null;
		if(variable.isPresent()) {
			 variab = variable.get();
		}
		List<Valores>valor=variab.getValores();
		valor.add(valores);
		vre.save(valores);
		 vr.save(variab);
		 return valores;
	}
	
	@Transactional
	public void eliminarVariable(String idVariable) {
		List<Valores> valor = vre.buscarPorVariable(idVariable);
		Variable variable=vr.buscarVariablePorId(idVariable);
		vr.borrarEnPedidoVariable(idVariable);
		vr.borrarEnPedidoVariable2(idVariable);
		vr.delete(variable);	
		for (Valores valores : valor) {
			
			vre.delete(valores);
		}
	}
	
	@Transactional
	public void eliminarVariableSola(String idValor,String idVariable) {
		List<Valores> valor = vre.buscarPorVariable(idVariable);
		for (Valores valores : valor) {
			if(valores.getId() == idValor) {
				vre.delete(valores);
			}
		}
	}
	@Transactional
	public void guardarEntidad(Variable var) {
		vr.save(var);
		
	}
}
