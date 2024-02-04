package com.br.boracodardevs.programacaoreativa.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(DuplicateKeyException.class)
	ResponseEntity<Mono<StandardError>> duplicateException(DuplicateKeyException ex, ServerHttpRequest request) {
		return ResponseEntity.badRequest()
						.body(Mono.just(StandardError.builder()
										.timestamp(LocalDateTime.now())
										.status(HttpStatus.BAD_REQUEST.value())
										.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
										.message(verifyDumpKey(ex.getMessage()))
										.path(request.getPath().toString())
										.build()));
	}

	private String verifyDumpKey(String message) {
		if (message.contains("email dup key")) {
			return "E-mail alredy registered";
		}
		return "Dump key exception";
	}
}
