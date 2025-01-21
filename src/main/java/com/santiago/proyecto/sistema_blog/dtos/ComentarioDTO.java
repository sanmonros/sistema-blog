package com.santiago.proyecto.sistema_blog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {

    private long id;

    @NotEmpty(message = "El nombre no debe ser vacio o nulo")
    private String nombre;

    @NotEmpty(message = "El email no debe ser vacio o nulo")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10,message = "El cuerpo del comentario debe tener al menos 10 caracteres")
    private String cuerpo;
}
