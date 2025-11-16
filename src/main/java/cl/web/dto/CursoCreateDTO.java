package cl.web.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CursoCreateDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cupos;
}
