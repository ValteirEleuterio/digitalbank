package com.matera.cursoferias.digitalbank.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matera.cursoferias.digitalbank.dto.request.LancamentoRequestDTO;
import com.matera.cursoferias.digitalbank.dto.request.TransferenciaRequestDTO;
import com.matera.cursoferias.digitalbank.dto.response.ComprovanteResponseDTO;
import com.matera.cursoferias.digitalbank.dto.response.ContaResponseDTO;
import com.matera.cursoferias.digitalbank.dto.response.ExtratoResponseDTO;
import com.matera.cursoferias.digitalbank.dto.response.ResponseDTO;
import com.matera.cursoferias.digitalbank.enumerator.TipoLancamento;
import com.matera.cursoferias.digitalbank.service.ContaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/contas")
@Slf4j
public class ContaController extends ControllerBase {

    private final ContaService contaService;

    public ContaController(MessageSource messageSource, ContaService contaService) {
        super(messageSource);
    	this.contaService = contaService;
    }
    
    @GetMapping("/{idConta}/lancamentos/{idLancamento}")
    public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> consultaComprovanteLancamento(@PathVariable("idConta") Long idConta,
    																					     @PathVariable("idLancamento") Long idLancamento){
    	log.debug("Iniciando GET em /api/v1/contas/{idConta}/lancamentos/{idLancamento} com idConta {} e idLancamento {}", idConta, idLancamento);
    	
    	ComprovanteResponseDTO comprovanteResponseDTO = contaService.consultaComprovanteLancamento(idConta, idLancamento);

    	log.debug("Finalizando GET em /api/v1/contas/{idConta}/lancamentos/{idLancamento} com idConta {} e idLancamento {} e response {}", idConta, idLancamento, comprovanteResponseDTO);
    	
    	return ResponseEntity.status(HttpStatus.OK)
    			             .body(new ResponseDTO<>(comprovanteResponseDTO));
    }
    
    
    @DeleteMapping("/{idConta}/lancamentos/{idLancamento}")
    public ResponseEntity<Void> removeLancamentoEstorno(@PathVariable("idConta") Long idConta, 
    													@PathVariable("idLancamento") Long idLancamento) {
    	log.debug("Iniciando DELETE em /api/v1/contas/{idConta}/lancamentos/{idLancamento} com idConta {} e idLancamento {}", idConta, idLancamento);
    	
    	contaService.removeLancamentoEstorno(idConta, idLancamento);
    	
    	log.debug("Finalizando DELETE em /api/v1/contas/{idConta}/lancamentos/{idLancamento} com idConta {} e idLancamento {}", idConta, idLancamento);
    	
    	return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/bloquear")
    public ResponseEntity<Void> bloqueiaConta(@PathVariable("id")Long id){
    	log.debug("Iniciando POST em /api/v1/contas/{id}/bloquear com id {}", id);
    	
    	contaService.bloqueiaConta(id);
    	
    	log.debug("Finalizando POST em /api/v1/contas/{id}/bloquear com id {}", id);
    	
    	return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/desbloquear")
    public ResponseEntity<Void> desbloqueiaConta(@PathVariable("id")Long id){
    	log.debug("Iniciando POST em /api/v1/contas/{id}/desbloquear com id {}", id);
    	
    	contaService.desbloqueiaConta(id);
    	
    	log.debug("Finalizando POST em /api/v1/contas/{id}/desbloquear com id {}", id);
    	
    	return ResponseEntity.noContent().build();
    }
    
    
    @PostMapping("/{idConta}/lancamentos/{idLancamento}/estornar")
    public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> estornaLancamento(@PathVariable("idConta") Long idConta,
		     													                 @PathVariable("idLancamento") Long idLancamento){
    	log.debug("Iniciando POST em /api/v1/contas/{idConta}/lancamentos/{idLancamento}/estornar com idConta {} e idLancamento {}", idConta, idLancamento);
    	
    	ComprovanteResponseDTO comprovanteResponseDTO = contaService.estornaLancamento(idConta, idLancamento);
    	
    	log.debug("Finalizando POST em /api/v1/contas/{idConta}/lancamentos/{idLancamento}/estornar com idConta {} e idLancamento {} e comprovante {}", idConta, idLancamento, comprovanteResponseDTO);
    	
    	return ResponseEntity.status(HttpStatus.OK)
    			             .body(new ResponseDTO<>(comprovanteResponseDTO));
    }
    
    @GetMapping(value = "/{id}/lancamentos", params = { "!dataInicial", "!dataFinal" })
    public ResponseEntity<ResponseDTO<ExtratoResponseDTO>> consultaExtratoCompleto(@PathVariable("id") Long id){
    	log.debug("Iniciando GET em /api/v1/contas/{id}/lancamento com id {}", id);
    	
    	ExtratoResponseDTO estratoResponseDTO = contaService.consultaExtratoCompleto(id);
    	
    	log.debug("Finalizando GET em /api/v1/contas/{id}/lancamento com id {}", id);
    	
    	return ResponseEntity.status(HttpStatus.OK)
    						 .body(new ResponseDTO<>(estratoResponseDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ContaResponseDTO>>> consultaTodas(){
    	log.debug("Iniciando GET em /api/v1/contas");
    	
    	List<ContaResponseDTO> responseContas = contaService.consultaTodas();
    	
    	log.debug("Finalizando GET em /api/v1/contas com  response {}", Arrays.toString(responseContas.toArray()));
    	
    	return ResponseEntity.status(HttpStatus.OK)
    			             .body(new ResponseDTO<>(responseContas));
    }
    
    @PostMapping("/{id}/depositar")
    public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaDeposito(@PathVariable("id") Long id, @Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
    	log.debug("Iniciando POST em /api/v1/contas/{id}/depositar com id {} e com request {}", id, lancamentoRequestDTO);
    	
    	ComprovanteResponseDTO comprovanteResponseDTO = contaService.efetuaLancamento(id, lancamentoRequestDTO, TipoLancamento.DEPOSITO);

    	log.debug("Finalizando POST em /api/v1/contas/{id}/depositar com id {} e com response {}", id, comprovanteResponseDTO);
    	
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new ResponseDTO<>(comprovanteResponseDTO));
    }

    @PostMapping("/{id}/sacar")
    public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaSaque(@PathVariable("id") Long id, @Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
    	log.debug("Iniciando POST em /api/v1/contas/{id}/sacar com id {} e com request {}", id, lancamentoRequestDTO);
    	
    	ComprovanteResponseDTO comprovanteResponseDTO = contaService.efetuaLancamento(id, lancamentoRequestDTO, TipoLancamento.SAQUE);

    	log.debug("Finalizando POST em /api/v1/contas/{id}/sacar com id {} e com response {}", id, comprovanteResponseDTO);
    	
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new ResponseDTO<>(comprovanteResponseDTO));
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaPagamento(@PathVariable("id") Long id, @Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
    	log.debug("Iniciando POST em /api/v1/contas/{id}/pagar com id {} e com request {}", id, lancamentoRequestDTO);
    	
    	ComprovanteResponseDTO comprovanteResponseDTO = contaService.efetuaLancamento(id, lancamentoRequestDTO, TipoLancamento.PAGAMENTO);

    	log.debug("Finalizando POST em /api/v1/contas/{id}/pagar com id {} e com response {}", id, comprovanteResponseDTO);
    	
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new ResponseDTO<>(comprovanteResponseDTO));
    }

    @PostMapping("/{id}/transferir")
    public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaTransferencia(@PathVariable("id") Long id, @Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
    	log.debug("Iniciando POST em /api/v1/contas/{id}/transferir com id {} e com request {}", id, transferenciaRequestDTO);
    	
    	ComprovanteResponseDTO comprovanteResponseDTO = contaService.efetuaTransferencia(id, transferenciaRequestDTO);
    	
    	log.debug("Finalizando POST em /api/v1/contas/{id}/transferir com id {} e com response {}", id, comprovanteResponseDTO);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(new ResponseDTO<>(comprovanteResponseDTO));
    }
    
    @GetMapping(value = "/{id}/lancamentos", params = { "dataInicial", "dataFinal" })
    public ResponseEntity<ResponseDTO<ExtratoResponseDTO>> consultaExtratoPorPeriodo(@PathVariable("id") Long id,
    																				@RequestParam(value = "dataInicial", required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicial,
    																				@RequestParam(value = "dataFinal", required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFinal){
    	log.debug("Iniciando GET em /api/v1/contas/{id}/lancamento com id {} e data inicial: {} e data final: {}", id, dataInicial, dataFinal);
    	
    	ExtratoResponseDTO extratoResponseDTO = contaService.consultaExtratoPorPeriodo(id, dataInicial, dataFinal);
    	
    	log.debug("Finalizando GET em /api/v1/contas/{id}/lancamento com id {} e data inicial: {} e data final: {} e response {} ", id, dataInicial, dataFinal, extratoResponseDTO);
    	
    	return ResponseEntity.status(HttpStatus.OK)
    						 .body(new ResponseDTO<>(extratoResponseDTO));
    }

}
