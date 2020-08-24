package br.com.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.manager.model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
		
		@Query("SELECT EN FROM User EN where EN.login = ?1 and EN.password = ?2")
		User findByLoginPassword(String login, String password);
		
	}
