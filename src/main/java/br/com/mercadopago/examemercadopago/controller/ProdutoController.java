package br.com.mercadopago.examemercadopago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mercadopago.examemercadopago.model.Produto;
import br.com.mercadopago.examemercadopago.repository.ProdutoRepository;

@Controller
@RequestMapping("produto")
public class ProdutoController {
	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("detalhe/{id}")
	public String detalhe(@PathVariable("id") Long id, Model model) {
		Produto produto = produtoRepository.getById(id);
		model.addAttribute("produto", produto);
		return "produto/detalhe";
	}
}
