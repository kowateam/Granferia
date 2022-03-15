package com.app.grantienda.seguridad;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.app.grantienda.entidades.User;
import com.app.grantienda.enums.Provider;
import com.app.grantienda.service.UserDetails;
import com.app.grantienda.service.UsuarioService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private UsuarioService us;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String mail = oAuth2User.getEmail();
		String nombre = oAuth2User.getName();
		System.out.println("el usuario autenticado por google " +mail);
		System.out.println("el usuario autenticado por google " +nombre);
		User usuario = us.buscarPorMail(mail);	
		if(usuario == null) {
			try {
				
				us.createNewuserAfterOAuthLoginSucces(mail,nombre,Provider.GOOGLE);
				HttpSession session = request.getSession();
				User usuario1 = us.buscarPorMail(mail);	
				session.setAttribute("usersession", usuario1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			us.modificaruserOAuth(nombre, mail,Provider.GOOGLE);
			HttpSession session = request.getSession();
			User usuario1 = us.buscarPorMail(mail);	
			session.setAttribute("usersession", usuario1);
		}
			
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
