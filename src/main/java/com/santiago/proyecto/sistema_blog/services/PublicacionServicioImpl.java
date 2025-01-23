package com.santiago.proyecto.sistema_blog.services;

import com.santiago.proyecto.sistema_blog.dtos.ComentarioDTO;
import com.santiago.proyecto.sistema_blog.dtos.PublicacionDTO;
import com.santiago.proyecto.sistema_blog.dtos.PublicacionRespuesta;
import com.santiago.proyecto.sistema_blog.entities.Comentario;
import com.santiago.proyecto.sistema_blog.entities.Publicacion;
import com.santiago.proyecto.sistema_blog.exceptions.ResourceNotFoundException;
import com.santiago.proyecto.sistema_blog.repositories.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublicacionServicioImpl implements PublicacionServicioI{

    @Autowired
    @Qualifier("customModelMapper")
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;


    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

        PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
        return publicacionRespuesta;
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor,
                                                             String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

        List<Publicacion> listaDePublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion))
                .collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        // Log para verificar si los comentarios están siendo recuperados
        System.out.println("Comentarios cargados: " + publicacion.getComentarios().size());
        // Forzar la carga de los comentarios
        //Hibernate.initialize(publicacion.getComentarios());

        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
        return mapearDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        // Si hay comentarios asociados, serán eliminados automáticamente gracias a la cascada
        publicacionRepositorio.delete(publicacion);
    }

    // Convierte entidad a DTO
    private PublicacionDTO mapearDTO(Publicacion publicacion) {

        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);

        // Asegurar que comentarios no sea null
        if (publicacion.getComentarios() != null) {
            Set<ComentarioDTO> comentarioDTOS = publicacion.getComentarios().stream()
                    .map(comentario -> modelMapper.map(comentario, ComentarioDTO.class))
                    .collect(Collectors.toSet());
            publicacionDTO.setComentarios(comentarioDTOS);
        } else {
            publicacionDTO.setComentarios(new HashSet<>());
        }

        return publicacionDTO;
    }

    // Convierte de DTO a Entidad
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);

        // Asegurar que comentarios no sea null
        if (publicacionDTO.getComentarios() != null) {
            Set<Comentario> comentarios = publicacionDTO.getComentarios().stream()
                    .map(comentarioDTO -> modelMapper.map(comentarioDTO, Comentario.class))
                    .collect(Collectors.toSet());
            publicacion.setComentarios(comentarios);
        } else {
            publicacion.setComentarios(new HashSet<>());
        }

        return publicacion;
    }
}
