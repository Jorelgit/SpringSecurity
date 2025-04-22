package co.edu.usco.pw.springSecurityDB.controller;

import co.edu.usco.pw.springSecurityDB.model.RoleEntity;
import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import co.edu.usco.pw.springSecurityDB.repository.RoleRepository;
import co.edu.usco.pw.springSecurityDB.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserWithRoles(@PathVariable String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserEntity user = userOptional.get();

        // 🔧 Cargar roles manualmente
        List<RoleEntity> roles = roleRepository.findByUsersContaining(user);
        user.setRoles(new HashSet<>(roles)); // Asegúrate de tener un setRoles()

        System.out.println("Usuario: " + user.getUsername());
        System.out.println("Roles (cargados manualmente): " + user.getRoles());

        return ResponseEntity.ok(user);
    }

}
