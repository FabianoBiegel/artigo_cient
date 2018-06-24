package br.edu.ulbra.artigoCientifico.service.interfaces;

import br.edu.ulbra.artigoCientifico.model.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);
}
