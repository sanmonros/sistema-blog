package com.santiago.proyecto.sistema_blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroresDetalles {

    private Date marcaDeTiempo;
    private String mensaje;
    private String detalles;

}