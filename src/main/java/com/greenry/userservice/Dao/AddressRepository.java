package com.greenry.userservice.Dao;

import com.greenry.userservice.Entities.Address;
import com.greenry.userservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AddressRepository extends JpaRepository<Address,Integer> {
    public Address findAddressByFullName(final String fullName);
}
