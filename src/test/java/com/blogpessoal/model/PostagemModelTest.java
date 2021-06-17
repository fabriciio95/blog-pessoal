package com.blogpessoal.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blogpessoal.validations.ValidationsGroups.ValidationAtualizacaoPostagem;

class PostagemModelTest {

	@Autowired
	private ValidatorFactory factory =  Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	private Postagem postagem;
	private Tema tema;
	
	@BeforeEach
	void start() {
		tema = new Tema("Programação");
		postagem = new Postagem("Spring", "Um framework baseado em Java.", tema);
	}
	
	@Test
	void testValidaAtributosComGrupoDefault() {
		postagem.getTema().setId(1L);
		Set<ConstraintViolation<Postagem>> violacao = validator.validate(postagem, Default.class);
		assertTrue(violacao.isEmpty());

		postagem.getTema().setId(null);
		violacao = validator.validate(postagem, Default.class);
		assertFalse(violacao.isEmpty());
		
		postagem.getTema().setId(1L);
		postagem.setId(1L);
		violacao = validator.validate(postagem, Default.class);
		assertFalse(violacao.isEmpty());
	}
	
	@Test
	void testValidaAtributosComGrupoValidationAtualizacaoPostagem() {
		postagem.getTema().setId(1L);
		Set<ConstraintViolation<Postagem>> violacao = validator
				.validate(postagem, ValidationAtualizacaoPostagem.class);
		assertFalse(violacao.isEmpty());
		
		postagem.setId(1L);
		postagem.getTema().setId(null);
		violacao = validator
				.validate(postagem, ValidationAtualizacaoPostagem.class);
		assertFalse(violacao.isEmpty());
		
		postagem.getTema().setId(1L);
		violacao = validator
				.validate(postagem, ValidationAtualizacaoPostagem.class);
		assertTrue(violacao.isEmpty());
	}

}
