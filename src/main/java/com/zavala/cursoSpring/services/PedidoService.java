package com.zavala.cursoSpring.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zavala.cursoSpring.domain.ItemPedido;
import com.zavala.cursoSpring.domain.PagamentoComBoleto;
import com.zavala.cursoSpring.domain.Pedido;
import com.zavala.cursoSpring.domain.enums.EstadoPagamento;
import com.zavala.cursoSpring.repositories.ItemPedidoRepository;
import com.zavala.cursoSpring.repositories.PagamentoRepository;
import com.zavala.cursoSpring.repositories.PedidoRepository;
import com.zavala.cursoSpring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));  //Ramiro
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());  //para preencher a data de vencimento
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.00);
			//ip.setPreco(produtoRepository.findOne(ip.getProduto().getId()).getPreco());
			//Optional<Produto> p = produtoRepository.findById(ip.getProduto().getId());		//Ramiro
			ip.setProduto(produtoService.find(ip.getProduto().getId()));						//Ramiro
		    ip.setPreco(ip.getProduto().getPreco());
		    ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());  //Ramiro
		
		//System.out.println(obj);  						//Teste imprime pedido. Automaticamente chama ao ToString
		//emailService.sendOrderConfirmationEmail(obj);  	//envia e-mail texto plano
		emailService.sendOrderConfirmationHtmlEmail(obj);   // envia e-mail html
		
		return obj;
	}
}
