package com.generation.lojagames.controller;

import com.generation.lojagames.exception.ResourceNotFoundException;
import com.generation.lojagames.model.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.generation.lojagames.model.Produto;
import com.generation.lojagames.model.repository.ProdutoRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto not found"));
    }

    @PostMapping
    public Produto createProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public Produto updateProduto(@RequestBody Produto produtoDetails) {
        if (produtoRepository.existsById(produtoDetails.getId())) {
            if (categoriaRepository.existsById(produtoDetails.getCategoria().getId()))
                return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtoDetails)).getBody();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The selected category was not found.", null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto not found"));
        produtoRepository.delete(produto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoria/{idCategoria}")
    public List<Produto> listarProdutosPorCategoria(@PathVariable Long idCategoria) {
        return produtoRepository.findByCategoriaId(idCategoria);
    }
}
