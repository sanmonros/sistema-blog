package com.santiago.proyecto.sistema_blog.services;

import com.santiago.proyecto.sistema_blog.dtos.ComentarioDTO;
import com.santiago.proyecto.sistema_blog.entities.Comentario;
import com.santiago.proyecto.sistema_blog.entities.Publicacion;
import com.santiago.proyecto.sistema_blog.exceptions.BlogAppExcepcion;
import com.santiago.proyecto.sistema_blog.exceptions.ResourceNotFoundException;
import com.santiago.proyecto.sistema_blog.repositories.ComentarioRepositorio;
import com.santiago.proyecto.sistema_blog.repositories.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComentarioServicioImpl implements ComentarioServicioI{



    @Autowired
    @Qualifier("customModelMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Override
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
        // Buscar la publicación asociada
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        // Mapear DTO a entidad Comentario
        Comentario comentario = mapearEntidad(comentarioDTO);

        // Asociar el comentario con la publicación
        comentario.setPublicacion(publicacion);

        // Garantizar que el ID del comentario sea nulo
        comentario.setId(null);
        // Guardar el comentario en el repositorio
        Comentario nuevoComentario = comentarioRepositorio.save(comentario);

        // Mapear entidad a DTO y devolver
        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId) {
        List<Comentario> comentarios = comentarioRepositorio.findByPublicacion(publicacionId);
        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId,ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());


        Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        comentarioRepositorio.delete(comentario);
    }

    private ComentarioDTO mapearDTO(Comentario comentario) {
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {
        return modelMapper.map(comentarioDTO, Comentario.class);
    }
}
