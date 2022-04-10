package br.edu.infnet.acme.controller;

import br.edu.infnet.acme.domain.Cotacao;
import br.edu.infnet.acme.domain.Produto;
import br.edu.infnet.acme.repository.CotacaoRepository;
import br.edu.infnet.acme.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cotacao")
public class CotacaoController {
    @Autowired
    CotacaoRepository cotacaoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @PostMapping
    public Cotacao save(@ModelAttribute @RequestBody Cotacao cotacao) {
        return cotacaoRepository.save(cotacao);
    }

    @PutMapping
    public Cotacao update(@ModelAttribute @RequestBody Cotacao cotacao) {
        return cotacaoRepository.save(cotacao);
    }

    @GetMapping
    public List<Cotacao> findAll() {
        return (List<Cotacao>) cotacaoRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Cotacao> findById(@PathVariable Integer id) {
        return cotacaoRepository.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        cotacaoRepository.deleteById(id);}

    @PostMapping ( value = "/registrar")
    public Produto registrarCotacao(@RequestParam Integer id,@ModelAttribute @RequestBody Cotacao cotacao) {
        Produto produto;
        produto = produtoRepository.findById(id).orElse(null);
        if (produto != null) {
            List<Cotacao> cotacaos = new ArrayList<>();
            cotacaos.add(cotacao);
            cotacao.setProduto(produtoRepository.findById(produto.getId()).orElse(null));
            produto.setCotacao(cotacaos);
            return produtoRepository.save(produto);
        } else
            return null;
    }
}

