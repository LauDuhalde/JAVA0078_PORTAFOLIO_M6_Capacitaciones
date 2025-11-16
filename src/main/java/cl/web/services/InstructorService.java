package cl.web.services;

import cl.web.dto.InstructorCreateDTO;
import cl.web.dto.InstructorDTO;
import java.util.List;

public interface InstructorService {

    InstructorDTO crearInstructor(InstructorCreateDTO dto);

    InstructorDTO obtenerPorId(Long id);

    List<InstructorDTO> listarInstructores();

    void asignarCurso(Long instructorId, Long cursoId);
}
