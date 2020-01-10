package br.com.machadowelton.challenge_accepted.services.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.machadowelton.challenge_accepted.domain.Planeta;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {
	
	public Optional<Planeta> findByNome(String nome);
	
}
