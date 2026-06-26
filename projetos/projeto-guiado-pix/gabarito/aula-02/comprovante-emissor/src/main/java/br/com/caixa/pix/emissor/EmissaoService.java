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
 * Aula 2 — SAGA orquestrada (síncrona): valida, aceita, orquestra a gravação remota
 * e, se ela falhar, executa a COMPENSAÇÃO (marca a emissão como FALHOU).
 */
@Service
public class EmissaoService {

    private final GravadorClient gravador;
    private final EmissaoRegistro registro;

    public EmissaoService(GravadorClient gravador, EmissaoRegistro registro) {
        this.gravador = gravador;
        this.registro = registro;
    }

    public ComprovanteAceito aceitar(ComprovanteRequest req) {
        validar(req);
        UUID id = UUID.randomUUID();
        registro.aceito(id);
        try {
            gravador.gravar(new GravarComprovanteCommand(id, req));
            registro.gravado(id);
        } catch (Exception falha) {
            registro.falhou(id);            // ação compensatória
            throw new GravacaoFalhouException(id);
        }
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
