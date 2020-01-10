package br.com.machadowelton.challenge_accepted.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -8866528704257319243L;

	public RecursoNaoEncontradoException() {}
	
	public RecursoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public RecursoNaoEncontradoException(Throwable thr) {
		super(thr);
	}
	
	public RecursoNaoEncontradoException(String msg, Throwable thr) {
		super(msg, thr);
	}
	
}
