package br.com.mercadopago.examemercadopago.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import br.com.mercadopago.examemercadopago.model.CarrinhoDeCompras;
import br.com.mercadopago.examemercadopago.model.CarrinhoItem;
import br.com.mercadopago.examemercadopago.model.Produto;
import br.com.mercadopago.examemercadopago.repository.ProdutoRepository;

@Controller
@RequestMapping(value = "carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoController {

	@Autowired
	private ProdutoRepository ProdutoRepository;

	@Autowired
	private CarrinhoDeCompras carrinho;

	@PostMapping("/adiciona")
	public String adiciona(Model model, Long produtoId) {
		Produto produto = ProdutoRepository.findByIdProduto(produtoId);
		carrinho.adiciona(new CarrinhoItem(produto));
		BigDecimal totalCarrinho = carrinho.getTotalCarrinho();
		model.addAttribute("itens", carrinho.getItensMap());
		model.addAttribute("total", totalCarrinho);
		return "carrinho/itens";
	}

}
