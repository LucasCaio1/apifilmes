package com.br.lucas.apifilme.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.domain.Page;

import com.br.lucas.apifilme.modelo.Filme;
import com.br.lucas.apifilme.modelo.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe usada para devolver valores gerais de um filme para o usuário.
 * @author Lucas Caio Vargas
 *
 */
public class FilmeDto {
	
	private String titulo;
	@JsonProperty("data")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;
	@Enumerated(EnumType.STRING)
	private Genero genero;
	private String diretor;
	private String comentario;
	
	public FilmeDto(Filme filme) {
		this.titulo = filme.getTitulo();
		this.data = filme.getData();
		this.genero = filme.getGenero();
		this.diretor = filme.getDiretor();
		this.comentario = filme.getComentario();
	}
	public String getTitulo() {
		return titulo;
	}
	public LocalDate getData() {
		return data;
	}
	public Genero getGenero() {
		return genero;
	}
	public String getDiretor() {
		return diretor;
	}
	public String getComentario() {
		return comentario;
	}
	public static Page<FilmeDto> converter(Page<Filme> filmePage) {
		return filmePage.map(FilmeDto::new);
	}
	
	
}
