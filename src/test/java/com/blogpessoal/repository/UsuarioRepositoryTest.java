package com.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogpessoal.model.Usuario;
import com.blogpessoal.repositories.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		PasswordEncoder enconder = new BCryptPasswordEncoder();
		Usuario usuario = new Usuario("Fabricio Macedo", "fabri", enconder.encode("fabri"));
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isEmpty())
		usuarioRepository.save(usuario);
	}
	
	@Test
	void buscarUsuarioPorUsuarioTest() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("fabri");
		assertTrue(usuario.isPresent());
		
		usuario = usuarioRepository.findByUsuario("fabricio");
		assertTrue(usuario.isEmpty());
	}
	
	@AfterAll
	void end() {
		usuarioRepository.deleteAll();
	}
}
