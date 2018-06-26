package br.edu.ulbra.artigoCientifico.model;

import java.io.File;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Submissao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    String evento;

    @Column(nullable = false)
    String user;

    @Column(nullable = false)
    String titulo;

    @Column(nullable = false)
    String Resumo;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    Date dataSubmissao;

    @Column(nullable = true)
    File subArq;

    @OneToMany(mappedBy = "submissao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Evento> eventos;    

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
        this.Resumo = resumo;
    }    

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataSubmissao() {
        return dataSubmissao;
    }

    public void setDataSubmissao(Date dataSubmissao) {
        this.dataSubmissao = dataSubmissao;
    }

    public File getSubArq() {
        return subArq;
    }

    public void setSubArq(File subArq) {
        this.subArq = subArq;
    }
    
}
