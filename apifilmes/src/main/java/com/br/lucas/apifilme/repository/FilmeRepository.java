package com.br.lucas.apifilme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.br.lucas.apifilme.modelo.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
	
}
