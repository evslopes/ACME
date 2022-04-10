package br.edu.infnet.acme.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Produto> produtos;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cotacao> cotacoes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
