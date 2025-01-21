package com.santiago.proyecto.sistema_blog.entities;

import com.santiago.proyecto.sistema_blog.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    /** Id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Enum Nombre ROL */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}