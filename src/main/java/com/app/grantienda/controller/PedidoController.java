package com.app.grantienda.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Pedido;
import com.app.grantienda.entidades.ProductoPedido;
import com.app.grantienda.entidades.User;
import com.app.grantienda.service.EmprendimientoService;
import com.app.grantienda.service.HistorialPedidoService;
import com.app.grantienda.service.NotificacionService;
import com.app.grantienda.service.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoService ps;
	@Autowired
	private NotificacionService ns;
	
	@Autowired
	private HistorialPedidoService hps;
	
	@Autowired
	private EmprendimientoService es;
	
	@PostMapping("/crearpedido")
	public String crearPedido(@RequestParam String nombreProducto,@RequestParam Integer prodCantidad,@RequestParam String prodNota,@RequestParam String idUsuario,@RequestParam String idEmprendimiento,@RequestParam String precio, @RequestParam String total,@RequestParam String idProducto,String variable,String item) {
		//,@RequestParam(required = false) String nombreVariable,@RequestParam(required = false) String nombreItem
		Pedido pedido=null;
		try {
		
			pedido=ps.crearPedido(nombreProducto, prodCantidad, prodNota, idUsuario,precio,total, idEmprendimiento,idProducto, variable, item);
			
		} catch (Exception e) {
			throw new Error("No se pudo crear el pedido : "+e);
		}
		return"redirect:/pedido/confirmacionpedido/"+idEmprendimiento+"/"+ pedido.getId();
	}

	@GetMapping("/confirmacionpedido/{idemprendimiento}/{idPedido}")
	public String confirmacionPedido(@PathVariable String idemprendimiento,@PathVariable String idPedido, ModelMap modelo){
		Emprendimiento emp = es.buscarEmprendimiento(idemprendimiento);
		Pedido pedido = ps.buscarPedido(idPedido);
		Integer cantidad = null;
		String nombre= null;
		for (ProductoPedido i : pedido.getProductos()) {
			cantidad=i.getCantidad();
			nombre =i.getNombre();
		}
		modelo.addAttribute("emprendimiento",emp);
		modelo.addAttribute("pedido",pedido);
		modelo.addAttribute("cantidad",cantidad);
		modelo.addAttribute("nombre",nombre);
		
		return "confirmacionPedido.html";
	}
	
	@PostMapping("/aceptarrechazar")
	public String aceptarPedido(@RequestParam String idPedido,@RequestParam String estadoEmp,@RequestParam(required = false) String nota) throws Exception {
		Pedido pedido = ps.aceptarRechazar(idPedido,estadoEmp,nota);
		hps.guardarHistorial(pedido, estadoEmp,nota);
		
		return"redirect:/pedido/lista/"+pedido.getEmprendimiento().getId();
	}
	
	@GetMapping("/lista/{idemprendimiento}")
	public String paginaProductoCompra(@PathVariable String idemprendimiento,ModelMap modelo){
		Emprendimiento emp = es.buscarEmprendimiento(idemprendimiento);
		List<Pedido> pedido = ps.buscarPedidoPorEmprendimiento(idemprendimiento);
		modelo.addAttribute("emprendimiento",emp);
		modelo.addAttribute("pedido",pedido);
		Integer cantidad = 5 - pedido.size();
		modelo.addAttribute("cantidadpedido",cantidad);
		
		
		return "paginaPedido.html";
	}

	@GetMapping("/pedidosrealizados")
	public String paginaProductoCompra(HttpSession session,ModelMap modelo){ 
		User user = (User) session.getAttribute("usersession");
		List<Pedido> pedido=(List<Pedido>) ps.buscarPorUsuario(user.getId());
		modelo.addAttribute("pedido",pedido);
		
		return "paginaPedidoRealizado.html";
	}

	
	@GetMapping("/cancelar")
	public String cancelarPedido(@RequestParam String idpedido,@RequestParam String motivo) {
		System.out.println("entramos a cancelar pedido");
		ps.cancelarPedido(idpedido, motivo);
		return "redirect:/";
	}
	
}
