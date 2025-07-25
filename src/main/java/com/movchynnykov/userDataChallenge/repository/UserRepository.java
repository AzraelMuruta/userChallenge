package com.movchynnykov.userDataChallenge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movchynnykov.userDataChallenge.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
	
	Optional<User> findByEmail(String email);

}
