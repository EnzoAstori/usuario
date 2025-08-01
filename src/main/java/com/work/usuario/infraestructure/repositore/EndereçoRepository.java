package com.work.usuario.infraestructure.repositore;

import com.enzo.aprendendospring.infraestructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndereçoRepository extends JpaRepository<Endereco,Long> {

}
