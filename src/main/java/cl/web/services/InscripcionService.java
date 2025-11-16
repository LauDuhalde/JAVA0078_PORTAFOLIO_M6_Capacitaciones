package cl.web.services;

import cl.web.dto.InscripcionDTO;

public interface InscripcionService {

	InscripcionDTO inscribirEmpleado(Long empleadoId, Long cursoId);
}
