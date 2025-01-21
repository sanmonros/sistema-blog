package com.santiago.proyecto.sistema_blog.configurations;

import com.santiago.proyecto.sistema_blog.utils.UserValidations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfig {

    @Bean
    public UserValidations userValidation(){
        return new UserValidations();
    }
}