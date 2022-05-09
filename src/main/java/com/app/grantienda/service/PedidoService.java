package com.app.grantienda.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Pedido;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.entidades.ProductoPedido;
import com.app.grantienda.entidades.User;
import com.app.grantienda.repositorio.PedidoRepository;

@Service
public class PedidoService {
	@Autowired
	private UsuarioService us;
	@Autowired
	private ItemVariableSerice ivs;
	@Autowired
	private ItemProductoService is;
	@Autowired
	private ProductoPedidoService pps;
	@Autowired
	private ProductoService ps;
	@Autowired
	private NotificacionService ns;
	@Autowired
	private EmprendimientoService es;
	@Autowired
	private PedidoRepository pr;
	@Transactional
	public Pedido crearPedido(String nombreProducto,Integer prodCantidad,String prodNota,String idUsuario,String precio,String total,String idEmprendimiento,String idProducto,String Variable,String item) throws Exception {		
		Pedido pedido = new Pedido();
		
		pedido.setAlta(new Date());
		pedido.setEstado("pedidoEnviado");
		pedido.setNota(prodNota);
		User usuario = us.buscarPorId(idUsuario);
		pedido.setUser(usuario);
		pedido.setTotal(total);
		pedido.setIdProducto(idProducto);
		
		ProductoPedido pp= new ProductoPedido();
		pp.setNombre(nombreProducto);
		pp.setCantidad(prodCantidad);
		pp.setPrecio(precio);
		pp.setVariablePedido(Variable);
		System.out.println("el item del pedido "+item);
		pp.setItemPedido(item);
		pps.guardarProductoPedido(pp);
		List<ProductoPedido>productos= new ArrayList<>();
		productos.add(pp);
		pedido.setProductos(productos);
		
		Emprendimiento emp = es.buscarEmprendimiento(idEmprendimiento);
		if(emp.getContadorPedidos()== null || emp.getContadorPedidos() == 0) {
			emp.setContadorPedidos(1);
		}else {
			emp.setContadorPedidos(emp.getContadorPedidos()+1);
		}
		
		es.guardarEmprendimiento(emp);
		String cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

		// Logo
		cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

		cuerpo += "<tr><td colspan=\"1\">";

		// Título
		cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">¡Buenas noticias!</h1>";
						
		// Copy
		cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Tu emprendimiento recibió un nuevo pedido. Mirá los detalles y confirmalo en la plataforma.</p>";
		
		// Copy
		cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #666666; text-align: center; width: 300px\"><i>Si en las próximas 24 horas no confirmás la aceptación del pedido, va a ser rechazado automáticamente.</i></p>";

		// CTA
		cuerpo +="<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";
			
		cuerpo += "</td></tr>";
		cuerpo += "</table>";
		ns.enviar(emp.getUser().getMail(), "Nuevo pedido en granferia", cuerpo);
		timerReg(pedido);
		/*Producto producto = ps.buscarProducto(idProducto);
		producto.setUnidProducto(producto.getUnidProducto());*/
		pedido.setEmprendimiento(emp);
		return pr.save(pedido);
	}
	@Transactional
	public Pedido buscarPedido(String idPedido) {
		System.out.print("el id del pedido :"+idPedido);
		Optional<Pedido>pedido= pr.findById(idPedido);
		System.out.println("el pedido en buscar el pedido:" +pedido);
		Pedido pedid=null;
		if(pedido.isPresent()) {
			pedid=pedido.get();
		}
		return pedid;
	}
	
	@Transactional
	public List<Pedido> buscarPorUsuario(String id) {
		
		List<Pedido> pedidos = pr.buscarPorUser(id);
		
		return pedidos;
	}
	
	@Transactional
	public void eliminarPedido(String id) throws Exception {
		Pedido pedido=buscarPedido(id);
		ns.enviar(pedido.getUser().getMail(), "Su pedido ha cambiado de estado. ", "Su pedido paso al estado de : " + pedido.getEstado());
		pr.delete(pedido);
		
	}
	@Transactional
    public void timerReg(Pedido pedido) {
    	
    	Timer timer =new Timer();
    	TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				if(pedido.getEstado() == "pedidoEnviado") {
					try {
						eliminarPedido(pedido.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					timer.cancel();
				}else{
					
					timer.cancel();
				}
			}
		};
    	timer.schedule(task,86400000);	
    }
	@Transactional
	public void guardarPedido(Pedido pedido) {
		pr.save(pedido);
		
	}
	@Transactional
	public List<Pedido> buscarPedidoPorEmprendimiento(String idemprendimiento) {
		List<Pedido> pedido = pr.buscarPorEmprendimiento(idemprendimiento);
		return pedido;
	}
	public Pedido aceptarRechazar(String idPedido, String estadoEmp,String nota) throws Exception {
		Pedido pedido = buscarPedido(idPedido);
		pedido.setEstado(estadoEmp);
		pedido.setNota(nota);
		Integer cantidad = 0;
		Integer guardado;
		String idProducto = pedido.getIdProducto();
		List<ProductoPedido>productos=pedido.getProductos();
		for (ProductoPedido productoPedido : productos) {
			cantidad=productoPedido.getCantidad();
		}
		if(estadoEmp.equals("aceptarEmp")) {
			
			Producto prod=ps.buscarProducto(pedido.getIdProducto());
			guardado=  prod.getUnidProducto()- cantidad ;
			if(guardado == 0) {
				prod.setStock("sinStock");
				prod.setUnidProducto(null);
			}else {
				prod.setUnidProducto(guardado);
			}
			ps.guardarProducto(prod);
		}
		pr.save(pedido);
		String cuerpo= "";
		if(estadoEmp.equals("aceptarEmp"))  {
			cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

			// Logo
			cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

			cuerpo += "<tr><td colspan=\"1\">";

			// Título
			cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">¡Buenas noticias!</h1>";
							
			// Copy
			cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Tu pedido ya se encuentra en proceso. El contacto del emprendimiento lo vas a encontrar en los detalles del pedido.</p>";

			// Copy
			cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #666666; text-align: center; width: 300px\"><i>No compartas información personal o de tus tarjetas por mensaje o por email. Granferia NUNCA te va solicitar esta información.</i></p>";

			// CTA
			cuerpo +="<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";
				
			cuerpo += "</td></tr>";
			cuerpo += "</table>";
		} 
		if(estadoEmp.equals("rechazarEmp")) {
			cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

			// Logo
			cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

			cuerpo += "<tr><td colspan=\"1\">";

			// Título
			cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Pedido cancelado</h1>";
							
			// Copy
			cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Tu pedido fue cancelado por el emprendimiento. Lamentamos las malas noticias.</p>";

			// CTA
			cuerpo +="<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";
				
			cuerpo += "</td></tr>";
			cuerpo += "</table>";
		}
		ns.enviar(pedido.getUser().getMail(), "Cambio en el estado de tu pedido", cuerpo);
		return pedido;
	}
	@Transactional
	public void borrarEmprendimientoEnPedido(String id,String motivo) {
		List<Pedido> pedidos=buscarPedidoPorEmprendimiento(id);
		for (Pedido pedido : pedidos) {
		pedido.setEmprendimiento(null);
		pedido.setEstado("Cancelado");
		pedido.setNota(motivo);
		String cuerpo;
		cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

		// Logo
		cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

		cuerpo += "<tr><td colspan=\"1\">";

		// Título
		cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Pedido cancelado</h1>";
						
		// Copy
		cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Tu pedido fue cancelado por el emprendimiento.Por el motivo de "+motivo+". Lamentamos las malas noticias.</p>";

		// CTA
		cuerpo +="<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";
			
		cuerpo += "</td></tr>";
		cuerpo += "</table>";
		guardarPedido(pedido);
		try {
			ns.enviar(pedido.getUser().getMail(), "Cambio en el estado de tu pedido", cuerpo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	
	@Transactional
	public void cancelarPedido(String idpedido,String motivo) {
		System.out.println("el id del pedido en cancelar pedido: "+idpedido);
		Pedido pedido = buscarPedido(idpedido);
		System.out.println("el pedido en cancelar el pedido es: "+pedido.getIdProducto());
		if(pedido != null) {
			pedido.setEstado("rechazarEmp");
			pedido.setNota(motivo);
			guardarPedido(pedido);
			String cuerpo;
			cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

			// Logo
			cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

			cuerpo += "<tr><td colspan=\"1\">";

			// Título
			cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Pedido cancelado</h1>";
							
			// Copy
			cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Tu pedido fue cancelado por el emprendimiento.Por el motivo de '"+motivo+"'. Lamentamos las malas noticias.</p>";

			// CTA
			cuerpo +="<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href=\"https://granferia.online/\">Ir a granferia</a>";
				
			cuerpo += "</td></tr>";
			cuerpo += "</table>";
			guardarPedido(pedido);
			try {
				ns.enviar(pedido.getUser().getMail(), "Cambio en el estado de tu pedido", cuerpo);
				System.out.println("enviamos el mail");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@Transactional
	public Pedido buscarUltimoPedido(String id) {
		Pedido producto =pr.buscarPorUserUltimoPedido(id);
		return producto;
	}
	@Transactional
	public String buscarPoridpedido(String id) {
		String producto =pr.buscarPorIdPedido(id);
		return producto;
	}
	@Transactional
	public Pedido pedido(String id) {
		Pedido producto =pr.getById(id);
		return producto;
	}
	@Transactional
	public String buscarProductoPedido(String id) {
		String producto = pr.buscarProductoPedido(id);
		return producto;
	}
	@Transactional
	public String buscaruserPedido(String id) {
		String user = pr.buscarUserPedido(id);
		return user;
	}
	@Transactional
	public String buscarProductoPedidoPorUsuario(String iduser, String id) {
		String user = pr.buscarProductoPedidoPorUsuario(iduser, id);
		return user;
	}
	
	@Transactional
	public String buscarPedidosPendientes(String id) {
		String ped = pr.cantidadDePedidosPendientes(id);
		return ped;
	}
	
	
}
