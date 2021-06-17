package com.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogpessoal.model.Tema;
import com.blogpessoal.repositories.TemaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class TemaRepositoryTest {

	@Autowired
	private TemaRepository temaRepository;
	
	@BeforeAll
	void start() {
		Tema tema = new Tema("Programação");
		temaRepository.save(tema);
		
		tema = new Tema("Matemática");
		temaRepository.save(tema);
		
		tema = new Tema("Educação Financeira");
		temaRepository.save(tema);
	}
	
	@Test
	void buscarTemasPorDescricaoTest() {
		List<Tema> temas = temaRepository.findByDescricaoContainingIgnoreCase("a");
		assertEquals(3, temas.size());
		
		temas = temaRepository.findByDescricaoContainingIgnoreCase("m");
		assertEquals(2, temas.size());
		
		temas = temaRepository.findByDescricaoContainingIgnoreCase("u");
		assertEquals(1, temas.size());
	}
	
	
	
	@AfterAll
	void end() {
		temaRepository.deleteAll();
	}

}
