package com.greenry.userservice.Service;

import com.greenry.userservice.Dao.SellerRepository;
import com.greenry.userservice.Entities.Seller;
import com.greenry.userservice.Entities.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public Seller createSeller(User user) throws MessagingException {
        Seller seller = new Seller();
        seller.setUser(user);
        seller.setSellerName(user.getUsername());
        seller.setSellerPhone(user.getUserPhone());
        seller.setSellerEmail(user.getUserEmail());
        seller.setSellerPhone(user.getUserPhone());
        seller.setNoOfProducts(0);
        user.setUserASeller(true);
        user.setSeller(seller);
        return this.sellerRepository.save(seller);
    }


}
