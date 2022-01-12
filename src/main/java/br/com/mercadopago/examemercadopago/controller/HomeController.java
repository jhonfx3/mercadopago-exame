package br.com.mercadopago.examemercadopago.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mercadopago.exceptions.MPException;

import br.com.mercadopago.examemercadopago.model.Produto;
import br.com.mercadopago.examemercadopago.repository.ProdutoRepository;

@Controller
public class HomeController {
	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/")
	public String home(Model model) throws MPException {
		Produto buscarProduto = produtoRepository.findByIdProduto(Long.valueOf(1234));
		if (buscarProduto == null) {
			System.out.println("produto não encontrado, criando produto...");
			Produto produto = new Produto();
			produto.setId(Long.valueOf("1234"));
			produto.setNomeProduto("J7 prime 2");
			produto.setDescricaoProduto("Celular de Tienda e-commerce");
			produto.setUrlImagem(
					"https://images.tcdn.com.br/img/img_prod/458206/celular_samsung_galaxy_j7_prime_2_sm_g611mt_oc1_6ghz_32gb_tv_tela5_5_13mp_pto_15922_1_20180821092813.jpg");
			produto.setPreco(new BigDecimal("950.50"));
			produtoRepository.save(produto);
		}
		List<Produto> produtos = produtoRepository.findAll();
		model.addAttribute("produtos", produtos);
		return "index";
	}

	@GetMapping("/sucesso")
	public String sucesso(HttpServletRequest request, Model model) {
		// model.addAttribute("payment_type", paymentType);
		System.out.println("Sucesso sendo chamado...");
		passarParametrosParaFrontEnd(request, model);
		return "sucesso";
	}

	@GetMapping("/pendente")
	public String pendente(HttpServletRequest request, Model model) {
		System.out.println("Pendente sendo chamado...");
		passarParametrosParaFrontEnd(request, model);

		return "pendente";
	}

	@GetMapping("/falha")
	public String falha(HttpServletRequest request, Model model) {
		System.out.println("Falha sendo chamado...");
		passarParametrosParaFrontEnd(request, model);
		return "falha";
	}

	private void passarParametrosParaFrontEnd(HttpServletRequest request, Model model) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String[] value = entry.getValue();
			String valorConvertido = String.join(",", value);
			model.addAttribute(entry.getKey(), valorConvertido);
			System.out.println(entry.getKey() + "/" + valorConvertido);
		}
	}
}
