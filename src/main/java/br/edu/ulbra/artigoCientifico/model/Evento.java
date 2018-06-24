package br.edu.ulbra.artigoCientifico.model;

import javax.persistence.*;

@Entity
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	String usuarioResponsavel;

	@Column(nullable = false)
	String nomeEvento;

//	@Column(nullable = false)
//	String nomeImagem;

//	@Transient
//	private Long aviliacoesPositivas;

//	@Transient
//	private Long aviliacoesNegativas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(String nome) {
		this.usuarioResponsavel = nome;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String vinicola) {
		this.nomeEvento = vinicola;
	}

//	public String getNomeImagem() {
//		return nomeImagem;
//	}

//	public void setNomeImagem(String nomeImagem) {
//		this.nomeImagem = nomeImagem;
//	}

}
