package cl.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.web.dto.EmpleadoCreateDTO;
import cl.web.dto.EmpleadoDTO;
import cl.web.mappers.EmpleadoMapper;
import cl.web.repositories.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper mapper;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, EmpleadoMapper mapper) {
        this.empleadoRepository = empleadoRepository;
        this.mapper = mapper;
    }

    @Override
    public EmpleadoDTO crearEmpleado(EmpleadoCreateDTO dto) {
        return mapper.toDTO(empleadoRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public EmpleadoDTO obtenerPorId(Long id) {
        return empleadoRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public List<EmpleadoDTO> listarEmpleados() {
        return empleadoRepository.findAll().stream().map(mapper::toDTO).toList();
    }
}

