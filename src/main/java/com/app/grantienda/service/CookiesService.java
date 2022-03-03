package com.app.grantienda.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.User;

@Service
public class CookiesService {

	@Transactional
	public void setCookies(HttpServletResponse response,HttpSession session) {
	
		Cookie aceptaCookies = new Cookie("cookie", "acepta");
		aceptaCookies.setMaxAge(60*60*24*730);
		session.setAttribute("spinner", "spinner");
		response.addCookie(aceptaCookies);
	}
	
	@Transactional
	public void setCookiesId(HttpServletResponse response,HttpSession session) {
		User usuario = (User) session.getAttribute("usersession");
		Cookie sessionId = new Cookie("SESSIONID", usuario.getId());
		sessionId.setMaxAge(60*60*24);
		response.addCookie(sessionId);
	
	}
	@Transactional
	public void sacarSpinner(HttpSession session) {
		session.removeAttribute("spinner");
	}
}
