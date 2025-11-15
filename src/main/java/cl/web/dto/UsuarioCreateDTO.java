package cl.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO { // Se usa para la creación del usuario

    @NotEmpty(message = "Username no puede estar vacio")
    private String username;

    @NotEmpty(message = "Password no puede estar vacio")
    @Size(min = 6, message = "Password no puede ser menor a 6 caracteres")
    private String password;

    @NotEmpty(message = "Email no puede estar vacio")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotEmpty(message = "Rol no puede estar vacio")
    private String rol;  // admin | instructor | empleado
}
