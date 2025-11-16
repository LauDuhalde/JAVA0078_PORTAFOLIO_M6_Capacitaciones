package cl.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cl.web.dto.UsuarioCreateDTO;
import cl.web.dto.UsuarioDTO;
import cl.web.services.UsuarioServiceImpl;
import jakarta.validation.Valid;

@Controller
public class WebController {
	
	@Autowired
	UsuarioServiceImpl usuarioServiceImpl;

    // Vista de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Vista de registro
    @GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
        UsuarioCreateDTO user = new UsuarioCreateDTO();
        model.addAttribute("usuario", user);
        return "registro";
    }
    
    @PostMapping("/registro")
    public String registro_guardar(@Valid @ModelAttribute("usuario") UsuarioCreateDTO userDto,
                                   BindingResult result,
                                   Model model) {
        try {

            UsuarioDTO existeUsername = usuarioServiceImpl.obtenerPorUsername(userDto.getUsername());
            if (existeUsername != null) {
                result.rejectValue("username", null, "Ese nombre de usuario ya está en uso");
            }

            if (result.hasErrors()) {
                model.addAttribute("usuario", userDto);
                return "registro";
            }

            usuarioServiceImpl.crearUsuario(userDto);
            model.addAttribute("mensaje", "Usuario creado con exito");
            return "login";

        } catch (Exception e) {
            // Captura cualquier error no previsto y lo deriva a página de error
        	model.addAttribute("errorMessage", "Ocurrió un error inesperado. Intenta más tarde.");
        	return "registro";
        }
    }

    // Vista para admin gestionar cursos
    @GetMapping("/admin/cursos")
    public String adminCursos() {
        return "admin/cursos";
    }

    // Vista para empleados ver cursos disponibles
    @GetMapping("/empleado/cursos")
    public String empleadoCursos() {
        return "empleado/cursos";
    }
}
