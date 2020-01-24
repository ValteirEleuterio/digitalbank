package com.matera.cursoferias.digitalbank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.matera.cursoferias.digitalbank.entity.Cliente;
import com.matera.cursoferias.digitalbank.repository.ClienteRepository;

@Component
public class AppStartupRunner implements ApplicationRunner {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Cliente cliente = new Cliente( 
				"Valteir", 
				"08755516963", 
				44998609170L,
				BigDecimal.valueOf(1000L),
				"Rua das Seringueiras",
				116,
				null,
				"Parque das Bandeiras",
				"Maringá",
				"PR",
				"87023510");
		
		clienteRepository.save(cliente);
		
		
		Cliente cliente2 = clienteRepository.findById(cliente.getId()).orElse(null);
		System.out.println("Cliente 2:" + cliente2);
		
		Cliente cliente3 = clienteRepository.buscaPorCpf(cliente.getCpf()).orElse(null);
		System.out.println("Cliente 3:" + cliente3);
				
		Optional<Cliente> cliente4 = clienteRepository.findById(cliente.getId());
		System.out.println(cliente4.isPresent());
		
		clienteRepository.delete(cliente2);
		
		
		
	}

	

}
