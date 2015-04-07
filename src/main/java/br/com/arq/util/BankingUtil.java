package br.com.arq.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.ofx4j.domain.data.MessageSetType;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.ResponseMessageSet;
import net.sf.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import net.sf.ofx4j.domain.data.banking.BankingResponseMessageSet;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.io.AggregateUnmarshaller;

/**
 * Arquivo: BankingUtil.java <br/>
 *
 * @since 03/11/2014
 * @author Wesley Luiz
 * @version 1.0.0
 */
public final class BankingUtil {

	/**
	 * Método responsável por obter a lista de transações de um arquivo com a
	 * extensão <i>ofx</i> informado.
	 *
	 * @author Wesley Luiz
	 * @since 03/11/2014
	 * @param arquivo
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Transaction> obterTransacoesArquivoOfx(final InputStream arquivo) {
		final List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			final AggregateUnmarshaller a = new AggregateUnmarshaller(ResponseEnvelope.class);
			final ResponseEnvelope re = (ResponseEnvelope) a.unmarshal(arquivo);
			final MessageSetType type = MessageSetType.banking;
			final ResponseMessageSet message = re.getMessageSet(type);
			List<BankStatementResponseTransaction> bank;

			if (ObjetoUtil.isReferencia(message)) {
				bank = ((BankingResponseMessageSet) message).getStatementResponses();

				for (final BankStatementResponseTransaction b : bank) {
					for (final Transaction transaction : b.getMessage().getTransactionList().getTransactions()) {
						final Double amount = transaction.getAmount();
						if (amount < 0) {
							transaction.setAmount(amount * (-1));
						}
						transactions.add(transaction);
					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return transactions;
	}
}
