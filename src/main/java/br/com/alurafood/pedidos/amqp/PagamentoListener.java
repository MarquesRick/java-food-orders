package br.com.alurafood.pedidos.amqp;

import br.com.alurafood.pedidos.dto.PagamentoDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    @RabbitListener(queues = "payment_done")
    public void receiveMessage(PagamentoDto pagamentoDto){
        var message = """
               Dados Pagamento: %s
               Numero Pedido: %s
               Valor R$: %s
               Status: %s
               """.formatted(pagamentoDto.getId(),
                pagamentoDto.getNumero(),
                pagamentoDto.getValor(),
                pagamentoDto.getStatus());
        System.out.println("Received message: " + message);
    }
}
