package com.santiago.proyecto.sistema_blog.services;

import com.santiago.proyecto.sistema_blog.entities.Usuario;

import java.util.List;

public interface IUserService {
    List<Usuario> findAllUsers();
}
