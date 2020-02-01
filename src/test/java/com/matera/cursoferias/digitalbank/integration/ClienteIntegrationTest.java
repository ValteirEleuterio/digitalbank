package com.matera.cursoferias.digitalbank.integration;

import static com.matera.cursoferias.digitalbank.utils.DigitalBankTestUtils.criaClienteRequestDTO;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

import com.matera.cursoferias.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.cursoferias.digitalbank.enumerator.SituacaoConta;
import com.matera.cursoferias.digitalbank.utils.DigitalBankTestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ClienteIntegrationTest {
	
	private static final String URL_BASE = "/api/v1/clientes";
	
	@Test
	public void cadastraClienteComSucesso() {
		ClienteRequestDTO clienteRequestDTO = criaClienteRequestDTO();
		
		DigitalBankTestUtils.criaRequisicaoPost(URL_BASE, clienteRequestDTO, HttpStatus.CREATED)
			.root("dados")
			.body("idCliente", greaterThan(0))
			.body("idConta", greaterThan(0))
			.body("numeroAgencia", greaterThan(0))
			.body("numeroConta", equalTo(clienteRequestDTO.getTelefone()))
			.body("situacao", equalTo(SituacaoConta.ABERTA.getCodigo()))
			.body("saldo", equalTo(BigDecimal.ZERO.intValue()));
		
	}
	
	

}
