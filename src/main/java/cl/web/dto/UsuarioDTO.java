package cl.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO { //Respuesta sin password por seguridad
	private Long id;
    private String username;
    private String email;
    private String rol;

}
