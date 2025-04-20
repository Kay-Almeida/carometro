package br.com.carometro.avaliacaoperfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAvaliacaoPerfil {

    @Autowired
    private AvaliacaoPerfilRepository repository;

    public void registrarAvaliacao(AvaliacaoPerfil avaliacao) {
        repository.save(avaliacao);
    }

    public List<AvaliacaoPerfil> listarPorPerfil(Long perfilId) {
        return repository.findByPerfilId(perfilId);
    }

    public List<AvaliacaoPerfil> listarPorAvaliador(Long avaliadorId) {
        return repository.findByAvaliadorId(avaliadorId);
    }
}
