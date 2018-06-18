package br.edu.ulbra.artigocientifico.service.interfaces;

import br.edu.ulbra.artigocientifico.model.User;

public interface SecurityService {

	String findLoggedInUsername();

	User findLoggedInUser();

	void autologin(String username, String password);
}
