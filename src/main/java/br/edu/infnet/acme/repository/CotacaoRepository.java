package br.edu.infnet.acme.repository;

import br.edu.infnet.acme.domain.Cotacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotacaoRepository extends CrudRepository<Cotacao, Integer> {

    @Query("from Cotacao c where c.id = :produto_id")
    public List<Cotacao> listAllbyProduto_id(Integer produto_id);
}
