package br.com.carometro.perfil;

import br.com.carometro.perfil.StatusPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.carometro.usuario.*;

import java.util.List;

@Service
public class ServicePerfil {

    @Autowired
    private PerfilRepository perfilRepository;

    public void salvarPerfil(Perfil perfil) {
        perfilRepository.save(perfil);
    }

    public List<Perfil> listarPerfisPendentes() {
        return perfilRepository.buscarPerfisPendentes();
    }

    public void aprovarPerfil(Long id) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        perfil.getUsuario().setStatusPerfil(StatusPerfil.APROVADO);
        perfilRepository.save(perfil);
    }

    public void reprovarPerfil(Long id) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        perfil.getUsuario().setStatusPerfil(StatusPerfil.REPROVADO);
        perfilRepository.save(perfil);
    }

    public Perfil buscarPorUsuarioId(Long usuarioId) {
        return perfilRepository.buscarPorUsuarioId(usuarioId);
    }
}
