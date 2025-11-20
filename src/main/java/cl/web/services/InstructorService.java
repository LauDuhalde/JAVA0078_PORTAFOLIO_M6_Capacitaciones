package cl.web.services;

import java.util.List;

import cl.web.dto.InstructorCreateDTO;
import cl.web.dto.InstructorDTO;

public interface InstructorService {

    InstructorDTO crearInstructor(InstructorCreateDTO dto);

    InstructorDTO obtenerPorId(Long id);

    List<InstructorDTO> listarInstructores();

    void asignarCurso(Long instructorId, Long cursoId);
    
    InstructorDTO obtenerPorUsuarioId(Long id);
}
