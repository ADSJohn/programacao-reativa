package com.br.boracodardevs.programacaoreativa.service;

import com.br.boracodardevs.programacaoreativa.entity.User;
import com.br.boracodardevs.programacaoreativa.mapper.UserMapper;
import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import com.br.boracodardevs.programacaoreativa.repository.UserRepository;
import com.br.boracodardevs.programacaoreativa.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static java.lang.String.format;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository repository;

	@Mock
	UserMapper mapper;

	@InjectMocks
	UserService service;

	@Test
	void testSave() {
		UserRequest request = new UserRequest("Dev John", "devjohn@email.com", "123");
		User entity = User.builder().build();

		Mockito.when(mapper.toEntity(ArgumentMatchers.any(UserRequest.class))).thenReturn(entity);
		Mockito.when(repository.save(ArgumentMatchers.any(User.class))).thenReturn(Mono.just(User.builder().build()));

		Mono<User> result = service.save(request);

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
	}

	@Test
	void testFindById() {
		Mockito.when(repository.findbById(ArgumentMatchers.anyString()))
			.thenReturn(Mono.just(User.builder().build()));

		Mono<User> result = service.findById("123");

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, Mockito.times(1)).findbById(ArgumentMatchers.anyString());
	}

	@Test
	void testFindAll() {
		Mockito.when(repository.findAll()).thenReturn(Flux.just(User.builder().build()));

		Flux<User> result = service.findAll();

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	void testUpdate() {

		UserRequest request = new UserRequest("Dev John", "devjohn@email.com", "123");
		User entity = User.builder().build();

		Mockito.when(mapper.toEntity(ArgumentMatchers.any(UserRequest.class), ArgumentMatchers.any(User.class)))
			.thenReturn(entity);
		Mockito.when(repository.findbById(ArgumentMatchers.anyString())).thenReturn(Mono.just(entity));
		Mockito.when(repository.save(ArgumentMatchers.any(User.class))).thenReturn(Mono.just(entity));

		Mono<User> result = service.update("123", request);

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
	}

	@Test
	void testDelete() {
		User entity = User.builder().build();

		Mockito.when(repository.findAndRemove(ArgumentMatchers.anyString())).thenReturn(Mono.just(entity));

		Mono<User> result = service.delete("123");

		StepVerifier.create(result)
			.expectNextMatches(user -> user.getClass() == User.class)
			.expectComplete()
			.verify();

		Mockito.verify(repository, Mockito.times(1)).findAndRemove(ArgumentMatchers.anyString());
	}

	@Test
	void testObjectNotFoundException() {
		Mockito.when(repository.findbById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());
		try {
			service.findById("123").block();
		} catch (Exception ex) {
			Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
			Assertions.assertEquals(format("Object not found. Id: %s Type: %s ", "123", User.class.getSimpleName()), ex.getMessage());
		}
	}
}