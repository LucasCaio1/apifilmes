package com.br.lucas.apifilme.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // habilita o modo de segurança na nossa app
@Configuration // Avisa que esta é uma classe de configuração
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService autenticacaoService;

	// configurações de controle de acesso. ex: login
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// configurações de autorização. ex: quem pode acessar qual url
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/filmes/listar").permitAll()
				.antMatchers(HttpMethod.GET, "/fimes/buscarGenero").permitAll()
				.antMatchers(HttpMethod.GET, "/filmes/buscarTitulo").permitAll()
				.antMatchers(HttpMethod.GET, "/filmes/buscarDiretor").permitAll().anyRequest().authenticated().and()
				.formLogin();
	}

	// cofigurações para recursos estático. ex: javascript, css, arquivos de imagem
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
}
