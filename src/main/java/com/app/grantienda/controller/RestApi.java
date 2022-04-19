package com.app.grantienda.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.grantienda.entidades.Foto;
import com.app.grantienda.entidades.Pedido;
import com.app.grantienda.entidades.User;
import com.app.grantienda.service.FotoService;
import com.app.grantienda.service.NotificacionService;
import com.app.grantienda.service.PedidoService;
import com.app.grantienda.service.ProductoService;
import com.app.grantienda.service.SeguidoresService;
import com.app.grantienda.service.UsuarioService;
import com.app.grantienda.service.ValoracionService;

@RestController
@RequestMapping("/rest")
public class RestApi {
	@Autowired
	private UsuarioService us;
	@Autowired
	private FotoService fs;
	@Autowired
	private SeguidoresService seguidoresService;
	@Autowired
	private ProductoService ps;
	@Autowired
	private ValoracionService valoracionService;
	@Autowired
	private NotificacionService ns;
	
	@Autowired
	private PedidoService pedidoService;
	@GetMapping(path = "/{id}/{celular}")
	public void guardarTelefonoUsuario(@PathVariable String id,@PathVariable String celular){
		try {
			us.guardarTelefono(id,celular);
			
		} catch (Exception e) {
			throw new Error("no se pudo guardar el celular.");
		}
		}
	@GetMapping(path="/sabermas/{id}")
	public void notificarSaberMas(@PathVariable String id) throws Exception {
		List<User> usuarios = us.buscarPorRoleAdmin();
		User usuario= us.buscarPorId(id);
		for (User user : usuarios) {
			String cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

			// Logo
			cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

			cuerpo += "<tr><td colspan=\"1\">";
	
			// Título
			cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Registro de marca</h1>";
							
			// Copy
			cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Se solicitó más información sobre el registro de marca. Podés responder a esta solicitud escribiendo a <a href=\"mailto:"+ usuario.getMail() +"\" target=\"_blank\" style=\"color:#000000\">" + usuario.getMail() + "</a></p>";
			
			cuerpo += "</td></tr>";
			cuerpo += "</table>";
			
			ns.enviar(user.getMail(), "Solicitud de información", cuerpo);
		}
		
	}
	@GetMapping(path="/removervolver")
	public void removerVolver(HttpSession session) {
		session.removeAttribute("volverCargaVariable");
		
	}
	
	@GetMapping(path = "/borrarfoto/{id}")
	public void borrarFoto(@PathVariable String id) {
		Foto foto =fs.buscarFotoPorId(id);
		fs.borrarFoto(foto);
	}
	
	@GetMapping(path = "/borrarproducto/{id}/{idEmprendimiento}")
	public void borrarProducto(@PathVariable String id,@PathVariable String idEmprendimiento) {
		try {
			ps.borrarProducto(id,idEmprendimiento);
			
		} catch (Exception e) {
			throw new Error("no se pudo borrar el producto" +e);
		}

	}
	
	@GetMapping(path = "/borrarproductosinfoto/{id}")
	public void borrarProductoSinFoto(@PathVariable String id) {
		try {
			ps.borrarProductoSinFoto(id);
		} catch (Exception e) {
			throw new Error("no se pudo borrar la foto "+e);
		}}
		
	
	@GetMapping(path = "/guardarproducto/{idusuario}/{idproducto}")
	public void guardarProducto(@PathVariable String idusuario,@PathVariable String idproducto) {
		try {
			us.guardarProductoUsuario(idusuario, idproducto);
		}catch (Exception e) {
			throw new Error("no se pudo guardar el producto " +e);
		}
		
		}
	@GetMapping(path = "/guardarcelular/{idusuario}/{celular}")
	public void guardarCelular(@PathVariable String idusuario,@PathVariable String celular) {
		try {
			us.guardarTelefono(idusuario, celular);
		}catch (Exception e) {
			throw new Error("no se pudo guardar el celular " +e);
		}
		
	}
	
	@GetMapping("/seguir/{idEmprendimiento}/{idUser}")		
	public void Seguir(@PathVariable String idEmprendimiento,@PathVariable String idUser) {

		seguidoresService.sumarSeguidor(idEmprendimiento,idUser);	
}
	
	@GetMapping("/valorar/{prod}/{tiempo}/{producto}/{servicio}/{comentario}")		
	public String valoracion(@PathVariable String tiempo,@PathVariable String producto,@PathVariable String servicio,@PathVariable String comentario,@PathVariable String prod, HttpSession session) {
		User usuario= (User) session.getAttribute("usersession");
		String idUser = usuario.getId();
		valoracionService.guardarValoracion(tiempo, producto, servicio, comentario, idUser, prod);
			return tiempo;
}
	
	
	
}
