package cl.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.web.dto.UsuarioCreateDTO;
import cl.web.dto.UsuarioDTO;
import cl.web.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empleado", ignore = true)     // se asigna en el service
    @Mapping(target = "instructor", ignore = true)   // se asigna en el service
    Usuario toEntity(UsuarioCreateDTO dto);
}
