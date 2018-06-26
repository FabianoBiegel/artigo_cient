package br.edu.ulbra.artigoCientifico.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ulbra.artigoCientifico.config.StringConstants;
import br.edu.ulbra.artigoCientifico.model.Evento;
import br.edu.ulbra.artigoCientifico.model.Role;
import br.edu.ulbra.artigoCientifico.repository.EventoRepository;
import br.edu.ulbra.artigoCientifico.service.interfaces.SecurityService;

//import java.nio.file.Paths;
import java.util.List;

@Controller
public class IndexController {
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
		mv.addObject(StringConstants.ADMIN, true);
		List<Evento> eventos = (List<Evento>) eventoRepository.findAll();
		mv.addObject("wines", eventos);
		return mv;
	}

	@RequestMapping("/minhalista")
	public ModelAndView minhalista() {
		ModelAndView mv = new ModelAndView("lista");
		mv.addObject("userLogged", securityService.findLoggedInUser());
		mv.addObject(StringConstants.ADMIN, true);
		return mv;
	}

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
