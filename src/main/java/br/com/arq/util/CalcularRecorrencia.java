package br.com.arq.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import br.com.arq.model.Frequencia;

/**
 * Arquivo: CalcularRecorrencia.java Criado em: 21/08/2014
 *
 * @author Wesley Luiz
 * @version 1.0.0
 */
@Component
public final class CalcularRecorrencia {

	private List<Date> datas;

	/**
	 * Método responsável por obter as datas de vencimento a partir dos dados
	 * informados na contrução dessa classe.
	 *
	 * @author Wesley Luiz
	 * @return - Lista de datas contendo os vencimentos.
	 */
	public List<Date> gerarDatasRecorrentes(final Frequencia frequencia, final Date database, final Integer limite) {
		datas = new ArrayList<Date>();

		if (ObjetoUtil.isReferencia(database, limite, frequencia)) {
			final DateTime dateTime = new DateTime(database);
			int diaTemp = 0;

			for (int i = 0; i < limite; i++) {
				switch (frequencia) {

				case DIARIO:
					adicionarData(database, new DateTime(dateTime.plusDays(i)), i);
					break;

				case SEMANAL:
					adicionarData(database, new DateTime(dateTime.plusWeeks(i)), i);
					break;

				case MENSAL:
					adicionarData(database, new DateTime(dateTime.plusMonths(i)), i);
					break;

				case BIMESTRAL:
					diaTemp = i * 2;
					adicionarData(database, new DateTime(dateTime.plusMonths(diaTemp)), i);
					break;

				case TRIMESTRAL:
					diaTemp = i * 3;
					adicionarData(database, new DateTime(dateTime.plusMonths(diaTemp)), i);
					break;

				case SEMESTRAL:
					diaTemp = i * 6;
					adicionarData(database, new DateTime(dateTime.plusMonths(diaTemp)), i);
					break;

				case ANUAL:
					adicionarData(database, new DateTime(dateTime.plusYears(i)), i);
					break;
				}
			}

			return datas;
		}

		return null;
	}

	/**
	 * Método responsável por montar a lista de datas, com a nova data
	 * calculada.
	 *
	 * @author Wesley Luiz
	 * @param data - Data calculada.
	 * @param i - Valor da iteração atual.
	 */
	private void adicionarData(final Date database, final DateTime data, final int i) {
		if (i > 0) {
			datas.add(data.toDate());
		} else {
			datas.add(database);
		}
	}
}
