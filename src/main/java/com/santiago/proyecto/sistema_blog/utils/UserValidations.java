package com.santiago.proyecto.sistema_blog.utils;

import com.santiago.proyecto.sistema_blog.dtos.ResponseDTO;
import com.santiago.proyecto.sistema_blog.entities.Role;
import com.santiago.proyecto.sistema_blog.entities.Usuario;
import com.santiago.proyecto.sistema_blog.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserValidations {

    @Autowired
    RoleRepository roleRepository;


    public ResponseDTO validate(Usuario user) {
        ResponseDTO response = new ResponseDTO();
        response.setNumOfErrors(0);
        StringBuilder errorMessage = new StringBuilder();

        // Validación del campo username
        if (user.getUsername() == null ||
                user.getUsername().length() < 3 ||
                user.getUsername().length() > 15) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            errorMessage.append("El campo username no puede ser nulo, también debe tener entre 3 y 15 caracteres. ");
        }

        // Validación del campo nombre
        if (user.getNombre() == null ||
                user.getNombre().length() < 3 ||
                user.getNombre().length() > 15) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            errorMessage.append("El campo nombre no puede ser nulo, también debe tener entre 3 y 15 caracteres. ");
        }

        // Validación del campo email
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            errorMessage.append("El campo email no es valido. ");
        }

        // Validación de la contraseña
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            errorMessage.append("La contraseña debe tener entre 8 y 16 caracteres, al menos un número, una minúscula y una mayúscula. ");
        }

        // Validación del campo role
        if (user.getRole() == null || user.getRole().getId() == null) {
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            errorMessage.append("El campo role es obligatorio y debe ser válido. ");
        } else {
            // Verifica que el role_id existe en la base de datos
            Optional<Role> roleOptional = roleRepository.findById(Long.valueOf(user.getRole().getId()));
            if (roleOptional.isEmpty()) {
                response.setNumOfErrors(response.getNumOfErrors() + 1);
                errorMessage.append("El role especificado no es válido. ");
            } else {
                user.setRole(roleOptional.get());  // Asignamos el role completo al usuario
            }
        }

        // Si hubo errores, asigna el mensaje acumulado
        if (response.getNumOfErrors() > 0) {
            response.setMessage(errorMessage.toString());
        }

        return response;
    }
}