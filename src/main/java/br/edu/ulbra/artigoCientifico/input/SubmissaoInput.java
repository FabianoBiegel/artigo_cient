package br.edu.ulbra.artigoCientifico.input;

//import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Date;

public class SubmissaoInput {

    private String evento;

    private String user;

    private String titulo;

    private String Resumo;

    private Date dataSubmissao;

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

    public void setResumo(String Resumo) {
        this.Resumo = Resumo;
    }

    public Date getDataSubmissao() {
        return dataSubmissao;
    }

    public void setDataSubmissao(Date dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    public File getSubArq() {
        return SubArq;
    }

    public void setSubArq(File SubArq) {
        this.SubArq = SubArq;
    }

    

}
