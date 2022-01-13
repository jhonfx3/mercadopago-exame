package br.com.mercadopago.examemercadopago.testes;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;

@RunWith(SpringRunner.class)
public class PaymentTest {

	@Before
	public void before() throws MPConfException {
		System.out.println("chamando...");
	}

	@After
	public void after() {
		System.out.println("after");
	}

	@Test
	public void verificaSeOsDadosDoPagamentoEstaoCorretos() throws MPException {
		MercadoPago.SDK.setAccessToken("APP_USR-334491433003961-030821-12d7475807d694b645722c1946d5ce5a-725736327");
		MercadoPago.SDK.setIntegratorId("dev_24c65fb163bf11ea96500242ac130004");
		String idPagamentoEx = "19487538090";

		Payment pagamento = Payment.findById(idPagamentoEx);
		Preference preference = Preference.findById("725736327-02ccbf2e-88a3-438a-a9c9-8dd3b08de9a9");
		Assertions.assertEquals(idPagamentoEx, pagamento.getId());
		Assertions.assertEquals("jcaferreira9@gmail.com", pagamento.getExternalReference());
		Assertions.assertEquals((float) 950.50, pagamento.getTransactionAmount());
		Assertions.assertEquals("Lalo", preference.getPayer().getName());
		Assertions.assertEquals("Landa", preference.getPayer().getSurname());
		Assertions.assertEquals("test_user_92801501@testuser.com", pagamento.getPayer().getEmail());
		Assertions.assertEquals("https://mercadopago-exame.herokuapp.com/notificacao/webhook",
				pagamento.getNotificationUrl());
		Assertions.assertEquals("Insurgentes Sur", preference.getPayer().getAddress().getStreetName());
	}
}
