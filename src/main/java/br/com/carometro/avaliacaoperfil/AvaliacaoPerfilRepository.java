package br.com.carometro.avaliacaoperfil;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoPerfilRepository extends JpaRepository<AvaliacaoPerfil, Long> {

    List<AvaliacaoPerfil> findByPerfilId(Long perfilId);

    List<AvaliacaoPerfil> findByAvaliadorId(Long avaliadorId);
}
