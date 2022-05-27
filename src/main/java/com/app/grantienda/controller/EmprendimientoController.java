
package com.app.grantienda.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.grantienda.entidades.CategoriaProducto;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Pedido;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.entidades.User;
import com.app.grantienda.enums.Categorias;
import com.app.grantienda.enums.Provincias;
import com.app.grantienda.service.CantidadVistasService;
import com.app.grantienda.service.CategoriaProductoService;
import com.app.grantienda.service.DireccionService;
import com.app.grantienda.service.EmprendimientoService;
import com.app.grantienda.service.FotoService;
import com.app.grantienda.service.PedidoService;
import com.app.grantienda.service.ProductoService;
import com.app.grantienda.service.SeguidoresService;
import com.mercadopago.resources.datastructures.advancedpayment.Payment;

@Controller
@RequestMapping("/emp")

public class EmprendimientoController {
	@Autowired
	private EmprendimientoService es;
	@Autowired
	private SeguidoresService seguidoresService;
	@Autowired
	private ProductoService ps;
	@Autowired
	private PedidoService pes;
	@Autowired
	private FotoService fs;
	@Autowired
	private DireccionService ds;
	@Autowired
	private CategoriaProductoService cp;
	@Autowired
	private CantidadVistasService cvs;
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/elegircuenta")
	public String seleccionarMembresia(ModelMap modelo,HttpSession session) {
		List<Emprendimiento>emprendimientos = es.buscarEmprendimientosPorEstado("Autorizado");
		modelo.addAttribute("cantidadEmprendimientos",emprendimientos.size());
		User usuario = (User) session.getAttribute("usersession");
		List<Emprendimiento>emprendimientosDelUsuario = es.buscarEmprendimientosPorIdUsuario(usuario.getId());
		for (Emprendimiento emprendimiento : emprendimientosDelUsuario) {
			if(emprendimiento.getMembresia().equals("GRATIS")) {
				modelo.addAttribute("unogratis",true);
			}
		}
		return"elegirCuenta.html";
	}

	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/crearemp")
	public String nuevoEmprendimiento(ModelMap modelo,HttpSession session) {
		User usuario = (User) session.getAttribute("user");
		modelo.addAttribute("user",usuario);

		if (usuario == null) {
			usuario= (User) session.getAttribute("usersession");
		modelo.addAttribute("user",usuario);

		}
		Object status=session.getAttribute("statuspago");
		System.out.println("el estado del pago "+status);
		modelo.addAttribute("categorias",Categorias.values());
		modelo.addAttribute("provincias",Provincias.values());
		return"crearEmprendimiento.html";
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/subiremp")
	public String crearEmprendimiento(
			@RequestParam String regNombreTitular,
			@RequestParam String regDocumentoTitular,
			@RequestParam String mail,
			@RequestParam String regEmpNombre,
			@RequestParam String regEmpDescripcion,
			@RequestParam String regEmpDir,
			@RequestParam Boolean hacenEnvios,
			@RequestParam Categorias categoria,
			@RequestParam(required = false)String subCategoria,
			@RequestParam String empActividad,
			@RequestParam String tieneLogo,
			@RequestParam String marcaRegistrada,
			@RequestParam String dirPrivacidad,
			@RequestParam String dirCalle,
			@RequestParam String dirNum,
			@RequestParam(required = false)String dirPiso,
			@RequestParam(required = false)String dirDepto,
			@RequestParam Provincias provincia,
			@RequestParam String localidadEmp,
			@RequestParam String codPostal,
			@RequestParam String empTel,
			@RequestParam (required = false) Boolean esWhatsapp,
			ModelMap modelo,HttpSession session) throws Exception {
		es.crearEmprendimiento(
				regNombreTitular,
				regDocumentoTitular,
				mail,
				regEmpNombre,
				regEmpDescripcion,
				regEmpDir, 
				hacenEnvios,
				categoria,
				subCategoria,
				empActividad, 
				tieneLogo,
				marcaRegistrada,
				dirPrivacidad,
				dirCalle,
				dirNum, 
				dirPiso,
				dirDepto,
				provincia, 
				localidadEmp,
				codPostal,
				empTel,
				esWhatsapp, session);
		modelo.put("exito", "¡Registro exitoso!");
		
		return "emprendimientoEstado.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
	@GetMapping("/confirmaremp/{id}")
	public String confirmacionEmprendimiento(@PathVariable String id,ModelMap modelo) {
		
		Emprendimiento emp = es.buscarEmprendimiento(id);
		modelo.addAttribute("emprendimiento",emp);
		modelo.addAttribute("subcategoria",emp.getSubCatergoria());
	
	return "confirmacionEmprendimiento.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
	@PostMapping("/emprendimientoestado/{id}")
	public String emprendimientoEstado(@PathVariable String id,@RequestParam String estadoEmp,@RequestParam(required = false) String reachazoText,ModelMap modelo,HttpSession session) {
		
		String aceptado= "aceptarEmp";
		String rechazado= "rechazarEmp";
		User usuario=(User) session.getAttribute("usersession");
			String idUser=usuario.getId();
		if( estadoEmp.equals(aceptado)) {
			try {
				es.aceptarEmprendimiento(id, idUser);
				devolverPerfil(session);
				return "redirect:/inicio";		
			}catch (Exception e) {
				throw new Error("emprendimiento no encontrado.");
			}
			
				
		}else if (estadoEmp.equals(rechazado)) {
			try {
				es.cancelarEmprendimiento(id, reachazoText,idUser);
				devolverPerfil(session);
				return "redirect:/inicio";
			}catch (Exception e) {
				throw new Error("emprendimiento no encontrado.");
			}
		}
		return "redirect:/inicio";
	}
	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/devolverperfil")
	public String devolverPerfil(HttpSession session) {
		session.setAttribute("volverperfil", "volver");
		return "redirect:/inicio";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/landingedit/{id}")
	public String landingEdit(@PathVariable String id,ModelMap modelo) {
		List<CategoriaProducto> catego= cp.buscarCategorias(id);
        modelo.addAttribute("categorias",catego);
		Emprendimiento emprendimiento = es.buscarEmprendimiento(id);
		List<Producto>productos=emprendimiento.getProductos();
		modelo.addAttribute("productos",productos);
		modelo.addAttribute("emprendimiento",emprendimiento);
		List<Pedido>pedidos= pes.buscarPedidoPorEmprendimiento(emprendimiento.getId());
		List<Pedido>pedidosPendientes= new ArrayList<>();
		for (Pedido pedido : pedidos) {
			if(pedido.getEstado().equals("pedidoEnviado")) {
				pedidosPendientes.add(pedido);
			}
		}
		modelo.addAttribute("cantidadVistas",emprendimiento.getCantidadVistasEmp().getCantidadVistas());
		modelo.addAttribute("cantidad",pedidosPendientes.size());
		return"landingEdit.html";
	}
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/landingconfig/{id}")
	public String landingConfig(@PathVariable String id,ModelMap modelo) {
		
		Emprendimiento emprendimiento = es.buscarEmprendimiento(id);
		modelo.addAttribute("emprendimiento",emprendimiento);
		modelo.addAttribute("provincias",Provincias.values());
		modelo.addAttribute("subcategoria",emprendimiento.getSubCatergoria());
		return"landingConfig.html";
	}
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/editar")
	public String editarEmprendimiento(@RequestParam String id,@RequestParam(required = false) String regEmpDescripcion,@RequestParam(required = false) String empActividad,@RequestParam(required = false) String subCategoria,@RequestParam(required = false) String empTel,@RequestParam(required = false) Boolean esWhatsapp, @RequestParam(required = false) String instagram) {
		
		try {
			es.modificarEmprendimiento(id, regEmpDescripcion, empActividad, subCategoria, empTel, esWhatsapp, instagram);			
		} catch (Exception e) {
			throw new Error("No se encontro el emprendimiento");
		}
		
		return "redirect:/emp/landingedit/"+id;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER')|| hasRole('ROLE_SUPERADMIN')")
	@GetMapping("/eliminar/{id}")
	public String eliminarEmprendimiento(@PathVariable String id) {
		try {
			seguidoresService.eliminar(id);
			es.eliminarEmp(id);
		} catch (Exception e) {
			throw new Error("No se encontro el emprendimiento");
		}
		
		return"redirect:/emp/devolverperfil";
	}
	
	@PostMapping("/modificardireccion/{id}")
	public String modificarDireccion(@PathVariable String id,@RequestParam(required = false) String dirPrivacidad,@RequestParam(required = false) String dirCalle,@RequestParam(required = false) String dirNum,@RequestParam(required = false) String dirPiso,@RequestParam(required = false) String dirDepto,@RequestParam(required = false) Provincias provincia,
		@RequestParam(required = false) String localidadEmp,
		@RequestParam(required = false) String codPostal) {
		
		ds.modificardireccion(id, dirPrivacidad, dirCalle,dirNum, dirPiso, dirDepto, provincia, localidadEmp, codPostal);
		
		return "redirect:/emp/landingconfig/"+id;
	}
	
	@PostMapping("/categoriaproducto")
	public String categoriaProducto(@RequestParam String IdEmprendimiento ,@RequestParam String idProductosubircatego,@RequestParam(required = false) String idCategoriapepe,HttpSession session) {
		try {
			System.out.println("la categoria pepe "+ idCategoriapepe);
			if(idCategoriapepe != null) {
				cp.subirProductoAcategoria(idProductosubircatego, idCategoriapepe);				
			}
			session.removeAttribute("volverCargaVariable");
			}catch (Exception e) {
				System.out.println(e);
			}
		return "redirect:/emp/landingedit/"+ IdEmprendimiento;
	}

	@PostMapping("/cargarpreferencias")
	public String cargarPreferenciasPagina(@RequestParam String idEmprendimiento,@RequestParam(required = false) String empLayout, @RequestParam(required = false) String empTheme) {
		try {
			es.guardarPreferencias(idEmprendimiento,empLayout,empTheme);
		} catch (Exception e) {
			throw new Error ("error al cargar las preferencias de la pagina");
		}
		
		return "redirect:/emp/landingedit/"+ idEmprendimiento;
	}
	
	
}
