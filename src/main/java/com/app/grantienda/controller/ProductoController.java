package com.app.grantienda.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.grantienda.entidades.CategoriaProducto;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Foto;
import com.app.grantienda.entidades.Pedido;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.entidades.Valores;
import com.app.grantienda.entidades.Variable;
import com.app.grantienda.service.CategoriaProductoService;
import com.app.grantienda.service.EmprendimientoService;
import com.app.grantienda.service.PedidoService;
import com.app.grantienda.service.ProductoService;
import com.app.grantienda.service.VariableService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	private ProductoService ps;
	@Autowired
	private CategoriaProductoService cp;
	@Autowired
	private PedidoService pes;

	@Autowired
	private EmprendimientoService es;
	@Autowired
	private VariableService vs;
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/subirproducto")
	public String subirproducto(@RequestParam String idEmprendimiento,@RequestParam String productoServicio,@RequestParam String digitalAnalogo,@RequestParam String nombreProducto,@RequestParam String descProducto,@RequestParam(required = false) String esOferta,@RequestParam(required = false) String aCotizar,@RequestParam(required = false) Integer precioProducto,@RequestParam(required = false) Integer precioOferta,@RequestParam String stock,@RequestParam(required = false) Integer unidProducto,@RequestParam(required = false) String stockTime,@RequestParam(required = false) String efectivo,@RequestParam(required = false) String tarjeta,@RequestParam(required = false) String mercadopago,ModelMap modelo){
		Producto producto;
		try {
		producto = ps.subirProducto(idEmprendimiento,productoServicio,digitalAnalogo, nombreProducto, descProducto,esOferta,aCotizar, precioProducto,precioOferta, stock, unidProducto,stockTime,efectivo,tarjeta, mercadopago);
		
		}catch (Exception e) {
			throw new Error("no se pudo subir el producto " + e);
		}
		return "redirect:/producto/cargarvariable/"+producto.getId().toString() + "/" + idEmprendimiento;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/modificarproducto")
	public String modificarProducto(@RequestParam String idProducto,@RequestParam String idEmprendimiento,@RequestParam String productoServicio,@RequestParam String digitalAnalogo,@RequestParam String nombreProducto,@RequestParam String descProducto,@RequestParam(required = false) String esOferta,@RequestParam(required = false) String aCotizar,@RequestParam(required = false) Integer precioProducto,@RequestParam(required = false) Integer precioOferta,@RequestParam String stock,@RequestParam(required = false) Integer unidProducto,@RequestParam(required = false) String stockTime,@RequestParam(required = false) String efectivo,@RequestParam(required = false) String tarjeta,@RequestParam(required = false) String mercadopago,ModelMap modelo){
		Producto producto;
		try {
		producto = ps.modificarProducto(idProducto,idEmprendimiento,productoServicio,digitalAnalogo, nombreProducto, descProducto,esOferta,aCotizar, precioProducto,precioOferta, stock, unidProducto,stockTime,efectivo,tarjeta, mercadopago);
		
		}catch (Exception e) {
			throw new Error("no se pudo subir el producto");
		}
		return "redirect:/producto/editarvariable/"+idEmprendimiento+"/"+idProducto;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/cargarvariable/{idProducto}/{idEmprendimiento}")
	public String cargarVariables(@PathVariable String idProducto,@PathVariable String idEmprendimiento,ModelMap modelo,HttpSession session) {
		Producto producto = ps.buscarProducto(idProducto);
		Emprendimiento emp = es.buscarEmprendimiento(idEmprendimiento);
		modelo.addAttribute("producto",producto.getId());
		modelo.addAttribute("emprendimiento",emp);
		
		return "cargaVariables.html";
	}
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	 @PostMapping("/cargarsola")
	 public String subircategoriasola(@RequestParam String prodVariableTitulo,@RequestParam String idProducto,@RequestParam String idEmprendimiento,ModelMap modelo,HttpSession session) {
		Producto producto = ps.buscarProducto(idProducto);
		 try {
				 Variable variable = vs.guardar(prodVariableTitulo,idProducto);
				 session.setAttribute("variable",variable);
				 session.setAttribute("cargavariable", true);
				 
		} catch (Exception e) {
			throw new Error("no se pudo subir la categoria" + e);
		}
		 return "redirect:/producto/cargarvariable/"+producto.getId().toString() + "/" + idEmprendimiento;
	 }
	

	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/cargarfoto/{id}/{idemprendimiento}")
	public String cargarFotoProducto(@PathVariable("id") String idproducto,@PathVariable("idemprendimiento") String idEmprendimiento,ModelMap modelo,HttpSession session) {
		List<CategoriaProducto>categoriaproductos = cp.buscarCategorias(idEmprendimiento);
		Producto producto = ps.buscarProducto(idproducto);
		Emprendimiento emp = es.buscarEmprendimiento(idEmprendimiento);
		List<Foto>fotos=producto.getFoto();
		modelo.addAttribute("fotos",fotos);
		modelo.addAttribute("categoriaproductos",categoriaproductos);
		modelo.addAttribute("producto",producto.getId());
		modelo.addAttribute("emprendimiento",emp);
		
		return "cargaFoto.html";
	}
	
	@GetMapping("/{id}/{idemprendimiento}")
	public String paginaProducto(@PathVariable String id,@PathVariable String idemprendimiento,ModelMap modelo){
		Emprendimiento emp = es.buscarEmprendimiento(idemprendimiento);
		Producto producto = ps.buscarProducto(id);
		modelo.addAttribute("producto",producto);
		modelo.addAttribute("emprendimiento",emp);
		
		return "paginaProducto.html";
	}

	 @PostMapping("/eliminarcategoria")
    public String eliminarCategoria(@RequestParam String idVariable,@RequestParam String idEmprendimiento, @RequestParam String idProducto) {

        try {
           vs.eliminarVariable(idVariable);

       } catch (Exception e) {
           System.err.println(e);
       }
        return"redirect:/producto/cargarvariable/"+idProducto+"/"+idEmprendimiento;
    }
	 
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/editarproducto/{idEmprendimiento}/{idProducto}")
	public String editarProducto(@PathVariable String idEmprendimiento,@PathVariable String idProducto,ModelMap modelo){
		Emprendimiento emp=es.buscarEmprendimiento(idEmprendimiento);
		Producto producto=ps.buscarProducto(idProducto);
		modelo.addAttribute("producto", producto);
		modelo.addAttribute("emprendimiento", emp);
		return "editarProducto.html";
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/editarvariable/{idemprendimiento}/{idproducto}")
	public String editarVariable(@PathVariable String idemprendimiento, @PathVariable String idproducto,ModelMap modelo ) {
		
		Emprendimiento emp=es.buscarEmprendimiento(idemprendimiento);
		Producto producto=ps.buscarProducto(idproducto);
		modelo.addAttribute("producto", producto);
		modelo.addAttribute("emprendimiento", emp);
		return"editarVariables.html";
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/bajarcategorias/{id}")
	public String bajarCategoriasAUnaSola(@PathVariable String id) {
		
		es.bajarCategoria(id);
		
		return"redirect:/emp/landingconfig/"+id;
	}

}

