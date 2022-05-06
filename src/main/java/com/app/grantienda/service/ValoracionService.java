package com.app.grantienda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.Producto;
import com.app.grantienda.entidades.User;
import com.app.grantienda.entidades.Valoracion;
import com.app.grantienda.repositorio.EmprendimientoRepositorio;
import com.app.grantienda.repositorio.ProductoRepositorio;
import com.app.grantienda.repositorio.UserRepository;
import com.app.grantienda.repositorio.ValoracionRepository;


@Service
public class ValoracionService {
	
	
	@Autowired
	private ValoracionRepository valoracionRepository ;
	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private ProductoRepositorio productoRepository ;
	@Autowired
	private EmprendimientoRepositorio emprendimientoRepository ;
	@Transactional
	public String ValoracionP(String id) {
		
		String valoracionP = valoracionRepository.buscarValoracionDeProducto(id);
		if(valoracionP== null) {
			return valoracionP = "";
		}
		Double num = Double.valueOf(valoracionP);
		valoracionP = String.valueOf(num);	
		
		return valoracionP;
	}
	
	@Transactional
	public String ValoracionTiempo(String id) {
		
		String valoracionTiempo = valoracionRepository.buscarValoracionTiempo(id);
		if(valoracionTiempo== null) {
			return valoracionTiempo = "";
		}
		Double num = Double.valueOf(valoracionTiempo);
		valoracionTiempo = String.valueOf(num);	
		
		return valoracionTiempo;
	}
	
	@Transactional
	public String ValoracionS(String id) {
		
		String valoracionS = valoracionRepository.buscarValoracionServicio(id);
		if(valoracionS== null) {
			return valoracionS = "";
		}
		Double num = Double.valueOf(valoracionS);
		valoracionS = String.valueOf(num);	
		
		return valoracionS;
	}
	
	@Transactional
	public String ValoracionT(String id) {
		Double valoracionT;
		Double valoracionP;
		Double valoracionS;
		String vp=valoracionRepository.buscarValoracionDeProducto(id);
		String vt=valoracionRepository.buscarValoracionTiempo(id);
		String vs=valoracionRepository.buscarValoracionServicio(id);
		if(vt== null && vp== null && vs== null ) {
			return vt = "";
		}else {
		 valoracionT = Double.valueOf(vt);
		 valoracionP = Double.valueOf(vp);
		 valoracionS = Double.valueOf(vs); 
		
		Double sumP = (valoracionT + valoracionS + valoracionP)/3 ;
		String valoracion = String.format("%.2f", sumP);
		
		return valoracion;
		}
	}
	
	@Transactional
	public String ValoracionTotal(String id) {
		
		double listaProm[];
		String[] listaIds = valoracionRepository.buscarTodosProdDeUnEmp(id); 
		double sum = 0;
		if(listaIds == null) {
			return "0,0";
		}
		
		if(listaIds.length == 1) {
			return this.ValoracionT(listaIds[0]);
		}
		listaProm= new double[listaIds.length];
		for(int i=0;i<listaIds.length;i++) {
			double a=Double.valueOf(this.ValoracionT(listaIds[i]));
			listaProm[i] = a;
		}
		
		for(int i=0;i<listaProm.length;i++) {
		
			sum+=listaProm[i];
		}
		sum=sum/3;
		String sumS= String.valueOf(sum);
		return sumS;
		
		
		}
		
	
	@Transactional
	public void guardarValoracion(String tiempo, String producto, String servicio, String comentario, String idUser,String idProducto) {
	          
		String existe = valoracionRepository.buscar(idUser, idProducto);
		
		if(existe==null) {
		   Valoracion valoracion = new Valoracion();
			 valoracion.setTiempoV(tiempo);
	         valoracion.setProductoV(producto);
	         valoracion.setServicioV(servicio);
	         valoracion.setComentario(comentario);
	         valoracion.setUser(idUser);
	         valoracion.setProducto(idProducto);
	        valoracionRepository.save(valoracion);
		}else {
		
		Valoracion valoracion = valoracionRepository.getById(existe);
		
		valoracion.setTiempoV(tiempo);
        valoracion.setProductoV(producto);
        valoracion.setServicioV(servicio);
        valoracion.setComentario(comentario);
        valoracion.setUser(idUser);
        valoracion.setProducto(idProducto);
	
        valoracionRepository.save(valoracion);   
		}
	}    
	@Transactional
	public List<Producto> masValorados() {
		List<Producto> lista;
		List<Producto> lista2;
		lista = new ArrayList<>();
		lista2 = new ArrayList<>();
		String[] ids = productoRepository.buscarTodosIdsproductos();
		Random ran = new Random();
		for(int i = 0; i < ids.length;i++) {
			String num = this.ValoracionP(ids[i]);
			if(num=="") {}
			else {
				double val = Double.valueOf(num);
				if(val >= 4) {
					Producto prod = productoRepository.buscarProductoPorId(ids[i]);
					
					lista.add(prod);
				}
			}
		}
		
		if(lista.size()>8) {
			for(int i = 0; i <8;i++) {
				
				int random= ran.nextInt(lista.size());
				lista2.add(lista.get(random));
			}
			return lista2;
			
		}
		
		return lista;
	}
	
	@Transactional
	public List<String> listaidsemprendimientos(List<Producto> lista) {
		List<String> ids;
		ids= new ArrayList<>();
		for(int i = 0; i<lista.size();i++) {
			String id = lista.get(i).getId();
			ids.add(productoRepository.buscarIdEmprendimiento(id));
		}
		return ids;
	}
	
	
	@Transactional
	public List<String> listaidsemprendimientosFotos(List<String> lista) {
		List<String> ids;
		ids= new ArrayList<>();
		for(int i = 0; i<lista.size();i++) {
			String id = lista.get(i);
			ids.add(emprendimientoRepository.buscariddefoto(id));
		}
		return ids;
	}
	@Transactional
	public List<String> listaNombreEmp(List<String> lista) {
		List<String> ids;
		ids= new ArrayList<>();
		for(int i = 0; i<lista.size();i++) {
			String id = lista.get(i);
			ids.add(emprendimientoRepository.buscarNombreEmp(id));
		}
		return ids;
	}
	            
	}
	
