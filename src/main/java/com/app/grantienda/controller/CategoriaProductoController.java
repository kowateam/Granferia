package com.app.grantienda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.grantienda.entidades.Valores;
import com.app.grantienda.entidades.Variable;
import com.app.grantienda.service.CategoriaProductoService;
import com.app.grantienda.service.CookiesService;
import com.app.grantienda.service.EmprendimientoService;
import com.app.grantienda.service.UsuarioService;
import com.app.grantienda.service.ValoresService;
import com.app.grantienda.service.VariableService;

@RestController
@RequestMapping("/categoria")
public class CategoriaProductoController {

	@Autowired
	private CategoriaProductoService cps;
	@Autowired
	private CookiesService cs;
	@Autowired
	private VariableService vs;
	@Autowired
	private ValoresService vals;
	@Autowired
	private UsuarioService us;

	@GetMapping(path = "/subir/{categoria}/{id}")
	public void cargarCategoria(@PathVariable("categoria") String categoria, @PathVariable("id") String id,HttpSession session) {
		try {
			cps.subirCategoria(categoria, id);
			session.setAttribute("volverCargaVariable", "volver");
		} catch (Exception e) {
			throw new Error("No se pudo subir la categoria");
		}

	}

	@GetMapping(path = "/cargar/{idproducto}/{idcategoria}")
	public void cargarCategoriaProducto(@PathVariable("idproducto") String idPproducto, String idCategoria) {

		try {
			cps.subirProductoAcategoria(idPproducto, idCategoria);
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	
	@GetMapping(path = "/editar/{id}/{nombre}")
	public void modificarCategoria(@PathVariable("id") String id,@PathVariable("nombre") String nombre) {
	try {
		cps.modificarCategoria(id, nombre);
	} catch (Exception e) {
		System.err.println(e);
	}	
		
	}	
	 @GetMapping(path = "/cargarvalor/{idVariable}/{nombre}")
	 public void cargarVariableValor(@PathVariable String idVariable, @PathVariable String nombre,HttpSession session) {
		 try {
		Valores valor =vs.guardarVariableValores(idVariable, nombre);
		session.setAttribute("valor", valor);
		 } catch (Exception e) {
			 throw new Error ("No se pudo cargar la variable" + e);
		 }
		 	 
	 }
	 @GetMapping(path="/quitardelasession")
     public void eliminarCategoria(HttpSession session) {

         try {
            session.removeAttribute("variable");
            session.removeAttribute("cargavariable");
        } catch (Exception e) {
            System.err.println(e);
        }
     }
		
	 @GetMapping(path = "/removerspinner")
	 public void removerSpinner(HttpSession session) {
		 try {
		cs.sacarSpinner(session);
		 } catch (Exception e) {
			 throw new Error ("no se pudo sacar el spinne " +e);
		 }
		 	 
	 }
	 @GetMapping(path = "/empmastarde/{id}")
	 public void removerEmpMasTarde(@PathVariable String id) {
		 try {
		us.guardaCrearEmpNotifi(id);
		 } catch (Exception e) {
			 throw new Error ("no se pudo guardar la notificacion " +e);
		 }
		 	 
	 }
	 
	 @GetMapping(path = "/eliminarcategoria/{id}")
	 public void eliminarCategorias(@PathVariable String id) {
		 try {
			 cps.borrarCategoriaSola(id);
		 } catch (Exception e) {
			 throw new Error ("no se pudo guardar la notificacion " +e);
		 }
		 	 
	 }
	 
	 @GetMapping(path = "/eliminarvariable/{idValor}/{idVariable}")
	 public void eliminarVariable(@PathVariable String idValor,@PathVariable String idVariable) {
		 try {
			 vs.eliminarVariableSola(idValor,idVariable);
		 } catch (Exception e) {
			 throw new Error ("no se pudo borrar la variable " +e);
		 }
		 
	 }
	 
	 
}
