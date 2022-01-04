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
	public String checkoutPro(Model model) throws MPException {
		List<Produto> produtos = produtoRepository.findAll();
		model.addAttribute("produtos", produtos);
		// Cria um objeto de preferência
		Preference preference = new Preference();

		// Cria um item na preferência
		Item item = new Item();
		item.setTitle("Meu produto").setQuantity(1).setUnitPrice((float) 75.56);
		Payer pagador = new Payer();
		Address endereco = new Address();
		Phone telefone = new Phone();

		pagador.setName("Lalo Landa");
		pagador.setEmail("test_user_92801501@testuser.com");

		telefone.setAreaCode("55");
		telefone.setNumber("98529-8743");

		endereco.setStreetName("Insurgentes Sur");
		endereco.setStreetNumber(1602);
		endereco.setZipCode("78134-190");
		pagador.setAddress(endereco);
		pagador.setPhone(telefone);
		preference.setPayer(pagador);
		preference.setExternalReference("jcaferreira9@gmail.com");
		preference.setAutoReturn(AutoReturn.approved);
		preference.appendItem(item);
		BackUrls backUrls = new BackUrls("http://localhost:8080/sucesso", "http://localhost:8080/pendente",
				"http://localhost:8080/falha");
		preference.setBackUrls(backUrls);

		PaymentMethods paymentMethods = new PaymentMethods();
		paymentMethods.setInstallments(6);
		paymentMethods.setExcludedPaymentMethods("amex");

		preference.setPaymentMethods(paymentMethods);
		preference.save();
		System.out.println("preference id -> " + preference.getId());
		model.addAttribute("preferencia", preference);
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
