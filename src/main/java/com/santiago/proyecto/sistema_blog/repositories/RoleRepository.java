package com.santiago.proyecto.sistema_blog.repositories;

import com.santiago.proyecto.sistema_blog.entities.Role;
import com.santiago.proyecto.sistema_blog.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Optional<Role> findById(Integer id);


}
