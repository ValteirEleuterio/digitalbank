package com.matera.cursoferias.digitalbank.service;

import static com.matera.cursoferias.digitalbank.utils.DigitalBankTestUtils.criaClienteRequestDTO;
import static com.matera.cursoferias.digitalbank.utils.DigitalBankTestUtils.criaContaResponseDTO;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matera.cursoferias.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.cursoferias.digitalbank.dto.response.ContaResponseDTO;
import com.matera.cursoferias.digitalbank.entity.Cliente;
import com.matera.cursoferias.digitalbank.repository.ClienteRepository;
import com.matera.cursoferias.digitalbank.utils.DigitalBankTestUtils;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
	
	@Mock
	private ClienteRepository clienteRepositorio;
	
	@Mock
	private ContaService contaService;
	
	@InjectMocks
	private ClienteService clienteService;
	
	@Test
	public void cadastraClienteComSucesso() {
		ClienteRequestDTO clienteRequestDTO = criaClienteRequestDTO();
		ContaResponseDTO contaResponseMock = criaContaResponseDTO();
		
		when(clienteRepositorio.findByCpf(eq(clienteRequestDTO.getCpf()))).thenReturn(empty());
		when(contaService.cadastra(any(Cliente.class))).thenReturn(contaResponseMock);
			
		ContaResponseDTO contaResponseDTO = clienteService.cadastra(clienteRequestDTO);
		
		verify(clienteRepositorio).findByCpf(eq(clienteRequestDTO.getCpf()));
		verify(clienteRepositorio).save(any(Cliente.class));
		verify(contaService).cadastra(any(Cliente.class));
		verifyNoMoreInteractions(clienteRepositorio);
		verifyNoMoreInteractions(contaService);
		
		Assertions.assertEquals(contaResponseMock, contaResponseDTO);
		
	}

}
