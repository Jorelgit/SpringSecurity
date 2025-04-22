package co.edu.usco.pw.springSecurityDB.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


import java.util.HashSet;
import java.util.Set;

@ToString(exclude = "roles") // Evita ciclo en toString
@Entity
@Data
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long user_id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean enabled = true;


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
	)
	private Set<RoleEntity> roles = new HashSet<>();


}
