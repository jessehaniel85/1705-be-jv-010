package br.com.caixa.pix.notificacao;

import br.com.caixa.pix.contracts.ComprovanteGravadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Aula 6 — consome o tópico no consumer group "notificacao".
 * Outros grupos (antifraude, BI) consomem o MESMO tópico de forma independente.
 */
@Component
public class NotificacaoListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoListener.class);

    @KafkaListener(topics = "comprovante-gravado", groupId = "notificacao")
    public void aoGravar(ComprovanteGravadoEvent evento) {
        log.info("Notificando cliente: comprovante {} gravado em {}",
                evento.identificadorComprovante(), evento.dataHoraGravacao());
    }
}
