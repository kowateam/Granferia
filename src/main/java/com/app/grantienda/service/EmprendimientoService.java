package com.app.grantienda.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.catalina.valves.rewrite.InternalRewriteMap.Escape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.CantidadVistas;
import com.app.grantienda.entidades.CategoriaProducto;
import com.app.grantienda.entidades.Direccion;
import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Historial;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.entidades.User;
import com.app.grantienda.enums.Categorias;
import com.app.grantienda.enums.Provincias;
import com.app.grantienda.repositorio.EmprendimientoRepositorio;

@Service
public class EmprendimientoService {

	@Autowired
	private EmprendimientoRepositorio er;
	@Autowired
	private UsuarioService us;
	@Autowired
	private ProductoGuardadoService pgs;
	@Autowired
	private PedidoService pes;
	@Autowired
	private DireccionService ds;
	@Autowired
	private ProductoService ps;
	@Autowired
	private NotificacionService ns;
	@Autowired
	private CantidadVistasService cvs;
	@Autowired
	private CategoriaProductoService cps;
	@Autowired
	private HistorialService hs;

	@PreAuthorize("hasRole('ROLE_SUPERADMIN') || hasAnyRole('ROLE_USER')")
	@Transactional
	public Emprendimiento crearEmprendimiento(String regNombreTitular, String regDocumentoTitular, String mail,
			String regEmpNombre, String regEmpDescripcion, String regEmpDir, Boolean hacenEnvios, Categorias categoria,
			String subCategoria, String empActividad, String tieneLogo, String marcaRegistrada, String dirPrivacidad,
			String dirCalle, String dirNum, String dirPiso, String dirDepto, Provincias provinciaEmp,
			String localidadEmp, String codPostal, String empTel, Boolean esWhatsapp,HttpSession session) throws Exception {
		validar(regEmpNombre, regEmpDescripcion, subCategoria, empTel, marcaRegistrada);

		Emprendimiento empp = er.buscarEmpPorNombre(regEmpNombre);

		if (empp != null) {
			throw new Error("El Emprendimiento Ya Existe.");
		}

		Emprendimiento emp = new Emprendimiento();
		emp.setNombreRazonSocial(regNombreTitular);
		// String encriptada = new BCryptPasswordEncoder().encode(regDocumentoTitular);
		emp.setDniCuit(regDocumentoTitular);
		emp.setRegEmpNombre(regEmpNombre);
		emp.setRegEmpDescripcion(regEmpDescripcion);
		emp.setRegEmpDir(regEmpDir);
		emp.setEmpActividad(empActividad);
		emp.setHaceEnvios(hacenEnvios);
		emp.setCategoria(categoria);
		emp.setSubCatergoria(subCategoria);
		emp.setTieneLogo(tieneLogo);
		emp.setEmpTel(empTel);
		emp.setEsWhatsapp(esWhatsapp);
		emp.setMarcaRegistrada(marcaRegistrada);
		emp.setAlta(new Date());
		emp.setEstadoEmp("Procesando stand");
		emp.setAceptado(false);
		emp.setEmpLayout("layout1");
		CantidadVistas cv = new CantidadVistas();
		cvs.guardarCantidadVistas(cv);
		emp.setCantidadVistasEmp(cv);
		
		Object status=session.getAttribute("statuspago");
		System.out.println("el estado del pago "+status);
		if(status == null) {
			emp.setMembresia("GRATIS");
		}else 
		if(status != null && status.equals("approved")) {
			
			emp.setMembresia("PREMIUM");
			
			
		}
		User usuario = us.buscarPorMail(mail);
		emp.setUser(usuario);

		Direccion dir = new Direccion();
		dir.setDireccionVisibilidad(dirPrivacidad);
		dir.setDirCalle(dirCalle);
		dir.setDirNum(dirNum);
		dir.setDirPiso(dirPiso);
		dir.setDirDepto(dirDepto);
		dir.setProvinciaEmp(provinciaEmp);
		dir.setLocalidadEmp(localidadEmp);
		dir.setCodPostal(codPostal);

		ds.guardarDireccion(dir);

		emp.setDireccion(dir);

		er.save(emp);
		if(status != null && status.equals("approved")) {
			timerPremium(emp.getId());
			timerSemanaAntes(emp.getId());
		}
		session.removeAttribute("statuspago");
		CategoriaProducto cp = cps.subirCategoria("Todos", emp.getId());
		cps.primero(cp.getId());
		List<User> admins = us.admin();
		for (User admin : admins) {
			String cuerpo = "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

			// Logo
			cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

			cuerpo += "<tr><td colspan=\"1\">";

			// Título
			cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Se cargó una nueva solicitud</h1>";

			// Copy
			cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">El emprendimiento <b>"
					+ regEmpNombre + "</b> se encuentra pendiente de autorización.</p>";

			cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Revisá toda la información adjunta a la solicitud en la plataforma.</p>";

			// CTA
			cuerpo += "<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";

			cuerpo += "</td></tr>";
			cuerpo += "</table>";
			ns.enviar(admin.getMail(), "Nuevo emprendimiento en granferia", cuerpo);
		}
		return emp;

	}
	
	 public void timerPremium(String idEmprendimiento) {
	    	
	    	Timer timer =new Timer();
	    	TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					Emprendimiento emp = buscarEmprendimiento(idEmprendimiento);
				emp.setMembresia("GRATIS");
				guardarEmprendimiento(emp);
						timer.cancel();
						timerNuevoEmprendimiento(emp.getId());	
				}
			};
	    	timer.schedule(task,6000*60*24*30);	
	    }
	
	 public void timerSemanaAntes(String idEmprendimiento) {
	    	
	    	Timer timer =new Timer();
	    	TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					Emprendimiento emp = buscarEmprendimiento(idEmprendimiento);
			try {
				String cuerpo = "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

				// Logo
				cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

				cuerpo += "<tr><td colspan=\"1\">";

				// Título
				cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Se esta terminando el tiempo de su membresia Premium</h1>";


				cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Le queda una semana para renovar su membresia. </p>";

				// CTA
				cuerpo += "<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";
				cuerpo += "</td></tr>";
				cuerpo += "</table>";
				ns.enviar(emp.getUser().getMail(),"Se Esta Acabando su plan Premium", cuerpo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						timer.cancel();
						timerNuevoEmprendimiento(emp.getId());	
				}
			};
	    	timer.schedule(task,6000*60*24*21);	
	    }

	@Transactional
	public void modificarEmprendimiento(String id, String descripcion, String empActividad, String subcategoria,
			String empTel, Boolean esWhatsapp, String instagram) {

		Optional<Emprendimiento> empp = er.findById(id);

		if (empp.isPresent()) {

			Emprendimiento emp = empp.get();

			emp.setRegEmpDescripcion(descripcion);
			emp.setEmpActividad(empActividad);
			emp.setSubCatergoria(subcategoria);
			emp.setEmpTel(empTel);
			emp.setEsWhatsapp(esWhatsapp);
			emp.setInstagram(instagram);

			er.save(emp);

		} else {
			throw new Error("No se encontro al Emprendimiento.");
		}
	}

	@Transactional
	public void eliminarEmp(String id) {

		Optional<Emprendimiento> empp = er.findById(id);
		Emprendimiento emp = null;
		//cps.borrarCategoriasRelacionesEmprendimiento(id);
		if(empp.isPresent()) {
			emp=empp.get();
		}
		if(emp != null) {
			
			er.delete(emp);
			pes.borrarEmprendimientoEnPedido(id,"El emprendimiento fue eliminado");
			//pgs.borrarPedidosEmprendimiento(id);

		} else {
			throw new Error("No se encontro el Emprendimiento.");
		}
	}

	public List<Emprendimiento> buscarEmprendimientosPorIdUsuario(String id) {

		List<Emprendimiento> emp = er.buscarEmprendimientoPorIdUsuario(id);

		return emp;
	}

	public List<Emprendimiento> buscarEmprendimientosPorEstado(String estado) {

		List<Emprendimiento> emp = er.buscarEmprendimientoPorEstado(estado);

		return emp;
	}

	public List<Emprendimiento> buscarPorNombrePagina(String buscar) {

		List<Emprendimiento> emp = er.buscarPorNombrePagina(buscar);
		return emp;
	}

	public List<Emprendimiento> buscarTodosEmprendimientos() {

		List<Emprendimiento> emp = er.findAll();
		return emp;
	}

	public void validar(String regEmpNombre, String descripcion, String subCategoria, String empTel, String marcaReg) {

		if (regEmpNombre.isEmpty()) {
			throw new Error("El nombre no puede venir vacio.");
		}
		if (descripcion.isEmpty()) {
			throw new Error("El nombre no puede venir vacio.");
		}

		if (empTel.isEmpty()) {
			throw new Error("El nombre no puede venir vacio.");
		}
		if (marcaReg.isEmpty()) {
			throw new Error("La marca no puede venir vacia.");
		}
		if (subCategoria.isEmpty()) {
			throw new Error("La subcategoria no puede venir vacia.");
		}

	}

	@Transactional
	public Emprendimiento buscarEmprendimiento(String id) {
		Optional<Emprendimiento> emp = er.findById(id);
		if (emp.isPresent()) {
			return emp.get();
		}
		return null;
	}

	@Transactional
	public Emprendimiento buscarEmprendimientoPorIdUser(String id) {
		Optional<Emprendimiento> emp = er.findById(id);
		if (emp.isPresent()) {
			return emp.get();
		}
		return null;
	}

	@Transactional
	public void aceptarEmprendimiento(String id, String idUser) throws Exception {

		Emprendimiento emp = buscarEmprendimiento(id);
		emp.setEstadoEmp("Autorizado");
		emp.setAceptado(true);
		er.save(emp);
		String cuerpo = "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

		// Logo
		cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

		cuerpo += "<tr><td colspan=\"1\">";

		// Título
		cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">¡Felicitaciones!</h1>";

		// Copy
		cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Tu stand <b>"
				+ emp.getRegEmpNombre()
				+ "</b> fue aprobado, ya podés empezar a cargar productos y personalizar tu stand.</p>";

		// CTA
		cuerpo += "<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";

		cuerpo += "</td></tr>";
		cuerpo += "</table>";
		hs.guardarHistorial(emp.getRegEmpNombre(), emp.getEstadoEmp(), idUser);
		ns.enviar(emp.getUser().getMail(), "Emprendimiento actualizado", cuerpo);
	}

	@Transactional
	public void cancelarEmprendimiento(String id, String reachazoText, String idUser) throws Exception {

		Emprendimiento emp = buscarEmprendimiento(id);
		emp.setEstadoEmp("Rechazado");
		emp.setReachazoText(reachazoText);
		er.save(emp);
		String cuerpo = "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

		// Logo
		cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

		cuerpo += "<tr><td colspan=\"1\">";

		// Título
		cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Lo sentimos</h1>";

		// Copy
		cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Tu stand <b>"
				+ emp.getRegEmpNombre() + "</b> no pudo ser aprobado, te compartimos las razones: "
				+ emp.getReachazoText() + "</p>";

		cuerpo += "</td></tr>";
		cuerpo += "</table>";
		hs.guardarHistorialRechazo(emp.getRegEmpNombre(), emp.getEstadoEmp(), emp.getReachazoText(), idUser);
		ns.enviar(emp.getUser().getMail(), "Emprendimiento actualizado", cuerpo);
	}

	@Transactional
	public void guardarEmprendimiento(Emprendimiento emprendimiento) {

		er.save(emprendimiento);
	}

	public List<Emprendimiento> buscartodos() {
		List<Emprendimiento> emp = er.findAll();
		return emp;
	}

	public List<Emprendimiento> buscarEmprendimientoPorProducto(String idProducto) {
		List<Emprendimiento> emp = er.buscarPorProducto(idProducto);
		return emp;
	}

	public Emprendimiento buscarEmprendimientoPorDireccionWeb(String direccionweb) {
		Emprendimiento emp = er.buscarUnoPorNombrePagina(direccionweb);
		return emp;
	}

	@Transactional
	public void bajarCategoria(String id) {
		Emprendimiento emp = buscarEmprendimiento(id);
		emp.setMembresia("GRATIS");
		emp.setEmpTheme(null);
		System.out.print("entramos a bajar categoria");
		List<Producto>productos= new ArrayList<>();
		List<CategoriaProducto> cate = emp.getCategorias();
		/*List<CategoriaProducto> cate2 = new ArrayList<>();*/
		CategoriaProducto nueva = new CategoriaProducto() ;
		nueva.setEmprendimiento(emp);
		nueva.setNombre("Todos");
		nueva.setPrimero(true);
		nueva.setProductos(productos);
		cps.guardar(nueva);
		for (Producto producto : emp.getProductos()) {
			producto.setCategoria_producto(nueva);
			ps.guardarProducto(producto);
			productos.add(producto);
		}
		System.out.println("lalalala");
		cate.clear();
		cate.add(nueva);
		emp.setCategorias(cate);
		guardarEmprendimiento(emp);
		
		
		System.out.println("pasamos las categorias");
		for (CategoriaProducto categoriaProducto : cate) {
			cps.borrarCategoriaSola(categoriaProducto.getId());
			System.out.println(categoriaProducto.getNombre());
		}
	}

	public void guardarPreferencias(String idEmprendimiento, String empLayout, String empTheme) {
		Emprendimiento emp = buscarEmprendimiento(idEmprendimiento);
		emp.setEmpLayout(empLayout);
		emp.setEmpTheme(empTheme);
		guardarEmprendimiento(emp);
	}

	public List<Emprendimiento> buscarEmprendimientoPorProductoSolo(String id) {
		List<Emprendimiento> emp = er.buscarPorProducto(id);
		return emp;
	}


	public void modificarProductosEmprendimiento(String idEmprendimiento, String idProducto) {
		Producto producto = ps.buscarProducto(idProducto);
		Producto productoAEliminar = null;
		Emprendimiento emp = buscarEmprendimiento(idEmprendimiento);
		List<Producto>productos = emp.getProductos();
		for (Producto producto2 : productos) {
			if(producto2.getId().equals(producto.getId())) {
				productoAEliminar = producto2;
			}
		}
		productos.remove(productoAEliminar);
		emp.setProductos(productos);			
		er.save(emp);
		
	}
	
	  public void timerNuevoEmprendimiento(String idEmprendimiento) {
	    	
	    	Timer timer =new Timer();
	    	TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					Emprendimiento emp = buscarEmprendimiento(idEmprendimiento);
				emp.setContadorPedidos(0);
				guardarEmprendimiento(emp);
						timer.cancel();
						timerNuevoEmprendimiento(emp.getId());	
				}
			};
	    	timer.schedule(task,6000*60*60*24*30);	
	    }
	  
	  public Emprendimiento todos(String id) {
	    	
	    	Emprendimiento a = er.getById(id);
	    	return a;
	    }

}
