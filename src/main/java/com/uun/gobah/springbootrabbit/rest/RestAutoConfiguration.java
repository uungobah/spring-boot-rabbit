package com.uun.gobah.springbootrabbit.rest;

import com.uun.gobah.springbootrabbit.outbound.rabbit.RabbitPublisher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author nurulakbar
 */
@Configuration(proxyBeanMethods = false)
public class RestAutoConfiguration {



  @Bean
  RouterFunction<ServerResponse> testPublish (ObjectProvider<RabbitPublisher> rabbitPublishers){

    RabbitPublisher publisher = rabbitPublishers.getIfUnique(() ->
        id -> Mono.error(new IllegalStateException("No publisher configured in application context")));

    RequestPredicate predicate = RequestPredicates.method(HttpMethod.POST)
        .and(RequestPredicates.path("/publish"));

    HandlerFunction<ServerResponse> handler = serverRequest -> publisher.publish(serverRequest.queryParam("value").get())
        .flatMap(voidMono -> ServerResponse.ok().bodyValue("OK"));

    return RouterFunctions.route(predicate, handler);

  }

}
