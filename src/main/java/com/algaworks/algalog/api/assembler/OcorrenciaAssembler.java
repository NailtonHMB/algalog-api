package com.algaworks.algalog.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.model.OcorrenciaRepresentation;
import com.algaworks.algalog.domain.model.Ocorrencia;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OcorrenciaAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public OcorrenciaRepresentation toModel(Ocorrencia ocorrencia) {
		return modelMapper.map(ocorrencia, OcorrenciaRepresentation.class);
	}
	
	public List<OcorrenciaRepresentation> toCollectionModel(List<Ocorrencia> ocorrencias){
		return ocorrencias.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
