package com.santiago.proyecto.sistema_blog.dtos;

import com.santiago.proyecto.sistema_blog.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {

    private String nombre;
    private String username;
    private String email;
    private String password;
    private Role roles;
}
