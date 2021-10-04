package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algalog.domain.ValidationGroups;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Não necessariamente precisa ter um recurso que possua um model na sua API
 * 
 * 
 *  --> O QUE É A CLASSE ENTREGA?
 *  	> É o modelo de representação do recurso Entrega, ou seja, diz quais informações
 *  		deve conter na representação de um recurso do tipo entrega.
 *  
 *  Porém, nossa classe ENTREGA se encontra em duas camadas: camada de domínio e a camada de 
 *  representação.
 *  
 *  O IDEAL É ISOLAR O DOMAIN MODEL DO REPRESENTATION MODEL
 *  
 *  DTO:= DATA TRANSFER MODEL
 * 
 * Por padrão, o Spring segue o ISO 8601 para data e hora, que o padrão recomendado
 * para Rest API.
 * 
 * O que é Offset de data e hora?
 * ---É a diferença entre um padrão de hora e outro (ex: horário de brasilia tem offset
 * de -3H do UTC)
 * 
 * OffsetDateTime é a classe que trabalha com data e hora igual ao LocalDateTime, mas com
 * offset
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Classe da entidade pode ter métodos de negócio tbm
	 * 
	 * @ManyToOne::javax.persistence. faz o mapeamento relacional da Entrega com o Cliente
	 * na relação muitos para um (muitas entregas para um cliente).
	 * 
	 * Inicialmente, do lado relacional (tabela do banco de dados) será considerada para
	 * o mapeamento a coluna cliente_id.
	 * 
	 * mas isso é possivel de ser alterado utilizando o annotation @JoinColumn(name="cliente_id")
	 * onde você pode inserir o nome da coluna que será feito o mapeamento
	 * 
	 * @Valid:javax.validation: o Valid apontando para o atributo cliente permite que,
	 * quando estamos no processo de validação da Entrega, cascateemos a validação, tbm,
	 * para o cliente.
	 * 
	 * @ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class): javax.validation:
	 * indica que, quando começar a validação do atributo cliente, o spring muda de 
	 * grupo de validação. o que era o grupo Default agora será o ValidationGroups.ClienteId
	 */
	@ManyToOne
	//@JoinColumn(name="cliente_id")
	//@NotNull
	//@Valid
	//@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
	private Cliente cliente;
	
	/**
	 * @OneToMany(mappedBy="entrega") :: javax.persistence => mapeia um atributo de
	 * Entrega chamado ocorrencias com o atributo de Ocorrencia chamado entrega no sentido
	 * de uma entrega para muitas ocorrencias
	 */
	@OneToMany(mappedBy="entrega", cascade=CascadeType.ALL)
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
	/**
	 * @Embedded::javax.persistence. Abstrair os dados destinatario para outra classe
	 * (Destinatario), porem mapeando para a mesma tabela Entrega (Todos os dados de 
	 * Destinatario ficarão na tabela Entrega no BD)
	 */
	@Embedded
	//@NotNull
	//@Valid
	private Destinatario destinatario;
	/**
	 * @NotNull: javax.validation.constraints: Validação que naõ permite que o campo seja null
	 */
	//@NotNull
	private BigDecimal taxa;
	
	/**
	 * @Enumerated: javax.persistence: indica o formato que será armazenado na tabela o
	 * atributo Enum logo abaixo. Sendo STRING, quer dizer que armazenará os nomes
	 * das constantes do Enum (no caso, CANCELADA, PENDENTE E FINALIZADA)
	 * 
	 * @JsonProperty(access = Access.READ_ONLY): jackson: Edita as propriedades logo abaixo do Json
	 * que se obtem. aqui, edita-se a forma de acesso do usuário da api ao atributo abaixo
	 * através do json enviado no corpo da requisição http, mudando para que seja apenas 
	 * leitura 
	 */
	@Enumerated(EnumType.STRING)
	//@JsonProperty(access = Access.READ_ONLY)
	private StatusEntrega status;
	//@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataPedido;
	//@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataFinalizacao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Destinatario getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Destinatario destinatario) {
		this.destinatario = destinatario;
	}
	public BigDecimal getTaxa() {
		return taxa;
	}
	public void setTaxa(BigDecimal taxa) {
		this.taxa = taxa;
	}
	public StatusEntrega getStatus() {
		return status;
	}
	public void setStatus(StatusEntrega status) {
		this.status = status;
	}
	public OffsetDateTime getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(OffsetDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}
	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	public Ocorrencia adicionarOcorrencia(String descricao) {
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		this.getOcorrencias().add(ocorrencia);
		
		return ocorrencia;
	}
	public List<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}
	public void setOcorrencias(List<Ocorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}
	public void finalizar() {

		if(!podeSerFinalizada()) {
			throw new NegocioException("Entrega não pode ser finalizada");
		}
		
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
}
