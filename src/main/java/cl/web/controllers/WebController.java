package cl.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cl.web.dto.UsuarioCreateDTO;
import cl.web.services.UsuarioServiceImpl;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class WebController {
    
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";  // busca templates/login.html
    }

    @GetMapping("/")
    public String home(Authentication auth) {

        if (auth == null) {
            return "index"; // página pública, solo sin login
        }

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals("ROLE_ADMIN"));

        boolean isEmpleado = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals("ROLE_EMPLEADO"));

        if (isAdmin) {
            return "redirect:/admin/cursos";
        }

        if (isEmpleado) {
            return "redirect:/empleado/cursos";
        }

        return "index"; // fallback
    }

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
            usuarioServiceImpl.crearUsuario(userDto);
            model.addAttribute("mensaje", "Usuario creado con éxito");
            return "login";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ocurrió un error inesperado. Intenta más tarde.");
            logger.error("Error al crear usuario: " + e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/admin/cursos")
    public String adminCursos() {
        return "admin/cursos";
    }

    @GetMapping("/empleado/cursos")
    public String empleadoCursos() {
        return "empleado/cursos";
    }
}
