package com.br.lucas.apifilme.form;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.http.ResponseEntity;

import com.br.lucas.apifilme.modelo.Filme;
import com.br.lucas.apifilme.modelo.Genero;
import com.br.lucas.apifilme.repository.FilmeRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Formulário a ser seguido para pegar um json do usuário.
 * @author Lucas Caio
 *
 */

public class FilmForm {

	private String titulo;
	@JsonProperty("data")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;
	@Enumerated(EnumType.STRING)
	private Genero genero;
	private String diretor;
	private String comentario;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getAno() {
		return data;
	}

	public void setAno(LocalDate ano) {
		this.data = ano;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
/**
 * Pega os valores do form e usa eles para instanciar um objeto Filme. Faz a checagem no banco com o repository e caso o filme já esteja inserido, lança uma IllegalArgumentException
 * @param repository É usado para acessar o banco de dados.
 * @throws IllegalArgumentException É lançada caso o filme já exista no banco de dados.
 */
	public ResponseEntity<Filme> converter(FilmeRepository repository) throws IllegalArgumentException {
		Optional<Filme> optional = repository.findByTitulo(titulo); 

		if (optional.isEmpty()) {
			Filme filme = new Filme(titulo, data, genero, diretor, comentario);
			return ResponseEntity.ok(filme);
		} else {
			throw new IllegalArgumentException("O filme já existe no Banco de Dados!");

		}

	}

}
