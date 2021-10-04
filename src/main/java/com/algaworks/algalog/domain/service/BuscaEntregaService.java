package com.algaworks.algalog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.exception.RecursoNaoEncontradoException;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BuscaEntregaService {

	@Autowired
	private EntregaRepository entregaRepository;
	
	public Entrega buscar (Long entregaId) {
		return entregaRepository.findById(entregaId)
				.orElseThrow(()->new RecursoNaoEncontradoException("entrega não encontrada"));
	}
}
