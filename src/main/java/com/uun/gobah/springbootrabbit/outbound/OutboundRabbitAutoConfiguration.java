package com.uun.gobah.springbootrabbit.outbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uun.gobah.springbootrabbit.outbound.rabbit.DefaultRabbitPublisher;
import com.uun.gobah.springbootrabbit.outbound.rabbit.RabbitPublisher;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nurulakbar
 */
@Configuration
@AutoConfigureAfter(RabbitAutoConfiguration.class)
public class OutboundRabbitAutoConfiguration {


  @Bean
  RabbitPublisher defaultRabbitPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper){
    rabbitTemplate.setExchange("");
    rabbitTemplate.setRoutingKey("com.uun.gobah.publish");
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));

    return new DefaultRabbitPublisher(rabbitTemplate);
  }

  @Bean
  Queue publishRabbit(){
    return QueueBuilder.nonDurable("com.uun.gobah.publish").build();
  }
}
