package com.blogpessoal.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UsuarioModelTest {

	@Autowired
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private Usuario usuario;
	private UsuarioLogin usuarioLogin;
	
	@BeforeEach
	void start() {
		usuario = new Usuario("Fabricio Macedo", "fabri", "fabri");
		usuarioLogin = new UsuarioLogin("fabri", "fabri");
	}
	
	@Test
	void testUsuarioAtributosEmBranco() {
		
		Set<ConstraintViolation<Usuario>> violacao = validator.validate(usuario);
		assertTrue(violacao.isEmpty());
		
		usuario.setSenha("");
		usuario.setUsuario("");
		usuario.setNome("");
		violacao = validator.validate(usuario);
		assertFalse(violacao.isEmpty());
		
	}
	
	@Test
	void testUsuarioSizeDosAtributos() {
		Set<ConstraintViolation<Usuario>> violacao = validator.validate(usuario);
		assertTrue(violacao.isEmpty());
		
		usuario.setNome("caio");
		usuario.setUsuario("caio");
		usuario.setSenha("caio");
		violacao = validator.validate(usuario);
		assertFalse(violacao.isEmpty());
		
		usuario.setNome("caiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaaa");
		usuario.setUsuario("caiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaacaiocaioaaa");
		usuario.setSenha("caioo");
		violacao = validator.validate(usuario);
		assertFalse(violacao.isEmpty());
		
	}
	
	@Test
	void testUsuarioLoginUsuarioESenhaNotBlank() {
		Set<ConstraintViolation<UsuarioLogin>> violacao = validator.validate(usuarioLogin);
		assertTrue(violacao.isEmpty());
		
		usuarioLogin.setUsuario("");
		usuarioLogin.setSenha("");
		violacao = validator.validate(usuarioLogin);
		assertFalse(violacao.isEmpty());
		
	}
}
