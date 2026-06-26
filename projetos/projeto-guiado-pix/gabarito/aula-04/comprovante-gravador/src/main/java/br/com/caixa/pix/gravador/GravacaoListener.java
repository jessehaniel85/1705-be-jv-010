package br.com.caixa.pix.gravador;

import br.com.caixa.pix.contracts.GravarComprovanteCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/** Consome a fila de gravação (Aula 4). O gravar() é idempotente sob redelivery. */
@Component
public class GravacaoListener {

    private final GravadorService gravador;

    public GravacaoListener(GravadorService gravador) {
        this.gravador = gravador;
    }

    @RabbitListener(queues = MessagingConfig.FILA_GRAVACAO)
    public void aoReceber(GravarComprovanteCommand command) {
        gravador.gravar(command);
    }
}
