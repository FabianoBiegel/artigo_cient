package br.edu.ulbra.artigocientifico.controller;

import br.edu.ulbra.artigocientifico.model.Role;
import br.edu.ulbra.artigocientifico.repository.UserRepository;
import br.edu.ulbra.artigocientifico.service.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

		if (securityService.findLoggedInUser() != null && securityService.findLoggedInUser().getRoles() != null) {
			for(Role p : securityService.findLoggedInUser().getRoles()){
				if (p.getName().equals("ROLE_ADMIN")) {
					mv.addObject("admin", true);
					break;
				}
				else {
					mv.addObject("admin", false);
				}
			}
		}

		return mv;
	}
}
