package com.example.demorabbitmqprovider;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
    //绑定键
    public final static String man_queue_name = "topic.man.queue";
    public final static String woman_queue_name = "topic.woman.queue";
    public final static String all_queue_name = "topic.all.queue";

    @Bean
    public Queue manQueue() {
        return new Queue(TopicRabbitConfig.man_queue_name);
    }

    @Bean
    public Queue womanQueue() {
        return new Queue(TopicRabbitConfig.woman_queue_name);
    }

    @Bean
    public Queue allQueue() {
        return new Queue(TopicRabbitConfig.all_queue_name);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingManExchangeMessage() {
        return BindingBuilder.bind(manQueue()).to(exchange()).with("topic.man");
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingWomanExchangeMessage() {
        return BindingBuilder.bind(womanQueue()).to(exchange()).with("topic.woman");
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingAllExchangeMessage() {
        return BindingBuilder.bind(allQueue()).to(exchange()).with("topic.all");
    }

}
