package com.app.grantienda.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.grantienda.Utility;
import com.app.grantienda.entidades.CantidadVistas;
import com.app.grantienda.entidades.CategoriaProducto;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Historial;
import com.app.grantienda.entidades.Pedido;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.entidades.ProductoGuardado;
import com.app.grantienda.entidades.User;
import com.app.grantienda.enums.Provincias;
import com.app.grantienda.service.CantidadVistasService;
import com.app.grantienda.service.CategoriaProductoService;
import com.app.grantienda.service.CookiesService;
import com.app.grantienda.service.EmprendimientoService;
import com.app.grantienda.service.HistorialService;
import com.app.grantienda.service.PedidoService;
import com.app.grantienda.service.ProductoGuardadoService;
import com.app.grantienda.service.ProductoPedidoService;
import com.app.grantienda.service.ProductoService;
import com.app.grantienda.service.SeguidoresService;
import com.app.grantienda.service.UsuarioService;
import com.app.grantienda.service.ValoracionService;


@Controller
@RequestMapping("/")
public class PortalController {

	@Autowired
	private SeguidoresService seguidoresService;
	@Autowired
	private ValoracionService valoracionService;
	@Autowired
	private ProductoPedidoService productoPedidoService;
	@Autowired
	private CookiesService cs;
	@Autowired
	private UsuarioService us;
	@Autowired
	private CategoriaProductoService cv;
	@Autowired
	private ProductoService ps;
	@Autowired
	private CantidadVistasService cvs;
	@Autowired
	private PedidoService pds;
	@Autowired
	private EmprendimientoService es;
	@Autowired
	private HistorialService hs;
	@Autowired
	private ProductoGuardadoService pgs;
	
	@PostMapping("/cookies")
		public String cargarCategoria(HttpServletResponse response,HttpSession session) {
			try {
				cs.setCookies(response,session);
			} catch (Exception e) {
				throw new Error("No se pudo setear las cookies");
			}
		return "redirect:/";
		}
	
	@GetMapping("/")
	public String index(HttpSession session,ModelMap modelo,@CookieValue(name = "cookie", required = false ) String acepta) {
			if(acepta!= null) {
				modelo.addAttribute("cookie",acepta);
			}
			User usuario= (User) session.getAttribute("usersession");
			modelo.addAttribute("usersession",usuario);
			if(usuario !=null) {
				return"redirect:inicio";
			}
			List<Emprendimiento>todosEmprendimientos=es.buscartodos();
			List<Producto> lista = valoracionService.masValorados();
			
			List<String> lista2=valoracionService.listaidsemprendimientos(lista);
			List<String> lista3=valoracionService.listaidsemprendimientosFotos(lista2);
			List<String> lista4=valoracionService.listaNombreEmp(lista2);
			
			
			
			
			
			modelo.addAttribute("idfotos",lista3);
			modelo.addAttribute("idEmp",lista2); 
			modelo.addAttribute("NomEmp",lista4); 
			modelo.addAttribute("masvalorados",lista);
			modelo.addAttribute("todosEmprendimientos",todosEmprendimientos);
			modelo.addAttribute("provincias",Provincias.values());
		return "index.html";
	}


	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@GetMapping("/inicio")
	public String inicio(HttpSession session, ModelMap modelo,@CookieValue(name = "cookie", required = false ) String acepta) {
		if(acepta!= null) {
			modelo.addAttribute("cookie",acepta);
		}
		  User 	usuario= (User) session.getAttribute("usersession");	  
		List<Emprendimiento>emp= es.buscarEmprendimientosPorIdUsuario(usuario.getId());
		if(usuario.getRole().toString().equals("SUPERADMIN")) {
			emp= es.buscarTodosEmprendimientos();
		}
		String rol= "SUPERADMIN";
		if(usuario.getRole().toString().equals(rol)) {
			List<Historial>historial= hs.traerHistorial();
			modelo.addAttribute("historial",historial);		
			List<User>usuarios= us.buscarTodosLosUsuarios();
			modelo.addAttribute("cantidadUsuarios",usuarios.size());
			List<Emprendimiento>emprendimientos = es.buscarEmprendimientosPorEstado("Autorizado");
			modelo.addAttribute("cantidadEmprendimientos",emprendimientos.size());
		}
		modelo.addAttribute("emprendimientos",emp);
		session.setAttribute("usersession", usuario);
		
		CantidadVistas cv = cvs.buscarCantidadVistasPrincipal();
		if( cv == null) {
			 cvs.CrearCantidadVistas();
			
		}
		cvs.guardarVistasGeneral();
		modelo.addAttribute("cantidadVistaGeneral",cv);
		
		List<Emprendimiento>todosEmprendimientos=es.buscartodos();
		
		List<ProductoGuardado>productosGuardados=pgs.buscarProductosPorUsuario(usuario.getId()); 
		List<Producto>productosGuardadosCompletos = new ArrayList<>();
		Producto producto;
		if(productosGuardados != null) {
			
			for (ProductoGuardado productoGuardado : productosGuardados) {
				if(productoGuardado != null) {
					producto = ps.buscarProducto(productoGuardado.getIdProducto());
					if(producto != null) {
						productosGuardadosCompletos.add(producto);
					}
				}
		}
		}
		if(!productosGuardadosCompletos.isEmpty()|| productosGuardadosCompletos != null) {
			
			modelo.addAttribute("productosGuardadosComp",productosGuardadosCompletos);
		}
		Pedido pedido = pds.buscarUltimoPedido(usuario.getId());	
		List<Producto> lista = valoracionService.masValorados();
			
			List<String> lista2=valoracionService.listaidsemprendimientos(lista);
			List<String> lista3=valoracionService.listaidsemprendimientosFotos(lista2);
			modelo.addAttribute("idfotos",lista3);
			modelo.addAttribute("idEmp",lista2); 
			modelo.addAttribute("masvalorados",lista);
			modelo.addAttribute("ultimopedido",pedido);
			modelo.addAttribute("todosEmprendimientos",todosEmprendimientos);
			modelo.addAttribute("provincias",Provincias.values());
			return "index.html";
	}

	@GetMapping("/ingresar")
	public String ingresar(HttpSession session, ModelMap modelo, @RequestParam(required = false) String salir,
			@RequestParam(required = false) String error) {

		if (error != null && !error.isEmpty()) {
			if (error.equals("error")) {
				modelo.addAttribute("error",
						"El email o la contraseña ingresados son incorrectos. Por favor intentalo de nuevo.");
			} else {
				modelo.addAttribute("error", error);
			}
			return "ingresar.html";
		}

		if (salir != null) {
			modelo.addAttribute("logout", "Has salido de la plataforma exitosamente.");
		}

		return checkLogueado(session);
	}

    private String checkLogueado(HttpSession session) {
        if (session.getAttribute("usersession") != null) {
            return "redirect:/inicio";
        }

        return "ingresar.html";
    }
	@GetMapping("/crearcuenta")
	public String crearCuenta(ModelMap modelo) {
		return "crearCuenta.html";
	}

	// registro usuario
	@PostMapping("/registraruser")
	public String registrarusuario(ModelMap modelo, @RequestParam String mail, @RequestParam String password,
			@RequestParam String regNombre, @RequestParam(required = false) String celular ,@RequestParam String avatar,
			HttpServletRequest request) throws Error, Exception {

		try {

			String siteURL = Utility.getSiteURL(request);
			us.registraruser(mail, password, regNombre,celular, avatar, siteURL);

			modelo.addAttribute("titulo", "Se Registro con Exito!");
			modelo.addAttribute("exito", "Se le envio un mail para validar su cuenta.");

		} catch (Error ex) {
			
			modelo.put("error", ex.getMessage().toString());

			return "registroEstado.html";

		}
		return exito(modelo, mail);
	}
	@PostMapping("/editarusuario")
	public String editarUsuario(@RequestParam String id,@RequestParam(required = false) String celular,@RequestParam String cambiarNombre,@RequestParam String password,HttpSession session) {
		try {
			User usuario =us.modificaruser(id,celular, cambiarNombre, password);
			session.setAttribute("usersession", usuario);
			
		} catch (Exception e) {
			throw new Error("no se pudo modificar el usuario");
		}
		
		return"redirect:/emp/devolverperfil";
	}
	

	@GetMapping("/verificar")
	public String verificarCuenta(@RequestParam("code") String codigoDeVerificacion, ModelMap modelo) {

		User usuario = us.buscarPorCodigo(codigoDeVerificacion);
		String mail = usuario.getMail();

		if (us.verificar(codigoDeVerificacion)) {
			String exitoreg = "Se verifico su cuenta";
			modelo.addAttribute("exitoreg", exitoreg);
			return exito(modelo, mail);
		} else {
			String errorToken = "No se pudo verificar su cuenta";
			modelo.addAttribute("errorToken", errorToken);
			return exito(modelo, usuario.getMail());
		}
	}

	@GetMapping("/registroestado")
	public String exito(ModelMap modelo, String mail) {
		User usuario = us.buscarPorMail(mail);
		Boolean activo = usuario.getUserActivo();
		modelo.put("activo", activo);
		modelo.addAttribute("usuario", usuario);
		return "registroEstado.html";
	}
	
	
	@GetMapping("/empestado")
	public String empexito(ModelMap modelo) {


		return "registroEstado.html";
	}

	// --------------Metodos Para recuperar Contraseña-----------------------------
	

	@PostMapping("/recuperarpassword")
	public String recuperar(ModelMap modelo, @RequestParam String mail,HttpServletRequest request) throws Exception {

		try {
			String siteURL = Utility.getSiteURL(request);
			us.enviarRecupero(mail,siteURL);
			modelo.addAttribute("exito","Te enviamos un email a " + mail);

		} catch (Error e) {
			modelo.put("error", e.getMessage());
			return "recuperar.html";
		}
		

		return "/ingresar";
	}
	
	@GetMapping("/recuperar")
	public String recuperarCuenta(@RequestParam("code") String codigoDeContrasena, ModelMap modelo) {
		
				User usuario=us.buscarPorCodigoPass(codigoDeContrasena);
				if(usuario != null) {
					
					modelo.put("usuario", usuario);
					return "cambiarContrasena.html";
				}else {
					modelo.put("error", "Usuario no encontrado. Codigo de verificación vencido.");
					return "emprendimientoEstado.html";
				}		
	}	
	
	@PostMapping("/nuevapass")		
		public String nuevpass(@RequestParam String mail,@RequestParam String password) {
		try {	
		us.recuperarContrasena(mail, password);
		}catch (Exception e) {
			throw new Error("Error al procesar la nueva contraseña");
		}

		return "redirect:/ingresar";
	}
	
	@GetMapping("/terminos")
	public String politicasDePrivacidad() {
		
		return"terminosYCondiciones.html";
	}
	@GetMapping("/privacidad")
	public String condicionesDelServicio() {
		
		return"privacidad.html";
	}
	@PostMapping("/eliminarcuenta")
	public String eliminarcuenta() {
		
		return"confirmacionBorrarCuenta.html";
	}
	
	@GetMapping("/{direccionweb}")
	public String direccionWeb(@PathVariable String direccionweb,HttpSession session,ModelMap modelo) {
		Emprendimiento emp = es.buscarEmprendimientoPorDireccionWeb(direccionweb);			
			if(emp != null) {
				User 	usuario= (User) session.getAttribute("usersession");
				cvs.guardarVistasEmprendimiento(emp.getId());
				modelo.addAttribute("emprendimiento",emp);
				List<CategoriaProducto> catego= cv.buscarCategorias(emp.getId());
				String pedidos = productoPedidoService.contadorDePedido(emp.getId());
				String existe = seguidoresService.verificarSeguidor(emp.getId(),usuario.getId());
				String seguidores = seguidoresService.contadorDeSeguidores(emp.getId());
				
				String promedioT = valoracionService.ValoracionTotal(emp.getId());
				if(promedioT== null || promedioT == "") {
					promedioT="0,0";
				}
				modelo.addAttribute("categorias",catego);
				modelo.addAttribute("seguir",existe);
				modelo.addAttribute("seguidores",seguidores);
				modelo.addAttribute("pedidos",pedidos);
				modelo.addAttribute("promedio",promedioT);
			}

		
		return "landingPublic.html";
	}
	

	@GetMapping("/quienessomos")
	public String quienesSomos() {
		
		return"quienesSomos.html";
	}
	@GetMapping("/soporte")
	public String soporte() {
		
		return"soporte.html";
	}
		
	}

	
	
	

