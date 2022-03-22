package io.ezsurvey.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByEmail(String email);
}
