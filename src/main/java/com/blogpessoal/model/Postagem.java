package com.blogpessoal.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.blogpessoal.validations.ValidationsGroups.ValidationAtualizacaoPostagem;
import com.blogpessoal.validations.ValidationsGroups.ValidationId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
@Entity
public class Postagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(groups = ValidationAtualizacaoPostagem.class)
	@Null
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(groups = {Default.class, ValidationAtualizacaoPostagem.class})
	@Size(groups = {Default.class, ValidationAtualizacaoPostagem.class}, min = 5, max = 100)
	private String titulo;
	
	@NotBlank(groups = {Default.class, ValidationAtualizacaoPostagem.class})
	@Size(groups = {Default.class, ValidationAtualizacaoPostagem.class}, min = 10, max = 500)
	private String texto;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new java.sql.Date(System.currentTimeMillis());
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationId.class)
	@ConvertGroup(from = ValidationAtualizacaoPostagem.class, to = ValidationId.class)
	@NotNull(groups = {Default.class, ValidationAtualizacaoPostagem.class})
	@ManyToOne
	@JsonIgnoreProperties("postagens")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagens")
	private Usuario usuario;
	
	public Postagem() {}

	public Postagem(String titulo, String texto, Tema tema) {
		this.titulo = titulo;
		this.texto = texto;
		this.tema = tema;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
