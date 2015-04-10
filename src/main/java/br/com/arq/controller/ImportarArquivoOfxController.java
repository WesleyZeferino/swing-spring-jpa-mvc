package br.com.arq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.Folha;
import br.com.arq.ui.ImportarArquivoOfxUI;

@Component
public class ImportarArquivoOfxController extends AppController<Folha> {

	@Autowired
	private FolhaDAO dao;
	
	@Autowired
	private ImportarArquivoOfxUI ui;
	
	public FolhaDAO getDao() {
		return dao;
	}
	
	public ImportarArquivoOfxUI getUi() {
		return ui;
	}

}
