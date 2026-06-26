package br.com.caixa.pix.emissor;

import br.com.caixa.pix.contracts.ComprovanteAceito;
import br.com.caixa.pix.contracts.ComprovanteRequest;
import br.com.caixa.pix.contracts.GravarComprovanteCommand;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Bounded context EMISSÃO.
 * Aula 4 — assíncrono: valida, responde 202 e PUBLICA o comando de gravação na fila.
 * A gravação acontece depois, no consumidor (idempotente). Garantias de entrega e
 * tratamento de falha (retry/DLQ) entram na Aula 5.
 */
@Service
public class EmissaoService {

    private final GravacaoPublisher publisher;
    private final EmissaoRegistro registro;

    public EmissaoService(GravacaoPublisher publisher, EmissaoRegistro registro) {
        this.publisher = publisher;
        this.registro = registro;
    }

    public ComprovanteAceito aceitar(ComprovanteRequest req) {
        validar(req);
        UUID id = UUID.randomUUID();
        registro.aceito(id);
        publisher.publicar(new GravarComprovanteCommand(id, req)); // não espera a gravação
        return new ComprovanteAceito(id, LocalDateTime.now());
    }

    private void validar(ComprovanteRequest r) {
        exigir(r.nome(), "nome");
        exigir(r.numeroDocumento(), "numero_documento");
        if (r.tipoDocumento() == null) throw new ComprovanteInvalidoException("tipo_documento é obrigatório");
        tamanho(r.numeroAgencia(), 4, "numero_agencia");
        tamanho(r.numeroConta(), 5, "numero_conta");
        tamanho(r.digitoVerificadorConta(), 1, "digito_verificador_conta");
        if (r.valorTransacao() == null || r.valorTransacao().compareTo(BigDecimal.ZERO) <= 0)
            throw new ComprovanteInvalidoException("valor_transacao deve ser maior que zero");
        if (r.tipoChavePixDestino() == null) throw new ComprovanteInvalidoException("tipo_chave_pix_destino é obrigatório");
        exigir(r.chavePixDestino(), "chave_pix_destino");
    }
    private void exigir(String v, String c) {
        if (v == null || v.isBlank()) throw new ComprovanteInvalidoException(c + " é obrigatório");
    }
    private void tamanho(String v, int n, String c) {
        if (v == null || v.length() != n) throw new ComprovanteInvalidoException(c + " deve ter " + n + " caractere(s)");
    }
}
