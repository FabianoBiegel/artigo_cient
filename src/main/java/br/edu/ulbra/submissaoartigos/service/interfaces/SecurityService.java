package br.edu.ulbra.submissaoartigos.service.interfaces;

import br.edu.ulbra.submissaoartigos.model.User;

public interface SecurityService {

	String findLoggedInUsername();

	User findLoggedInUser();

	void autologin(String username, String password);
}
