package com.mro.stockcontrol.bo;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mro.stockcontrol.model.Cliente;
import com.mro.stockcontrol.model.Sexo;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class ClienteBOTeste {
	
	@Autowired
	private ClienteBO bo;
	
	@Test
	@Order(1)
	public void insere() {
		Cliente cliente =  new Cliente();
		cliente.setNome("Marlon Ricardo de Oliveria");
		cliente.setCpf("01234567890");
		cliente.setDataDeNascimento(LocalDate.of(2000, 1, 8));
		cliente.setSexo(Sexo.MASCULINO);
		cliente.setTelefone("0123456789");
		cliente.setCelular("01234567890");
		cliente.setAtivo(true);
		cliente.setEmail("marlon@gmail.com");
		bo.insere(cliente);
	}
	@Test
	@Order(2)
	public void pesquisaPeloId() {
		Cliente cliente = bo.pesquisaPeloId(1L);
		System.out.println(cliente);
	}
	@Test
	@Order(3)
	public void atualiza() {
		Cliente cliente = bo.pesquisaPeloId(1L);
		cliente.setCpf("11111111111");
		bo.atualiza(cliente);
	}

}
