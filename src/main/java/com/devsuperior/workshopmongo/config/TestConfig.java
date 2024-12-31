package com.devsuperior.workshopmongo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.devsuperior.workshopmongo.entities.User;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

@Configuration
@Profile("test") // para rodar apenas no perfil de teste
public class TestConfig {
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private PostRepository postRepository;

	@PostConstruct // forca o spring a criar o que foi determinado abaixo
	public void init() {
		userRepository.deleteAll();
		
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));
	}

}
