package br.edu.ulbra.artigoCientifico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ulbra.artigoCientifico.model.User;
import br.edu.ulbra.artigoCientifico.repository.RoleRepository;
import br.edu.ulbra.artigoCientifico.repository.UserRepository;
import br.edu.ulbra.artigoCientifico.service.interfaces.UserService;

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
