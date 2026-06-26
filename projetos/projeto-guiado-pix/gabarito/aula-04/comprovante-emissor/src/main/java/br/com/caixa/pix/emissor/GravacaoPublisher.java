package br.com.caixa.pix.emissor;

import br.com.caixa.pix.contracts.GravarComprovanteCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Publica o comando de gravação na fila (Aula 4) — desacopla a emissão da gravação. */
@Component
public class GravacaoPublisher {

    private final RabbitTemplate rabbit;

    public GravacaoPublisher(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void publicar(GravarComprovanteCommand command) {
        rabbit.convertAndSend(MessagingConfig.FILA_GRAVACAO, command);
    }
}
