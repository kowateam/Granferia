package com.app.grantienda.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.Direccion;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.enums.Provincias;
import com.app.grantienda.repositorio.DireccionRepository;
import com.app.grantienda.repositorio.EmprendimientoRepositorio;

@Service
public class DireccionService {

	
	@Autowired
	private DireccionRepository dr;
	@Autowired
	private EmprendimientoService es;
	
	@Transactional
	public void registrarDireccion( String id,String direccionVisibilidad,String dirNum,String dirCalle, String dirPiso, String dirDepto,Provincias provincia,String localidadEmp,
			String codPostal) {
		
		
		validar(direccionVisibilidad,dirCalle,dirPiso, dirDepto,
				 localidadEmp);
    	
    	
    	Direccion dir = new Direccion();
    	
		dir.setDireccionVisibilidad(direccionVisibilidad);
		dir.setDirCalle(dirCalle);
		dir.setDirNum(dirNum);
		dir.setDirPiso(dirPiso);
		dir.setDirDepto(dirDepto);
		dir.setProvinciaEmp(provincia);
		dir.setLocalidadEmp(localidadEmp);
		dir.setCodPostal(codPostal);
		
		
		dr.save(dir);
	}
		
	private void validar(String direccionVisibilidad,String dirCalle,String dirNum,
			String localidadEmp,String codPostal) {
		
		
		if(direccionVisibilidad.isEmpty()) {
			throw new Error("Debe elegir si quiere que su direccion sea visible.");
		}
		if(dirCalle.isEmpty()) {
			throw new Error("La calle no debe venir Vacia.");
		}
		if(dirNum.isEmpty()) {
			throw new Error("Debe elegir un numero.");
		}
		if(localidadEmp.isEmpty()) {
			throw new Error("Debe elegir una localidad.");
		}
		if(codPostal.isEmpty()) {
			throw new Error("Debe elegir el codigo postal.");
		}
		
		
		
	}
	
	@Transactional
	public void modificardireccion(String id,String direccionVisibilidad,String dirCalle,String dirNum, String dirPiso, String dirDepto,Provincias provincia,String localidadEmp,
			String codPostal) {
		//validar(direccionVisibilidad,dirCalle,dirPiso, dirDepto,
			//	 localidadEmp);

		Emprendimiento emp = es.buscarEmprendimiento(id);
		Direccion dir = emp.getDireccion();
		dir.setDireccionVisibilidad(direccionVisibilidad);
		dir.setDirNum(dirNum);
		dir.setDirCalle(dirCalle);
		dir.setDirPiso(dirPiso);
		dir.setDirDepto(dirDepto);
		dir.setProvinciaEmp(provincia);
		dir.setProvinciaEmp(provincia);
		dir.setCodPostal(codPostal);
		
		dr.save(dir);
		emp.setDireccion(dir);
		es.guardarEmprendimiento(emp);
		
	}

	public void guardarDireccion(Direccion dir) {
		dr.save(dir);
		
	}
		
}
