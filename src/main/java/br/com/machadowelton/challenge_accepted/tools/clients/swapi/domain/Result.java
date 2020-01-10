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
public class Result {

	private String name;
	private String rotation_period;
	private String orbital_period;
	private String diameter;
	private String climate;
	private String gravity;
	private String terrain;
	private String surface_water;
	private String population;
	private List<String> residents;
	private List<String> films;
	private String created;
	private String edited;
	private String url;

}
