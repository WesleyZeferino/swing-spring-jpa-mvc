package br.com.arq.util;

import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public final class XlsBuilder {

	private final HSSFWorkbook book;
	private final HSSFSheet sheet;
	private final CellBuilder cellBuilder;
	private HSSFRow row;

	private XlsBuilder() {
		book = new HSSFWorkbook();
		sheet = book.createSheet();
		cellBuilder = new CellBuilder();
	}

	public static XlsBuilder create() {
		return new XlsBuilder();
	}

	public CellBuilder addRow(final int index) {
		return addRow(index, null);
	}

	public CellBuilder addRow(final int index, final HSSFCellStyle style) {
		row = sheet.createRow(index);
		Optional.ofNullable(style).ifPresent(row::setRowStyle);
		return cellBuilder;
	}

	public HSSFWorkbook getWorkbook() {
		return book;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public final class CellBuilder {

		private CellBuilder() {

		}

		public CellBuilder addCell(final int index, final Object value) {
			return addCell(index, value, null);
		}

		public CellBuilder addCell(final int index, final Object value, final HSSFCellStyle style) {
			final HSSFCell cell = row.createCell(index);
			setCell(value, cell);

			Optional.ofNullable(style).ifPresent(cell::setCellStyle);

			return cellBuilder;
		}

		private void setCell(final Object value, final HSSFCell cell) {
			if (value instanceof String) {
				cell.setCellValue((String) value);
			} else if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else if (value instanceof Integer) {
				cell.setCellValue((Integer) value);
			}
		}
	}
}
