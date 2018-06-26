package br.edu.ulbra.artigoCientifico.input;

//import org.springframework.web.multipart.MultipartFile;
import java.sql.Date;

public class EventoInput {

    private Long id;
    private String usuarioResponsavel;
    private String nomeEvento;
    private Long idTipo;
    private Date dataEvento;
    private Date dataAbertura;
    private Date dataFechamento;
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

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
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
