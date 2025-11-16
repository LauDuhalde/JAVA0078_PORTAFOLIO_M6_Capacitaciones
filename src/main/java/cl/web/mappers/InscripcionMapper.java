package cl.web.mappers;

import cl.web.dto.InscripcionCreateDTO;
import cl.web.dto.InscripcionDTO;
import cl.web.entities.Inscripcion;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    @Mapping(target = "empleadoId", source = "empleado.id")
    @Mapping(target = "cursoId", source = "curso.id")
    InscripcionDTO toDTO(Inscripcion inscripcion);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empleado", ignore = true)
    @Mapping(target = "curso", ignore = true)
    Inscripcion toEntity(InscripcionCreateDTO dto);
}
