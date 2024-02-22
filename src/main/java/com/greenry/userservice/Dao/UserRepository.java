package com.greenry.userservice.Dao;

import com.greenry.userservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findUserByUserName(final String userName);

    Optional<User> findUserByUserEmail(final String userEmail);

}
