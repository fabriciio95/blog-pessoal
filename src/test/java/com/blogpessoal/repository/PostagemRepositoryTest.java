package com.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.blogpessoal.model.Postagem;
import com.blogpessoal.model.Tema;
import com.blogpessoal.repositories.PostagemRepository;
import com.blogpessoal.repositories.TemaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class PostagemRepositoryTest {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@BeforeAll
	void start() {
		Tema tema = temaRepository.save(new Tema("Tecnologia"));
		
		Postagem postagem = new Postagem("Programação", "Java é top!", tema);
		postagemRepository.save(postagem);
		
		postagem = new Postagem("Spring", "Um framework baseado em Java!", tema);
		postagemRepository.save(postagem);
		
		postagem = new Postagem("Jakarta Persistence API", "Uma especificação utilizada para fazer o"
				+ " mapeamento objeto-relacional", tema);
		postagemRepository.save(postagem);
		
	}
	
	@Test
	void buscarPostagensPorTituloTest() {
		List<Postagem> postagens = postagemRepository.findByTituloContainingIgnoreCase("a");
		assertEquals(2, postagens.size());
		
		postagens = postagemRepository.findByTituloContainingIgnoreCase("r");
		assertEquals(3, postagens.size());
	}

	@AfterAll
	void end() {
		postagemRepository.deleteAll();
		temaRepository.deleteAll();
	}
}
