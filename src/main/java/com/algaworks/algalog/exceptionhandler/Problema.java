package com.algaworks.algalog.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*Classe que vai representar um Erro
 * */
/**
 * Jackson
 * @JsonInclude(Include.NON_NULL):: Quando for transformar o objeto em formato
 * Json, não incluir campos que estejam NULL
 * 
 *
 */
@JsonInclude(Include.NON_NULL)
@Setter
@Getter
public class Problema {

	private Integer status;
	private OffsetDateTime dataHora;
	private String titulo;
	private List<Campo> campo;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public OffsetDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(OffsetDateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	public List<Campo> getCampo() {
		return campo;
	}
	public void setCampo(List<Campo> campo) {
		this.campo = campo;
	}

	@AllArgsConstructor
	@Getter
	public static class Campo{
		private String nome;
		private String mensagem;
		
		Campo(String nome, String mensagem){
			this.nome = nome;
			this.mensagem = mensagem;
		}

		public String getNome() {
			return nome;
		}

		public String getMensagem() {
			return mensagem;
		}
	}

}
