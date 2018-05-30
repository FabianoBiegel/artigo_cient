package com.trab.gestao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue

	private String user;
	private String nome;
	private Date data;
	private Date dataComeca;
	private Date dataTermina;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getDataComeca() {
		return dataComeca;
	}
	public void setDataComeca(Date dataComeca) {
		this.dataComeca = dataComeca;
	}
	public Date getDataTermina() {
		return dataTermina;
	}
	public void setDataTermina(Date dataTermina) {
		this.dataTermina = dataTermina;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	

}