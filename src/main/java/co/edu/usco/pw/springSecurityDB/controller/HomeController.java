package co.edu.usco.pw.springSecurityDB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	// Creo los controladores con las diferenres rutas

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/user/home_user")
	public String userHome() {
		return "user/home_user";
	}

	@GetMapping("/admin/home_admin")
	public String adminHome() {
		return "admin/home_admin";
	}

	@GetMapping("/editor/home_editor")
	public String editorHome() {
		return "editor/home_editor";
	}

	@GetMapping("/creator/home_creator")
	public String creatorHome() {
		return "creator/home_creator";
	}


	// Redireccion a pagina de error cuando no hay permisos
	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}


}
