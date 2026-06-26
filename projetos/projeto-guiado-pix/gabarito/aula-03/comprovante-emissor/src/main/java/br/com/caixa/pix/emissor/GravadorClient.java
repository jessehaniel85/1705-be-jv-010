package br.com.caixa.pix.emissor;

import br.com.caixa.pix.contracts.GravarComprovanteCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/** Passo remoto da SAGA (Aula 2): chama o gravador via REST. Vira fila na Aula 4. */
@Component
public class GravadorClient {

    private final RestClient http;

    public GravadorClient(@Value("${gravador.base-url}") String baseUrl) {
        this.http = RestClient.create(baseUrl);
    }

    /** Lança exceção se o gravador responder erro (dispara a compensação). */
    public void gravar(GravarComprovanteCommand command) {
        http.post().uri("/interno/comprovantes").body(command).retrieve().toBodilessEntity();
    }
}
