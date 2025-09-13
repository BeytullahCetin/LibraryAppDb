package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
