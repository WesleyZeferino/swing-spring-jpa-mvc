package br.com.arq.validation;

public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ValidacaoException(final String mensagem) {
		super(mensagem);
	}

}
