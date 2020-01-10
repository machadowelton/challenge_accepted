package br.com.machadowelton.challenge_accepted.tools.clients.swapi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.machadowelton.challenge_accepted.tools.clients.swapi.domain.SwApiPlaneta;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

@Service
public class SwApi {
	
	
	@Value("${br.com.challenge_accepted.swapi}")
	private String swapi;
	
	private final String comp = "/planets/?search=";
	
	public int buscarFilmesPorNomePlaneta(String planetName) {
		List<String> planetas = new ArrayList<String>();
		String reqUrl = String.format("%s%s%s", swapi, comp, planetName);
		while(true) {
			OkHttpClient client = new OkHttpClient.Builder()
					.proxy(
							new Proxy(Proxy.Type.HTTP, 
									new InetSocketAddress("vbr008002-029.bbmapfre.corp", 80)))
					.build();
			Request request = new Request.Builder().url(reqUrl).build();
			ResponseBody responseBody;
			try {
				responseBody = client.newCall(request).execute().body();
				String jsonString = responseBody.string();
				Gson gson = new Gson();
				SwApiPlaneta planetasEncontrados = gson.fromJson(jsonString, SwApiPlaneta.class);
				planetasEncontrados.getResults().forEach( r -> {
					r.getFilms().forEach(planetas::add);
				});
				if(Optional.ofNullable(planetasEncontrados.getNext()).isPresent())
					reqUrl = planetasEncontrados.getNext();
				else
					break;
			} catch (IOException e) {
				throw new InternalError("Ocorreu um erro realizar a requisição");
			}
		}
		return planetas.size();
	}
	
	
}
