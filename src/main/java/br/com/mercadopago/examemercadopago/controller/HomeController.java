package br.com.mercadopago.examemercadopago.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mercadopago.exceptions.MPException;

import br.com.mercadopago.examemercadopago.model.Produto;
import br.com.mercadopago.examemercadopago.repository.ProdutoRepository;

@Controller
public class HomeController {
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ServletContext context;

	@Value("${server.servlet.context-path}")
	private String context2;

	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) throws MPException {
		Produto buscarProduto = produtoRepository.findByIdProduto(Long.valueOf(1234));
		System.out.println("bse url? ->" + context.getContextPath() + "base url2 " + context2);
		System.out.println(context);
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
	public String sucesso(HttpServletRequest request, @RequestParam("collection_id") String collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String externalReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, Model model) {
		// model.addAttribute("payment_type", paymentType);
		System.out.println("Sucesso sendo chamado...");
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String[] value = entry.getValue();
			String valorConvertido = String.join(",", value);
			model.addAttribute(entry.getKey(), valorConvertido);
			System.out.println(entry.getKey() + "/" + valorConvertido);
		}
		return "sucesso";
	}

	@GetMapping("/pendente")
	public String pendente(HttpServletRequest request, @RequestParam("collection_id") String collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String externalReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, Model model) {
		model.addAttribute("payment_type", paymentType);
		System.out.println("Pendente sendo chamado...");
		return "pendente";
	}

	@GetMapping("/falha")
	public String falha(HttpServletRequest request, @RequestParam("collection_id") String collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String externalReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, Model model) {
		model.addAttribute("payment_type", paymentType);
		System.out.println("Falha sendo chamado...");
		return "falha";
	}
}
