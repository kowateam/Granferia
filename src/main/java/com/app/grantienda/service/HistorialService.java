package com.app.grantienda.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.Historial;
import com.app.grantienda.entidades.User;
import com.app.grantienda.repositorio.HistorialRepository;

@Service 
public class HistorialService {

	@Autowired
	public HistorialRepository hr;
	@Autowired
	public UsuarioService us;
	
	@Transactional
	public void guardarHistorial(String nombreEmprendimiento,String estadoEmp,String idUser) {
		
		User usuario = us.buscarPorId(idUser);
		Historial historial = new Historial();
		historial.setNombreEmprendimiento(nombreEmprendimiento);
		historial.setEstadoEmp(estadoEmp);
		historial.setAlta(new Date());
		historial.setQuienRechazo(usuario.getMail());
		
		hr.save(historial);
	}
	
	@Transactional
	public void guardarHistorialRechazo(String nombreEmprendimiento,String estadoEmp,String rechazoText,String idUser) {
		
		User usuario = us.buscarPorId(idUser);
		Historial historial = new Historial();
		historial.setNombreEmprendimiento(nombreEmprendimiento);
		historial.setEstadoEmp(estadoEmp);
		historial.setRechazoText(rechazoText);	
		historial.setQuienRechazo(usuario.getMail());
		historial.setBaja(new Date());
		
		hr.save(historial);
	}
	
	@Transactional
	public List<Historial> traerHistorial() {
		
		List<Historial>historial = hr.findAll();
		
		return historial;
	}
	
	
}
