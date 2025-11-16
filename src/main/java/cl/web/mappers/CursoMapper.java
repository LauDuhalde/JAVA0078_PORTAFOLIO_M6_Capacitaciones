package cl.web.mappers;

import cl.web.dto.CursoCreateDTO;
import cl.web.dto.CursoDTO;
import cl.web.entities.Curso;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(target = "instructorId", source = "instructor.id")
    CursoDTO toDTO(Curso curso);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "inscripciones", ignore = true)
    Curso toEntity(CursoCreateDTO dto);
}
