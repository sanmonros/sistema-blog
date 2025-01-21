package com.santiago.proyecto.sistema_blog.repositories;

import com.santiago.proyecto.sistema_blog.entities.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepositorio extends JpaRepository<Publicacion,Long> {
}
