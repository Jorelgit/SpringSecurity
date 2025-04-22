package co.edu.usco.pw.springSecurityDB.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

// Creo una clase SuccesHandler, que se va a encargar de redirigir de manera automatica a cada vista segun el rol
// Lo que hace es que verifica los roles y redirige a la vista correspondiente

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String redirectURL = request.getContextPath();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            System.out.println("Rol detectado: " + authority.getAuthority()); // VERIFICACIÓN
            String role = authority.getAuthority();

            switch (role) {
                case "ROLE_ADMIN":
                    redirectURL = "/admin/home_admin";
                    break;
                case "ROLE_EDITOR":
                    redirectURL = "/editor/home_editor";
                    break;
                case "ROLE_CREATOR":
                    redirectURL = "/creator/home_creator";
                    break;
                case "ROLE_USER":
                    redirectURL = "/user/home_user";
                    break;
            }

            // Salir del loop al encontrar un rol válido
            if (!redirectURL.equals(request.getContextPath())) break;
        }

        if (redirectURL.equals(request.getContextPath())) {
            redirectURL = "/403"; // Ruta por defecto si no se encuentra ningún rol
        }

        response.sendRedirect(redirectURL);
    }




}
