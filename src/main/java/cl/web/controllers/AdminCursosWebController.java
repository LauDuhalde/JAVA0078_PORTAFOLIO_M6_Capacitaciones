package cl.web.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.web.dto.CursoCreateDTO;
import cl.web.dto.CursoDTO;
import cl.web.dto.InstructorDTO;
import cl.web.dto.UsuarioDTO;
import cl.web.services.CursoServiceImpl;
import cl.web.services.InstructorServiceImpl;
import cl.web.services.UsuarioServiceImpl;

@Controller
@RequestMapping("/admin/cursos")
public class AdminCursosWebController {
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
	
	final UsuarioServiceImpl usuarioServiceImpl;
    final CursoServiceImpl cursoServiceImpl;
    final InstructorServiceImpl instructorServiceImpl;

    public AdminCursosWebController(UsuarioServiceImpl usuarioServiceImpl,
    					CursoServiceImpl cursoServiceImpl,
    					InstructorServiceImpl instructorServiceImpl) {
    	this.usuarioServiceImpl = usuarioServiceImpl;
    	this.cursoServiceImpl = cursoServiceImpl;
    	this.instructorServiceImpl = instructorServiceImpl;
    }
	
    @GetMapping
    public String adminCursos(Authentication authentication, Model model) {
    	model.addAttribute("cursos",cursoServiceImpl.listarCursos());
    
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
    
    @GetMapping("/registrarse-como-instructor/{cursoId}")
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
    
    // Mostrar formulario para crear curso
    @GetMapping("/nuevo")
    public String mostrarFormularioCrearCurso(Model model) {
        model.addAttribute("curso", new CursoCreateDTO());
        return "admin/crear-curso";
    }

    // Guardar curso
    @PostMapping("/guardar")
    public String guardarCurso(
            @ModelAttribute("curso") CursoCreateDTO cursoDTO,
            RedirectAttributes redirectAttributes) {

        try {
            cursoServiceImpl.crearCurso(cursoDTO);
            redirectAttributes.addFlashAttribute("success", "Curso creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el curso");
        }

        return "redirect:/admin/cursos";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        CursoDTO curso = cursoServiceImpl.obtenerPorId(id);
        model.addAttribute("curso", curso);
        return "admin/editar-curso";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
        	cursoServiceImpl.eliminarCurso(id);
            redirectAttributes.addFlashAttribute("success", "Curso eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el curso");
        }
        return "redirect:/admin/cursos";
    }
    
    @PostMapping("/editar")
    public String actualizarCurso(@ModelAttribute CursoDTO curso,
                                   RedirectAttributes redirectAttributes) {
        try {
        	cursoServiceImpl.editarCurso(curso);
            redirectAttributes.addFlashAttribute("success", "Curso actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el curso");
        }
        return "redirect:/admin/cursos";
    }

	

}
