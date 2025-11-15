package cl.web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {

    private Long id;
    private LocalDate fechaInscripcion;
    private String estado;

    private Long empleadoId; // Relación N:1
    private Long cursoId;    // Relación N:1
}

