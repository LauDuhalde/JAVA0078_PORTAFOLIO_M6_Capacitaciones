package cl.web.dto;

import lombok.Data;

@Data
public class InstructorCreateDTO {
    private String nombre;
    private String apellido;
    private String especialidad;
    
    private Long usuarioId; // Relación 1:1
}

