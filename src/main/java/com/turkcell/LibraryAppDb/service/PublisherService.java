package com.turkcell.LibraryAppDb.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.turkcell.LibraryAppDb.entity.Publisher;
import com.turkcell.LibraryAppDb.repository.PublisherRepository;

@Service
public class PublisherService {
	private final PublisherRepository publisherRepository;

	public PublisherService(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	public Optional<Publisher> findById(int id) {
		return publisherRepository.findById(id);
	}
}
