package br.edu.ulbra.artigocientifico.service.interfaces;

import br.edu.ulbra.artigocientifico.model.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);
}
