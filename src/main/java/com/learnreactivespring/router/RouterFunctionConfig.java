package com.learnreactivespring.router;

import com.learnreactivespring.handlers.SimpleHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RouterFunctionConfig {

  @Bean
  public RouterFunction<ServerResponse> route(SimpleHandlerFunction simpleHandlerFunction) {
    return RouterFunctions.route(RequestPredicates.GET("/functional/flux"), simpleHandlerFunction::flux)
        .andRoute(RequestPredicates.GET("/functional/mono"), simpleHandlerFunction::mono);
  }
}
