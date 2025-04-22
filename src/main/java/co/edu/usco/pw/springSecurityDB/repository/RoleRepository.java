package co.edu.usco.pw.springSecurityDB.repository;

import co.edu.usco.pw.springSecurityDB.model.RoleEntity;
import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Este es el RoleRepository, que me permite la interaccion con la base de datos
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);

    List<RoleEntity> findByUsersContaining(UserEntity user);

}
