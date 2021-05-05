package com.uun.gobah.springbootrabbit.outbound.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;

/**
 * @author nurulakbar
 */
public class DefaultRabbitPublisher  implements RabbitPublisher{

  private final RabbitTemplate rabbitTemplate;

  public DefaultRabbitPublisher(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public Mono<Void> publish(String orderId) {
    return Mono.fromRunnable(() -> rabbitTemplate.convertAndSend(orderId)).then();
  }
}
