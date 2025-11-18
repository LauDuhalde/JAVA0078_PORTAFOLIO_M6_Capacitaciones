package cl.web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.web.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
	Optional<Empleado> findByUsuarioId(Long id);

}
