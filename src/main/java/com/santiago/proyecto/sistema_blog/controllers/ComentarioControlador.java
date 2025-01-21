package com.santiago.proyecto.sistema_blog.controllers;

import com.santiago.proyecto.sistema_blog.dtos.ComentarioDTO;
import com.santiago.proyecto.sistema_blog.services.ComentarioServicioI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicioI comentarioServicio;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable(value = "publicacionId") Long publicacionId){
        return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(value = "publicacionId") Long publicacionId, @PathVariable(value = "id") Long comentarioId){
        ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(
            @PathVariable(value = "publicacionId") long publicacionId,
            @Valid @RequestBody ComentarioDTO comentarioDTO) {

        return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO), HttpStatus.CREATED);
    }
    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value = "publicacionId") Long publicacionId,@PathVariable(value = "id") Long comentarioId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, comentarioId, comentarioDTO);
        return new ResponseEntity<>(comentarioActualizado,HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId") Long publicacionId,@PathVariable(value = "id") Long comentarioId){
        comentarioServicio.eliminarComentario(publicacionId, comentarioId);
        return new ResponseEntity<>("Comentario eliminado con exito",HttpStatus.OK);
    }
}
