package cl.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.web.dto.EmpleadoCreateDTO;
import cl.web.dto.EmpleadoDTO;
import cl.web.entities.Empleado;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    EmpleadoDTO toDTO(Empleado empleado);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "inscripciones", ignore = true)
    Empleado toEntity(EmpleadoCreateDTO dto);
}
