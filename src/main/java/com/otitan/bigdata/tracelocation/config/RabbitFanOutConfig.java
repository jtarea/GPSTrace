package com.otitan.bigdata.tracelocation.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitFanOutConfig {
    @Bean
    public Queue queueRelay() {
        return new Queue("fanout.dataRelay");
    }

    @Bean
    public Queue queueStore() {
        return new Queue("fanout.dataStore");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("traceDataFanout");
    }

    @Bean
    Binding bindingExchangeRelay() {
        return BindingBuilder.bind(queueRelay()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeStore() {
        return BindingBuilder.bind(queueStore()).to(fanoutExchange());
    }

}
