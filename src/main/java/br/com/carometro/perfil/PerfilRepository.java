package br.com.carometro.perfil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query("SELECT p FROM Perfil p WHERE p.statusPerfil = 'PENDENTE'")
    List<Perfil> buscarPerfisPendentes();

    @Query("SELECT p FROM Perfil p WHERE p.usuario.id = :usuarioId")
    Perfil buscarPorUsuarioId(Long usuarioId);
}
