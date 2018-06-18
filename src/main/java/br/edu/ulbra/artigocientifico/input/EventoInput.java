package br.edu.ulbra.artigocientifico.input;

import java.util.Date;

public class EventoInput {
  
    private String user;

    private String nome;

    private Date DataEvento;

    private Date dataComeca;

    private Date dataTermina;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataEvento() {
        return DataEvento;
    }

    public void setDataEvento(Date DataEvento) {
        this.DataEvento = DataEvento;
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
    
    
}
