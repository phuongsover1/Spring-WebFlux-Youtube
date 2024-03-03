package com.learnreactivespring.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxAndMonoController {
  @GetMapping("/flux")
  public Flux<Integer> returnFlux() {
    return Flux.just(1,2,3,4).log()
        .delayElements(Duration.ofSeconds(1));
  }

  @GetMapping(value = "/infinite-flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Long> returnInfiniteFlux() {
    return Flux.interval(Duration.ofSeconds(1))
        .log();
  }
}
