package cl.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.web.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
