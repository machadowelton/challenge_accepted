package br.com.machadowelton.challenge_accepted.tools.clients.swapi.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SwApiPlaneta {
	
	private Long count;
	private String next;
	private String previous;
	private List<Result> results;
}
