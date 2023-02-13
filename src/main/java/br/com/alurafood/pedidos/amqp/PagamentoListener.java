package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    @RabbitListener(queues = "payment_done")
    public void receiveMessage(Message message){
        System.out.println("Received message: " + message.toString());
    }
}
