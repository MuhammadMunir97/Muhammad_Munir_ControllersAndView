package com.muhammad.controller_and_views.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.muhammad.controller_and_views.modells.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public User findByEmail(String email);
}
