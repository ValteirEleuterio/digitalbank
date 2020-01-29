package com.matera.cursoferias.digitalbank.dto.response;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponseDTO<T> {

	private T dados;
	private List<ErroResponseDTO> erros;
	
	public ResponseDTO (T dados) {
		this.dados = dados;
	}
	
	public static ResponseDTO<Object> comErros(List<ErroResponseDTO> erros) {
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		
		responseDTO.setErros(erros);
		
		return responseDTO;
	}
	
	public static ResponseDTO<Object> comErro(ErroResponseDTO erro) {
		return comErros(Arrays.asList(erro));
	}
}
