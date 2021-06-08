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

import com.blogpessoal.model.Tema;
import com.blogpessoal.repositories.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> listar() {
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> listarPorId(@PathVariable Long id) {	
		return temaRepository.findById(id)
				//.map(tema -> ResponseEntity.ok(tema))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> listarPorDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(temaRepository.findByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<Tema> novo(@RequestBody @Valid Tema tema) {
		if(tema.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		
		 return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tema> atualizar(@RequestBody @Valid Tema tema, @PathVariable Long id) {
		if(!temaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		tema.setId(id);
		return ResponseEntity.ok(temaRepository.save(tema));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if(!temaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		temaRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
