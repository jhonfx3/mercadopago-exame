package br.com.mercadopago.examemercadopago.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
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

@RestController
@RequestMapping(value = "preferencia")
public class PreferenciaController {

	@RequestMapping(value = "/gerar", method = RequestMethod.POST)
	public ResponseEntity<String> gerarPreferencia(HttpServletRequest request, @RequestBody String json)
			throws MPException {
		String urlBase = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
		System.out.println(urlBase);

		Produto[] produtosJson = new Gson().fromJson(json, Produto[].class);
		List<Produto> produtos = Arrays.asList(produtosJson);

		// Criando uma lista de itens
		ArrayList<Item> itens = new ArrayList<Item>();
		// Cria um objeto de preferência
		Preference preference = new Preference();
		// Cria um item na preferência

		for (Produto produto : produtos) {
			Item item = new Item();
			item.setId(String.valueOf(produto.getId())).setTitle(produto.getNomeProduto()).setQuantity(1)
					.setUnitPrice(produto.getPreco().floatValue()).setDescription(produto.getDescricaoProduto())
					.setPictureUrl(produto.getUrlImagem());
			itens.add(item);
		}

		Payer pagador = new Payer();
		Address endereco = new Address();
		Phone telefone = new Phone();

		pagador.setName("Lalo");
		pagador.setSurname("Landa");
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
		preference.setItems(itens);
		BackUrls backUrls = new BackUrls(urlBase + "/sucesso", urlBase + "/pendente", urlBase + "/falha");
		preference.setBackUrls(backUrls);

		PaymentMethods paymentMethods = new PaymentMethods();
		paymentMethods.setInstallments(6);
		paymentMethods.setExcludedPaymentMethods("amex");

		preference.setPaymentMethods(paymentMethods);
		preference.setNotificationUrl("https://mercadopago-exame.herokuapp.com/notificacao/webhook");
		preference.setAutoReturn(AutoReturn.approved);
		preference.save();
		System.out.println("preference id -> " + preference.getId());
		System.out.println("auto return? -> " + preference.getAutoReturn());
		// Transformando a preferencia id em JSON
		json = new Gson().toJson(preference.getId());
		return ResponseEntity.ok(json);
	}

}
