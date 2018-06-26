package br.edu.ulbra.artigoCientifico.controller;

import br.edu.ulbra.artigoCientifico.config.RedirectConstants;
import br.edu.ulbra.artigoCientifico.config.StringConstants;
import br.edu.ulbra.artigoCientifico.input.SubmissaoInput;
import br.edu.ulbra.artigoCientifico.model.Evento;
import br.edu.ulbra.artigoCientifico.model.Role;
import br.edu.ulbra.artigoCientifico.model.Submissao;
import br.edu.ulbra.artigoCientifico.repository.EventoRepository;
import br.edu.ulbra.artigoCientifico.service.interfaces.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import br.edu.ulbra.artigoCientifico.repository.SubmissaoRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.UUID;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/submissoes")
public class SubmissaoController {
	@Value("${artigoCientifico.uploadFilePath}")
	private String uploadFilePath;
	@Autowired
	SubmissaoRepository submissaoRepository;
	@Autowired
	SecurityService securityService;
        @Autowired
	EventoRepository eventoRepository;

	private ModelMapper mapper = new ModelMapper();

	@RequestMapping()
	public ModelAndView listaSubmissoes() {
                //Evento event = eventoRepository.findById(2).get();
            
		ModelAndView mv = new ModelAndView("submissao/lista");
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
		List<Submissao> subs = (List<Submissao>) submissaoRepository.findAll();
		mv.addObject("subs", subs);                
                
                List<Evento> event = (List<Evento>) eventoRepository.findAll();                                
                mv.addObject("events", event);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novaSubmissaoForm(@ModelAttribute("sub") SubmissaoInput sub){
		ModelAndView mv = new ModelAndView("submissao/novo");
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

		mv.addObject("sub", sub);
                
                List<Evento> events = (List<Evento>) eventoRepository.findAll();
		mv.addObject("events", events);
		return mv;
	}

	@PostMapping("/novo")
	public String novoSubmissao(SubmissaoInput subInput, RedirectAttributes redirectAttrs) throws IOException {
//		if (wineInput.getNome().length() == 0 || wineInput.getVinicola().length() == 0 || wineInput.getImagem() == null || (wineInput.getImagem() != null && wineInput.getImagem().isEmpty()))
		/*if (subInput.getUsuarioResponsavel().length() == 0 || subInput.getNomeEvento().length() == 0)
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar todos os campos.");
			redirectAttrs.addFlashAttribute("sub", subInput);
			return "redirect:/admin/evento/novo";
		}*/

		Submissao sub = mapper.map(subInput, Submissao.class);
            

		/*File folderPath = new File(uploadFilePath);
		folderPath.mkdirs();

		MultipartFile arquivoFile = (MultipartFile) subInput.getSubArq();
		String fileName = UUID.randomUUID().toString() + "-" + arquivoFile.getOriginalFilename();
		File file = new File(Paths.get(uploadFilePath, fileName).toString());
		if (!file.createNewFile()){
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Arquivo de upload nao pode ser criado.");
			redirectAttrs.addFlashAttribute("sub", subInput);
			return "redirect:/submissao/novo";
		}
		arquivoFile.transferTo(file);*/
		//sub.setSubArq(fileName);

		submissaoRepository.save(sub);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Submissão cadastrada com sucesso.");
		return RedirectConstants.REDIRECT_SUBMISSOES;
	}

	@GetMapping("/{id}")
	public ModelAndView detalheSubmissao(@PathVariable("id") Long idSub, RedirectAttributes redirectAttrs){
		Submissao sub = submissaoRepository.findById(idSub).get();

		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "A submissao solicitada não existe.");
			return new ModelAndView(RedirectConstants.REDIRECT_SUBMISSOES);
		}

		SubmissaoInput subInput = mapper.map(sub, SubmissaoInput.class);

		ModelAndView mv = new ModelAndView("submissao/detalhe");
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
                
                List<Evento> events = (List<Evento>) eventoRepository.findAll();
		mv.addObject("events", events);

		mv.addObject("sub", sub);
		return mv;
	}

	@PostMapping("/{id}")
	public String salvarEvento(@PathVariable("id") Long idEvento, SubmissaoInput subInput, RedirectAttributes redirectAttrs) throws IOException, ParseException {
		Submissao sub = submissaoRepository.findById(idEvento).get();

		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Esse evento não existe.");
			redirectAttrs.addFlashAttribute("user", subInput);
			return RedirectConstants.REDIRECT_EVENTO + idEvento;
		}

		/*if (subInput.getUsuarioResponsavel().length() == 0 || subInput.getNomeEvento().length() == 0 )
		{
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Você precisa informar os campos de usuarioResponsavel e nomeEvento.");
			redirectAttrs.addFlashAttribute("event", eventInput);
			return RedirectConstants.REDIRECT_EVENTO + idEvento;
		}*/
		
		sub.setTitulo(subInput.getTitulo());
                sub.setResumo(subInput.getResumo());
                sub.setUser(subInput.getUser());
                                
                sub.setDataSubmissao(subInput.getDataSubmissao());
                
                sub.setSubArq(subInput.getSubArq());
                
                sub.setEventos(subInput.getEventos());

		submissaoRepository.save(sub);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Submissao alterada com sucesso.");

		return RedirectConstants.REDIRECT_SUBMISSOES;// + idEvento;
	}

	@RequestMapping("/{id}/delete")
	public String deletarSubmissao(@PathVariable("id") Long idSub, RedirectAttributes redirectAttrs) {
		Submissao sub = submissaoRepository.findById(idSub).get();
		if (sub == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Não existe uma sumissao com essa identificação.");
		} else {
			submissaoRepository.delete(sub);
			redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Submissao deletado com sucesso.");
		}
		return RedirectConstants.REDIRECT_SUBMISSOES;
	}
}
