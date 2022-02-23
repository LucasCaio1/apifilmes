package com.br.lucas.apifilme.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.lucas.apifilme.modelo.Usuario;
import com.br.lucas.apifilme.repository.UsuarioRepository;

//Classe que faz a autenticação do usuário
@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // username é o email
																								// digitado no login
		Optional<Usuario> usuario = repository.findByEmail(username);
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			throw new UsernameNotFoundException("Dados inválidos!");
		}
	}

}
