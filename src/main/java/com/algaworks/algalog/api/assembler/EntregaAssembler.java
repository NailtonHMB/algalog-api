package com.algaworks.algalog.api.assembler;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.model.EntregaRepresentation;
import com.algaworks.algalog.api.model.input.EntregaInput;
import com.algaworks.algalog.domain.model.Entrega;

/**
 * Classe responsável por fazer a conversão dos objetos domain model para representation model
 * 
 */
@Component
public class EntregaAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public EntregaRepresentation toRepresentation(Entrega entrega) {
		return modelMapper.map(entrega, EntregaRepresentation.class);
		}
	
	public List<EntregaRepresentation> toCollectionRepresentation(List<Entrega> entregas){
		
		return entregas.stream()
				.map(this::toRepresentation)
				.collect(Collectors.toList());
	}
	
	public Entrega toEntity(EntregaInput entregaInput) {
		
		return modelMapper.map(entregaInput,Entrega.class);
	}
}
