package br.edu.infnet.acme.repository;

import br.edu.infnet.acme.domain.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto,Integer> {
}
