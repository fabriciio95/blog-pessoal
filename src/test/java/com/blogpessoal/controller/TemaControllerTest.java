package com.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.blogpessoal.model.Tema;
import com.blogpessoal.model.Usuario;
import com.blogpessoal.repositories.TemaRepository;
import com.blogpessoal.repositories.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class TemaControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	private Tema tema;
	
	
	@BeforeAll
	void start() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		usuarioRepository.save(new Usuario("Fabricio", "fabri", encoder.encode("fabri")));
		tema = new Tema("Tecnologia");
	}
	
	@Test
	void testUrlsProtegidasPeloSecurity() {
		HttpEntity<Tema> request = new HttpEntity<Tema>(tema);
		
		ResponseEntity<String> resposta = testRestTemplate
				.exchange("/temas", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/temas/1", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/temas/descricao/a", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/temas", HttpMethod.POST, request, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/temas", HttpMethod.PUT, request, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
		resposta = testRestTemplate
				.exchange("/temas/1", HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, resposta.getStatusCode());
		
	}

	@Test
	void deveCadastrarTemaERetornar201() {
		HttpEntity<Tema> request = new HttpEntity<Tema>(tema);
		
		ResponseEntity<Tema> resposta = testRestTemplate.withBasicAuth("fabri", "fabri")
				.exchange("/temas", HttpMethod.POST, request, Tema.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
	
	@AfterAll
	void end() {
		temaRepository.deleteAll();
		usuarioRepository.deleteAll();
	}
	
}
