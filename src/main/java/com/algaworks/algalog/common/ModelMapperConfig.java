package com.algaworks.algalog.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@Configuration:= indica que esta classe é um bean que possui métodos que configuram e definem
 *outros Beans
 * 
 *
 */
@Configuration
public class ModelMapperConfig {

	/**
	 * @Bean:=Indica que essa função inicializa e configura um bean paraque a mesma seja
	 * disponibilizada para o Spring para ser injetada em outras classes
	 * 
	 * -->POR QUE É NECESSÁRIO FAZER ISSO COM MODEL MAPPER?
	 * 	>Porque Model Mapper é uma biblioteca a parte aderida ao projeto e não faz parte
	 * do ecossistema spring. Então, fazendo dessa forma, é possível injetar um objeto do 
	 * do tipo Modelmapper.
	 * 	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
