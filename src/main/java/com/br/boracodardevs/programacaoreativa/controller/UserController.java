package com.br.boracodardevs.programacaoreativa.controller;

import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import com.br.boracodardevs.programacaoreativa.model.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserController {

	@PostMapping
	ResponseEntity<Mono<Void>> save(@Valid @RequestBody UserRequest request);

	@GetMapping(value = "/{id}")
	ResponseEntity<Mono<UserResponse>> find(@PathVariable String id);

	@GetMapping
	ResponseEntity<Flux<UserResponse>> findAll();

	@PatchMapping(value = "/{id}")
	ResponseEntity<Mono<UserResponse>> update(@PathVariable String id, @RequestBody UserRequest request);

	@DeleteMapping
	ResponseEntity<Mono<Void>> delete(@PathVariable String id);
}
