package co.edu.usco.pw.springSecurityDB.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	// Esto garantiza que los roles se traen junto con el usuario
	// (a veces Hibernate con EAGER no lo hace en algunos contextos).

	@Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles WHERE u.username = :username")
	Optional<UserEntity> findByUsername(@Param("username") String username);



}