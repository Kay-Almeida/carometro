package br.com.carometro.usuario;

import br.com.carometro.exception.ServiceExc;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.carometro.logacesso.*;
import jakarta.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;

@Controller
public class UsuarioController {
	
	@Autowired
	private ServiceLogAcesso logAcessoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping("/")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/login");
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/index");
        modelAndView.addObject("Usuário", new Usuario());
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usuario", new Usuario());
        modelAndView.setViewName("login/cadastro");
        return modelAndView;
    }

    @PostMapping("/salvarUsuario")
    public ModelAndView cadastrar(Usuario usuario) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        serviceUsuario.salvarUsuario(usuario);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid Usuario usuario, BindingResult br,
                              HttpSession session, HttpServletRequest request) 
                              throws NoSuchAlgorithmException, ServiceExc {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usuario", new Usuario());

        if(br.hasErrors()) {
            modelAndView.setViewName("login/login");
            return modelAndView;
        }

        Usuario userLogin = serviceUsuario.loginUser(usuario.getUser(), Util.md5(usuario.getSenha()));
        String ip = request.getRemoteAddr();

        if(userLogin == null) {
            modelAndView.addObject("msg", "Usuário não encontrado. Tente novamente.");
            // Registra tentativa inválida
            logAcessoService.registrarAcesso(usuario, ip, "ERRO_LOGIN");
        } else {
            session.setAttribute("usuarioLogado", userLogin);
            logAcessoService.registrarAcesso(userLogin, ip, "LOGIN");
            return index();
        }
        
        String redirect = switch(userLogin.getTipoUsuario()) {
        case ADMIN -> "/admin/dashboard";
        case COORDENADOR -> "/coordenador/pendentes";
        case ALUNO -> userLogin.getPerfil() != null ? "/perfil" : "/perfil/novo";
    };

    return new ModelAndView("redirect:" + redirect);
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado != null) {
            String ip = request.getRemoteAddr();
            logAcessoService.registrarAcesso(usuarioLogado, ip, "LOGOUT");
        }

        session.invalidate();
        return login();
    }
}
