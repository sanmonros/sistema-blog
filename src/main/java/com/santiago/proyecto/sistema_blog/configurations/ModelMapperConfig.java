package com.santiago.proyecto.sistema_blog.configurations;

import com.santiago.proyecto.sistema_blog.dtos.ComentarioDTO;
import com.santiago.proyecto.sistema_blog.dtos.PublicacionDTO;
import com.santiago.proyecto.sistema_blog.entities.Comentario;
import com.santiago.proyecto.sistema_blog.entities.Publicacion;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean(name = "customModelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Ignorar ambigüedades
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        // Mapeo explícito para Publicacion y PublicacionDTO
        modelMapper.createTypeMap(Publicacion.class, PublicacionDTO.class).addMappings(mapper -> {
            mapper.skip(PublicacionDTO::setComentarios); // Evitar mapeo directo
        });

        // Conversor para comentarios
        modelMapper.addConverter(new Converter<Set<Comentario>, Set<ComentarioDTO>>() {
            @Override
            public Set<ComentarioDTO> convert(MappingContext<Set<Comentario>, Set<ComentarioDTO>> context) {
                if (context.getSource() == null) {
                    return new HashSet<>();
                }
                return context.getSource().stream()
                        .map(comentario -> modelMapper.map(comentario, ComentarioDTO.class))
                        .collect(Collectors.toSet());
            }
        });

        return modelMapper;
    }
}