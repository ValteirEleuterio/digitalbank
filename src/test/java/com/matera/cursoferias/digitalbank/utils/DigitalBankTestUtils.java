package com.matera.cursoferias.digitalbank.utils;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;

import com.matera.cursoferias.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.cursoferias.digitalbank.dto.response.ClienteResponseDTO;
import com.matera.cursoferias.digitalbank.dto.response.ContaResponseDTO;
import com.matera.cursoferias.digitalbank.enumerator.SituacaoConta;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class DigitalBankTestUtils {
	
	
	public static ValidatableResponse criaRequisicaoPost(String url, Object body, HttpStatus httpStatus) {
		return RestAssured.given()
						  .spec(criaRequestSpecification(body))
				          .log().all()
				          .when()
				          .post(url)
				          .then()
				          .statusCode(httpStatus.value());
	}
	
	public static ClienteRequestDTO criaClienteRequestDTO() {
		return ClienteRequestDTO.builder()
				.bairro("Bairro 1")
                .cep("87087087")
                .cidade("Maringá")
                .complemento(null)
                .cpf("72979929921")
                .estado("PR")
                .logradouro("Rua 1")
                .nome("Cliente 1")
                .numero(123)
                .rendaMensal(BigDecimal.valueOf(5000))
                .telefone(44999001234L)
                .build();
	}
	
	public static ContaResponseDTO criaContaResponseDTO() {
		return ContaResponseDTO.builder().idCliente(1L)
				.idConta(2L)
				.numeroAgencia(1234)
				.numeroConta(102030L)
				.saldo(BigDecimal.ZERO)
				.situacao(SituacaoConta.ABERTA.getCodigo())
				.build();
	}
		
	private static RequestSpecification criaRequestSpecification(Object body) {
		return new RequestSpecBuilder().setContentType(ContentType.JSON)
									   .addHeader("Accept", ContentType.JSON.toString())
									   .setBody(body)
									   .build();
	}

}
