package com.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogpessoal.model.Postagem;
import com.blogpessoal.model.Tema;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.repositories.PostagemRepository;
import com.blogpessoal.repositories.TemaRepository;
import com.blogpessoal.repositories.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class PostagemControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	private Postagem postagem;
	
	@BeforeAll
	void start() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		usuarioRepository.save(new Usuario("Fabricio", "fabri", encoder.encode("fabri")));
		Tema tema = temaRepository.save(new Tema("Tecnologia"));
		postagem = postagemRepository.save(new Postagem("Programação",
				"Spring é um framework baseado em Java", tema));
	}

	@Test
	void testUrlsProtegidasPeloSecurity() {
		HttpEntity<Postagem> request = new HttpEntity<Postagem>(postagem);
		
		ResponseEntity<String> resposta = testRestTemplate
				.exchange("/postagens", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/postagens/1", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/postagens/titulo/a", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/postagens", HttpMethod.POST, request, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/postagens", HttpMethod.PUT, request, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/postagens/1", HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
	}
	
	
	@AfterAll
	void end() {
		postagemRepository.deleteAll();
		temaRepository.deleteAll();
		usuarioRepository.deleteAll();
	}
}
