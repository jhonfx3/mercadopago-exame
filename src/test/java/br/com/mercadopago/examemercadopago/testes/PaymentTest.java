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
import com.mercadopago.resources.datastructures.preference.BackUrls;

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
		Preference preference = Preference.findById("725736327-7ce17388-d040-4772-a262-95ec6a65302c");
		Assertions.assertEquals(idPagamentoEx, pagamento.getId());
		Assertions.assertEquals((float) 950.50, pagamento.getTransactionAmount());
		Assertions.assertEquals("https://mercadopago-exame.herokuapp.com/notificacao/webhook",
				pagamento.getNotificationUrl());

		// Dados do pagador
		Assertions.assertEquals("Lalo", preference.getPayer().getName());
		Assertions.assertEquals("Landa", preference.getPayer().getSurname());
		Assertions.assertEquals("test_user_92801501@testuser.com", pagamento.getPayer().getEmail());
		Assertions.assertEquals("55", preference.getPayer().getPhone().getAreaCode());
		Assertions.assertEquals("98529-8743", preference.getPayer().getPhone().getNumber());

		// Dados do endereço do pagador
		Assertions.assertEquals("Insurgentes Sur", preference.getPayer().getAddress().getStreetName());
		Assertions.assertEquals(1602, preference.getPayer().getAddress().getStreetNumber());
		Assertions.assertEquals("78134-190", preference.getPayer().getAddress().getZipCode());

		// Dados do item(produto)
		Assertions.assertEquals("1234", preference.getItems().get(0).getId());
		Assertions.assertEquals("J7 prime 2", preference.getItems().get(0).getTitle());
		Assertions.assertEquals("Celular de Tienda e-commerce", preference.getItems().get(0).getDescription());
		Assertions.assertEquals(
				"https://images.tcdn.com.br/img/img_prod/458206/celular_samsung_galaxy_j7_prime_2_sm_g611mt_oc1_6ghz_32gb_tv_tela5_5_13mp_pto_15922_1_20180821092813.jpg",
				preference.getItems().get(0).getPictureUrl());
		Assertions.assertEquals(1, preference.getItems().get(0).getQuantity());
		Assertions.assertEquals((float) 950.50, preference.getItems().get(0).getUnitPrice());
		Assertions.assertEquals("jcaferreira9@gmail.com", pagamento.getExternalReference());

		// Dados dos pagamentos excluidos e max parcelas
		Assertions.assertEquals("amex", preference.getPaymentMethods().getExcludedPaymentMethods().get(0).getId());
		Assertions.assertEquals(6, preference.getPaymentMethods().getInstallments());
		BackUrls backUrls = preference.getBackUrls();

		// Dados das back urls
		Assertions.assertEquals("https://mercadopago-exame.herokuapp.com/sucesso", backUrls.getSuccess());
		Assertions.assertEquals("https://mercadopago-exame.herokuapp.com/pendente", backUrls.getPending());
		Assertions.assertEquals("https://mercadopago-exame.herokuapp.com/falha", backUrls.getFailure());

	}
}
