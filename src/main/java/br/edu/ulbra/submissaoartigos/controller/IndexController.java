package br.edu.ulbra.submissaoartigos.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ulbra.submissaoartigos.config.StringConstants;
import br.edu.ulbra.submissaoartigos.model.Evento;
import br.edu.ulbra.submissaoartigos.model.Role;
import br.edu.ulbra.submissaoartigos.repository.EventoRepository;
import br.edu.ulbra.submissaoartigos.service.interfaces.SecurityService;

//import java.nio.file.Paths;
import java.util.List;

@Controller
public class IndexController {
//	@Value("${gestao-vinhos.uploadFilePath}")
//	private String uploadFilePath;
	@Autowired
	SecurityService securityService;
	@Autowired
	EventoRepository eventoRepository;

	@RequestMapping("/")
	public String index(){
		return "redirect:/inicio";
	}

	@RequestMapping("/inicio")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("userLogged", securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals("ROLE_ADMIN")) {
					mv.addObject(StringConstants.ADMIN, true);
					break;
				}
				else {
					mv.addObject(StringConstants.ADMIN, false);
				}
			}
		}

		List<Evento> eventos = (List<Evento>) eventoRepository.findAll();
		mv.addObject("wines", eventos);
		return mv;
	}

	@RequestMapping("/minhalista")
	public ModelAndView minhalista() {
		ModelAndView mv = new ModelAndView("lista");
		mv.addObject("userLogged", securityService.findLoggedInUser());

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals("ROLE_ADMIN")) {
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

//	@GetMapping("/images/{fileName:.+}")
//	@ResponseBody
//	public FileSystemResource getFile(@PathVariable("fileName") String fileName){
//		return new FileSystemResource(Paths.get(uploadFilePath, fileName).toString());
//	}

	@GetMapping("/login")
	public ModelAndView loginForm(){
		return new ModelAndView("login/login");
	}

	@GetMapping("/denied")
	public ModelAndView denied(){
		return new ModelAndView("denied");
	}
}
