package br.edu.infnet.acme.service;

import br.edu.infnet.acme.domain.Cotacao;
import br.edu.infnet.acme.repository.CotacaoRepository;
import br.edu.infnet.acme.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    private CotacaoRepository cotacaoRepository;

    public List<Cotacao> obterLista(Integer id){
        return cotacaoRepository.listAllbyProduto_id(id);
    }


}
