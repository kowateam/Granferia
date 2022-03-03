package com.app.grantienda.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.grantienda.entidades.Emprendimiento;
import com.app.grantienda.entidades.Foto;
import com.app.grantienda.repositorio.EmprendimientoRepositorio;
import com.app.grantienda.service.FotoService;




@Controller
@RequestMapping("/foto")
public class FotoController {
	
	@Autowired
	private FotoService fs;
	@Autowired
	private EmprendimientoRepositorio er;
	
/*	@PostMapping("/subir")
	public String subirFotoProducto(String id,MultipartFile archivo) throws IOException {
	
		fs.guardarFotoProducto(id,archivo);
		
		return "redirect:/emp/landingedit";
		
	}*/
	
	@PostMapping("/subirlogo")
	public String subirLogo(String id,MultipartFile archivo) throws IOException {
	
		String id1=null;
		fs.guardarLogo(id,archivo);
		Optional<Emprendimiento> emp = er.findById(id);
		if(emp.isPresent()) {
			Emprendimiento empr = emp.get();
			 id1= empr.getId();
		}
		
		return "redirect:/emp/landingedit/"+ id1;
		
	}
	
	@PostMapping("/subirfotoproducto")
	public String subirFotoProducto(@RequestParam String idproducto,@RequestParam MultipartFile archivo,@RequestParam(required = false) String textoAlternativo,@RequestParam String IdEmprendimiento,ModelMap modelo) throws IOException {

		Foto foto =fs.guardarFotoProducto(idproducto,archivo, textoAlternativo);
		modelo.addAttribute("fotoid",foto.getId());
		return "redirect:/producto/cargarfoto/"+ idproducto + "/" + IdEmprendimiento ;
		
	}
	@PostMapping( "/borrarfoto")
	public String borrarFoto(@RequestParam String id,@RequestParam String idproducto,@RequestParam String idEmprendimiento) {
		Foto foto =fs.buscarFotoPorId(id);
		fs.borrarFoto(foto);
		
		return "redirect:/producto/cargarfoto/"+ idproducto + "/" + idEmprendimiento ;
	}
	 @GetMapping("/load/{id}")
	    public ResponseEntity<byte[]> photo(@PathVariable String id) {
	       
	 
	    	Foto foto = fs.buscarFotoPorId(id);
	    	
	        String mime = foto.getMime();

	        MediaType content = checkContent(mime);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(content);

	        return new ResponseEntity<>(foto.getContenido(), headers, HttpStatus.OK);
	    }
	    
	    private MediaType checkContent(String mime) {

	         if (mime.contains("image")) {  

	            if (mime.contains("png")) {
	                return (MediaType.IMAGE_PNG);
	            } else {
	                return (MediaType.IMAGE_JPEG);
	            }
	        
	    }
	         return MediaType.MULTIPART_FORM_DATA;
	
}}
