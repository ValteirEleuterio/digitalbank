package com.matera.cursoferias.digitalbank;

import java.math.BigDecimal;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.matera.cursoferias.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.cursoferias.digitalbank.service.ClienteService;

@Component
public class AppStartupRunner implements ApplicationRunner {

	private final ClienteService clienteService;
	
		
    public AppStartupRunner(ClienteService clienteService) {
		this.clienteService = clienteService;
	}



	@Override
    public void run(ApplicationArguments args) throws Exception {
    	ClienteRequestDTO cliente =  ClienteRequestDTO.builder().bairro("bairro")
    	.cep("6546546")
    	.cidade("Maringá")
    	.complemento(null)
    	.cpf("8702351055")
    	.estado("pr")
    	.logradouro("teste")
    	.nome("Valteir")
    	.numero(123)
    	.rendaMensal(BigDecimal.valueOf(5000l))
    	.telefone(998609170l)
    	.build();
    	
    	
    	ClienteRequestDTO cliente2 =  ClienteRequestDTO.builder().bairro("bairro")
    	    	.cep("6546546")
    	    	.cidade("Maringá")
    	    	.complemento(null)
    	    	.cpf("8702356055")
    	    	.estado("pr")
    	    	.logradouro("teste")
    	    	.nome("Valteir")
    	    	.numero(123)
    	    	.rendaMensal(BigDecimal.valueOf(5000l))
    	    	.telefone(998609140l)
    	    	.build();
    	
    	clienteService.cadastra(cliente);  
    	clienteService.cadastra(cliente2);
    	

    }

}
