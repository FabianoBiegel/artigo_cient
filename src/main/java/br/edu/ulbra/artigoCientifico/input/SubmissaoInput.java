package br.edu.ulbra.artigoCientifico.input;

//import org.springframework.web.multipart.MultipartFile;
import br.edu.ulbra.artigoCientifico.model.Evento;
import java.io.File;
import java.sql.Date;
import java.util.Set;

public class SubmissaoInput {    

    private String user;

    private String titulo;

    private String Resumo;

    private Date dataSubmissao;

    private File SubArq;
    
    private Set<Evento> eventos;    

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

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    

}
