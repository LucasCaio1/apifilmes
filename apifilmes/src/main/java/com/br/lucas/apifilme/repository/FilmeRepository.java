package com.br.lucas.apifilme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.lucas.apifilme.modelo.Filme;

/**
 * Faz a comunicação com o banco de dados referente a classe Filme.
 * @author Lucas Caio
 *
 */
public interface FilmeRepository extends JpaRepository<Filme, Long> {
	/**
	 * Procura no banco de dados um filme através do título passado no parâmetro.
	 * @param titulo Título de um filme.
	 */
	Optional<Filme> findByTitulo(String titulo);
}
