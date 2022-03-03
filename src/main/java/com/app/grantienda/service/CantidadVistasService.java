package com.app.grantienda.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.CantidadVistas;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.repositorio.CantidadVistasRepository;

@Service
public class CantidadVistasService {

	@Autowired
	private CantidadVistasRepository cvr;
	@Autowired
	private EmprendimientoService es;
	
	@Transactional
	public void guardarVistasEmprendimiento(String Emprendimiento) {
		
		Emprendimiento emp = es.buscarEmprendimiento(Emprendimiento);
		CantidadVistas	cv = null;
		if(emp.getCantidadVistasEmp() == null) {
		cv = new CantidadVistas();
		cv.setCantidadVistas(1);
		emp.setCantidadVistasEmp(cv);
		cvr.save(cv);

		}else {
			cv = emp.getCantidadVistasEmp();
			Integer count=cv.getCantidadVistas();
			if(count == null ) {
				count= 0;
			}
			count = count+1;
			cv.setCantidadVistas(count);
			emp.setCantidadVistasEmp(cv);
			cvr.save(cv);
		}
		es.guardarEmprendimiento(emp);
	}
	
	@Transactional
	public CantidadVistas CrearCantidadVistas() {
		CantidadVistas cv = new CantidadVistas();
		cv.setCantidadVistas(0);
		cv.setPrincipal(true);
		cvr.save(cv);
		return cv;
	}
	
	@Transactional
	public void guardarVistasGeneral() {
		
		CantidadVistas cv = buscarCantidadVistasPrincipal();
		Integer count=cv.getCantidadVistas();
		count = count+1;
		cv.setCantidadVistas(count);
		cvr.save(cv);
}

	public CantidadVistas buscarCantidadVistasPrincipal() {
		CantidadVistas cvd = cvr.buscarPrincipal();
		return cvd;
	}

	@Transactional
	private CantidadVistas buscarCantidadVistas(String id) {
		Optional<CantidadVistas> cv = cvr.findById(id);	
		CantidadVistas cvd = null;
		if(cv.isPresent()) {
			
			cvd = cv.get();
		}
		return cvd;
	}

	public void guardarCantidadVistas(CantidadVistas cv) {
		cvr.save(cv);
	}
}