package br.com.machadowelton.challenge_accepted.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.machadowelton.challenge_accepted.domain.Planeta;

public interface PlanetaService {
	
	public Planeta buscarPorId(Long id);
	
	public Planeta buscarPorNome(String nome);
	
	public void inserir(Planeta planeta);
	
	public Page<Planeta> listar(Pageable pageable);
	
	public void removerPorId(Long id);
	
}
