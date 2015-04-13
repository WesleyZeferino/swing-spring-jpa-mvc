package br.com.arq.util;

/**
 *
 * @author Wesley Luiz
 */
public class ObjetoUtil {

	/**
	 * Método responsável por validar um <i>objeto</i> caso possua referência na
	 * memória.
	 *
	 * @author Wesley Luiz
	 * @param valor
	 *            é a variável à ser validada.
	 * @return Retorna <i>true</i> se possuir referência ou <i>false</i> caso
	 *         não tenha.
	 */
	public static Boolean isReferencia(final Object valor) {
		return valor != null;
	}

	/**
	 * Método responsável por validar um <i>objeto</i> caso possua referência na
	 * memória.
	 *
	 * @author Wesley Luiz
	 * @param valores
	 *            - Recebe a(s) variável(eis) à ser(em) validada(s).
	 * @return Retorna <i>true</i> se possuir referência ou <i>false</i> caso
	 *         não tenha.
	 */
	public static Boolean isReferencia(final Object... valores) {
		for (final Object obj : valores) {
			if (!isReferencia(obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método responsável por validar uma <i>String</i> caso seja vazia ou não.
	 *
	 * @author Wesley Luiz
	 * @param valor
	 *            à ser validado.
	 * @return Retorna um <i>boolean true</i> caso a variável seja vazia ou
	 *         <i>false</i> caso não seja.
	 */
	public static Boolean isVazio(final String valor) {
		return isReferencia(valor) ? valor.trim().isEmpty() : true;
	}

	/**
	 * Método responsável por validar as <i>String</i>'s verificando se são
	 * vazias ou não.
	 *
	 * @author Wesley Luiz
	 * @param valor
	 *            à ser validado.
	 * @return Retorna um <i>boolean true</i> caso a variável seja vazia ou
	 *         <i>false</i> caso não seja.
	 */
	public static Boolean isVazio(final String... valor) {
		for (final String s : valor) {
			if (isVazio(s)) {
				return true;
			}
		}
		return false;
	}
}
