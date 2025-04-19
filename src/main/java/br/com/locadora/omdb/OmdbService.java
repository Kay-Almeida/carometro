package br.com.locadora.omdb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class OmdbService {
	@Value ("${omdb.api.url}")
	private String omdbApiUrl;
	
	@Value ("${omdb.api.key}")
	private String apiKey;
	
	private final RestTemplate restTemplate = new RestTemplate();

    public  String buscarFilmePorTitulo(String titulo) {
    	//usar os atributos acima
    	String url = ("https://www.omdbapi.com" + "/?apikey=" + "e0a1cf5d" + "&t=" + titulo).formatted();
       // System.out.println("URL " +url);
        return restTemplate.getForObject(url, String.class); // Retorna JSON como String
    }

}
