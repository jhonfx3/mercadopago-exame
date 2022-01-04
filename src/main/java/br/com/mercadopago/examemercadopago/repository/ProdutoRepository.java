package br.com.mercadopago.examemercadopago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mercadopago.examemercadopago.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query("SELECT p from Produto p WHERE p.id = :id")
	Produto findByIdProduto(@Param("id") Long id);

}