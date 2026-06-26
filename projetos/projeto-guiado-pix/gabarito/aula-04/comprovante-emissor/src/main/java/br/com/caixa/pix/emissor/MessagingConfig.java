package br.com.caixa.pix.emissor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Aula 4 — topologia mínima de fila (a DLQ entra na Aula 5). */
@Configuration
public class MessagingConfig {

    public static final String FILA_GRAVACAO = "gravacao.q";

    @Bean
    public Queue gravacaoQueue() {
        return new Queue(FILA_GRAVACAO, true);
    }

    /** Mensagens em JSON (usa o ObjectMapper do Boot, com suporte a LocalDateTime). */
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }
}
