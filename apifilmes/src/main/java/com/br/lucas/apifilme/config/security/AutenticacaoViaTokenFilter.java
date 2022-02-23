package com.br.lucas.apifilme.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.br.lucas.apifilme.modelo.Usuario;
import com.br.lucas.apifilme.repository.UsuarioRepository;

//Uma vez que o cliente esteja logado e com o token, esta classe recebe e valida o token  a cada nova requisição dele
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		boolean valido = tokenService.isTokenValido(token);
		
		if (valido) {
			AutenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);//Informa que validou e diz para seguir o fluxo
	}

	//Autentica o cliente já que eu verifiquei o token antes
	private void AutenticarCliente(String token) {
		Long id = tokenService.getIdUsuario(token); //busca o id do usuario logado via token
		Usuario usuario = usuarioRepository.findById(id).get(); // recupera o usuario via id
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, tokenService, usuario.getAuthorities()); // autentica via token passando o usuario o tokenService e as Authorities
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);  //Informa que este usuario está autenticado via token para esta requisição
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); //cabeçario com o token no Postman
		if (token.isEmpty() || token == null || !token.startsWith("Bearer "))
			return null;
		return token.substring(7, token.length()); //Devolve só o token tirando o Bearer que vai até a posição 7
	}

}
