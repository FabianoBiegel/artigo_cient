package br.edu.ulbra.artigoCientifico.model;

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
    @Temporal(TemporalType.TIMESTAMP)
    Date dataSubmissao;

    @Column(nullable = true)
    String SubArq;

    @OneToMany(mappedBy = "submissao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Evento> eventos;

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
        this.Resumo = resumo;
    }

    public Date getdataSubmissao() {
        return dataSubmissao;
    }

    public void setdataSubmissao(Date dataUp) {
        this.dataSubmissao = dataUp;
    }

    public String getSubArq() {
        return SubArq;
    }

    public void setSubArq(String subArq) {
        this.SubArq = subArq;
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

}
