package com.blogpessoal.model;

import javax.validation.constraints.NotBlank;

public class UsuarioLogin {

	private String nome;
	
	@NotBlank
	private String usuario;
	
	@NotBlank
	private String senha;
	
	private String token;

	public String getNome() {
		return nome;
	}
	
	public UsuarioLogin() {}

	public UsuarioLogin(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
