package br.com.caixa.pix.contracts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/** Projeção de leitura de um comprovante já gravado (GET). */
public record ComprovanteView(
        UUID identificadorComprovante,
        String nome,
        String numeroDocumento,
        BigDecimal valorTransacao,
        String chavePixDestino,
        LocalDateTime dataHoraTransacao,
        LocalDateTime dataHoraGravacao
) {
}
