package com.app.grantienda.service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Foto;
import com.app.grantienda.entidades.Producto;

import com.app.grantienda.repositorio.FotoRepositorio;



@Service
public class FotoService {
	
	@Autowired
	private FotoRepositorio fotorepo;
	@Autowired
	private ProductoService ps;
	@Autowired
	private EmprendimientoService es;
	
	@Transactional
	public Foto guardarLogo(String id,MultipartFile archivo) throws IOException{
		
		
		Emprendimiento emp = es.buscarEmprendimiento(id);
		if(emp.getFoto() != null) {
			Optional<Foto> foto = fotorepo.findById(emp.getFoto().getId());
			if(foto.isPresent()) {
				Foto foto1 = foto.get();
				foto1.setMime(archivo.getContentType());
				foto1.setNombre(archivo.getName());
				foto1.setContenido(archivo.getBytes());
				foto1.setAlta(new Date());
				emp.setFoto(foto1);
				es.guardarEmprendimiento(emp);
				fotorepo.save(foto1);
				fotorepo.flush();
				return foto1;
			}}else {
				
				Foto foto1 = new Foto();
				foto1.setMime(archivo.getContentType());
				foto1.setNombre(archivo.getName());
				foto1.setContenido(archivo.getBytes());
				foto1.setAlta(new Date());
				emp.setFoto(foto1);
				es.guardarEmprendimiento(emp);
				fotorepo.save(foto1);
				fotorepo.flush();
				return foto1;		
			}	
		return null;
	}
	
	@Transactional
	public Foto guardarFotoProducto(String id, MultipartFile archivo, String textoAlternativo)throws IOException{
		
		Producto producto = ps.buscarProducto(id);
		
		
		if (archivo != null ) {
			Foto foto = new Foto();
			foto.setMime(archivo.getContentType());
			foto.setNombre(archivo.getName());
			foto.setContenido(archivo.getBytes());
			foto.setAlta(new Date());
			foto.setTextoAlternativo(textoAlternativo);
			
			 fotorepo.save(foto);
			 fotorepo.flush();
			 
			 producto.getFoto().add(foto);
			 ps.guardarProducto(producto);
			 return foto;
		}
			return null;
		}
	
	public Foto actualizar(String idFoto,MultipartFile archivo) throws Error{
		if (archivo != null ) {
			try {
				Foto foto = new Foto();
				if (idFoto !=null) {
					Optional<Foto> respuesta = fotorepo.findById(idFoto);
					if(respuesta.isPresent()) {
						foto=respuesta.get();
					}
				}
				
			foto.setMime(archivo.getContentType());
			foto.setNombre(archivo.getName());
			foto.setContenido(archivo.getBytes());
			
			return fotorepo.save(foto);
			}catch(Exception e) {
				
				System.err.println(e.getMessage());
			}
		}
			return null;
		}
	
	@Transactional
	public void eliminarFoto(String id) {
		
		Optional<Foto>foto=fotorepo.findById(id);
		if(foto.isPresent()) {
			Foto foto1= foto.get();
			fotorepo.delete(foto1);
		}
		
	}
	@Transactional
	public Foto buscarFotoPorId(String id) {
		Foto foto1= null;
		Optional<Foto> foto = fotorepo.findById(id);
		if(foto.isPresent()) {
			 foto1 = foto.get();
		}
		return foto1;
	}
	@Transactional
	public void borrarFoto(Foto foto) {
		fotorepo.borrarFotosEnProducto(foto.getId());
		fotorepo.delete(foto);
	}
	
	/* public List<Foto> buscarFotosPorProducto(Emprendimiento emprendimiento, String idFoto) {

	        List<Foto> fotos = new ArrayList<>();

	        for (Foto foto : emprendimiento.getf) {
	            if (idMateria.equals(examen.getMateria().getId())) {
	                examenes.add(examen);
	            }
	        }

	        return fotos;
	    }*/
}
