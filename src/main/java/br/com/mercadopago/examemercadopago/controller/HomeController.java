package br.com.mercadopago.examemercadopago.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.Phone;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home(Model model) throws MPException {
		// Cria um objeto de preferência
		Preference preference = new Preference();

		// Cria um item na preferência
		Item item = new Item();
		item.setTitle("j7 prime 2").setQuantity(1).setUnitPrice((float) 75.56)
				.setDescription("Celular de Tienda e-commerce")
				.setPictureUrl("https://upconsertos.com.br/wp-content/uploads/2020/06/Troca-de-Tela-J7-Prime-2-1.jpg");
		ArrayList<Item> itens = new ArrayList<>();
		itens.add(item);
		preference.setItems(itens);
		BackUrls backUrls = new BackUrls("http://localhost:8080/sucesso", "http://localhost:8080/pendente",
				"http://localhost:8080/falha");
		preference.setBackUrls(backUrls);
		// Instanciando o pagador, endereco e telefone
		Payer pagador = new Payer();
		Address endereco = new Address();
		Phone telefone = new Phone();

		pagador.setName("Lalo Landa");
		pagador.setEmail("test_user_92801501@testuser.com");

		telefone.setNumber("98529-8743");
		telefone.setAreaCode("55");

		endereco.setStreetName("Insurgentes Sur");
		endereco.setStreetNumber(1602);
		endereco.setZipCode("8134-190");

		pagador.setPhone(telefone);
		pagador.setAddress(endereco);
		preference.setExternalReference("jcaferreira9@gmail.com");
		preference.setPayer(pagador);
		
		Preference save = preference.save();
		String id = preference.getId();
		model.addAttribute("id", id);
		return "redirect:" + save.getSandboxInitPoint();

	}

	@GetMapping("/sucesso")
	public String sucesso(HttpServletRequest request, @RequestParam("collection_id") String collectionId,
			@RequestParam("collection_status") String collectionStatus,
			@RequestParam("external_reference") String externalReference,
			@RequestParam("payment_type") String paymentType, @RequestParam("merchant_order_id") String merchantOrderId,
			@RequestParam("preference_id") String preferenceId, @RequestParam("site_id") String siteId,
			@RequestParam("processing_mode") String processingMode,
			@RequestParam("merchant_account_id") String merchantAccountId, Model model) {
		model.addAttribute("payment_type", paymentType);
		System.out.println("Sucesso sendo chamado...");
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (String nomeParameter : parameterMap.keySet()) {
			System.err.println(nomeParameter);
		}
		for (String[] nomeParameter : parameterMap.values()) {
			// System.out.println(nomeParameter);
			int length = nomeParameter.length;
			for (int i = 0; i < length; i++) {
				System.out.println(nomeParameter[i]);
			}
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
		System.out.println("Sucesso sendo chamado...");
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
