package br.com.arq.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.arq.controller.ListarFolhaController;
import br.com.arq.model.Folha;
import br.com.arq.model.TipoFolha;

public class FolhaDAOTest {

	@Autowired
	private ListarFolhaController controller;
	
	@Before
	@SuppressWarnings("resource")
	public void init() {
		Locale.setDefault(new Locale("pt", "BR"));
		new ClassPathXmlApplicationContext("META-INF/spring-config.xml");
	}
	
	@Test
	public void test() {
		List<Folha> dados = controller.getDao().findByTipo(TipoFolha.DESPESA);
		assertTrue(!dados.isEmpty());
	}
}
