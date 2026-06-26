package br.com.caixa.pix.gravador;

import br.com.caixa.pix.contracts.ComprovanteView;
import br.com.caixa.pix.contracts.GravarComprovanteCommand;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/** GRAVAÇÃO: persiste (idempotente) e expõe leitura por id (fonte da verdade da consulta). */
@Service
public class GravadorService {

    private final ComprovanteRepository repository;

    public GravadorService(ComprovanteRepository repository) {
        this.repository = repository;
    }

    public boolean gravar(GravarComprovanteCommand command) {
        if (repository.existsById(command.identificadorComprovante())) {
            return false;
        }
        repository.save(ComprovanteEntity.de(command.identificadorComprovante(), command.dados()));
        return true;
    }

    public Optional<ComprovanteView> buscar(UUID id) {
        return repository.findById(id).map(this::toView);
    }

    private ComprovanteView toView(ComprovanteEntity e) {
        return new ComprovanteView(e.getId(), e.getNome(), e.getNumeroDocumento(),
                e.getValorTransacao(), e.getChavePixDestino(),
                e.getDataHoraTransacao(), e.getDataHoraGravacao());
    }
}
