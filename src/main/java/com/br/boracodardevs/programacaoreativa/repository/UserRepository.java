package com.br.boracodardevs.programacaoreativa.repository;

import com.br.boracodardevs.programacaoreativa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public Mono<User> save(final User user) {
		return  mongoTemplate.save(user);
	}
}
