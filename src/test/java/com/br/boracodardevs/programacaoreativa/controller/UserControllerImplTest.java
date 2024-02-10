package com.br.boracodardevs.programacaoreativa.controller;

import com.br.boracodardevs.programacaoreativa.entity.User;
import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import com.br.boracodardevs.programacaoreativa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private UserService service;

	@Test
	@DisplayName("Test endpoint save with success")
	void testSave() {
		UserRequest request = new UserRequest("Dev John", "devjohn@email.com", "123");

		Mockito.when(service.save(ArgumentMatchers.any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

		webTestClient.post()
			.uri("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(request))
			.exchange()
			.expectStatus()
			.isCreated();

		Mockito.verify(service).save(ArgumentMatchers.any(UserRequest.class));
	}

	@Test
	void findById() {
	}

	@Test
	void findAll() {
	}

	@Test
	void update() {
	}

	@Test
	void delete() {
	}
}