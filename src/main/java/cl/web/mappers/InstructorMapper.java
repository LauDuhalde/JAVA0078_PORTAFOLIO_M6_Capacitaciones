package cl.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.web.dto.InstructorCreateDTO;
import cl.web.dto.InstructorDTO;
import cl.web.entities.Instructor;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    InstructorDTO toDTO(Instructor instructor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "cursos", ignore = true)
    Instructor toEntity(InstructorCreateDTO dto);
}
