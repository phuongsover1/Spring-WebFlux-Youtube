package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

  @Test
  public void fluxTest() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
        //.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
        .log();

    stringFlux
        .subscribe(System.out::println,
            (e) -> System.err.println(e),
            () -> System.out.println("Completed"));
  }

  @Test
  public void fluxTestElementWithErrors() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
        .concatWith(Flux.error(new RuntimeException("Error Occurred !!!")));

    StepVerifier.create(stringFlux)
        .expectNext("Spring", "Spring Boot", "Reactive Spring")
        .expectErrorMessage("Error Occurred !!!")
        .verify();

  }

  @Test
  public void fluxTestElementsCount_WithError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
        .concatWith(Flux.error(new RuntimeException("Error Occurred !!!")));

    StepVerifier.create(stringFlux)
        .expectNextCount(3)
        .expectErrorMessage("Error Occurred !!!")
        .verify();
  }

  @Test
  public void monoTest() {
    Mono<String> stringMono = Mono.just("Spring")
        .log();

    StepVerifier.create(stringMono)
        .expectNext("Spring")
        .verifyComplete();
  }

  @Test
  public void monoTest_Error() {
    StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
        .expectError(RuntimeException.class)
        .verify();
  }
}

