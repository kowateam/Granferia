package com.app.grantienda.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.ProductoGuardado;
import com.app.grantienda.entidades.User;
import com.app.grantienda.enums.Provider;
import com.app.grantienda.enums.Roles;
import com.app.grantienda.repositorio.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UsuarioService {
 
	@Autowired
    private UserRepository userRepositorio;
	@Autowired
	private NotificacionService notificacionService;	
	@Autowired
	private ProductoGuardadoService pgs;	
	@Autowired
	private ProductoService ps;	
	
    @Transactional
	public void registraruser(String mail,String password,String regNombre,String celular,String avatar,String siteURL) throws Exception {
    	
    	validar(mail,password,regNombre,avatar);
    	
    	User usuario = userRepositorio.buscarUserPorMail(mail);
    	
    	if(usuario!= null) {
    		throw new Error("Tuvimos un problema al procesar tu registro");
    	}

    	User user = new User();
    	user.setMail(mail);
    	String encriptada = new BCryptPasswordEncoder().encode(password);
    	user.setPassword(encriptada);
    	user.setRole(Roles.USER);
    	user.setAlta(new Date());
    	user.setRegNombre(regNombre);
    	user.setAvatar(avatar);
    	user.setUserActivo(false);
    	user.setCelular(celular);
    	
    	String random = RandomString.make(64);
    	user.setCodigoDeVerificacion(random);
   
    	 userRepositorio.save(user);
    	 enviarMaildeConfirmacion(user, siteURL);
    	 timerReg(user);
    	
	}
    
    public void timerReg(User user) {
    	
    	Timer timer =new Timer();
    	TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
			
				boolean activo=user.getUserActivo();
				
				if(activo = false) {
					
					userRepositorio.delete(user);
					timer.cancel();
				}else{
					
					timer.cancel();
				}
			}
		};
    	timer.schedule(task,900000);	
    }

    public void enviarMaildeConfirmacion(User user,String siteURL) throws Exception {
    	
		// VERIFICAR CUENTA:
		String cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

		// Logo
		cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";

		cuerpo += "<tr><td colspan=\"1\">";

		// Título
		cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">¡Hola " + user.getRegNombre()+"!</h1>";
		
		// Copy
		cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Terminá de verificar tu cuenta de Granferia con el siguiente botón.</p>";
		
		// CTA
		String verificacionURL= siteURL+ "/verificar?code=" + user.getCodigoDeVerificacion();
		cuerpo +="<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href='" + verificacionURL +"'>Verificar</a>";
		
		cuerpo += "</td></tr>";
		cuerpo += "</table>";

		// Pie del mail
		cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 14px; line-height: 20px; text-align: center; width: 280px; color: #000000; margin: 30px auto\">Si no fuiste vos quien ha registrado esta cuenta, <a href=\"#\" target=\"_blank\" style=\"color: #666666; text-decoration: underline\" >avisanos</a>.</p>";

		notificacionService.enviar(user.getMail(), "Verificar cuenta",cuerpo);
    }
    
    
    @Transactional
    public User modificaruser(String id,String celular,String nombre,String clave) throws Error {
    	
    	//validar(mail,clave);
    	
    	Optional<User> respuesta = userRepositorio.findById(id);
    	if(respuesta.isPresent()) {
    	User user = respuesta.get();
    	user.setRegNombre(nombre);
    	String encriptada = new BCryptPasswordEncoder().encode(clave);
    	user.setPassword(encriptada);
    	user.setCelular(celular);
    	
    	userRepositorio.save(user);
    	return user;
    	}else {
    		throw new Error ("No se encontro al user solicitado.");
    	}
    }
    
        
    @Transactional
    public void eliminar(String iduser){
    	
    	 Optional<User> respuesta = userRepositorio.findById(iduser);
    	 if(respuesta.isPresent()) {

    		 userRepositorio.deleteById(iduser);
    		 
    	 }else {
    		 throw new Error("No se encontro al user.");
    	 }
    }
		public User buscarPorMail(String mail) {
        return userRepositorio.buscarUserPorMail(mail);
    }
		public User buscarPorNombre(String nombre) {
			return userRepositorio.buscarUserPorNombre(nombre);
		}
		public User buscarPorId(String id) {
	        return userRepositorio.buscarUserPorId(id);
	    }
		public User buscarPorCodigo(String codigoVerificacion) {
	        return userRepositorio.findByCodigoVerificacion(codigoVerificacion);
		}
		public User buscarPorCodigoPass(String codigoDeContrasena) {
	        return userRepositorio.findByCodigoPass(codigoDeContrasena);
		}
		
		public Boolean verificar(String codigoDeVerificacion) {
			
			User user = userRepositorio.findByCodigoVerificacion(codigoDeVerificacion);
			if (user == null) {
				return false;
			}else {
				user.setCodigoDeVerificacion(null);
				user.setUserActivo(true);
				userRepositorio.save(user);
				return true;
			}
		}

		   public void timerPass(User user) {
		    	
		    	Timer timer =new Timer();
		    	TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						String resp =user.getCodigoDeContrasena();
						if(resp!=null) {
							user.setCodigoDeContrasena(null);
							userRepositorio.save(user);

							timer.cancel();
						}else {
							timer.cancel();
						}
					}
				};
		    	timer.schedule(task,90000);	
		    }
		
		@Transactional
		public void enviarRecupero(String mail,String siteURL) throws Exception{
			
			User user = userRepositorio.buscarUserPorMail(mail);

			if(user != null) {
				timerPass(user);

				// RESTABLECER CONTRASEÑA
				String cuerpo= "<table border=\"0\" align=\"center\" cellspacing=\"0\" bgcolor=\"#FAFAFA\" style=\"width:100%; background-color: #FAFAFA\">";

				// Logo
				cuerpo += "<tr><td align=\"center\" colspan=\"1\" style=\"text-align: center; border-bottom: 1px solid #F1F1F1\"><img style=\"display: block; margin: 40px auto\" src=\"https://granferia.online/img/gt/favicon/icon-tr.png\" width=\"64\" /></td></tr>";
		
				cuerpo += "<tr><td colspan=\"1\">";
		
				// Título
				cuerpo += "<h1 style=\"font-family: 'Noto Serif', serif; font-weight: 700; font-size: 28px; line-height: 28px; margin: 30px 0; color: #000000; text-align: center; margin: 40px 0 30px 0\">Hola " + user.getRegNombre()+",</h1>";
				
				// Copy
				cuerpo += "<p style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 24px; margin: 0 auto 40px auto; color: #000000; text-align: center; width: 300px\">Recibimos una solicitud para restablecer tu contraseña, en el caso de no haber sido vos, desestimá este mail.</p>";
				
				String random = RandomString.make(64);
		    	user.setCodigoDeContrasena(random);
				String verificacionURL= siteURL+ "/recuperar?code=" + user.getCodigoDeContrasena();

				// CTA
				cuerpo +="<a style=\"font-family: 'Noto Sans', sans-serif; font-size: 16px; line-height: 50px; display: block; color:#FFFFFF; background-color: #000000; width:332px; height:50px; margin: 0 auto 40px auto; text-decoration: none; text-align: center; font-weight: 700\" target=\"_blank\" href='" + verificacionURL +"'>Restablecer</a>";
				
				cuerpo += "</td></tr>";
				cuerpo += "</table>";
				
				notificacionService.enviar(mail, "Cambio de contraseña",cuerpo);
			}else {
				throw new Error("El email ingresado es incorrecto.");
			}
			
		}
		
		
		@Transactional
		public void recuperarContrasena(String mail,String newPassword ) throws Error, Exception {
			
			User user = userRepositorio.buscarUserPorMail(mail);
			
			String encripted = new BCryptPasswordEncoder().encode(newPassword);
			user.setPassword(encripted);
			
			userRepositorio.save(user);
		}
		
		@Transactional
		 public User cambiarAvatar(String mail,String avatar){
			 User user = userRepositorio.buscarUserPorMail(mail);
			 
			 user.setAvatar(avatar);
			 return userRepositorio.save(user);
		    }
		
		
		public List<User>lista(){
			return userRepositorio.findAll();
		}
		
		public User find(String id) {
			
			return userRepositorio.findById(id).get();
		}

		
	    private void validar(String mail, String clave,String nombre,String avatar) throws Error {

	        if (mail.isEmpty()) {
	            throw new Error("Debe ingresar su mail");
	        }

	        if (clave.isEmpty() || clave.length() <= 6) {
	            throw new Error("Debe ingresar la clave, no debe tener menos de 6 caracteres");
	        }
	        if (nombre.isEmpty()) {
	            throw new Error("Debe ingresar su nombre");
	        }
	  
	        if (avatar.isEmpty()) {
	            throw new Error("Debe ingresar su avatar");
	        }
	 
	    }

		public User createNewuserAfterOAuthLoginSucces(String email, String nombre,Provider provider) {
			User user = new User();
			user.setMail(email);
			user.setRegNombre(nombre);
			user.setUserActivo(true);
			user.setAlta(new Date());
			user.setRole(Roles.USER);
			user.setAvatar("avatar-01");
			user.setProvider(provider);
			
			return userRepositorio.save(user);
			
		}

		public void modificaruserOAuth(String nombre, String mail,Provider provider) {
			User us = userRepositorio.buscarUserPorMail(mail);
			if(us.getRegNombre()==null) {
				us.setRegNombre(nombre);
			}
			us.setProvider(provider);
			userRepositorio.save(us);
		}
		
		public List<User> admin(){
			List<User> admins = userRepositorio.buscadAdmin();
			return admins;
		}
		
		@Transactional
		public void guardaCrearEmpNotifi(String id) {
			Optional<User> user = userRepositorio.findById(id);
			User usuario=null;
			if(user.isPresent()) {
				usuario= user.get();
			}
			usuario.setCrearEmpNotifi(true);
			userRepositorio.save(usuario);
		}

		@Transactional
		public void guardarTelefono(String id, String celular) {
			Optional<User> user = userRepositorio.findById(id);
			User usuario=null;
			if(user.isPresent()) {
				usuario= user.get();
			}
			usuario.setCelular(celular);
			userRepositorio.save(usuario);
		}
		
		@Transactional
		public void crearEmpNotifi(String id) {
			User user = buscarPorId(id);
			user.setCrearEmpNotifi(null);
			user.setMostrarCrearEmp(true);
		}

		@Transactional
		public List<User> buscarPorRoleAdmin() {
			 List<User>usuarios =userRepositorio.buscadAdmin();
			return usuarios;
		}

		@Transactional
		public void guardarProductoUsuario(String id,String idProducto) {
			User usuario = buscarPorId(id);
			System.out.println("el usuario :"+ usuario.getRegNombre());
			List<ProductoGuardado>productos= usuario.getProductosGuardados();
			for (ProductoGuardado productoGuardado : productos) {
				System.out.println("los productos guardados en el usuario son :"+productoGuardado.getIdProducto());
			}
			ProductoGuardado pg = pgs.buscarProducto(idProducto);
			if(pg == null) {
				ProductoGuardado pg1 = new ProductoGuardado();
				pg1.setIdProducto(idProducto);
				pgs.guardarProducto(pg1); 
				productos.add(pg1);
			}
			//productos.remove(pg);
			if(pg != null) {
				
				pgs.borrarProductoGuardado(pg);
			}
			userRepositorio.save(usuario);	
		}

		public List<User> buscarTodosLosUsuarios() {
			List<User> usuarios = userRepositorio.findAll();
			return usuarios;
		}

}
