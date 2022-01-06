package br.com.mercadopago.examemercadopago.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping(value = "preferencia")
public class PreferenciaController {

	@RequestMapping(value = "/gerar", method = RequestMethod.POST)
	public ResponseEntity<String> gerarPreferencia(@RequestBody String teste) throws MPException {
		System.out.println("chamando ajax..!@" + teste);
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
		// Transformando a preferencia id em JSON
		String json = new Gson().toJson(preference.getId());
		return ResponseEntity.ok(json);
	}

}
