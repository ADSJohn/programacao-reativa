package com.br.boracodardevs.programacaoreativa.controller;

import com.br.boracodardevs.programacaoreativa.entity.User;
import com.br.boracodardevs.programacaoreativa.mapper.UserMapper;
import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import com.br.boracodardevs.programacaoreativa.model.response.UserResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

	public static final String ID = "1234";
	public static final String NAME = "Dev John";
	public static final String EMAIL = "devjohn@email.com";
	public static final String PASSWORD = "123";

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private UserMapper mapper;

	@MockBean
	private UserService service;

	@Test
	@DisplayName("Test endpoint save with success")
	void testSave() {
		UserRequest request = new UserRequest(NAME, EMAIL, PASSWORD);

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
	@DisplayName("Test endpoint save with badrequest")
	void testSaveWithBadRequest() {
		UserRequest request = new UserRequest(NAME.concat(" "), EMAIL, PASSWORD);

		webTestClient.post()
			.uri("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(request))
			.exchange()
			.expectStatus()
			.isBadRequest()
			.expectBody()
			.jsonPath("$.path").isEqualTo("/users")
			.jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
			.jsonPath("$.error").isEqualTo("Validation error")
			.jsonPath("$.message").isEqualTo("Error on validation attributes")
			.jsonPath("$.errorList[0].fieldName").isEqualTo("name")
			.jsonPath("$.errorList[0].message").isEqualTo("{field cannot have blank espaces at the beginning or at end}");
	}

	@Test
	@DisplayName("Teste find by id endpoint with success")
	void testFindById() {
		final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

		Mockito.when(service.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(User.builder().build()));
		Mockito.when(mapper.toResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponse);

		webTestClient.get()
			.uri("/users/" + ID)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(ID)
			.jsonPath("$.name").isEqualTo(NAME)
			.jsonPath("$.email").isEqualTo(EMAIL)
			.jsonPath("$.password").isEqualTo(PASSWORD);
	}

	@Test
	void testFindAll() {
		final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

		Mockito.when(service.findAll()).thenReturn(Flux.just(User.builder().build()));
		Mockito.when(mapper.toResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponse);

		webTestClient.get()
			.uri("/users")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.[0].id").isEqualTo(ID)
			.jsonPath("$.[0].name").isEqualTo(NAME)
			.jsonPath("$.[0].email").isEqualTo(EMAIL)
			.jsonPath("$.[0].password").isEqualTo(PASSWORD);
	}

	@Test
	void update() {
	}

	@Test
	void delete() {
	}
}