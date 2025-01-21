package com.santiago.proyecto.sistema_blog.repositories;

import com.santiago.proyecto.sistema_blog.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentarioRepositorio extends JpaRepository<Comentario,Long> {

    @Query("SELECT c FROM Comentario c WHERE c.publicacion.id = :publicacionId")
    List<Comentario> findByPublicacion(@Param("publicacionId") Long publicacionId);

}
