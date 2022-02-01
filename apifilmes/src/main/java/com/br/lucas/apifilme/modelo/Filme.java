package com.br.lucas.apifilme.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**Classe que representa um filme
 * @see com.br.lucas.apifilme.modelo.Genero
 * 
 * @author Lucas Caio Vargas
 * */

@Entity
public class Filme {
	
	/**
	 * Representa um ID auto gerado no banco de dados.
	 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private LocalDate data;
	@Enumerated(EnumType.STRING)
	private Genero genero;
	private String diretor;
	private String comentario;
	
	public Filme() {
		
	}

	public Filme(String titulo, LocalDate data, Genero genero, String diretor, String comentario) {
		this.titulo = titulo;
		this.data = data;
		this.genero = genero;
		this.diretor = diretor;
		this.comentario = comentario;
	}
	
	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate ano) {
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

	
	
	
	
}
