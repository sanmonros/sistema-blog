package com.santiago.proyecto.sistema_blog.dtos;

import lombok.Data;

@Data
public class ResponseDTO {

    private int numOfErrors;
    private String message;

}