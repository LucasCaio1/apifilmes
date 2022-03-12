package com.br.lucas.apifilme.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.lucas.apifilme.dto.FilmeDto;
import com.br.lucas.apifilme.dto.FilmeTituloGeneroDataDto;
import com.br.lucas.apifilme.form.BuscaPorGeneroForm;
import com.br.lucas.apifilme.form.BuscaPorTituloForm;
import com.br.lucas.apifilme.form.BuscarPorDiretorForm;
import com.br.lucas.apifilme.form.FilmForm;
import com.br.lucas.apifilme.modelo.Filme;
import com.br.lucas.apifilme.repository.FilmeRepository;

/**
 * Classe controller da classe Filme, realiza as determinadas operações:
 * cadastrar
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
	 * Cadastra um novo filme no banco de dados á partir de um json recebido,
	 * seguindo o padrão form.
	 * 
	 * @param form Formulário a ser seguido para pegar um json do usuário.
	 * @param uriComponentsBuilder Usada para criar uma uri.
	 * @return devolve created se conseguir criar o filme no banco ou badRequest caso não.
	 * @see com.br.lucas.apifilme.form.FilmForm
	 */

	@PostMapping("/cadastrar")
	@Transactional
	@CacheEvict(cacheNames = { "listaDeFilmesGenero", "listaDeFilmes", "listaDeFilmesTitulo" }, allEntries = true)
	public ResponseEntity<FilmeDto> cadastrar(@RequestBody @Valid FilmForm form,
			UriComponentsBuilder uriComponentsBuilder) {
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

	/**
	 * Devolve um Page de todos os filmes; o cabeçalho do retorno é o seguinte:
	 * título, data, gênero.
	 * 
	 * @param paginacao
	 * @return Todos os filmes.
	 * @see com.br.lucas.apifilme.dto.FilmeTituloGeneroDataDto
	 */
	@Cacheable(value = "listaDeFilmes")
	@GetMapping("/listar")
	public Page<FilmeTituloGeneroDataDto> listar(
			@PageableDefault(sort = "data", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		Page<Filme> filmePage = filmeRepository.findAll(paginacao);
		return FilmeTituloGeneroDataDto.converter(filmePage);
	}

	/**
	 * Devolve um Page dos filmes de acordo com o gênero; o cabeçalho do retorno é o
	 * seguinte: título, data, gênero.
	 * 
	 * @param form
	 * @param paginacao
	 * @return Os filmes por gênero.
	 * @see com.br.lucas.apifilme.form.BuscaPorGeneroForm
	 * @see com.br.lucas.apifilme.dto.FilmeTituloGeneroDataDto
	 */
	@Cacheable(value = "listaDeFilmesGenero")
	@GetMapping("/buscarGenero")
	public Page<FilmeTituloGeneroDataDto> buscarGenero(@RequestBody @Valid BuscaPorGeneroForm form,
			@PageableDefault(sort = "data", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		Page<Filme> filmePage = filmeRepository.findByGenero(form.getGenero(), paginacao);
		return FilmeTituloGeneroDataDto.converter(filmePage);
	}

	/**
	 * Devolve um Page dos filmes de acordo com o título; o cabeçalho do retorno é o
	 * seguinte: título, gênero, diretor, comentário, data.
	 * 
	 * @param form
	 * @param paginacao
	 * @return o filme buscado pelo título.
	 * @see com.br.lucas.apifilme.form.BuscaPorTituloForm
	 * @see com.br.lucas.apifilme.dto.FilmeDto
	 */
	@Cacheable(value = "listaDeFilmesTitulo")
	@GetMapping("/buscarTitulo")
	public Page<FilmeDto> buscarTitulo(@RequestBody @Valid BuscaPorTituloForm form,
			@PageableDefault(sort = "data", direction = Direction.ASC, page = 0, size = 1) Pageable paginacao) {
		Page<Filme> filmePage = filmeRepository.findByTitulo(form.getTitulo(), paginacao);
		return FilmeDto.converter(filmePage);
	}

	/**
	 * Devolve um Page dos filmes de acordo com o diretor; o cabeçalho do retorno é
	 * o seguinte: título, gênero, diretor, comentário, data.
	 * 
	 * @param form
	 * @param paginacao
	 * @return o filme buscado pelo diretor.
	 * @see com.br.lucas.apifilme.form.BuscaPorDiretorForm
	 * @see com.br.lucas.apifilme.dto.FilmeDto
	 */
	@Cacheable(value = "listaDeFilmesDiretor")
	@GetMapping("/buscarDiretor")
	public Page<FilmeDto> buscarDiretor(@RequestBody @Valid BuscarPorDiretorForm form,
			@PageableDefault(sort = "data", direction = Direction.ASC, page = 0, size = 1) Pageable paginacao) {
		Page<Filme> filmePage = filmeRepository.findByDiretor(form.getDiretor(), paginacao);
		return FilmeDto.converter(filmePage);
	}

	@DeleteMapping("/apagarFilme")
	public ResponseEntity<?> apagarFilme(@RequestBody @Valid BuscaPorTituloForm form) {
		Optional<Filme> tituloFilme = filmeRepository.findByTitulo(form.getTitulo());
		if (tituloFilme.isPresent()) {
			filmeRepository.delete(tituloFilme.get());
			ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();

	}
}
