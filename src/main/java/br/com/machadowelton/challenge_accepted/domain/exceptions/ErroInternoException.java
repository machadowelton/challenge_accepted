package br.com.machadowelton.challenge_accepted.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ErroInternoException extends RuntimeException {
	
	private static final long serialVersionUID = -8866528704257319243L;

	public ErroInternoException() {}
	
	public ErroInternoException(String msg) {
		super(msg);
	}
	
	public ErroInternoException(Throwable thr) {
		super(thr);
	}
	
	public ErroInternoException(String msg, Throwable thr) {
		super(msg, thr);
	}
	
}
