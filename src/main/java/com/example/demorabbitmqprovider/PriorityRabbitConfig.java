package com.example.demorabbitmqprovider;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriorityRabbitConfig {
    //绑定键
    public final static String PRIORITY_QUEUE = "priority.queue";
    public final static String PRIORITY_EXCHANGE = "priority.exchange";
    public final static String PRIORITY_ROUTING_KEY = "priority.routing.key";
    @Bean
    public Queue priorityQueue() {
        return QueueBuilder.durable(PRIORITY_QUEUE).withArgument("x-max-priority",10).build();
    }


    @Bean
    TopicExchange priorityExchange() {
        return new TopicExchange(PRIORITY_EXCHANGE);
    }


    @Bean
    Binding bindingPriorityExchangeMessage() {
        return BindingBuilder.bind(priorityQueue()).to(priorityExchange()).with(PRIORITY_ROUTING_KEY);
    }

}
