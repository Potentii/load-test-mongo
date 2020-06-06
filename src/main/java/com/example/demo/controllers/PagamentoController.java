package com.example.demo.controllers;

import com.example.demo.entities.Pagamento;
import com.example.demo.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PagamentoController {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	

	@PostMapping("/pagamentos")
    ResponseEntity<?> get(@RequestBody List<Pagamento> pagamentos){
		try {
		
			pagamentoRepository.insertAll(pagamentos);
			
			return ResponseEntity.ok(new Object());

		} catch(Exception e){
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Object());
		}
	}

	@GetMapping("/pagamentos")
	ResponseEntity<List<Pagamento>> get() {
		try {

			final List<Pagamento> pagamentos = pagamentoRepository.getWithFilter();

			return ResponseEntity.ok(pagamentos);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
	
}
