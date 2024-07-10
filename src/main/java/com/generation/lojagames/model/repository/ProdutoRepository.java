package com.generation.lojagames.model.repository;

import com.generation.lojagames.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}