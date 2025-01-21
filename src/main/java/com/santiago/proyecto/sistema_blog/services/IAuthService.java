package com.santiago.proyecto.sistema_blog.services;

import com.santiago.proyecto.sistema_blog.dtos.LoginDTO;
import com.santiago.proyecto.sistema_blog.dtos.ResponseDTO;
import com.santiago.proyecto.sistema_blog.entities.Usuario;

import java.util.HashMap;

public interface IAuthService {
    public HashMap<String,String> login(LoginDTO login) throws Exception;

    public ResponseDTO register(Usuario user) throws Exception;

}