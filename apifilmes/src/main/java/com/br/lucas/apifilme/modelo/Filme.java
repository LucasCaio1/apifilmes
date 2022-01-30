package com.br.lucas.apifilme.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**Classe que representa um filme
 * @author Lucas Caio Vargas
 * @version 1.0.0
 * 
 * @see com.br.lucas.apifilme.modelo.Genero
 * */

@Entity
public class Filme {
	
	/**
	 * Representa um ID auto gerado no banco de dados.
	 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private LocalDate ano;
	private Genero genero;
	private String diretor;
	private String comentario;
	
	
	
}
