package br.com.carometro.logacesso;

import br.com.carometro.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.carometro.logacesso.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceLogAcesso {

    @Autowired
    private LogAcessoRepository repository;

    public void registrarAcesso(Usuario usuario, String ip, String acao) {
        LogAcesso log = new LogAcesso();
        log.setUsuario(usuario);
        log.setIp(ip);
        log.setAcao(acao);
        log.setDataHora(LocalDateTime.now());

        repository.save(log);
    }

    public List<LogAcesso> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
