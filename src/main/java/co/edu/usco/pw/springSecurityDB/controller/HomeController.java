package co.edu.usco.pw.springSecurityDB.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping("public/hello")
	public String helloPublic() {
		return "Hola mundo público";
	}

	@GetMapping("user/hello")
	public String helloUser() {
		return "Hola Usuario";
	}

	@GetMapping("admin/hello")
	public String helloAdmin() {
		return "Hola Administrador";
	}
}
