package co.edu.usco.pw.springSecurityDB.services;

import co.edu.usco.pw.springSecurityDB.model.RoleEntity;
import jakarta.transaction.Transactional;
import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import co.edu.usco.pw.springSecurityDB.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// Hago la modificacion correspondiente, ya que la entidad user ya no tiene el atributo role y
	// ya que se hizo una modificación importante en la entidad UserEntity (cambié de un solo role a una colección Set<RoleEntity>),
	// es necesario ajustar esta clase para que funcione correctamente con los nuevos roles múltiples.

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Ejecutando loadUserByUsername para: " + username);


		try{
			UserEntity user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("Usuario '" + username + "' no encontrado en la base de datos"));
			System.out.println(user);

			System.out.println("Roles crudos del usuario: " + user.getRoles().stream().map(RoleEntity::getName).toList());

			// user.getRoles() obtiene todos los roles del usuario.
			// SimpleGrantedAuthority("ROLE_" + nombre) convierte cada rol en un GrantedAuthority.
			// Se usa el constructor completo de User para establecer el estado de la cuenta (enabled, accountNonExpired, etc.)

			Set<GrantedAuthority> authorities = user.getRoles().stream()
					.filter(role -> role != null && role.getName() != null)  // Asegura que no haya nulos
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
					.collect(Collectors.toSet());



			System.out.println("Usuario encontrado: " + user.getUsername());
			System.out.println("Habilitado: " + user.isEnabled());
			System.out.println("Contraseña: " + user.getPassword());

			System.out.println("Roles encontrados:");
			user.getRoles().forEach(role -> {
				if (role == null) {
					System.out.println("  -> ¡OJO! Rol nulo encontrado");
				} else {
					System.out.println("  -> " + role.getName());
				}
			});

			System.out.println("Cantidad de roles encontrados: " + user.getRoles().size());

			System.out.println("Authorities construidas:");
			authorities.forEach(a -> System.out.println("  -> " + a.getAuthority()));

			return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					user.isEnabled(),
					true,
					true,
					true,
					authorities
			);


		}catch(Exception e){
			System.out.println("Error en LoadUserByUsername: " + e.getMessage());
			e.printStackTrace();
			throw new InternalAuthenticationServiceException("Fallo al autenticar ", e);
		}

	}
}