package br.com.locadora.filme;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.locadora.omdb.OmdbService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/filme")
public class FilmeController {
	
	@Autowired
	private FilmeRepository repository;
	
	private OmdbService omdbService = new OmdbService();
	
	@GetMapping ("/formulario")                  
	public String carregaPaginaFormulario (Long id, Model model){ 
		
		if(id != null) {
	        var filme = repository.getReferenceById(id);
	        model.addAttribute("filme", filme);
	    }
	    return "filme/formulario";              
	}     
	@GetMapping                                           
	public String carregaPaginaListagem (Model model){    
	    model.addAttribute("lista", repository.findAll(Sort.by("titulo").ascending()));
	    return "filme/listagem";                         
	} 

	@GetMapping("/buscaromdb")
    public String exibirPaginaBusca(
        @RequestParam(required = false) String termo, 
        Model model) {
		 
        if (termo != null && !termo.isEmpty()) {
            try {
                // Busca no OMDB e adiciona ao Model
                String resultado = omdbService.buscarFilmePorTitulo(termo);
                System.out.println("Passando FilmeControler OMDB= " +resultado);
                Filme filmeOdbc = JsonParser.extracaoFilme(resultado);
                model.addAttribute("resultados", filmeOdbc);
            } catch (Exception e) {
                model.addAttribute("erro", "Erro ao buscar no OMDB");
            }
        }
        return "filme/buscaromdb";
    }

	@PostMapping
	@Transactional
	public String cadastrar ( @Valid DadosCadastroFilme dados) {
			
			//@RequestParam("poster") MultipartFile arquivo,
			//RedirectAttributes redirectAttributes) {
		Filme filme = new Filme(dados);
//		try {
//			// Se houver upload de imagem
//			if (!arquivo.isEmpty()) {
//				String extensao = arquivo.getOriginalFilename()
//						.substring(arquivo.getOriginalFilename().lastIndexOf("."));
//
//				// Gera nome único para a imagem
//				String nomeImagem = UUID.randomUUID().toString() + extensao;
//				String caminho = "src/main/resources/static/uploads/" + nomeImagem;
//
//				// Salva o arquivo
//				Files.copy(arquivo.getInputStream(), Path.of(caminho), StandardCopyOption.REPLACE_EXISTING);
//
//				// Atualiza o caminho no objeto Filme
//				filme.setPoster("/uploads/" + nomeImagem);
//			}
//
//			repository.save(filme);
//			redirectAttributes.addFlashAttribute("sucesso", "Filme salvo com sucesso!");
//		} catch (Exception e) {
//			redirectAttributes.addFlashAttribute("erro", "Erro ao salvar filme: " + e.getMessage());
//		}
		repository.save(filme);
		return   "redirect:filme/formulario";      
	}
	
	@PutMapping
	@Transactional
	public String atualizar (DadosAtualizacaoFilme dados) {
		var filme = repository.getReferenceById(dados.id());
		filme.atualizarInformacoes(dados);
		return "redirect:filme";  
	}
	
	@DeleteMapping
	@Transactional
	public String removeFilme (Long id) {
		repository.deleteById (id);
		return "redirect:filme";  
	}
	
}
