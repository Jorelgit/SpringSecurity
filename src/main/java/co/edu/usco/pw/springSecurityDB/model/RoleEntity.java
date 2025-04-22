package co.edu.usco.pw.springSecurityDB.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

// Creo la nueva entidad correspondiente a los Roles

@ToString(exclude = "users") // Evita ciclo en toString
@Entity
@Data
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long role_id;

    @Column(nullable = false, unique = true)
    private String name;


    // Esto es necesario para que JPA mapee bien la relación bidireccional.

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // Esto evita que se serialice el set de usuarios
    private Set<UserEntity> users = new HashSet<>();


    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
