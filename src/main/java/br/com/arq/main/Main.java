package br.com.arq.main;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		Locale.setDefault(new Locale("pt", "BR"));
		
		try {
			new ClassPathXmlApplicationContext("/META-INF/spring-config.xml");
		} catch (Throwable ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Erro ao iniciar o contexto do spring", ex);
		}
	}
}
