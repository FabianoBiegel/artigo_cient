package br.edu.ulbra.submissaoartigos.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ulbra.submissaoartigos.config.RedirectConstants;
import br.edu.ulbra.submissaoartigos.config.StringConstants;
import br.edu.ulbra.submissaoartigos.input.EventoInput;
import br.edu.ulbra.submissaoartigos.model.Evento;
import br.edu.ulbra.submissaoartigos.model.Role;
import br.edu.ulbra.submissaoartigos.repository.EventoRepository;
import br.edu.ulbra.submissaoartigos.service.interfaces.SecurityService;

//import java.io.File;
import java.io.IOException;
//import java.nio.file.Paths;
import java.util.List;
//import java.util.UUID;

@Controller
@RequestMapping("/admin/evento")
public class AdminEventoController {
//	@Value("${gestao-vinhos.uploadFilePath}")
//	private String uploadFilePath;
	@Autowired
	EventoRepository eventoRepository;
	@Autowired
	SecurityService securityService;

	private ModelMapper mapper = new ModelMapper();

	@RequestMapping()
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("admin/evento/lista");
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

		mv.addObject(StringConstants.ADMIN, true);
		List<Evento> eventos = (List<Evento>) eventoRepository.findAll();
		mv.addObject("events", eventos);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novoVinhoForm(@ModelAttribute("event") EventoInput event){
		ModelAndView mv = new ModelAndView("admin/evento/novo");
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

		mv.addObject("event", event);
		return mv;
	}

	@PostMapping("/novo")
	public String novoVinho(EventoInput eventInput, RedirectAttributes redirectAttrs) throws IOException {
//		if (wineInput.getNome().length() == 0 || wineInput.getVinicola().length() == 0 || wineInput.getImagem() == null || (wineInput.getImagem() != null && wineInput.getImagem().isEmpty()))
		if (eventInput.getUsuarioResponsavel().length() == 0 || eventInput.getNomeEvento().length() == 0)
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar todos os campos.");
			redirectAttrs.addFlashAttribute("event", eventInput);
			return "redirect:/admin/evento/novo";
		}

		Evento event = mapper.map(eventInput, Evento.class);

//		File folderPath = new File(uploadFilePath);
//		folderPath.mkdirs();

//		MultipartFile imagemFile = wineInput.getImagem();
//		String fileName = UUID.randomUUID().toString() + "-" + imagemFile.getOriginalFilename();
//		File file = new File(Paths.get(uploadFilePath, fileName).toString());
//		if (!file.createNewFile()){
//			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Arquivo de upload nao pode ser criado.");
//			redirectAttrs.addFlashAttribute("wine", wineInput);
//			return "redirect:/admin/vinho/novo";
//		}
//		imagemFile.transferTo(file);
//		wine.setNomeImagem(fileName);

		eventoRepository.save(event);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Evento cadastrado com sucesso.");
		return RedirectConstants.REDIRECT_ADMIN_EVENTO;
	}

	@GetMapping("/{id}")
	public ModelAndView detalheEvento(@PathVariable("id") Long idEvento, RedirectAttributes redirectAttrs){
		Evento evento = eventoRepository.findById(idEvento).get();

		if (evento == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "O evento solicitado não existe.");
			return new ModelAndView("redirect:/admin/evento");
		}

		EventoInput eventoInput = mapper.map(evento, EventoInput.class);

		ModelAndView mv = new ModelAndView("admin/evento/detalhe");
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

		mv.addObject("event", eventoInput);
		return mv;
	}

	@PostMapping("/{id}")
	public String salvarEvento(@PathVariable("id") Long idEvento, EventoInput eventInput, RedirectAttributes redirectAttrs) throws IOException {
		Evento event = eventoRepository.findById(idEvento).get();

		if (event == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Esse evento não existe.");
			redirectAttrs.addFlashAttribute("user", eventInput);
			return RedirectConstants.REDIRECT_ADMIN_EVENTO + idEvento;
		}

		if (eventInput.getUsuarioResponsavel().length() == 0 || eventInput.getNomeEvento().length() == 0 )
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar os campos de usuarioResponsavel e nomeEvento.");
			redirectAttrs.addFlashAttribute("event", eventInput);
			return RedirectConstants.REDIRECT_ADMIN_EVENTO + idEvento;
		}

//		if (wineInput.getImagem() != null && !wineInput.getImagem().isEmpty()) {
//			MultipartFile imagemFile = wineInput.getImagem();
//			String fileName = UUID.randomUUID().toString() + "-" + imagemFile.getOriginalFilename();
//			File file = new File(Paths.get(uploadFilePath, fileName).toString());
//			if (!file.createNewFile()){
//				redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Arquivo nao pode ser criado.");
//				redirectAttrs.addFlashAttribute("user", wineInput);
//				return RedirectConstants.REDIRECT_ADMIN_EVENTO + idVinho;
//			}
//			imagemFile.transferTo(file);
//			wine.setNomeImagem(fileName);
//		}
//
//		wine.setNome(wineInput.getNome());
		event.setUsuarioResponsavel(eventInput.getUsuarioResponsavel());
		event.setNomeEvento(eventInput.getNomeEvento());

		eventoRepository.save(event);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Evento alterado com sucesso.");

		return RedirectConstants.REDIRECT_ADMIN_EVENTO + idEvento;
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
		return "redirect:/admin/evento";
	}
}
