package br.com.caixa.pix.emissor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/** Gravação não concluiu na SAGA — emissão compensada → HTTP 502. */
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class GravacaoFalhouException extends RuntimeException {
    public GravacaoFalhouException(UUID id) {
        super("Falha ao gravar o comprovante " + id + " (emissão compensada)");
    }
}
