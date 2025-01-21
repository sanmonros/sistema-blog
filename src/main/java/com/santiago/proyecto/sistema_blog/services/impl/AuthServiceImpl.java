package com.santiago.proyecto.sistema_blog.services.impl;

import com.santiago.proyecto.sistema_blog.dtos.LoginDTO;
import com.santiago.proyecto.sistema_blog.dtos.ResponseDTO;
import com.santiago.proyecto.sistema_blog.entities.Role;
import com.santiago.proyecto.sistema_blog.entities.Usuario;
import com.santiago.proyecto.sistema_blog.enums.ERole;
import com.santiago.proyecto.sistema_blog.repositories.RoleRepository;
import com.santiago.proyecto.sistema_blog.repositories.UsuarioRepositorio;
import com.santiago.proyecto.sistema_blog.services.IAuthService;
import com.santiago.proyecto.sistema_blog.services.IJWTUtilityService;
import com.santiago.proyecto.sistema_blog.utils.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UsuarioRepositorio userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Autowired
    private UserValidations userValidations;

    @Override
    public HashMap<String, String> login(LoginDTO loginRequest) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            Optional<Usuario> userOptional = userRepository.findByEmail(loginRequest.getUsernameOrEmail());

            if (userOptional.isEmpty()) {
                jwt.put("error", "User not registered!");
                return jwt;
            }

            Usuario user = userOptional.get();

            // Verificación de la contraseña
            if (verifyPassword(loginRequest.getPassword(), user.getPassword())) {
                // Obtener el nombre del rol (como cadena)
                String role = user.getRole().getName().name(); // Acceder al nombre del rol

                // Generar el JWT con el rol
                jwt.put("jwt", jwtUtilityService.generateJWT(user.getId(), List.of(role))); // Pasar el rol como lista
            } else {
                jwt.put("error", "Authentication failed");
            }
            return jwt;
        } catch (IllegalArgumentException e) {
            System.err.println("Error generating JWT: " + e.getMessage());
            throw new Exception("Error generating JWT", e);
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.toString());
            throw new Exception("Unknown error", e);
        }
    }


    public ResponseDTO register(Usuario user) throws Exception {
        try {
            ResponseDTO response = userValidations.validate(user);
            if (response.getNumOfErrors() > 0) {
                return response;
            }

            Optional<Usuario> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                response.setNumOfErrors(1);
                response.setMessage("User already exists!");
                return response;
            }

            // Buscar el rol usando el role_id recibido en el JSON
            Role role = roleRepository.findById(Long.valueOf(user.getRole().getId()))
                    .orElseThrow(() -> new Exception("Role not found"));

            // Establecer el rol en el usuario
            user.setRole(role);


            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword())); // Codificación de la contraseña
            userRepository.save(user);
            response.setMessage("User created successfully!");

            return response;
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }


}