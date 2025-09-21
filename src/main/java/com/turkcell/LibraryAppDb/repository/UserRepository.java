package com.turkcell.LibraryAppDb.repository;

import com.turkcell.LibraryAppDb.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}