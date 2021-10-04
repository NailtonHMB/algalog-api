package com.algaworks.algalog.api.controller;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;



/*Rest Controller indica que essa classe é capaz de lidar com requisições http 
 * utilizando Rest*/
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CatalogoClienteService catalogoClienteService;
	
	/*Get mapping indica que essa função será chamada para tratar uma requisição GET
	 * do resource '/clientes' quando for digitado no browser "localhost/clientes" */
	@GetMapping
	public List<Cliente> listar(){
		//return clienteRepository.findByNameContaining("henri");
		return clienteRepository.findAll();
		//return manager.createQuery("from Cliente",Cliente.class).getResultList();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		
		return clienteRepository.findById(clienteId)
				//.map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		//Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
	//	if(cliente.isPresent()) {
	//		return ResponseEntity.ok(cliente.get());
	//	}
	//	
	//	return ResponseEntity.notFound().build();
	}
	
	/*RETORNA UMA REPRESENTAÇÃO DE UM RECURSO. COMO O RECURSO É UM COLLECTION, ENTÃO É RE-
	 * REPRESENTADO POR UMA LISTA*/
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return catalogoClienteService.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, 
			@Valid @RequestBody Cliente cliente){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		//Se não fosse colocado, ele criaria uma outro cliente e não atualizaria
		cliente.setId(clienteId);
		//cliente = clienteRepository.save(cliente);
		cliente = catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		//clienteRepository.deleteById(clienteId);
		catalogoClienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
}
