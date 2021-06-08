package com.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogpessoal.model.Usuario;
import com.blogpessoal.model.UsuarioLogin;
import com.blogpessoal.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		
		return usuarioRepository.save(usuario);
	}
	
	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> usuarioLogin) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		Optional<Usuario> usuarioBD = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(usuarioBD.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuarioBD.get().getSenha())) {
				String auth = String.format("%s:%s",
						usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());
				byte[] tokenBase64 = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String tokenCompleto = "Basic " + new String(tokenBase64);
				
				usuarioLogin.get().setToken(tokenCompleto);
				usuarioLogin.get().setNome(usuarioBD.get().getNome());
				
				return usuarioLogin;
				
			}
		}
		
		return Optional.empty();
	}
}
