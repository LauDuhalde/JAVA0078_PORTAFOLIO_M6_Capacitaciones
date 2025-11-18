package cl.web.services;

import cl.web.dto.InstructorCreateDTO;
import cl.web.dto.InstructorDTO;
import cl.web.mappers.InstructorMapper;
import cl.web.entities.Curso;
import cl.web.entities.Instructor;
import cl.web.repositories.CursoRepository;
import cl.web.repositories.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final CursoRepository cursoRepository;
    private final InstructorMapper mapper;

    public InstructorServiceImpl(InstructorRepository instructorRepository,
                                 CursoRepository cursoRepository,
                                 InstructorMapper mapper) {
        this.instructorRepository = instructorRepository;
        this.cursoRepository = cursoRepository;
        this.mapper = mapper;
    }

    @Override
    public InstructorDTO crearInstructor(InstructorCreateDTO dto) {
        Instructor instructor = mapper.toEntity(dto);
        Instructor guardado = instructorRepository.save(instructor);
        return mapper.toDTO(guardado);
    }

    @Override
    public InstructorDTO obtenerPorId(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
        return mapper.toDTO(instructor);
    }

    @Override
    public List<InstructorDTO> listarInstructores() {
        return instructorRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void asignarCurso(Long instructorId, Long cursoId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        instructor.getCursos().add(curso);
        instructorRepository.save(instructor);
    }

	@Override
	public InstructorDTO obtenerPorUsuarioId(Long id) {
		Instructor instructor = instructorRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
        return mapper.toDTO(instructor);
	}
}
