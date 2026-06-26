package br.com.caixa.pix.emissor;

import br.com.caixa.pix.contracts.GravarComprovanteCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Publica na EXCHANGE com routing key (Aula 5). */
@Component
public class GravacaoPublisher {

    private final RabbitTemplate rabbit;

    public GravacaoPublisher(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void publicar(GravarComprovanteCommand command) {
        rabbit.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING, command);
    }
}
