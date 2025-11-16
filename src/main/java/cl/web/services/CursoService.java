package cl.web.services;

import cl.web.dto.CursoCreateDTO;
import cl.web.dto.CursoDTO;
import java.util.List;

public interface CursoService {

    CursoDTO crearCurso(CursoCreateDTO dto);

    CursoDTO obtenerPorId(Long id);

    List<CursoDTO> listarCursos();

    void asignarInstructor(Long cursoId, Long instructorId);
}
