package com.blogpessoal.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.model.Usuario;
import com.blogpessoal.model.UsuarioLogin;
import com.blogpessoal.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/login") 
	public ResponseEntity<UsuarioLogin> logar(@RequestBody @Valid Optional<UsuarioLogin> usuarioLogin) {
		return usuarioService.logar(usuarioLogin)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping
	public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid Usuario usuario) {
		try {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioService.cadastrarUsuario(usuario));
		} catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
