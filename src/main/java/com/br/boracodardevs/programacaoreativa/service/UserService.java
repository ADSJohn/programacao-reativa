package com.br.boracodardevs.programacaoreativa.service;

import com.br.boracodardevs.programacaoreativa.entity.User;
import com.br.boracodardevs.programacaoreativa.mapper.UserMapper;
import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import com.br.boracodardevs.programacaoreativa.repository.UserRepository;
import com.br.boracodardevs.programacaoreativa.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper mapper;

	public Mono<User> save(final UserRequest request) {
		return repository.save(mapper.toEntity(request));
	}

	public Mono<User> findById(final String id) {
		return handleNotFound(repository.findbById(id), id);
	}

	public Flux<User> findAll() {
		return repository.findAll();
	}

	public Mono<User> update(String id, UserRequest request) {
		return findById(id)
			.map(entity -> mapper.toEntity(request, entity))
			.flatMap(repository::save);
	}

	public Mono<User> delete(final String id) {
		return handleNotFound(repository.findAndRemove(id), id);
	}

	private <T> Mono<T> handleNotFound(Mono<T> mono, String id) {
		return mono.switchIfEmpty(
			Mono.error(new ObjectNotFoundException(
				format("Object not found. Id: %s Type: %s ", id, User.class.getSimpleName()))));
	}
}
