package cl.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String area;

    private Long usuarioId; // Relación 1:1
}
