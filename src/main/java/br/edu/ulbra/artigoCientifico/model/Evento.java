package br.edu.ulbra.artigoCientifico.model;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = true)
    Submissao submissao;

    @Column(nullable = false)
    String usuarioResponsavel;

    @Column(nullable = false)
    String nomeEvento;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    Date dataEvento;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    Date dataAbertura;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    Date dataFechamento;

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

    public Submissao getSub() {
        return submissao;
    }

    public void setSub(Submissao sub) {
        this.submissao = sub;
    }

    public Submissao getSubmissao() {
        return submissao;
    }

    public void setSubmissao(Submissao submissao) {
        this.submissao = submissao;
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

}
