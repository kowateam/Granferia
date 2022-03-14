package com.app.grantienda.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.subCategorias;
import com.app.grantienda.repositorio.CategoriasRepository;




@Service
public class CategoriaService {

	@Autowired
	private CategoriasRepository Categoriasrepo;
	

	public List<subCategorias>listaSub(String categoria){
	
		return Categoriasrepo.buscarUsuarioPorsubCategorias(categoria);
 
	}
	
	
	@Transactional
	public void subirCategoria(String categoria2,String sub) {
		subCategorias categoria = new subCategorias();
		categoria.setCategoria(categoria2);
		categoria.setSub(sub);
		
		Categoriasrepo.save(categoria);
		
		
	}
	
}
