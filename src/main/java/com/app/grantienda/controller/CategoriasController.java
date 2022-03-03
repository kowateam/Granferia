package com.app.grantienda.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.subCategorias;
import com.app.grantienda.service.CategoriaProductoService;
import com.app.grantienda.service.CategoriaService;
import com.app.grantienda.service.EmprendimientoService;
import com.app.grantienda.service.UsuarioService;


@RestController
@RequestMapping("/sub")
public class CategoriasController {
	@Autowired
	private CategoriaService sc;
	@Autowired
	private CategoriaProductoService cps;
	@Autowired
	private EmprendimientoService es;
	@Autowired
	private UsuarioService us;
	
	
	 @GetMapping(path="/sub/{categoria}", produces = "application/json")
	public List<subCategorias> buscarCcategoria(@PathVariable String categoria){
		
		return (List<subCategorias>) sc.listaSub(categoria);
	}
	
	@GetMapping(path="/cambiar")
	public void cambiarLlamadoPerfil(HttpSession session) {
		session.removeAttribute("volverperfil");
	}
	@GetMapping("/dispweb/{web}")
	public Boolean disponibilidadWeb(@PathVariable String web) {	
	List<Emprendimiento>emp	=es.buscarPorNombrePagina(web);
		if(emp.isEmpty()) {
			return true;
		}
		return false;
		
	}
	@GetMapping(path="/guardarcrearemp/{id}")
	public void guardarcrearemp(@PathVariable String id) {
		us.crearEmpNotifi(id);
	}
	
	
}
