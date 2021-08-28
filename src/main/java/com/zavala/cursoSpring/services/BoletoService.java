package com.zavala.cursoSpring.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.zavala.cursoSpring.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);  //acrescenta 7 dias como data de vencimento
		pagto.setDataVencimento(cal.getTime());
	}
}
