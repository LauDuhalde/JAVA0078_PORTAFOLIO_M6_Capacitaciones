package cl.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.web.dto.CursoCreateDTO;
import cl.web.dto.CursoDTO;
import cl.web.entities.Curso;
import cl.web.entities.Instructor;
import cl.web.mappers.CursoMapper;
import cl.web.repositories.CursoRepository;
import cl.web.repositories.InstructorRepository;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final InstructorRepository instructorRepository;
    private final CursoMapper mapper;

    public CursoServiceImpl(CursoRepository cursoRepository,
                            InstructorRepository instructorRepository,
                            CursoMapper mapper) {
        this.cursoRepository = cursoRepository;
        this.instructorRepository = instructorRepository;
        this.mapper = mapper;
    }

    @Override
    public CursoDTO crearCurso(CursoCreateDTO dto) {
        Curso curso = mapper.toEntity(dto);
        return mapper.toDTO(cursoRepository.save(curso));
    }

    @Override
    public CursoDTO obtenerPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        return mapper.toDTO(curso);
    }

    @Override
    public List<CursoDTO> listarCursos() {
        return cursoRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public void asignarInstructor(Long cursoId, Long instructorId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        curso.setInstructor(instructor);
        cursoRepository.save(curso);
    }
}

