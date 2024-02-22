package com.greenry.userservice.Service;

import com.greenry.userservice.Dao.AddressRepository;
import com.greenry.userservice.Dao.UserRepository;
import com.greenry.userservice.Entities.Address;
import com.greenry.userservice.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Address addAddress(Address address,String userName){

        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User not Found!!!!"));
        Set<Address> AddressSet = user.getAddress();
        if(AddressSet.isEmpty()){
            Set<Address> newAddress = new HashSet<>();
            newAddress.add(address);
            AddressSet=newAddress;
            user.setAddress(AddressSet);
            address.setUser(user);
            return this.addressRepository.save(address);

        }else{
            user.getAddress().add(address);
            address.setUser(user);
            return this.addressRepository.save(address);

        }

    }

    @Transactional
    public Address deleteAddress(String userName,String fullName){
        Address address = this.addressRepository.findAddressByFullName(fullName);

        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User not Found!!!!"));
        user.getAddress().remove(address);
        this.addressRepository.delete(address);
        return address;
    }

}
