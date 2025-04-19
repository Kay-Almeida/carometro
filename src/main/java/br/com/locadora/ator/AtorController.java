package br.com.locadora.ator;

import java.util.List;

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

import br.com.locadora.filme.Filme;
import br.com.locadora.filme.FilmeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/ator")
public class AtorController {
	
	@Autowired
	private AtorRepository repository;
	
	@Autowired
	private FilmeService filmeService;
	
	@GetMapping ("/formulario")                  
	public String carregaPaginaFormulario (Long id, Model model){ 
		model.addAttribute("filmes", filmeService.getAllFilme());
		if(id != null) {
	        var ator = repository.getReferenceById(id);
	        model.addAttribute("ator", ator);
	    }
	    return "ator/formulario";              
	}     
	@GetMapping                                           
	public String carregaPaginaListagem (Model model){    
		
		//model.addAttributes("filmeator", );
	    model.addAttribute("lista", repository.findAll(Sort.by("nome").ascending()));
	    return "ator/listagem";                         
	} 

	@PostMapping
	@Transactional
	public String cadastrar ( @Valid DadosCadastroAtor dados, @RequestParam List<Long> filmesIds) {
		Ator ator = new Ator(dados);

		// Busca os Filmes pelos IDs cria uma lista de Filmes 
		List<Filme> filmes = filmeService.findAllById(filmesIds);
	
//		for (Filme filme : filmes) {
//			//System.out.println("Filme " + filme.getTitulo());
//		} 
		ator.setFilmes(filmes);
		repository.save(ator);
		return   "redirect:ator/formulario";      
	}
	
	@PutMapping
	@Transactional
	public String atualizar (DadosAtualizacaoAtor dados) {
		var ator = repository.getReferenceById(dados.id());
		ator.atualizarInformacoes(dados);
		return "redirect:ator";  
	}
	
	@DeleteMapping
	@Transactional
	public String removeAtor (Long id) {
		repository.deleteById (id);
		return "redirect:ator";  
	}
	
}
