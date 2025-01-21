package com.santiago.proyecto.sistema_blog.services;

import com.santiago.proyecto.sistema_blog.dtos.PublicacionDTO;
import com.santiago.proyecto.sistema_blog.dtos.PublicacionRespuesta;

public interface PublicacionServicioI {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorId(long id);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO,long id);

    public void eliminarPublicacion(long id);
}
