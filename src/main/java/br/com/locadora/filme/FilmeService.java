package br.com.locadora.filme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {
	@Autowired
	private FilmeRepository repository;
	
	public List<Filme> getAllFilme() {
		return repository.findAll(Sort.by("titulo").ascending());
	}
	public Filme getFilmeById(Long id) {
		return repository.getReferenceById(id);
	}
	public List<Filme> findAllById(List<Long> filmesIds) {
		return repository.findAllById(filmesIds);
	}

}
