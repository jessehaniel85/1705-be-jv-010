package br.com.caixa.pix.emissor;

import br.com.caixa.pix.contracts.ComprovanteAceito;
import br.com.caixa.pix.contracts.ComprovanteRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Núcleo do bounded context EMISSÃO (Aula 1 — DDD).
 *
 * Responsabilidade: validar o comprovante e aceitá-lo (gerar id + 202).
 * A gravação ainda NÃO acontece aqui — será orquestrada na Aula 2 (SAGA) e
 * desacoplada por mensageria na Aula 4.
 */
@Service
public class EmissaoService {

    public ComprovanteAceito aceitar(ComprovanteRequest req) {
        validar(req);
        UUID id = UUID.randomUUID();
        return new ComprovanteAceito(id, LocalDateTime.now());
    }

    /** Consistência das informações obrigatórias do comprovante (regra do módulo). */
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

    private void exigir(String valor, String campo) {
        if (valor == null || valor.isBlank())
            throw new ComprovanteInvalidoException(campo + " é obrigatório");
    }

    private void tamanho(String valor, int n, String campo) {
        if (valor == null || valor.length() != n)
            throw new ComprovanteInvalidoException(campo + " deve ter " + n + " caractere(s)");
    }
}
