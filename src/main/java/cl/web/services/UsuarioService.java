package cl.web.services;

import cl.web.dto.UsuarioCreateDTO;
import cl.web.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {

    UsuarioDTO crearUsuario(UsuarioCreateDTO dto);

    UsuarioDTO obtenerPorId(Long id);

    UsuarioDTO obtenerPorUsername(String username);

    List<UsuarioDTO> listarUsuarios();
}
