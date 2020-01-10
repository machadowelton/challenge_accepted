package br.com.machadowelton.challenge_accepted.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.machadowelton.challenge_accepted.domain.Planeta;
import br.com.machadowelton.challenge_accepted.domain.exceptions.ErroInternoException;
import br.com.machadowelton.challenge_accepted.domain.exceptions.RecursoNaoEncontradoException;
import br.com.machadowelton.challenge_accepted.domain.exceptions.ValidacaoException;
import br.com.machadowelton.challenge_accepted.services.impl.PlanetaServiceImpl;
import br.com.machadowelton.challenge_accepted.tools.clients.swapi.SwApi;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlanetaServiceImplTest {
		
	@TestConfiguration
	static class PlanetaServiceImplTestConfiguration {
		
		@Bean
		public PlanetaServiceImpl service() {
			return new PlanetaServiceImpl();
		}
		
		@Bean 
		public SwApi swApi() {
			return new SwApi();
		}
		
	}
	
	
	@Autowired
	private PlanetaServiceImpl service;	
	
	@Test
	public void testInserir() {
		Planeta planeta = Planeta.builder()
							.nome("Alderaan")
							.clima("temperate")
							.terreno("grasslands, mountains")
							.build();
		service.inserir(planeta);
		Planeta planetaRetorno = service.buscarPorNome(planeta.getNome());
		assertThat(planetaRetorno.getNome().equals(planeta.getNome())).isEqualTo(Boolean.TRUE);
	}
	
	@Test(expected = ErroInternoException.class)
	public void testInserirErroInterno() {
		Planeta planeta = null;
		service.inserir(planeta);		
	}
	
	@Test(expected = ValidacaoException.class)
	public void testInserirValidacao() {
		final List<Planeta> planetasFinal = Arrays.asList(
				Planeta.builder()
					.nome("Hoth")
					.clima("frozen")
					.terreno("tundra, ice caves, mountain ranges")
					.build(),
					Planeta.builder()
					.nome("Hoth")
					.clima("frozen")
					.terreno("tundra, ice caves, mountain ranges")
					.build()														
			);
		planetasFinal.forEach(service::inserir);
	}
	
	@Test
	public void testBuscarPorId() {
		Planeta planeta = Planeta.builder()
							.nome("Yavin IV")
							.clima("temperate, tropical")
							.terreno("jungle, rainforests")
							.build();
		service.inserir(planeta);
		Planeta planetaRetorno = service.buscarPorNome(planeta.getNome());
		assertThat(planetaRetorno.getNome().equals(planeta.getNome())).isEqualTo(Boolean.TRUE);
		assertThat(planetaRetorno.getClima().equals(planeta.getClima())).isEqualTo(Boolean.TRUE);
		assertThat(planetaRetorno.getTerreno().equals(planeta.getTerreno())).isEqualTo(Boolean.TRUE);
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void testBuscarPorIdRecursoNaoEncontrado() {
		service.buscarPorId(10L^10L);
	}
	
	@Test(expected = ErroInternoException.class)
	public void testBuscarPorIdErroInterno() {
		service.buscarPorId(null);
	}
	
	@Test
	public void testBuscarPorNome() {
		final Planeta planetaFinal = Planeta.builder()
				.nome("Hoth")
				.clima("frozen")
				.terreno("tundra, ice caves, mountain ranges")
				.build();
		service.inserir(planetaFinal);
		Planeta planeta = service.buscarPorNome(planetaFinal.getNome());
		assertThat(planeta.getNome().equals(planetaFinal.getNome())).isEqualTo(Boolean.TRUE);
		assertThat(planeta.getClima().equals(planetaFinal.getClima())).isEqualTo(Boolean.TRUE);
		assertThat(planeta.getTerreno().equals(planetaFinal.getTerreno())).isEqualTo(Boolean.TRUE);
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void testBuscarPorNomeRecursoNaoEncontrado() {
		service.buscarPorNome("SemNome");
	}
	
	@Test(expected = ValidacaoException.class)
	public void testBuscarPorErroInterno() {
		service.buscarPorNome(null);
	}
	
	@Test
	public void testListar() {
		final List<Planeta> planetasFinal = Arrays.asList(
					Planeta.builder()
						.nome("Hoth")
						.clima("frozen")
						.terreno("tundra, ice caves, mountain ranges")
						.build(),
					Planeta.builder()
						.nome("Yavin IV")
						.clima("temperate, tropical")
						.terreno("jungle, rainforests")
						.build(),
					Planeta.builder()
						.nome("Alderaan")
						.clima("temperate")
						.terreno("grasslands, mountains")
						.build()												
				);		
		planetasFinal.forEach(service::inserir);
		List<Planeta> planetas = service.listar(PageRequest.of(0, 5)).getContent();
		assertThat(planetas.size() == planetasFinal.size()).isEqualTo(Boolean.TRUE);
		Iterator<Planeta> iteratorPlanetasFinal = planetasFinal.iterator();
		Iterator<Planeta> iteratorPlanetas = planetas.iterator();
		while(iteratorPlanetas.hasNext() && iteratorPlanetasFinal.hasNext()) {
			Planeta planeta = iteratorPlanetas.next();
			Planeta planetaFinal = iteratorPlanetasFinal.next();
			assertThat(planeta.getNome().equals(planetaFinal.getNome())).isEqualTo(Boolean.TRUE);
			assertThat(planeta.getClima().equals(planetaFinal.getClima())).isEqualTo(Boolean.TRUE);
			assertThat(planeta.getTerreno().equals(planetaFinal.getTerreno())).isEqualTo(Boolean.TRUE);
		}
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void testListarRecursoNaoEncontrado() {
		service.listar(PageRequest.of(0, 5));
	}
	
	@Test(expected = ErroInternoException.class)
	public void testListarErroInterno() {
		service.listar(null);
	}
	
	@Test()
	public void testRemoverPorId() {
		final Planeta planetaFinal = Planeta.builder()
				.nome("Hoth")
				.clima("frozen")
				.terreno("tundra, ice caves, mountain ranges")
				.build();
		service.inserir(planetaFinal);
		final Planeta planetaRetorno = service.buscarPorNome(planetaFinal.getNome());
		service.removerPorId(planetaRetorno.getId());
		try {
			service.buscarPorId(planetaRetorno.getId());
			throw new Exception();
		} catch (Exception e) {
			assertThat(e instanceof RecursoNaoEncontradoException).isEqualTo(Boolean.TRUE);
		}
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void testRemoverPorIdRecursoNaoEncontrado() {
		service.removerPorId(50L);
	}
	
	@Test(expected = ErroInternoException.class)
	public void testRemoverPorIdErroInterno() {
		service.removerPorId(null);
	}
	
}
