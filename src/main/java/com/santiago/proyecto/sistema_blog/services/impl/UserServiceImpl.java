package com.santiago.proyecto.sistema_blog.services.impl;

import com.santiago.proyecto.sistema_blog.entities.Usuario;
import com.santiago.proyecto.sistema_blog.repositories.UsuarioRepositorio;
import com.santiago.proyecto.sistema_blog.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UsuarioRepositorio userRepository;

    @Override
    public List<Usuario> findAllUsers() {
        return userRepository.findAll();
    }
}