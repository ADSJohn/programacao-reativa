package com.br.boracodardevs.programacaoreativa.service;

import com.br.boracodardevs.programacaoreativa.entity.User;
import com.br.boracodardevs.programacaoreativa.mapper.UserMapper;
import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import com.br.boracodardevs.programacaoreativa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
}
