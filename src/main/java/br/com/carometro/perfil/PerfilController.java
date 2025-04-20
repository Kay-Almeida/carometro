package br.com.carometro.perfil;

import br.com.carometro.usuario.*;
import jakarta.validation.Valid;
import br.com.carometro.exception.PerfilException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ServicePerfil servicePerfil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Exibir formulário para criar/editar perfil
    @GetMapping("/formulario")
    public String exibirFormulario(Principal principal, Model model) {
        // Busca o usuário logado (versão sem Optional)
        Usuario usuario = usuarioRepository.findByEmail(principal.getName());
        if (usuario == null) {
            throw new PerfilException("Usuário não encontrado");
        }

        // Busca o perfil existente (versão sem Optional)
        Perfil perfil = servicePerfil.buscarPorUsuarioId(usuario.getId());
        if (perfil == null) {
            perfil = new Perfil(); // Cria novo se não existir
        }
        
        perfil.setUsuario(usuario);
        model.addAttribute("perfil", perfil);
        
        return "perfil/formulario";
    }

    // Processar envio do formulário
    @PostMapping("/salvar")
    public String salvarPerfil(
            @ModelAttribute("perfil") @Valid Perfil perfil,
            BindingResult result,
            @RequestParam("foto") MultipartFile foto,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "perfil/formulario";
        }

        // Valida consentimento
        if (!perfil.getUsuario().isConsentimentoDados()) {
            result.rejectValue("usuario.consentimentoDados", "error.perfil", "Você deve concordar com os termos de uso");
            return "perfil/formulario";
        }

        try {
            // Processa upload da foto se foi enviada
            if (!foto.isEmpty()) {
                String nomeArquivo = processarUpload(foto);
                perfil.setUrlFoto(nomeArquivo);
            }

            // Salva o perfil
            servicePerfil.salvarPerfil(perfil);
            
            attributes.addFlashAttribute("sucesso", 
                perfil.getId() != null ? "Perfil atualizado com sucesso!" : "Perfil criado com sucesso!");
            
            return "redirect:/perfil/visualizar/" + perfil.getId();
            
        } catch (IOException e) {
            result.reject("error.upload", "Erro ao processar a foto do perfil");
            return "perfil/formulario";
        } catch (Exception e) {
            result.reject("error.sistema", "Ocorreu um erro ao salvar o perfil");
            return "perfil/formulario";
        }
    }

    @GetMapping("/visualizar/{id}")
    public String visualizarPerfil(@PathVariable Long id, Model model) {
        // Versão sem Optional - usando o service já ajustado
        Perfil perfil = servicePerfil.buscarPorId(id);
        
        if (perfil == null) {
            throw new PerfilException("Perfil não encontrado");
        }
        
        model.addAttribute("perfil", perfil);
        return "perfil/visualizar";
    }

    // Método para processar upload da foto
    private String processarUpload(MultipartFile arquivo) throws IOException {
        // Gera nome único para o arquivo
        String nomeOriginal = arquivo.getOriginalFilename();
        String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        String nomeArquivo = UUID.randomUUID().toString() + extensao;

        // Cria diretório se não existir
        Path diretorio = Paths.get(uploadDirectory);
        if (!Files.exists(diretorio)) {
            Files.createDirectories(diretorio);
        }

        // Salva o arquivo
        Path caminho = diretorio.resolve(nomeArquivo);
        Files.copy(arquivo.getInputStream(), caminho);

        return nomeArquivo;
    }

    // Listar perfis pendentes (para coordenadores/admins)
    @GetMapping("/pendentes")
    public String listarPendentes(Model model) {
        model.addAttribute("perfisPendentes", servicePerfil.listarPorStatus(StatusPerfil.PENDENTE));
        return "perfil/pendentes";
    }

    // Aprovar perfil
    @PostMapping("/aprovar/{id}")
    public String aprovarPerfil(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            servicePerfil.atualizarStatus(id, StatusPerfil.APROVADO);
            attributes.addFlashAttribute("sucesso", "Perfil aprovado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao aprovar perfil: " + e.getMessage());
        }
        return "redirect:/perfil/pendentes";
    }

    // Reprovar perfil
    @PostMapping("/reprovar/{id}")
    public String reprovarPerfil(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            servicePerfil.atualizarStatus(id, StatusPerfil.REPROVADO);
            attributes.addFlashAttribute("sucesso", "Perfil reprovado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao reprovar perfil: " + e.getMessage());
        }
        return "redirect:/perfil/pendentes";
    }
}