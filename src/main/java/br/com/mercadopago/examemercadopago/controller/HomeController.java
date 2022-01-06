package br.com.mercadopago.examemercadopago.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.Preference.AutoReturn;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.PaymentMethods;
import com.mercadopago.resources.datastructures.preference.Phone;

import br.com.mercadopago.examemercadopago.model.Produto;
import br.com.mercadopago.examemercadopago.repository.ProdutoRepository;

@Controller
public class HomeController {
	@Autowired
	private ProdutoRepository produtoRepository;


	@GetMapping("/")
	public String home(Model model) throws MPException {
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
