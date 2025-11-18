package cl.web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.web.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	Optional<Instructor> findByUsuarioId(Long id);
}
