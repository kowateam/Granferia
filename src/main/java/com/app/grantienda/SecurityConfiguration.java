package com.app.grantienda;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


import com.app.grantienda.seguridad.CustomOAuth2UserService;
import com.app.grantienda.seguridad.OAuth2LoginSuccessHandler;
import com.app.grantienda.service.UserDetails;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration<usuarioService> extends WebSecurityConfigurerAdapter{

	
	@Autowired
	public UserDetails userService;
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService).
		passwordEncoder(new BCryptPasswordEncoder());
	}

	
	
    
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf ()
		.csrfTokenRepository (CookieCsrfTokenRepository.withHttpOnlyFalse ());
			http.authorizeRequests()
				.antMatchers("/css/*", "/js/*", "/img/*", "/**","/main/**","/usuario/**","/actividad/**","/login/**", "/glosario/**").permitAll()
				.and().formLogin()
					.loginPage("/ingresar")
						.loginProcessingUrl("/logincheck")
						.usernameParameter("mail")
						.passwordParameter("password")
						.defaultSuccessUrl("/inicio")
						.failureUrl("/ingresar?error=error")
						.permitAll()
				.and().logout()
					.logoutUrl("/salir")
					.logoutSuccessUrl("/")
					.permitAll()
					.and()
					.oauth2Login()
					.loginPage("/ingresar")
					.userInfoEndpoint().userService(oauth2UserService)
					.and()
					.successHandler(oAuth2LoginSuccessHandler);
            
        }
	


    @Autowired
    private CustomOAuth2UserService oauth2UserService;
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    
  
}
     





