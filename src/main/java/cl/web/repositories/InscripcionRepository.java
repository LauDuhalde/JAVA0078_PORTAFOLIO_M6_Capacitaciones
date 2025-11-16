package cl.web.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import cl.web.entities.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

}
