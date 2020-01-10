package br.com.machadowelton.challenge_accepted.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.machadowelton.challenge_accepted.domain.Planeta;
import br.com.machadowelton.challenge_accepted.services.impl.PlanetaServiceImpl;

@RequestMapping("/planeta")
@RestController
public class PlanetaController {
	
	
	@Autowired
	private PlanetaServiceImpl planetaServiceImpl;
	
	@GetMapping("/{id}")
	public Planeta buscarPorId(@PathVariable("id") Long id) {
		return planetaServiceImpl.buscarPorId(id);
	}
	
	@GetMapping("/findByName")
	public Planeta buscarPorNome(@RequestParam("nome_planeta") String nomePlaneta) {
		return planetaServiceImpl.buscarPorNome(nomePlaneta);
	}
	
	@GetMapping
	public Page<Planeta> buscarTodos(Pageable pageable) {
		return planetaServiceImpl.listar(pageable);
	}
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Planeta planet) {
		planetaServiceImpl.inserir(planet);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerPorId(@PathVariable("id") Long id) {
		planetaServiceImpl.removerPorId(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
