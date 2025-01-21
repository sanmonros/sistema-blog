package com.santiago.proyecto.sistema_blog.controllers;

import com.santiago.proyecto.sistema_blog.dtos.LoginDTO;
import com.santiago.proyecto.sistema_blog.dtos.ResponseDTO;
import com.santiago.proyecto.sistema_blog.entities.Usuario;
import com.santiago.proyecto.sistema_blog.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    IAuthService authService;



    @PostMapping("/register")
    private ResponseEntity<ResponseDTO> register(@RequestBody Usuario user) throws Exception {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<HashMap<String,String>> login(@RequestBody LoginDTO loginRequest) throws Exception {

        HashMap<String,String> login = authService.login(loginRequest);
        if(login.containsKey("jwt")){
            return new ResponseEntity<>(login,HttpStatus.OK);
        }
        return new ResponseEntity<>(login,HttpStatus.UNAUTHORIZED);
    }

}
