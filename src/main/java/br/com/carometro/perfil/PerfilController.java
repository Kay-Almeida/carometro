package br.com.carometro.perfil;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import br.com.carometro.usuario.*;

import java.util.List;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private ServicePerfil servicePerfil;

    @GetMapping("/novo")
    public ModelAndView novoPerfil(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("perfil/cadastro");
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        modelAndView.addObject("perfil", perfil);
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvarPerfil(Perfil perfil) {
        servicePerfil.salvarPerfil(perfil);
        return new ModelAndView("redirect:/index");
    }

    @GetMapping("/pendentes")
    public ModelAndView listarPendentes() {
        ModelAndView mv = new ModelAndView("perfil/pendentes");
        List<Perfil> pendentes = servicePerfil.listarPerfisPendentes();
        mv.addObject("pendentes", pendentes);
        return mv;
    }

    @PostMapping("/aprovar/{id}")
    public String aprovarPerfil(@PathVariable Long id) {
        servicePerfil.aprovarPerfil(id);
        return "redirect:/perfil/pendentes";
    }

    @PostMapping("/reprovar/{id}")
    public String reprovarPerfil(@PathVariable Long id) {
        servicePerfil.reprovarPerfil(id);
        return "redirect:/perfil/pendentes";
    }
}
