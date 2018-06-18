package br.edu.ulbra.artigocientifico.model;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private Sub sub;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date DataEvento;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataComeca;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTermina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sub getSub() {
        return sub;
    }

    public void setSub(Sub sub) {
        this.sub = sub;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDataEvento() {
        return DataEvento;
    }

    public void setDataEvento(Date data) {
        this.DataEvento = data;
    }

    public Date getDataComeca() {
        return dataComeca;
    }

    public void setDataComeca(Date dataComeca) {
        this.dataComeca = dataComeca;
    }

    public Date getDataTermina() {
        return dataTermina;
    }

    public void setDataTermina(Date dataTermina) {
        this.dataTermina = dataTermina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
