package cl.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.web.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
