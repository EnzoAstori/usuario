package com.work.usuario.infraestructure.repositore;


import com.work.usuario.infraestructure.entity.Telefones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefones,Long> {
}
