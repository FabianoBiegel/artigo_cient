package br.edu.ulbra.submissaoartigos.service.interfaces;

import br.edu.ulbra.submissaoartigos.model.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);
}
