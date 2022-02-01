package com.br.lucas.apifilme.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.br.lucas.apifilme.modelo.Filme;
import com.br.lucas.apifilme.modelo.Genero;

/**
 * Classe usada para devolver um Page Filme com os valores titulo, gênero.
 * @author Lucas Caio
 *
 */
public class FilmeTituloGeneroDataDto {
	private String titulo;
	private LocalDate data;
	private Genero genero;
	
	public FilmeTituloGeneroDataDto() {
		
	}
	
	FilmeTituloGeneroDataDto(Filme filme) {
		this.titulo = filme.getTitulo();
		this.data = filme.getData();
		this.genero = filme.getGenero();
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
	
	/**
	 * Converte um Page de Filme em um Page de FilmeTituloGeneroDataDto
	 * @param listaDeFilmes
	 * @return um Page de FilmeTituloGeneroDataDto
	 */
	public static Page<FilmeTituloGeneroDataDto> converter(Page<Filme> listaDeFilmes) {
		return listaDeFilmes.map(FilmeTituloGeneroDataDto::new);
	}
	
	
}
