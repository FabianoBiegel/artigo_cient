package br.edu.ulbra.artigocientifico.input;

//import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

public class SubInput {

    private String evento;

    private String user;

    private String titulo;

    private String Resumo;

    private Date dataSubmissao;

    private String SubArq;

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

    public void setResumo(String Resumo) {
        this.Resumo = Resumo;
    }

    public Date getDataSubmissao() {
        return dataSubmissao;
    }

    public void setDataSubmissao(Date dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    public String getSubArq() {
        return SubArq;
    }

    public void setSubArq(String SubArq) {
        this.SubArq = SubArq;
    }

}
