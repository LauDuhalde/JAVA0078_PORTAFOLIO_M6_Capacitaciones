package cl.web.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InscripcionCreateDTO {
    private LocalDate fechaInscripcion;
    private String estado;
    private Long empleadoId;
    private Long cursoId;
}
