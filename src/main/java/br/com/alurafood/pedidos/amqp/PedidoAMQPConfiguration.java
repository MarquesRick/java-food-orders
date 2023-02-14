package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoAMQPConfiguration {
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter msgConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(msgConverter);
        return rabbitTemplate;
    }

    //create Queue
    @Bean
    public Queue orderDetailQueue(){
        return QueueBuilder.nonDurable("payment_order_detail").deadLetterExchange("payment_exchange_dlx").build();
    }

    @Bean
    public Queue orderDetailDLQQueue(){
        return QueueBuilder.nonDurable("payment_order_detail_dlq").build();
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange("payment_exchange").build();
    }

    @Bean
    public FanoutExchange deadLetterExchange(){
        return ExchangeBuilder.fanoutExchange("payment_exchange_dlx").build();
    }

    //binding
    @Bean
    public Binding bindingPaymentOrder(){
        return BindingBuilder.bind(orderDetailQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingPaymentOrderDLX(){
        return BindingBuilder.bind(orderDetailDLQQueue()).to(deadLetterExchange());
    }


    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn){
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initRabbit(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }


}
