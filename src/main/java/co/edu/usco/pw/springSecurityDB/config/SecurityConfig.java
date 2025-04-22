package co.edu.usco.pw.springSecurityDB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import co.edu.usco.pw.springSecurityDB.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;

	@Autowired
	private CustomLoginSuccessHandler successHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/api/registrar", "/debug/user/{username}", "/css/**", "/static/**", "/js/**", "/images/**").permitAll() 	// permite el acceso libre a estas rutas
						.requestMatchers("/user/**").hasRole("USER")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/editor/**").hasRole("EDITOR")
						.requestMatchers("/creator/**").hasRole("CREATOR")
						//.requestMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
						.requestMatchers("/new/**").hasAnyRole("ADMIN", "CREATOR")
						.requestMatchers("/edit/**").hasAnyRole("ADMIN", "EDITOR")
						.requestMatchers("/delete/**").hasRole("ADMIN")
						.anyRequest().authenticated())
						.formLogin(form -> form
								.loginPage("/login")
								.failureUrl("/login?error")
								.successHandler(successHandler)	// aquí usamos tu handler personalizado
								.permitAll())
						.logout(logout -> logout.permitAll())
						.exceptionHandling(ex -> ex
								.accessDeniedPage("/403"))

						.authenticationProvider(authenticationProvider()); // authenticationProvider


		return http.build();
	}

}
