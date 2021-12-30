package br.com.mercadopago.examemercadopago;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercadopago.MercadoPago;

@SpringBootApplication
public class ExameMercadopagoApplication implements CommandLineRunner {
	@Value("${mercadopago_access_token}")
	private String mercadoPagoAccessToken;

	public static void main(String[] args) {
		SpringApplication.run(ExameMercadopagoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MercadoPago.SDK.setAccessToken("APP_USR-334491433003961-030821-12d7475807d694b645722c1946d5ce5a-725736327");
		MercadoPago.SDK.setIntegratorId("dev_24c65fb163bf11ea96500242ac130004");
	}

}
