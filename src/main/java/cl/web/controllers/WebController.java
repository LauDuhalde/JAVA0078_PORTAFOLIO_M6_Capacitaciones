package cl.web.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.web.dto.CursoDTO;
import cl.web.dto.EmpleadoDTO;
import cl.web.dto.InscripcionDTO;
import cl.web.dto.InstructorDTO;
import cl.web.dto.UsuarioCreateDTO;
import cl.web.dto.UsuarioDTO;
import cl.web.services.CursoServiceImpl;
import cl.web.services.EmpleadoServiceImpl;
import cl.web.services.InscripcionServiceImpl;
import cl.web.services.InstructorServiceImpl;
import cl.web.services.UsuarioServiceImpl;
import jakarta.validation.Valid;

@Controller
public class WebController {
	public static class CursoConInstructor {
	    private final CursoDTO curso;
	    private final String instructorNombre;

	    public CursoConInstructor(CursoDTO curso, String instructorNombre) {
	        this.curso = curso;
	        this.instructorNombre = instructorNombre;
	    }

	    public CursoDTO getCurso() {
	        return curso;
	    }

	    public String getInstructorNombre() {
	        return instructorNombre;
	    }
	}

    
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    final UsuarioServiceImpl usuarioServiceImpl;
    final CursoServiceImpl cursoServiceImpl;
    final EmpleadoServiceImpl empleadoServiceImpl;
    final InstructorServiceImpl instructorServiceImpl;
    final InscripcionServiceImpl inscripcionServiceImpl;

    public WebController(UsuarioServiceImpl usuarioServiceImpl,
    					CursoServiceImpl cursoServiceImpl,
    					EmpleadoServiceImpl empleadoServiceImpl,
    					InstructorServiceImpl instructorServiceImpl,
    					InscripcionServiceImpl inscripcionServiceImpl) {
    	this.usuarioServiceImpl = usuarioServiceImpl;
    	this.cursoServiceImpl = cursoServiceImpl;
    	this.empleadoServiceImpl = empleadoServiceImpl;
    	this.instructorServiceImpl = instructorServiceImpl;
    	this.inscripcionServiceImpl = inscripcionServiceImpl;
    }
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
    public String adminCursos(Authentication authentication, Model model) {
    	model.addAttribute("cursos",cursoServiceImpl.listarCursos());
    	/*String username = authentication.getName(); // El username del login
    	
    	UsuarioDTO usuario = usuarioServiceImpl.obtenerPorUsername(username);
    	InstructorDTO instructor = instructorServiceImpl.obtenerPorUsuarioId(usuario.getId());
    	model.addAttribute("instructor",instructor);*/
    	
    	List<CursoDTO> cursos = cursoServiceImpl.listarCursos();

        List<CursoConInstructor> cursosConInstructor = cursos.stream().map(curso -> {

            // buscar el instructor por ID con el service
        	InstructorDTO instructor = null;

        	if (curso.getInstructorId() != null) {
        	    instructor = instructorServiceImpl.obtenerPorId(curso.getInstructorId());
        	}


            String nombreInstructor = (instructor != null)
                    ? instructor.getNombre() + " " + instructor.getApellido()
                    : "";

            return new CursoConInstructor(curso, nombreInstructor);

        }).toList();

        model.addAttribute("cursos", cursosConInstructor);
    	
        return "admin/cursos";
    }

    @GetMapping("/empleado/cursos")
    public String empleadoCursos(Authentication authentication, Model model) {
    	
    	String username = authentication.getName();
        UsuarioDTO usuario = usuarioServiceImpl.obtenerPorUsername(username);
        EmpleadoDTO empleado = empleadoServiceImpl.obtenerPorUsuarioId(usuario.getId());

    	List<InscripcionDTO> inscripciones = inscripcionServiceImpl.obtenerPorEmpleadoId(empleado.getId());

        // IDs de los cursos donde ya está inscrito
        Set<Long> cursosInscritos = inscripciones.stream()
                .map(i -> i.getCursoId())
                .collect(Collectors.toSet());
    	
        model.addAttribute("cursos",cursoServiceImpl.listarCursos());
        model.addAttribute("cursosInscritos", cursosInscritos);
        
        return "empleado/cursos";
    }
    
    @PostMapping("/empleado/inscribir")
    public String inscribir(
            @RequestParam Long cursoId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
    	
        String username = authentication.getName();
        UsuarioDTO usuario = usuarioServiceImpl.obtenerPorUsername(username);
        EmpleadoDTO empleado = empleadoServiceImpl.obtenerPorUsuarioId(usuario.getId());

        if (empleado == null) {
            redirectAttributes.addFlashAttribute("error", "Solo los empleados pueden inscribirse.");
            return "redirect:/empleado/cursos";
        }

        try {
            inscripcionServiceImpl.inscribirEmpleado(empleado.getId(), cursoId);
            redirectAttributes.addFlashAttribute("success", "Inscripción realizada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/empleado/cursos";
    }
    
    @GetMapping("/admin/cursos/registrarse-como-instructor/{cursoId}")
    public String registrarseComoInstructor(
            @PathVariable Long cursoId,
            Authentication auth,
            RedirectAttributes redirectAttrs) {

        UsuarioDTO usuario = usuarioServiceImpl.obtenerPorUsername(auth.getName());
        InstructorDTO instructor = instructorServiceImpl.obtenerPorUsuarioId(usuario.getId());

        if (instructor == null) {
            redirectAttrs.addFlashAttribute("error", "No eres instructor registrado.");
            return "redirect:/admin/cursos";
        }

        try {
            cursoServiceImpl.asignarInstructor(cursoId, instructor.getId());
            redirectAttrs.addFlashAttribute("success", "Instructor asignado con éxito.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/cursos";
    }


}
