package com.app.grantienda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.grantienda.entidades.User;
import com.app.grantienda.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService us;
	@PostMapping("/avatar")
	public String modificarAvatar(HttpSession session,@RequestParam String avatar) {


		User usuario= (User) session.getAttribute("usersession");	 
		User usuario1 = us.cambiarAvatar(usuario.getMail(), avatar);
		session.setAttribute("usersession", usuario1);

		
	return "redirect:/inicio";
}
}