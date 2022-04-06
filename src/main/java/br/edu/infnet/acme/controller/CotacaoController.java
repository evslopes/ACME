package br.edu.infnet.acme.controller;

import br.edu.infnet.acme.domain.Cotacao;
import br.edu.infnet.acme.repository.CotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cotacao")
public class CotacaoController {
    @Autowired
    CotacaoRepository cotacaoRepository;

    @PostMapping
    public Cotacao save(@RequestBody Cotacao cotacao) {
        return cotacaoRepository.save(cotacao);
    }

    @PutMapping
    public Cotacao update(@RequestBody Cotacao cotacao) {
        return cotacaoRepository.save(cotacao);
    }

    @GetMapping
    public List<Cotacao> findAll() {
        return (List<Cotacao>) cotacaoRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Cotacao> findById(Integer id) {
        return cotacaoRepository.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@RequestBody Integer id) {
        cotacaoRepository.deleteById(id);}
}

