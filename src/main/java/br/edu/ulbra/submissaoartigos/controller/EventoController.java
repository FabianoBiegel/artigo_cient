package br.edu.ulbra.submissaoartigos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ulbra.submissaoartigos.config.RedirectConstants;
import br.edu.ulbra.submissaoartigos.config.StringConstants;
import br.edu.ulbra.submissaoartigos.model.Evento;
import br.edu.ulbra.submissaoartigos.model.Role;
import br.edu.ulbra.submissaoartigos.repository.EventoRepository;
import br.edu.ulbra.submissaoartigos.service.interfaces.SecurityService;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	@Autowired
	SecurityService securityService;
	@Autowired
	EventoRepository eventoRepository;

	@RequestMapping("/minhalista")
	public ModelAndView minhaLista() {
		ModelAndView mv = new ModelAndView("eventos/listarEventos");
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
		return mv;
	}

	@RequestMapping("/evento/{id}")
	public ModelAndView detalhe(@PathVariable("id") Long idEvento, RedirectAttributes redirectAttrs) {
		Evento evento = eventoRepository.findById(idEvento).get();

		if (evento == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "O evento solicitado não existe.");
			return new ModelAndView(RedirectConstants.REDIRECT_INICIO);
		}

		ModelAndView mv = new ModelAndView("eventos/detalhe");
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
		mv.addObject("event", evento);
		return mv;
	}
}
