package com.trab.gestao.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sub implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue

	private String evento;
	private String user;
	private String titulo;
	private String Resumo;
	private Date dataUp;
	private File SubArq;
	
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getResumo() {
		return Resumo;
	}
	public void setResumo(String resumo) {
		Resumo = resumo;
	}
	public Date getDataUp() {
		return dataUp;
	}
	public void setDataUp(Date dataUp) {
		this.dataUp = dataUp;
	}
	public File getSubArq() {
		return SubArq;
	}
	public void setSubArq(File subArq) {
		SubArq = subArq;
	}
	
	


}