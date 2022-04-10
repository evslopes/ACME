package br.edu.infnet.acme.controller;

import br.edu.infnet.acme.domain.Produto;
import br.edu.infnet.acme.repository.ProdutoRepository;
import br.edu.infnet.acme.service.AmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    AmazonService amazonService;

    @PostMapping()
    public Produto save(@ModelAttribute @RequestBody Produto produto, @RequestPart("foto") MultipartFile file) {
        String url_img = amazonService.uploadFile(file);
        produto.setImg(url_img);
        return produtoRepository.save(produto);
    }

    @PutMapping()
    public Produto update(@ModelAttribute @RequestBody Produto produto,@RequestPart("foto") MultipartFile file) {
        amazonService.uploadFile(file);
        return produtoRepository.save(produto);
    }

    @GetMapping()
    public List<Produto> findAll() {
        return (List<Produto>) produtoRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Produto> obterPorId(@PathVariable Integer id) {
        return produtoRepository.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {

        Optional<Produto> produto = produtoRepository.findById(id);

        amazonService.deleteFile(produto.get().getImg());
        produtoRepository.deleteById(id);
        }

}
