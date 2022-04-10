package br.edu.infnet.acme.domain;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String nome;
    private String tipo;
    private String img;


    @ManyToMany(mappedBy = "produtos")
    private List<Cotacao> cotacao;

    public Produto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Cotacao> getCotacao() {
        return cotacao;
    }

    public void setCotacao(List<Cotacao> cotacao) {
        this.cotacao = cotacao;
    }
}
