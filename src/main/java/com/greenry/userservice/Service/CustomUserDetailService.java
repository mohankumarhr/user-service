package com.greenry.userservice.Service;

import com.greenry.userservice.Dao.UserRepository;
import com.greenry.userservice.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User not Found!!!!"));
        if(user==null){
            new RuntimeException("User not Found");

        }
        return user;
    }
}
