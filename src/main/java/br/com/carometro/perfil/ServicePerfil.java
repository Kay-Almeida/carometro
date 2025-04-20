package br.com.carometro.perfil;

import br.com.carometro.exception.PerfilException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicePerfil {

    @Autowired
    private PerfilRepository repository;

    public Perfil buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new PerfilException("Perfil não encontrado"));
    }

    public Perfil buscarPorUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId); // Retorna null se não encontrar
    }

    public List<Perfil> listarPorStatus(StatusPerfil status) {
        return repository.findByStatusPerfil(status);
    }

    public Perfil salvarPerfil(Perfil perfil) {
        if (perfil == null) {
            throw new PerfilException("Perfil não pode ser nulo");
        }
        
        // Se for novo perfil, define status como PENDENTE
        if (perfil.getId() == null) {
            perfil.setStatusPerfil(StatusPerfil.PENDENTE);
        }
        
        return repository.save(perfil);
    }

    public void atualizarStatus(Long id, StatusPerfil status) {
        Perfil perfil = repository.findById(id)
                         .orElseThrow(() -> new PerfilException("Perfil não encontrado"));
        
        perfil.setStatusPerfil(status);
        repository.save(perfil);
    }
    
    // Método adicional para facilitar a busca
    public Perfil buscarPorUsuarioIdOuNovo(Long usuarioId) {
        Perfil perfil = repository.findByUsuarioId(usuarioId);
        return perfil != null ? perfil : new Perfil();
    }
}