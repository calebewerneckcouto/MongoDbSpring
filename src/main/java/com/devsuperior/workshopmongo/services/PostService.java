package com.devsuperior.workshopmongo.services;

import java.time.Instant;
import java.time.format.DateTimeParseException;
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

	public List<PostDTO> findByTitle(String text) {
		List<Post> list = repository.searchTitle(text);
		return list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
	}

	public List<PostDTO> fullSearch(String text, String start, String end) {

		Instant startMoment = convertMoment(start, Instant.ofEpochMilli(0L));// caso nao informe ira pegar post em
																				// 1970...
		Instant endMoment = convertMoment(start, Instant.now());// instante atual caso nao informe.....
		List<Post> list = repository.fullSearch(text, startMoment, endMoment);
		return list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
	}

	private Instant convertMoment(String originalString, Instant alternative) {

		try {
			return Instant.parse(originalString);
		} catch (DateTimeParseException e) {
			return alternative;
		}

	}

}
