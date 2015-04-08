package br.com.arq.validation;

import static javax.validation.Validation.buildDefaultValidatorFactory;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import br.com.arq.model.Entidade;

@Component
public class Validador<T extends Entidade> {

	private final static ValidatorFactory FACTORY = buildDefaultValidatorFactory();

	public void validar(final T entidade) {
		final StringBuilder sb = new StringBuilder();
		if (entidade != null) {
			final javax.validation.Validator validator = FACTORY.getValidator();
			final Set<ConstraintViolation<T>> constraintViolations = validator.validate(entidade);

			if (!constraintViolations.isEmpty()) {
				for (final ConstraintViolation<T> constraint : constraintViolations) {
					sb.append(String.format("%n%s: %s", constraint.getPropertyPath(), constraint.getMessage()));
				}
			}
		}
		
		String mensagem = sb.toString();
		
		if (!mensagem.isEmpty()) {
			throw new ValidacaoException(mensagem);
		}
	}
}
