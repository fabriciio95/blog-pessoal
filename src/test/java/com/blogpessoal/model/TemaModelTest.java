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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogpessoal.validations.ValidationsGroups.ValidationAtualizacaoTema;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TemaModelTest {

	@Autowired
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	private Tema tema;
	
	@BeforeEach
	void start() {
		tema = new Tema("Programação");
	}
	
	@Test
	void testValidaAtributosComGrupoDefault() {
		
		Set<ConstraintViolation<Tema>> violacao = validator.validate(tema, Default.class);
		assertTrue(violacao.isEmpty());
		
		tema.setId(1L);
		violacao = validator.validate(tema, Default.class);
		assertFalse(violacao.isEmpty());
	}
	
	@Test
	void testValidaAtributosComGrupoValidationAtualizacaoTema() {
		
		Set<ConstraintViolation<Tema>> violacao =  validator.validate(tema, ValidationAtualizacaoTema.class);
		assertFalse(violacao.isEmpty());
		
		tema.setId(1L);
		violacao = validator.validate(tema, ValidationAtualizacaoTema.class);
		assertTrue(violacao.isEmpty());
		
	}
}
