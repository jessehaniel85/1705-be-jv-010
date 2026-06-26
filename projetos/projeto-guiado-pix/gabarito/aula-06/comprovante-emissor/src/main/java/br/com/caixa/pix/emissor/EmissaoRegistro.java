package br.com.caixa.pix.emissor;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Estado das emissões em andamento (Aula 2). Suporte didático à SAGA:
 * a transição para FALHOU é a AÇÃO COMPENSATÓRIA quando a gravação não conclui.
 */
@Component
public class EmissaoRegistro {

    public enum Status { ACEITO, GRAVADO, FALHOU }

    private final Map<UUID, Status> estados = new ConcurrentHashMap<>();

    public void aceito(UUID id)  { estados.put(id, Status.ACEITO); }
    public void gravado(UUID id) { estados.put(id, Status.GRAVADO); }
    public void falhou(UUID id)  { estados.put(id, Status.FALHOU); } // compensação
    public Status status(UUID id) { return estados.get(id); }
}
