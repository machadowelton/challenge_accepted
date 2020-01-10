package br.com.machadowelton.challenge_accepted.services.impl;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.machadowelton.challenge_accepted.domain.Planeta;
import br.com.machadowelton.challenge_accepted.domain.exceptions.ErroInternoException;
import br.com.machadowelton.challenge_accepted.domain.exceptions.RecursoNaoEncontradoException;
import br.com.machadowelton.challenge_accepted.domain.exceptions.ValidacaoException;
import br.com.machadowelton.challenge_accepted.services.PlanetaService;
import br.com.machadowelton.challenge_accepted.services.repositories.PlanetaRepository;
import br.com.machadowelton.challenge_accepted.tools.clients.swapi.SwApi;

@Service
public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	private PlanetaRepository repository;

	@Autowired
	private SwApi swapi;

	@Override
	public Planeta buscarPorId(Long id) {
		try {
			return repository.findById(id).map(p -> {
				p.setQuantidadeFilmes(swapi.buscarFilmesPorNomePlaneta(p.getNome()));
				return p;
			}).orElseThrow(() -> new RecursoNaoEncontradoException("Planeta não encontrado pelo id: " + id));
		} catch (Exception e) {
			if (e instanceof RecursoNaoEncontradoException)
				throw e;
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public Planeta buscarPorNome(String nome) {
		try {
			Optional<String> nomePlaneta = Optional.ofNullable(nome);
			if(!nomePlaneta.isPresent()) throw new ValidacaoException("Nome do planeta não pode ser nulo");
			return repository.findByNome(nome).map(p -> {
				p.setQuantidadeFilmes(swapi.buscarFilmesPorNomePlaneta(p.getNome()));
				return p;
			}).orElseThrow(() -> new RecursoNaoEncontradoException("Planeta não encontrado pelo nome: " + nome));
		} catch (Exception e) {
			if (e instanceof RecursoNaoEncontradoException)
				throw e;
			else if( e instanceof ValidacaoException)
				throw e;
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public void inserir(Planeta planeta) {
		try {
			repository.save(planeta);
		} catch (Exception e) {
			if(e instanceof DataIntegrityViolationException)
				throw new ValidacaoException("Ocorreu um erro de dado duplicado", e);
			else
				throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public Page<Planeta> listar(Pageable pageable){
		try {
			Page<Planeta> planetas = repository.findAll(pageable);
			if(planetas.get().count() == 0)
				throw new RecursoNaoEncontradoException("Nenhum planeta cadastrado");
			planetas.get().forEach(p -> {
				p.setQuantidadeFilmes(swapi.buscarFilmesPorNomePlaneta(p.getNome()));
			});
			return planetas;
		} catch (Exception e) {
			if (e instanceof RecursoNaoEncontradoException)
				throw e;
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public void removerPorId(Long id) {
		try {
			if (repository.existsById(id))
				repository.deleteById(id);
			else
				throw new RecursoNaoEncontradoException("Nenhum planeta encontrado pelo id: " + id);
		} catch (Exception e) {
			if (e instanceof RecursoNaoEncontradoException)
				throw e;
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

}
