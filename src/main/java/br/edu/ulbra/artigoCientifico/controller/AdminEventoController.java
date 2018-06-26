package br.edu.ulbra.artigoCientifico.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ulbra.artigoCientifico.config.RedirectConstants;
import br.edu.ulbra.artigoCientifico.config.StringConstants;
import br.edu.ulbra.artigoCientifico.input.EventoInput;
import br.edu.ulbra.artigoCientifico.model.Evento;
import br.edu.ulbra.artigoCientifico.model.Role;
import br.edu.ulbra.artigoCientifico.repository.EventoRepository;
import br.edu.ulbra.artigoCientifico.service.interfaces.SecurityService;

//import java.io.File;
import java.io.IOException;
//import java.nio.file.Paths;
import java.util.List;
//import java.util.UUID;

@Controller
@RequestMapping("/eventos")
public class AdminEventoController {
	@Autowired
	EventoRepository eventoRepository;
	@Autowired
	SecurityService securityService;

	private ModelMapper mapper = new ModelMapper();

	@RequestMapping()
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("evento/lista");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());
		mv.addObject(StringConstants.ADMIN, true);
		List<Evento> eventos = (List<Evento>) eventoRepository.findAll();
		mv.addObject("events", eventos);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novoVinhoForm(@ModelAttribute("event") EventoInput event){
		ModelAndView mv = new ModelAndView("evento/novo");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());
		mv.addObject(StringConstants.ADMIN, true);
		mv.addObject("event", event);
		return mv;
	}

	@PostMapping("/novo")
	public String novoVinho(EventoInput eventInput, RedirectAttributes redirectAttrs) throws IOException {
		if (eventInput.getUsuarioResponsavel().length() == 0 || eventInput.getNomeEvento().length() == 0)
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar todos os campos.");
			redirectAttrs.addFlashAttribute("event", eventInput);
			return "redirect:/eventos/novo";
		}

		Evento event = mapper.map(eventInput, Evento.class);
		eventoRepository.save(event);
		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Evento cadastrado com sucesso.");
		return RedirectConstants.REDIRECT_EVENTO;
	}

	@GetMapping("/{id}")
	public ModelAndView detalheEvento(@PathVariable("id") Long idEvento, RedirectAttributes redirectAttrs){
		Evento evento = eventoRepository.findById(idEvento).get();

		if (evento == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "O evento solicitado não existe.");
			return new ModelAndView("redirect:/eventos");
		}

		EventoInput eventoInput = mapper.map(evento, EventoInput.class);

		ModelAndView mv = new ModelAndView("evento/detalhe");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());
		mv.addObject(StringConstants.ADMIN, true);
		mv.addObject("event", eventoInput);
		return mv;
	}

	@PostMapping("/{id}")
	public String salvarEvento(@PathVariable("id") Long idEvento, EventoInput eventInput, RedirectAttributes redirectAttrs) throws IOException {
		Evento event = eventoRepository.findById(idEvento).get();

		if (event == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Esse evento não existe.");
			redirectAttrs.addFlashAttribute("user", eventInput);
			return RedirectConstants.REDIRECT_EVENTO + idEvento;
		}

		if (eventInput.getUsuarioResponsavel().length() == 0 || eventInput.getNomeEvento().length() == 0 )
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar os campos de usuarioResponsavel e nomeEvento.");
			redirectAttrs.addFlashAttribute("event", eventInput);
			return RedirectConstants.REDIRECT_EVENTO + idEvento;
		}

		event.setUsuarioResponsavel(eventInput.getUsuarioResponsavel());
		event.setNomeEvento(eventInput.getNomeEvento());
		eventoRepository.save(event);
		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Evento alterado com sucesso.");
		return RedirectConstants.REDIRECT_EVENTO + idEvento;
	}

	@RequestMapping("/{id}/delete")
	public String deletarEvento(@PathVariable("id") Long idEvento, RedirectAttributes redirectAttrs) {
		Evento evento = eventoRepository.findById(idEvento).get();
		if (evento == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Não existe um evento com essa identificação.");
		} else {
			eventoRepository.delete(evento);
			redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Evento deletado com sucesso.");
		}
		return "redirect:/eventos";
	}
}
