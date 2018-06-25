package br.edu.ulbra.artigoCientifico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ulbra.artigoCientifico.model.Role;
import br.edu.ulbra.artigoCientifico.repository.UserRepository;
import br.edu.ulbra.artigoCientifico.service.interfaces.SecurityService;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {
	@Autowired
	SecurityService securityService;
	@Autowired
	UserRepository userRepository;

	@RequestMapping()
	public String index(){
		return "redirect:/admin/inicio";
	}

	@RequestMapping("/inicio")
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("admin/inicio");
		mv.addObject("userLogged", securityService.findLoggedInUser());

//		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
//			for(Role p : securityService.findLoggedInUser().getRoles()){
//				if (p.getName().equals("ROLE_ADMIN")) {
//					mv.addObject("admin", true);
//					break;
//				}
//				else {
//					mv.addObject("admin", true);
//				}
//			}
//		}
		mv.addObject("admin", true);
		return mv;
	}
}
