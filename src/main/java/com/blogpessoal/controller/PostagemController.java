package com.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.model.Postagem;
import com.blogpessoal.repositories.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> findAllPostagem() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> findById(@PathVariable Long id) {
		return postagemRepository.findById(id)
				//.map(postagem ->  ResponseEntity.ok(postagem))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> findByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findByTituloContainingIgnoreCase(titulo));
	}
	
	
	@PostMapping
	public ResponseEntity<Postagem> novaPostagem(@RequestBody @Valid Postagem postagem) {
		if(postagem.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
	
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Postagem> atualizar(@PathVariable Long id, @RequestBody @Valid Postagem postagem) {
		if(!postagemRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		postagem.setId(id);
		return ResponseEntity.ok(postagemRepository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if(!postagemRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		postagemRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
}
