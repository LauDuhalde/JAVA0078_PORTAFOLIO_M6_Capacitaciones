package cl.web.restControllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.web.dto.CursoDTO;
import cl.web.dto.InscripcionCreateDTO;
import cl.web.dto.InscripcionDTO;
import cl.web.services.CursoServiceImpl;
import cl.web.services.EmpleadoServiceImpl;
import cl.web.services.InscripcionServiceImpl;

@RestController
@RequestMapping("/api")
public class ApiRestController {
	final CursoServiceImpl cursoServiceImpl;
	final EmpleadoServiceImpl empleadoServiceImpl;
    final InscripcionServiceImpl inscripcionServiceImpl;
	
	public ApiRestController(CursoServiceImpl cursoServiceImpl,
			EmpleadoServiceImpl empleadoServiceImpl,
			InscripcionServiceImpl inscripcionServiceImpl) {
		this.cursoServiceImpl = cursoServiceImpl;
		this.empleadoServiceImpl = empleadoServiceImpl;
    	this.inscripcionServiceImpl = inscripcionServiceImpl;
	}
	
	@GetMapping("/cursos")
	public List<CursoDTO> listar() {
        return cursoServiceImpl.listarCursos();
    }
	
	@PostMapping("/inscripciones")
    public InscripcionDTO crear(@RequestBody InscripcionCreateDTO inscripcion) {
        return inscripcionServiceImpl.nuevaInscripcion(inscripcion);
    }

}
