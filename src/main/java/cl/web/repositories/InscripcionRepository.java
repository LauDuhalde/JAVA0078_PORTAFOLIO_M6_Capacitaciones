package cl.web.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.web.entities.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    boolean existsByEmpleadoIdAndCursoId(Long empleadoId, Long cursoId);
    List<Inscripcion> findByEmpleadoId(Long id);
}
