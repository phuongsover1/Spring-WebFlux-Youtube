package com.learnreactivespring.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest
public class FluxAndMonoControllerTest {
  @Autowired
  WebTestClient webTestClient;

  @Test
  public void fluxApproach1() {
  Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
       .accept(MediaType.APPLICATION_JSON_UTF8)
       .exchange()
       .expectStatus().isOk()
       .returnResult(Integer.class)
       .getResponseBody();

    StepVerifier.create(integerFlux)
        .expectSubscription()
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .expectNext(4)
        .verifyComplete();
  }

  @Test
  public void fluxApproach2() {
    List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);
    List<Integer> integerList = webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .hasSize(4)
        .returnResult()
        .getResponseBody();

    Assertions.assertEquals(expectedIntegerList, integerList);

  }

  @Test
  public void fluxApproach3() {
    webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .consumeWith(response -> Assertions.assertEquals(Arrays.asList(1,2,3,4), response.getResponseBody()));
  }

  @Test
  public void infiniteFluxTest() {
    Flux<Long> longFlux = webTestClient.get().uri("/infinite-flux")
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .returnResult(Long.class)
        .getResponseBody();

    StepVerifier.create(longFlux)
        .expectSubscription()
        .expectNext(0l)
        .expectNext(1l)
        .expectNext(2l)
        .expectNext(3l)
        .expectNext(4l)
        .thenCancel()
        .verify();
  }
}
