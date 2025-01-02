package com.devsuperior.workshopmongo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopmongo.entities.Post;
import com.devsuperior.workshopmongo.models.dto.PostDTO;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.services.exceptions.ResourceNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;

	
	public PostDTO findById(String id) {
		Post entity = getFindById(id);
		return new PostDTO(entity);
	}
	
	private Post getFindById(String id) {
		Optional<Post> result = repository.findById(id);
		return result.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
	}
	
	
	public List<PostDTO>findByTitle(String text){
		List<Post> list = repository.findByTitleContainingIgnoreCase(text);
		return list.stream().map(x-> new PostDTO(x)).collect(Collectors.toList());
	}
	
}
