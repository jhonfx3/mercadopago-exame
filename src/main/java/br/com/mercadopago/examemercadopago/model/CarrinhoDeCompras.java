package br.com.mercadopago.examemercadopago.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoDeCompras implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();

	public void adiciona(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);
	}

	public int getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		return itens.get(item);
	}

	public BigDecimal getTotalCarrinho() {
		BigDecimal total = BigDecimal.ZERO;

		for (CarrinhoItem item : itens.keySet()) {
			total = total.add(item.getTotalCarrinhoItem(getQuantidade(item)));
		}
		return total;
	}

	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}

	public Map<CarrinhoItem, Integer> getItensMap() {
		return this.itens;
	}

	public void limpaCarrinho() {
		this.itens.clear();
	}

}
