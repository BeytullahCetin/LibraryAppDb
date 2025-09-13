package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

}
