package br.com.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.manager.domain.UserDto;
import br.com.manager.filters.JwtRequestFilter;
import br.com.manager.model.AuthenticationRequest;
import br.com.manager.model.AuthenticationResponse;
import br.com.manager.service.IUserService;
import br.com.manager.util.JwtUtil;
import io.swagger.annotations.Api;

@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"br.com.manager"})
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

}

@Api(tags = "Autenthicate", description = "API Customer")
@RestController
class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		final UserDetails userDetailsNew = userDetailsService
				.loadUserByUsernameAndPassword(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		if(userDetailsNew != null) {
			final String jwt = jwtTokenUtil.generateToken(userDetailsNew);
			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}else {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
				//Mock for test.	
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	}

}

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/h2/**") // este libera o acesso sem precisar logar ou token
           .antMatchers("/assets/**")
           .antMatchers("/layout/**")
           .antMatchers("/pages/**")
           .antMatchers("/swagger-ui.html/**") 
      	//   .antMatchers("/h2")
      	   .antMatchers("http://localhost:8080/main/api/")
      	   .antMatchers("main/api/***");
    }
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests()
				.antMatchers("/authenticate").permitAll()
				.antMatchers(
			            "/v2/api-docs", 
			            "/swagger-resources/**",  
			            "/swagger-ui.html", 
			            "/webjars/**" ,
			            "/swagger.json").permitAll()
				.antMatchers("/h2").permitAll()
				.antMatchers("/main/api/").permitAll()
				.antMatchers("/main/api/**").permitAll()
				.antMatchers("/customer/**").authenticated()
				.antMatchers("customer/hello-world**").permitAll()
				.antMatchers("http://localhost:8080/swagger-ui.html").permitAll()
				.antMatchers("http://localhost:8080/main/api/").permitAll()
				.antMatchers("/h2/").permitAll()
				.antMatchers("http://localhost:8080/h2").permitAll()
				.anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

}