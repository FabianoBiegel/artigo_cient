package br.edu.ulbra.submissaoartigos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ulbra.submissaoartigos.model.User;
import br.edu.ulbra.submissaoartigos.repository.RoleRepository;
import br.edu.ulbra.submissaoartigos.repository.UserRepository;
import br.edu.ulbra.submissaoartigos.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user){
		if (user.getUsername().length() != 0) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}


}
