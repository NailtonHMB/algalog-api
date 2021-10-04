package com.algaworks.algalog.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.Ocorrencia;
import com.algaworks.algalog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroOcorrenciaService {

	@Autowired
	private BuscaEntregaService buscaEntregaService;
	
	/**
	 * ==> POR QUE NÃO EXISTE SAVE DO EntregaRepository PARA SALVAR AS ALTERAÇÕES FEITAS
	 * NO ATRIBUTOS ocorrencias DE Entrega??
	 * 		
	 * 		=> Não é necessário por que o Jakarta Persistence já sincroniza as alterações
	 * realizadas quando existe uma transação aberta (@Transactional), ou seja, qualquer altera-
	 * ção feita na Entrega em entrega será salva quando a transação encerrar
	 * 
	 */
	@Transactional
	public Ocorrencia registrar(Long entregaId, String descricao) {
		
		return buscaEntregaService.buscar(entregaId).adicionarOcorrencia(descricao);
	}
}
