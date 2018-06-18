package br.edu.ulbra.artigocientifico.controller;

import br.edu.ulbra.artigocientifico.config.RedirectConstants;
import br.edu.ulbra.artigocientifico.config.StringConstants;
import br.edu.ulbra.artigocientifico.input.EventoInput;
import br.edu.ulbra.artigocientifico.model.Evento;
import br.edu.ulbra.artigocientifico.model.Role;
import br.edu.ulbra.artigocientifico.model.Sub;
import br.edu.ulbra.artigocientifico.service.interfaces.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import br.edu.ulbra.artigocientifico.repository.SubRepository;
import br.edu.ulbra.artigocientifico.repository.EventoRepository;

@Controller
@RequestMapping("/sub")
public class SubController {
	@Autowired
	SecurityService securityService;
	@Autowired
	EventoRepository eventoRepository;
	@Autowired
	SubRepository subRepository;

	private ModelMapper mapper = new ModelMapper();

	@RequestMapping("/minhalista")
	public ModelAndView minhaLista() {
		ModelAndView mv = new ModelAndView("sub/submissoes");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals(StringConstants.ROLE_ADMIN)) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		List<Evento> avaliations = eventoRepository.findByUser(securityService.findLoggedInUser());
		mv.addObject("avaliations", avaliations);
		return mv;
	}

	@RequestMapping("/submissoes/{id}")
	public ModelAndView detalhe(@PathVariable("id") Long idSubmissoes, RedirectAttributes redirectAttrs) {
		Sub sub = subRepository.findById(idSubmissoes).get();

		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "A submissao solicitado não existe.");
			return new ModelAndView(RedirectConstants.REDIRECT_INICIO);
		}

		ModelAndView mv = new ModelAndView("submissoes/detalhe");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals(StringConstants.ROLE_ADMIN)) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		mv.addObject("eventos", (sub.getEventos().isEmpty() ? null : sub.getEventos()));
		mv.addObject("submissao", sub);
		return mv;
	}

	@GetMapping("/submissoes/{id}/eventos")
	public ModelAndView avaliarForm(@PathVariable("id") Long idSubmissoes, RedirectAttributes redirectAttrs){
		Sub sub = subRepository.findById(idSubmissoes).get();

		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, StringConstants.ERROR_VINHO_NAO_EXISTE);
			return new ModelAndView(RedirectConstants.REDIRECT_INICIO);
		}

		ModelAndView mv = new ModelAndView("submissoes/eventos");
		Evento evento = eventoRepository.findByUserAndVinho(securityService.findLoggedInUser(), sub);
		EventoInput eventoInput = mapper.map((evento == null ? new Evento() : evento), EventoInput.class);
		mv.addObject("eventos", eventoInput);
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals(StringConstants.ROLE_ADMIN)) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		mv.addObject("date", new Date());
		mv.addObject("wine", sub);
		return mv;
	}

	@PostMapping("/submissoes/{id}/eventos")
	public String enviarAvaliacao(@PathVariable("id") Long idSubmissoes, EventoInput eventoInput, RedirectAttributes redirectAttrs){
		Sub sub = subRepository.findById(idSubmissoes).get();
		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, StringConstants.ERROR_VINHO_NAO_EXISTE);
			return RedirectConstants.REDIRECT_INICIO;
		}

		Evento evento = eventoRepository.findByUserAndVinho(securityService.findLoggedInUser(), sub);
		if (evento == null) {
			evento = new Evento();
			evento.setDataEvento(new Date());
                        evento.setDataComeca(eventoInput.getDataComeca());
                        evento.setDataTermina(eventoInput.getDataTermina());                                                                  
			evento.setNome(eventoInput.getNome());			
			evento.setUser(securityService.findLoggedInUser());
			evento.setSub(sub);
		} else {
			evento.setDataComeca(eventoInput.getDataComeca());
                        evento.setDataTermina(eventoInput.getDataTermina());                                                                  
			evento.setNome(eventoInput.getNome());		
		}
		eventoRepository.save(evento);

		redirectAttrs.addFlashAttribute("success", "Evento enviada com sucesso.");

		return "redirect:/submissoes/submissao/" + idSubmissoes;
	}
}
