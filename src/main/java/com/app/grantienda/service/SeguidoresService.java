package com.app.grantienda.service;

import javax.swing.DebugGraphics;
import javax.transaction.Transactional;

import org.apache.maven.wagon.observers.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.Seguidores;
import com.app.grantienda.repositorio.EmprendimientoRepositorio;
import com.app.grantienda.repositorio.SeguidoresRepository;
import com.app.grantienda.repositorio.UserRepository;

import antlr.debug.DebuggingParser;

@Service
public class SeguidoresService {

	
	@Autowired
	private SeguidoresRepository seguidoresRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmprendimientoRepositorio emprendimientoRepository;
	
	
	
	
	
	@Transactional
	public void sumarSeguidor( String idEmprendimiento,String idUser) {
		
			String existe = seguidoresRepository.existeSeguidor(idEmprendimiento,idUser);
			
		 if(existe== "true") {
		
			 Seguidores seguidor = seguidoresRepository.modificarSeguidor(idUser,idEmprendimiento);
		          seguidor.setSeguidor(false);
		          seguidoresRepository.save(seguidor);
		         
		}
		else if(existe == "false") {
			
			Seguidores	seguidor =  seguidoresRepository.modificarSeguidor(idUser,idEmprendimiento);
			          seguidor.setSeguidor(true);
			          seguidoresRepository.save(seguidor);
			         
			}
		  if(existe==null) {
			Seguidores seguidor= new Seguidores();
			seguidor.setUser(userRepository.buscarUserPorId(idUser));
			seguidor.setEmprendimiento(emprendimientoRepository.getById(idEmprendimiento));
			seguidor.setSeguidor(true);
			seguidoresRepository.save(seguidor);
		  }
		   
	}
	
	@Transactional
	public String verificarSeguidor(String idEmprendimiento, String idUser) {
		String existe = seguidoresRepository.existeSeguidor(idEmprendimiento,idUser);    	 
	    	 return existe;	
	    
	}
	
	
	@Transactional
	public String contadorDeSeguidores(String idEmprendimiento) {
		String seguidores = seguidoresRepository.contadorDeSeguidores(idEmprendimiento);
	    	 return seguidores;	
	    
	}
	@Transactional
	public void eliminar(String idEmprendimiento) {
		Seguidores seg = seguidoresRepository.delete(idEmprendimiento);
		
	      String segid = seg.getId();
	      
	      seguidoresRepository.deleteById(segid);
	    
	}

	
	
}


