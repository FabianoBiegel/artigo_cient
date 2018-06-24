package br.edu.ulbra.gestaovinhos.input;

//import org.springframework.web.multipart.MultipartFile;

public class EventoInput {
	private Long id;
	private String usuarioResponsavel;
	private String nomeEvento;
	private Long idTipo;
//	private MultipartFile imagem;
//	private String nomeImagem;

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

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

//	public MultipartFile getImagem() {
//		return imagem;
//	}

//	public void setImagem(MultipartFile imagem) {
//		this.imagem = imagem;
//	}

//	public String getNomeImagem() {
//		return nomeImagem;
//	}

//	public void setNomeImagem(String nomeImagem) {
//		this.nomeImagem = nomeImagem;
//	}
}
