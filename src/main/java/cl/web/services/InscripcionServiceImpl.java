package cl.web.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import cl.web.dto.InscripcionCreateDTO;
import cl.web.dto.InscripcionDTO;
import cl.web.entities.Curso;
import cl.web.entities.Empleado;
import cl.web.entities.Inscripcion;
import cl.web.mappers.InscripcionMapper;
import cl.web.repositories.CursoRepository;
import cl.web.repositories.EmpleadoRepository;
import cl.web.repositories.InscripcionRepository;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private final EmpleadoRepository empleadoRepository;
    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper mapper;

    public InscripcionServiceImpl(EmpleadoRepository empleadoRepository, 
    		CursoRepository cursoRepository, 
    		InscripcionRepository inscripcionRepository,
    		InscripcionMapper mapper) {
        this.empleadoRepository = empleadoRepository;
        this.cursoRepository = cursoRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.mapper = mapper;
    }

    @Override
    public InscripcionDTO inscribirEmpleado(Long empleadoId, Long cursoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        
        InscripcionCreateDTO dto = new InscripcionCreateDTO();
        dto.setCursoId(cursoId);
        dto.setEmpleadoId(empleadoId);
        dto.setEstado("ACTIVO");
        dto.setFechaInscripcion(LocalDate.now());
        
        Inscripcion inscripcion = mapper.toEntity(dto);
        return mapper.toDTO(inscripcionRepository.save(inscripcion));
    }
}

