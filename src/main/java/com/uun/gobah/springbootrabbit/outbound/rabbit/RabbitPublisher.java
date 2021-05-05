package com.uun.gobah.springbootrabbit.outbound.rabbit;

import reactor.core.publisher.Mono;

/**
 * @author nurulakbar
 */
public interface RabbitPublisher {

  Mono<Void> publish(String orderId);
}
