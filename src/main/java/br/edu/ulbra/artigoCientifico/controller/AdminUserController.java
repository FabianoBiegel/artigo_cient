package br.edu.ulbra.artigoCientifico.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ulbra.artigoCientifico.config.RedirectConstants;
import br.edu.ulbra.artigoCientifico.config.StringConstants;
import br.edu.ulbra.artigoCientifico.input.UserInput;
import br.edu.ulbra.artigoCientifico.model.Role;
import br.edu.ulbra.artigoCientifico.model.User;
import br.edu.ulbra.artigoCientifico.repository.RoleRepository;
import br.edu.ulbra.artigoCientifico.repository.UserRepository;
import br.edu.ulbra.artigoCientifico.service.interfaces.SecurityService;
import br.edu.ulbra.artigoCientifico.service.interfaces.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/usuarios")
public class AdminUserController {
	@Autowired
	SecurityService securityService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;

	private ModelMapper mapper = new ModelMapper();

	@RequestMapping()
	public ModelAndView listaUsuarios() {
		ModelAndView mv = new ModelAndView("usuario/lista");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		mv.addObject(StringConstants.ADMIN, true);
		List<User> usuarios = (List<User>) userRepository.findAll();
		mv.addObject("users", usuarios);
		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novoUsuarioForm(@ModelAttribute("user") UserInput user){
		List<Role> roles = (List<Role>)roleRepository.findAll();
		ModelAndView mv = new ModelAndView("usuario/novo");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());
		mv.addObject(StringConstants.ADMIN, true);
		mv.addObject("roles", roles);
		mv.addObject("user", user);
		return mv;
	}

	@PostMapping("/novo")
	public String novoUsuario(UserInput userInput, RedirectAttributes redirectAttrs){
		User usuario = userRepository.findByUsername(userInput.getUsername());

		if (usuario != null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Um usuário com esse email já está cadastrado.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_USUARIO_NOVO;
		}

		if (userInput.getPassword().length() == 0) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Uma senha deve ser informada.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_USUARIO_NOVO;
		}

		if (!userInput.getPassword().equals(userInput.getPasswordConfirm())) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Senha e confirmação de senha não são iguais.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_USUARIO_NOVO;
		}

		Role role = roleRepository.findByName("ROLE_USER");
		User user = mapper.map(userInput, User.class);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		userService.save(user);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Usuário cadastrado com sucesso.");
		return RedirectConstants.REDIRECT_USUARIO;
	}

	@GetMapping("/{id}")
	public ModelAndView detalheUsuario(@PathVariable("id") Long idUsuario, RedirectAttributes redirectAttrs){
		User usuario = userRepository.findById(idUsuario).get();

		if (usuario == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "O usuário solicitado não existe.");
			return new ModelAndView(RedirectConstants.REDIRECT_USUARIO);
		}

		UserInput userInput = mapper.map(usuario, UserInput.class);

		Set<Role> userRoles = usuario.getRoles();
		if (!userRoles.isEmpty()){
			userInput.setIdRole(userRoles.iterator().next().getId());
		}

		List<Role> roles = (List<Role>)roleRepository.findAll();

		ModelAndView mv = new ModelAndView("usuario/detalhe");
		mv.addObject(StringConstants.USER_LOGGED, securityService.findLoggedInUser());

		mv.addObject(StringConstants.ADMIN, true);
		mv.addObject("roles", roles);
		mv.addObject("user", userInput);
		return mv;
	}

	@PostMapping("/{id}")
	public String salvarUsuario(@PathVariable("id") Long idUsuario, UserInput userInput, RedirectAttributes redirectAttrs) {
		User usuario = userRepository.findById(idUsuario).get();

		if (usuario == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Esse usuário não existe.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return "redirect:/usuarios/" + idUsuario;
		}

		User usuarioTest = userRepository.findByUsername(userInput.getUsername());
		if (usuarioTest != null && !usuario.getUsername().equals(usuarioTest.getUsername())) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Um usuário com esse email já está cadastrado.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_USUARIO + idUsuario;
		}

		if (userInput.getPassword().length() != 0 && !userInput.getPassword().equals(userInput.getPasswordConfirm())) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Senha e confirmação de senha não são iguais.");
			redirectAttrs.addFlashAttribute("user", userInput);
			return RedirectConstants.REDIRECT_USUARIO + idUsuario;
		}

		usuario.setName(userInput.getName());
		usuario.setUsername(userInput.getUsername());
		if (userInput.getPassword().length() != 0) {
			usuario.setPassword(userInput.getPassword());
		}

		userService.save(usuario);

		redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Usuário alterado com sucesso.");
		redirectAttrs.addFlashAttribute("user", userInput);
		return RedirectConstants.REDIRECT_USUARIO + idUsuario;
	}

	@PostMapping("/{id}/resetSenha")
	public String resetarSenhaUsuario(@PathVariable("id") Long idUsuario){
		return RedirectConstants.REDIRECT_USUARIO + idUsuario;
	}

	@RequestMapping("/{id}/delete")
	public String deletarUsuario(@PathVariable("id") Long idUsuario, RedirectAttributes redirectAttrs) {
		User usuario = userRepository.findById(idUsuario).get();
		if (usuario == null) {
			redirectAttrs.addFlashAttribute(StringConstants.ERROR, "Não existe uma usuário com essa identificação.");
		} else {
			userRepository.delete(usuario);
			redirectAttrs.addFlashAttribute(StringConstants.SUCCESS, "Usuário deletado com sucesso.");
		}

		return RedirectConstants.REDIRECT_USUARIO;
	}
}
