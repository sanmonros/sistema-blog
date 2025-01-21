package com.santiago.proyecto.sistema_blog.dtos;

import com.santiago.proyecto.sistema_blog.entities.Comentario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2,message = "El titulo de la publicación deberia tener al menos 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min = 10,message = "La descripción de la publicación deberia tener al menos 10 caracteres")
    private String descripcion;

    @NotEmpty
    private String contenido;

    //private Set<ComentarioDTO> comentarios = new HashSet<>(); // Inicializar con un HashSet vacío

    private Set<ComentarioDTO> comentarios;  // Mantén este campo tal como está.

}
