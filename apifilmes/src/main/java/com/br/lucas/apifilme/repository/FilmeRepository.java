package com.br.lucas.apifilme.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.lucas.apifilme.modelo.Filme;
import com.br.lucas.apifilme.modelo.Genero;

/**
 * Faz a comunicação com o banco de dados referente a classe Filme.
 * @author Lucas Caio
 *
 */
public interface FilmeRepository extends JpaRepository<Filme, Long> {
	/**
	 * Procura no banco de dados um filme através do título passado no parâmetro.
	 * @param titulo Título de um filme.
	 * @return um Optional de um filme de acordo com o título passsado no parâmetro.
	 */
	Optional<Filme> findByTitulo(String titulo); 
	
	/**
	 * Procura no banco de dados os filmes através do gênero passado no parâmetro.
	 * @param genero
	 * @param paginacao
	 * @return um Page contendo todos os filmes do gênero passado como parâmetro.
	 */
	Page<Filme> findByGenero(Genero genero, Pageable paginacao);
	
}
