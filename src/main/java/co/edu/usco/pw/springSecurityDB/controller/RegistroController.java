package co.edu.usco.pw.springSecurityDB.controller;

import co.edu.usco.pw.springSecurityDB.model.RoleEntity;
import co.edu.usco.pw.springSecurityDB.model.UserEntity;
import co.edu.usco.pw.springSecurityDB.repository.RoleRepository;
import co.edu.usco.pw.springSecurityDB.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class RegistroController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/registrar")
    @ResponseBody
    public String registrarUsuarioPrueba(

            // Datos de prueba
            @RequestParam String username,  // Coloco aca PostParam, ya que voy a recibir los datpos de postman en esta prueba
            @RequestParam String password,
            @RequestParam String rol)
    {


        // Verifica que no exista un usuario con ese username
        if (userRepository.findByUsername(username).isPresent()) {
            return "El usuario ya existe.";
        }

        Optional<RoleEntity> rolEncontrado = roleRepository.findByName(rol.toUpperCase());
        if (rolEncontrado.isEmpty()) {
            return "Rol no encontrado en la base de datos.";
        }

        UserEntity nuevoUsuario = new UserEntity();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(passwordEncoder.encode(password));
        nuevoUsuario.setEnabled(true);

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(rolEncontrado.get());
        nuevoUsuario.setRoles(roles);

        userRepository.save(nuevoUsuario);

        return "Usuario registrado correctamente con rol: " + rol;
    }

}
