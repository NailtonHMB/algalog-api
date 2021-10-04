package com.algaworks.algalog.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResumoRepresentation {

	private Long id;
	private String nome;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
