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

	private static final String ID = "1234";
	private static final String NAME = "Dev John";
	private static final String EMAIL = "devjohn@email.com";
	private static final String PASSWORD = "123";
	private static final String BASE_URI = "/users";

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private UserMapper mapper;

	@MockBean
	private UserService service;

	@Test
	@DisplayName("Test save endpoint with success")
	void testSaveWithSuccess() {
		UserRequest request = new UserRequest(NAME, EMAIL, PASSWORD);

		Mockito.when(service.save(ArgumentMatchers.any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

		webTestClient.post()
			.uri(BASE_URI )
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(request))
			.exchange()
			.expectStatus()
			.isCreated();

		Mockito.verify(service).save(ArgumentMatchers.any(UserRequest.class));
	}

	@Test
	@DisplayName("Test save endpoint with badrequest")
	void testSaveWithBadRequest() {
		UserRequest request = new UserRequest(NAME.concat(" "), EMAIL, PASSWORD);

		webTestClient.post()
			.uri(BASE_URI )
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(request))
			.exchange()
			.expectStatus()
			.isBadRequest()
			.expectBody()
			.jsonPath("$.path").isEqualTo(BASE_URI)
			.jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
			.jsonPath("$.error").isEqualTo("Validation error")
			.jsonPath("$.message").isEqualTo("Error on validation attributes")
			.jsonPath("$.errorList[0].fieldName").isEqualTo("name")
			.jsonPath("$.errorList[0].message").isEqualTo("{field cannot have blank espaces at the beginning or at end}");
	}

	@Test
	@DisplayName("Test find by id endpoint with success")
	void testFindByIdWithSuccess() {
		final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

		Mockito.when(service.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(User.builder().build()));
		Mockito.when(mapper.toResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponse);

		webTestClient.get()
			.uri(BASE_URI + "/" + ID)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(ID)
			.jsonPath("$.name").isEqualTo(NAME)
			.jsonPath("$.email").isEqualTo(EMAIL)
			.jsonPath("$.password").isEqualTo(PASSWORD);

		Mockito.verify(service).findById(ArgumentMatchers.anyString());
		Mockito.verify(mapper).toResponse(ArgumentMatchers.any(User.class));
	}

	@Test
	@DisplayName("Test find all endpoint with success")
	void testFindAllWithSuccess() {
		final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

		Mockito.when(service.findAll()).thenReturn(Flux.just(User.builder().build()));
		Mockito.when(mapper.toResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponse);

		webTestClient.get()
			.uri(BASE_URI )
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.[0].id").isEqualTo(ID)
			.jsonPath("$.[0].name").isEqualTo(NAME)
			.jsonPath("$.[0].email").isEqualTo(EMAIL)
			.jsonPath("$.[0].password").isEqualTo(PASSWORD);

		Mockito.verify(service).findAll();
		Mockito.verify(mapper).toResponse(ArgumentMatchers.any(User.class));
	}

	@Test
	@DisplayName("Test update endpoint with success")
	void testUpdateWithSuccess() {
		final var userRequest = new UserRequest(NAME, EMAIL, PASSWORD);
		final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

		Mockito.when(service.update(ArgumentMatchers.anyString(), ArgumentMatchers.any(UserRequest.class)))
			.thenReturn(Mono.just(User.builder().build()));

		Mockito.when(mapper.toResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponse);

		webTestClient.patch()
			.uri(BASE_URI + "/" + ID)
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(userRequest))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(ID)
			.jsonPath("$.name").isEqualTo(NAME)
			.jsonPath("$.email").isEqualTo(EMAIL)
			.jsonPath("$.password").isEqualTo(PASSWORD);

		Mockito.verify(service).update(ArgumentMatchers.anyString(), ArgumentMatchers.any(UserRequest.class));
		Mockito.verify(mapper).toResponse(ArgumentMatchers.any(User.class));
	}

	@Test
	@DisplayName("Test delete endpoint with success")
	void testDeleteWithSuccess() {

		Mockito.when(service.delete(ArgumentMatchers.anyString())).thenReturn(Mono.just(User.builder().build()));

		webTestClient.delete()
			.uri(BASE_URI + "/" + ID)
			.exchange()
			.expectStatus().isOk();

		Mockito.verify(service).delete(ArgumentMatchers.anyString());
	}
}