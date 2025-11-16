package cl.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.web.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
