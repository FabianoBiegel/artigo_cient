package br.edu.ulbra.artigocientifico.controller;

import br.edu.ulbra.artigocientifico.config.RedirectConstants;
import br.edu.ulbra.artigocientifico.config.StringConstants;
import br.edu.ulbra.artigocientifico.input.SubInput;
import br.edu.ulbra.artigocientifico.model.Evento;
import br.edu.ulbra.artigocientifico.model.Role;
import br.edu.ulbra.artigocientifico.model.TipoVinho;
import br.edu.ulbra.artigocientifico.model.Sub;
import br.edu.ulbra.artigocientifico.repository.TipoVinhoRepository;
import br.edu.ulbra.artigocientifico.service.interfaces.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import br.edu.ulbra.artigocientifico.repository.SubRepository;
import br.edu.ulbra.artigocientifico.repository.EventoRepository;

@Controller
@RequestMapping("/admin/vinho")
public class AdminSubController {
	@Value("${gestao-vinhos.uploadFilePath}")
	private String uploadFilePath;
	@Autowired
	SubRepository subRepository;
	@Autowired
	SecurityService securityService;
	@Autowired
	TipoVinhoRepository tipoVinhoRepository;
	@Autowired
	EventoRepository eventoRepository;

	private ModelMapper mapper = new ModelMapper();

	@RequestMapping()
	public ModelAndView listaVinhos() {
		ModelAndView mv = new ModelAndView("admin/vinho/lista");
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
		List<Sub> subs = (List<Sub>) subRepository.findAll();
		mv.addObject("submissoes", subs);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novoVinhoForm(@ModelAttribute("submissao") SubInput wine){
		List<TipoVinho> tipos = (List<TipoVinho>)tipoVinhoRepository.findAll();

		ModelAndView mv = new ModelAndView("admin/submissao/novo");
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

		mv.addObject("submissao", wine);
//		mv.addObject("types", tipos);
		return mv;
	}

	@PostMapping("/novo")
	public String novoVinho(SubInput subInput, RedirectAttributes redirectAttrs) throws IOException {
		if (subInput.getUser().length() == 0 || subInput.getEvento().length() == 0 || 
                        subInput.getDataSubmissao()== null)
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar todos os campos.");
			redirectAttrs.addFlashAttribute("wine", subInput);
			return "redirect:/admin/vinho/novo";
		}

		Sub sub = mapper.map(subInput, Sub.class);

		//TipoVinho tipo = tipoVinhoRepository.findById(wineInput.getIdTipo()).get();
		//sub.setTipo(tipo);

		File folderPath = new File(uploadFilePath);
		folderPath.mkdirs();

		/*MultipartFile imagemFile = wineInput.getImagem();
		String fileName = UUID.randomUUID().toString() + "-" + imagemFile.getOriginalFilename();
		File file = new File(Paths.get(uploadFilePath, fileName).toString());
		if (!file.createNewFile()){
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Arquivo de upload nao pode ser criado.");
			redirectAttrs.addFlashAttribute("wine", wineInput);
			return "redirect:/admin/vinho/novo";
		}
		imagemFile.transferTo(file);*/
		//sub.setSubArq(fileName);

		subRepository.save(sub);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Vinho cadastrado com sucesso.");
		return RedirectConstants.REDIRECT_ADMIN_VINHO;
	}

	@GetMapping("/{id}")
	public ModelAndView detalheVinho(@PathVariable("id") Long idSubmissao, RedirectAttributes redirectAttrs){
		Sub sub = subRepository.findById(idSubmissao).get();

		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "A submissao solicitada não existe.");
			return new ModelAndView("redirect:/admin/submissao");
		}

		SubInput subInput = mapper.map(sub, SubInput.class);

		ModelAndView mv = new ModelAndView("admin/submissoes/detalhe");
		//List<TipoVinho> tipos = (List<TipoVinho>)tipoVinhoRepository.findAll();
		//mv.addObject("types", tipos);
		mv.addObject("eventos", (sub.getEventos().isEmpty() ? null : sub.getEventos()));
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

		mv.addObject("submissoes", subInput);
		return mv;
	}

	@PostMapping("/{id}")
	public String salvarSubmissao(@PathVariable("id") Long idSubmissao, SubInput subInput, RedirectAttributes redirectAttrs) throws IOException {
		Sub sub = subRepository.findById(idSubmissao).get();

		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Esse vinho não existe.");
			redirectAttrs.addFlashAttribute("user", subInput);
			return RedirectConstants.REDIRECT_ADMIN_VINHO + idSubmissao;
		}

		if (subInput.getEvento().length() == 0 || subInput.getTitulo().length() == 0 )
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar os campos de nome e vinícola.");
			redirectAttrs.addFlashAttribute("submissao", subInput);
			return RedirectConstants.REDIRECT_ADMIN_VINHO + idSubmissao;
		}

		/*if (subInput.getImagem() != null && !subInput.getImagem().isEmpty()) {
			MultipartFile imagemFile = subInput.getImagem();
			String fileName = UUID.randomUUID().toString() + "-" + imagemFile.getOriginalFilename();
			File file = new File(Paths.get(uploadFilePath, fileName).toString());
			if (!file.createNewFile()){
				redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Arquivo nao pode ser criado.");
				redirectAttrs.addFlashAttribute("user", subInput);
				return RedirectConstants.REDIRECT_ADMIN_VINHO + idSubmissao;
			}
			imagemFile.transferTo(file);
			sub.setNomeImagem(fileName);
		}*/

		//TipoVinho tipo = tipoVinhoRepository.findById(wineInput.getIdTipo()).get();
		//wine.setTipo(tipo);
                
		sub.setEvento(subInput.getEvento());
		sub.setTitulo(subInput.getTitulo());

		subRepository.save(sub);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Submissao alterado com sucesso.");

		return RedirectConstants.REDIRECT_ADMIN_VINHO + idSubmissao;
	}

	@RequestMapping("/{id}/delete")
	public String deletarVinho(@PathVariable("id") Long idSubmissao, RedirectAttributes redirectAttrs) {
		Sub sub = subRepository.findById(idSubmissao).get();
		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Não existe um vinho com essa identificação.");
		} else {
			subRepository.delete(sub);
			redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Vinho deletado com sucesso.");
		}

		return "redirect:/admin/submissao";
	}

	@RequestMapping("/{vid}/avaliacao/{id}/delete")
	public String deletarComentario(@PathVariable("vid") Long idSub, @PathVariable("id") Long idEventos, RedirectAttributes redirectAttrs) {
		Evento evento = eventoRepository.findById(idEventos).get();
		if (evento == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Não existe uma avaliação com essa identificação.");
		} else {
			eventoRepository.delete(evento);
			redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Avaliacao deletada com sucesso.");
		}

		return RedirectConstants.REDIRECT_ADMIN_VINHO + idSub;
	}
}
