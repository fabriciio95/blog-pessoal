package com.blogpessoal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogpessoal.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long>{

	List<Tema> findByDescricaoContainingIgnoreCase(String descricao);
}
