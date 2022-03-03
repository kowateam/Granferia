package com.app.grantienda.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.app.grantienda.repositorio.UserRepository;
@Service
public class UserDetails implements UserDetailsService{
	@Autowired
    private UserRepository userRepositorio;
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		
		com.app.grantienda.entidades.User user = userRepositorio.buscarUserPorMail(mail);
		
			
		 if (user != null && user.getUserActivo()==true) {
	            
	            List<GrantedAuthority> permisos = new ArrayList<>();
	            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());
	            permisos.add(p1);
	            //Boolean enabled = !user.isuserActivo();
	            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	            HttpSession session = attr.getRequest().getSession(true);
	            session.setAttribute("usersession", user);
	            return new User(user.getMail(), user.getPassword(), permisos);
	        } else {
	            return null;
	        }
		}
	    

}
