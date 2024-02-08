package com.br.boracodardevs.programacaoreativa.controller.impl;

import com.br.boracodardevs.programacaoreativa.controller.UserController;
import com.br.boracodardevs.programacaoreativa.mapper.UserMapper;
import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import com.br.boracodardevs.programacaoreativa.model.response.UserResponse;
import com.br.boracodardevs.programacaoreativa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserControllerImpl implements UserController {

	@Autowired
	private UserService service;

	@Autowired
	private UserMapper mapper;

	@Override
	public ResponseEntity<Mono<Void>> save(final UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request).then());
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> findById(String id) {
		return ResponseEntity.ok().body(service.findById(id).map(mapper::toResponse));
	}

	@Override
	public ResponseEntity<Flux<UserResponse>> findAll() {
		return null;
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<Mono<Void>> delete(String id) {
		return null;
	}
}
