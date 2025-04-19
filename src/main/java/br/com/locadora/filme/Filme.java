package br.com.locadora.filme;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.locadora.ator.Ator;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "filme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Filme implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
	//https://omdbapi.com/?t=The+Peanut+Butter+Solution&apikey=c2ad5ffb

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String ano;
	private String classificacao;
	private LocalDate dataLancamento;
	private String duracao;
	private String genero;
	private String nomeDiretor;
	private String resumo;
	private String pais;
	private String lingua;
	private String poster;
	
	@ManyToMany(mappedBy = "filmes",  fetch = FetchType.LAZY)
	private List<Ator> atores = new ArrayList<Ator>();
	
	
	public Filme(DadosCadastroFilme dados) {
		this.titulo = dados.titulo();
		this.nomeDiretor = dados.nomeDiretor();
		
	}

	public void atualizarInformacoes(DadosAtualizacaoFilme dados) {
		if (dados.titulo() != null )
			this.titulo = dados.titulo();
		if (dados.nomeDiretor() != null)
			this.nomeDiretor =dados.nomeDiretor();
	}
}
