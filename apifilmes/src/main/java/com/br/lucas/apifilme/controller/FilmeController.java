package com.br.lucas.apifilme.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.lucas.apifilme.dto.FilmeDto;
import com.br.lucas.apifilme.form.FilmForm;
import com.br.lucas.apifilme.modelo.Filme;
import com.br.lucas.apifilme.repository.FilmeRepository;

/**
 * Classe controller da classe Filme, realiza as determinadas operações: cadastrar
 * 
 * @see #cadastrar(FilmForm, UriComponentsBuilder)
 * @see com.br.lucas.apifilme.modelo.Filme
 * 
 * @author Lucas Caio Vargas
 *
 */

@RestController
@RequestMapping("/filmes")
public class FilmeController {

	/**
	 * Faz a comunicação com o banco de dados referente a classe Filme.
	 */
	@Autowired
	FilmeRepository filmeRepository;

	/**
	 * Cadastra um novo filme á partir de um json recebido, seguindo o padrão form.
	 * 
	 * @param form  Formulário a ser seguido para pegar um json do usuário.
	 * @param uriComponentsBuilder  Usada para criar uma uri.
	 * @see com.br.lucas.apifilme.form.FilmForm
	 */

	@PostMapping("/cadastrar")
	@Transactional
	ResponseEntity<FilmeDto> cadastrar(@RequestBody @Valid FilmForm form, UriComponentsBuilder uriComponentsBuilder) {
		try {
			ResponseEntity<Filme> entityFilme = form.converter(filmeRepository);
			Filme filme = entityFilme.getBody();
			filmeRepository.save(filme);
			URI uri = uriComponentsBuilder.path("/filmes{id}").buildAndExpand(filme.getId()).toUri();
			ResponseEntity<FilmeDto> responseEntity = ResponseEntity.created(uri).body(new FilmeDto(filme));
			return responseEntity;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().build();
	}
}
