package com.mro.stockcontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mro.stockcontrol.bo.ClienteBO;
import com.mro.stockcontrol.model.Cliente;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteBO ClienteBO;
	
	@RequestMapping(value = "/novo", method = RequestMethod.GET)
	public ModelAndView novo(ModelMap model) {
		Cliente cliente = new Cliente();
        cliente.setAtivo(true); // todos clientes cadastrados são ativos
        model.addAttribute("cliente", cliente);
		return new ModelAndView("/cliente/formulario", model);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String salva(@Valid @ModelAttribute Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "/cliente/formulario";
		}
		
		if (cliente.getId() == null) {
			ClienteBO.insere(cliente);
			attr.addFlashAttribute("feedback", "Cliente foi cadastrado com sucesso");
		} else {
			ClienteBO.atualiza(cliente);
			attr.addFlashAttribute("feedback", "Cliente foi atualizado com sucesso");
		}
		
		return "redirect:/clientes";
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView lista(ModelMap model) {
		model.addAttribute("clientes", ClienteBO.lista());
		return new ModelAndView("/cliente/lista", model);
	}
	
	@RequestMapping(value = "/edita/{id}", method = RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cliente", ClienteBO.pesquisaPeloId(id));
		return new ModelAndView("/cliente/formulario", model);
	}
	
	@RequestMapping(value = "/inativa/{id}", method = RequestMethod.GET)
	public String inativa(@PathVariable("id") Long id, RedirectAttributes attr) {
		Cliente cliente = ClienteBO.pesquisaPeloId(id);
		ClienteBO.inativa(cliente);
		attr.addFlashAttribute("feedback", "Cliente inativado com sucesso");
		return "redirect:/clientes";
	}
	
	@RequestMapping(value = "/ativa/{id}", method = RequestMethod.GET)
	public String ativa(@PathVariable("id") Long id, RedirectAttributes attr) {
		Cliente cliente = ClienteBO.pesquisaPeloId(id);
		ClienteBO.ativa(cliente);
		attr.addFlashAttribute("feedback", "Cliente ativado com sucesso");
		return "redirect:/clientes";
	}


}
