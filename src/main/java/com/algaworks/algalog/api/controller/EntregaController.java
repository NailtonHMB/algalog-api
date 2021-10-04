package com.algaworks.algalog.api.controller;

import java.util.List;
import java.util.function.Function;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.EntregaAssembler;
import com.algaworks.algalog.api.model.DestinatarioRepresentation;
import com.algaworks.algalog.api.model.EntregaRepresentation;
import com.algaworks.algalog.api.model.input.EntregaInput;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.FinalizacaoEntregaService;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

	@Autowired
	private FinalizacaoEntregaService finalizacaoEntregaService;
	
	@Autowired
	private SolicitacaoEntregaService solicitacaoEntregaService;
	
	@Autowired
	private EntregaRepository entregaRepository;
	
	@Autowired
	private EntregaAssembler entregaAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaRepresentation solicitar(@Valid @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
		
		return entregaAssembler.toRepresentation(solicitacaoEntregaService.solicitar(novaEntrega));
	}

	@GetMapping
	public List<EntregaRepresentation> listar(){
		return entregaAssembler.toCollectionRepresentation(entregaRepository.findAll());
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
	
		finalizacaoEntregaService.finalizar(entregaId);
	}
	
	@GetMapping("{entregaId}")
	//public ResponseEntity<Entrega> buscar(@PathVariable Long entregaId) {
	public ResponseEntity<EntregaRepresentation> buscar(@PathVariable Long entregaId) {
		return entregaRepository.findById(entregaId)
				//.map(ResponseEntity::ok)
				.map(entrega-> ResponseEntity.ok(entregaAssembler.toRepresentation(entrega)))
					/**EntregaRepresentation entregaRep = new EntregaRepresentation();
					entregaRep.setNomeCliente(entrega.getCliente().getName());
					entregaRep.setId(entrega.getId());
					entregaRep.setStatus(entrega.getStatus());
					entregaRep.setTaxa(entrega.getTaxa());
					entregaRep.setDataPedido(entrega.getDataPedido());
					entregaRep.setDataFinalizacao(entrega.getDataFinalizacao());
					entregaRep.setDestinatario(new DestinatarioRepresentation());
					entregaRep.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
					entregaRep.getDestinatario().setNome(entrega.getDestinatario().getNome());
					entregaRep.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
					entregaRep.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
					entregaRep.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());*/
				.orElse(ResponseEntity.notFound().build());
	}
}
