package br.edu.ulbra.artigoCientifico.service.interfaces;

import br.edu.ulbra.artigoCientifico.model.User;

public interface SecurityService {

	String findLoggedInUsername();

	User findLoggedInUser();

	void autologin(String username, String password);
}
