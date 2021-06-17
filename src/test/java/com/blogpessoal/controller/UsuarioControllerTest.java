package com.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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

import com.blogpessoal.model.Usuario;
import com.blogpessoal.model.UsuarioLogin;
import com.blogpessoal.repositories.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	void deveCadastrarERetornar201() {
		Usuario usuario = new Usuario("Fabio Macedo", "fabio", "fabio");
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios", HttpMethod.POST, 
				request, Usuario.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void deveFazerLoginERetornarToken() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		usuarioRepository.save(new Usuario("Fabricio", "fabricio", encoder.encode("fabricio")));
		UsuarioLogin usuarioLogin = new UsuarioLogin("fabricio", "fabricio");
		HttpEntity<UsuarioLogin> request = new HttpEntity<UsuarioLogin>(usuarioLogin);
		
		ResponseEntity<UsuarioLogin> resposta = testRestTemplate
				.exchange("/usuarios/login", HttpMethod.POST, request, UsuarioLogin.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertFalse(resposta.getBody().getToken().isBlank());
	}
	
	@Test
	void salva2UsuariosRepetidosERetorna400() {
		Usuario usuario = new Usuario("Fabricio Macedo", "fabri", "fabri");
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios", HttpMethod.POST, 
				request, Usuario.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.CREATED);
		
		resposta = testRestTemplate.exchange("/usuarios", HttpMethod.POST, 
				request, Usuario.class);
		assertEquals(resposta.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@AfterAll
	void end() {
		usuarioRepository.deleteAll();
	}
}
