package br.com.arq.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jdesktop.observablecollections.ObservableCollections;
import org.springframework.stereotype.Component;

import br.com.arq.model.Categoria;
import br.com.arq.model.ContaBancaria;

@Component
public class ListasUteis {
	
	private List<Categoria> categorias;
	private List<ContaBancaria> contas;
	
	@PostConstruct
	private void init() {
		categorias = ObservableCollections.observableList(new ArrayList<Categoria>());
		contas = ObservableCollections.observableList(new ArrayList<ContaBancaria>());
	}
	
	public void setContas(List<ContaBancaria> contas) {
		this.contas.clear();
		this.contas.addAll(contas);
	}
	
	public List<ContaBancaria> getContas() {
		return contas;
	}
	
	public void setCategorias(List<Categoria> categorias) {
		this.categorias.clear();
		this.categorias.addAll(categorias);
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}
}
