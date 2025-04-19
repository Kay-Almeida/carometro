package br.com.locadora.filme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonParser {
  
        // Extraindo partes específicas
        public static Filme extracaoFilme (String json) throws JsonMappingException, JsonProcessingException {
        	//Criar uma lista de Filmes
        	System.out.println("Json= " + json);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(json);
			Filme filme = new Filme();
			filme.setTitulo(rootNode.path("Title").asText());
			filme.setAno(rootNode.path("Year").asText());
			filme.setClassificacao(rootNode.path("Rated").asText());
			// filme.setDataLancamento(rootNode.path("filme").path("Released").asText());
			filme.setDuracao(rootNode.path("Runtime").asText());
			filme.setGenero(rootNode.path("Genre").asText());
			
			System.out.println("Titulo=" + filme.getTitulo() +" Duracao= "+ filme.getDuracao());
			return filme;
        }
}

