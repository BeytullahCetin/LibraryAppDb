package com.turkcell.LibraryAppDb.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.turkcell.LibraryAppDb.entity.Language;
import com.turkcell.LibraryAppDb.repository.LanguageRepository;

@Service
public class LanguageService {
	private final LanguageRepository languageRepository;

	public LanguageService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	public Optional<Language> findById(int id) {
		return languageRepository.findById(id);
	}
}
