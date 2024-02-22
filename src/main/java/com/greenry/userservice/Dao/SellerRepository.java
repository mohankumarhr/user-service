package com.greenry.userservice.Dao;

import com.greenry.userservice.Entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface SellerRepository extends JpaRepository<Seller,Integer> {

    public Seller findSellerBySellerName(final String sellerName);
}
