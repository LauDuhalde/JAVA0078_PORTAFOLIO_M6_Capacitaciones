package cl.web.services;

import java.util.List;

import cl.web.dto.EmpleadoCreateDTO;
import cl.web.dto.EmpleadoDTO;

public interface EmpleadoService {

    EmpleadoDTO crearEmpleado(EmpleadoCreateDTO dto);

    EmpleadoDTO obtenerPorId(Long id);

    List<EmpleadoDTO> listarEmpleados();
}
