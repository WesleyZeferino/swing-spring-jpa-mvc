package br.com.arq.main;

import java.util.Locale;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt","BR"));
    	new ClassPathXmlApplicationContext("META-INF/spring-config.xml");
	}
}
