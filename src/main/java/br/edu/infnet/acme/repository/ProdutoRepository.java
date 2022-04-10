package br.edu.infnet.acme.repository;

import br.edu.infnet.acme.domain.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto,Integer> {

    @Query("from Produto p where p.id = :produto_id")
    public List<Produto> findAll(Integer produto_id);
}
