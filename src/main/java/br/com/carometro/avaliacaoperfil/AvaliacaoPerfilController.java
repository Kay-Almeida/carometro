package br.com.carometro.avaliacaoperfil;

import br.com.carometro.perfil.Perfil;
import br.com.carometro.perfil.ServicePerfil;
import br.com.carometro.usuario.Usuario;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/avaliacao")
public class AvaliacaoPerfilController {

    @Autowired
    private ServiceAvaliacaoPerfil serviceAvaliacao;

    @Autowired
    private ServicePerfil servicePerfil;

    @PostMapping("/avaliar/{perfilId}")
    public String avaliarPerfil(@PathVariable Long perfilId,
                                @RequestParam String comentario,
                                @RequestParam String status,
                                HttpSession session) {

        Usuario avaliador = (Usuario) session.getAttribute("usuarioLogado");
        Perfil perfil = servicePerfil.buscarPorUsuarioId(perfilId);

        AvaliacaoPerfil avaliacao = new AvaliacaoPerfil();
        avaliacao.setPerfil(perfil);
        avaliacao.setAvaliador(avaliador);
        avaliacao.setComentario(comentario);
        avaliacao.setStatus(Enum.valueOf(br.com.carometro.perfil.StatusPerfil.class, status.toUpperCase()));

        serviceAvaliacao.registrarAvaliacao(avaliacao);

        return "redirect:/perfil/pendentes";
    }

    @GetMapping("/historico/{perfilId}")
    public ModelAndView historico(@PathVariable Long perfilId) {
        ModelAndView mv = new ModelAndView("avaliacao/historico");
        mv.addObject("avaliacoes", serviceAvaliacao.listarPorPerfil(perfilId));
        return mv;
    }
}
