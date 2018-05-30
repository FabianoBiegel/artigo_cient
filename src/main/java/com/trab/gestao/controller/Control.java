package com.trab.gestao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Control {
	
	@GetMapping("/index")
	public String lista() {
		return "Index";

		}
	
	@GetMapping("/eventos")
	public String listar() {
		return "Lista";

		}
	@GetMapping("/evento/userId")
	public String listar2() { 
		
		return "Lista2";
	}
	
	@GetMapping("/evento/userId/delete")
	public String listar3() { 
		
		return "Lista3";
	}
	
	@GetMapping("/usuario/cadastro")
	public String listar4() { 
		
		return "Lista4";
	}
	
	@GetMapping("/submissoes")
	public String listar5() { 
	
		return "Lista5";
	}
	
	@GetMapping("/submissoes/userId")
	public String listar6() { 
		
		return "Lista6";
	}
	
	@GetMapping("/submissoes/userId/delete")
	public String listar7() { 
	
		return "Lista7";
	}
	
	@GetMapping("/submissoes/evento/idEvento")
	public String listar8() { 
		
		return "Lista8";
	}
}
	


