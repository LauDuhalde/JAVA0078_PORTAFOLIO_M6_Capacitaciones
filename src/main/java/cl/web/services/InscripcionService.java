package cl.web.services;

import java.util.List;

import cl.web.dto.InscripcionCreateDTO;
import cl.web.dto.InscripcionDTO;

public interface InscripcionService {

	InscripcionDTO inscribirEmpleado(Long empleadoId, Long cursoId);

	List<InscripcionDTO> obtenerPorEmpleadoId(Long id);
	
	InscripcionDTO nuevaInscripcion(InscripcionCreateDTO dto);
}
