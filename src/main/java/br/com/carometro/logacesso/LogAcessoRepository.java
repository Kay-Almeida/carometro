package br.com.carometro.logacesso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogAcessoRepository extends JpaRepository<LogAcesso, Long> {

    List<LogAcesso> findByUsuarioId(Long usuarioId);
}
