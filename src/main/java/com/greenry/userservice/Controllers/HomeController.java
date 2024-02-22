package com.greenry.userservice.Controllers;

import com.greenry.userservice.Dao.AddressRepository;
import com.greenry.userservice.Dao.UserRepository;
import com.greenry.userservice.Entities.Address;
import com.greenry.userservice.Entities.Seller;
import com.greenry.userservice.Entities.User;
import com.greenry.userservice.Service.AddressService;
import com.greenry.userservice.Service.SellerService;
import com.greenry.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;


    @Autowired
    private SellerService sellerService;


    @GetMapping("/get-CurrentUser")
    public String getCurrentUser(Principal principal){
        return principal.getName();
    }
//Update User data

    @PostMapping("/update-user/{userName}/{values}")
    public ResponseEntity<User> updateUserData(@RequestBody User user1, @PathVariable("values") String values, @PathVariable("userName")String userName){
       User user = this.userService.updateUser(user1,userName,values);
        return ResponseEntity.ok(user);


    }


    //Add Address
    @PostMapping("/add-Address/{userName}")
    public ResponseEntity<Address> addAddress(@RequestBody Address address,@PathVariable("userName") String userName){
        Address address1 = this.addressService.addAddress(address,userName);
        if(address1!=null)return ResponseEntity.ok(address1);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //update-Address


    @PostMapping("/update-Address/{userName}/{fullName}")
    public ResponseEntity<Address> updateAddress(@PathVariable("userName")String userName,@PathVariable("fullName")String fullName,@RequestBody Address address){
        Address address1 = this.addressService.deleteAddress(userName, fullName);
        Address address2 = this.addressService.addAddress(address,userName);
        if(address2!=null)return ResponseEntity.ok(address2);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


    }

    @GetMapping("/{userName}/get-addresses")
    public ResponseEntity<Set<Address>> getAddress(@PathVariable("userName")String userName){
        User user = userRepository.findUserByUserName(userName).orElseThrow(()->new RuntimeException("User not found!!"));
        return ResponseEntity.ok(user.getAddress());

    }

    @DeleteMapping("/delete-Address/{userName}/{fullName}")
    public ResponseEntity<Address> deleteAddress(@PathVariable("userName")String userName,@PathVariable("fullName") String fullName){
        Address address=this.addressService.deleteAddress(userName,fullName);
        if(address!=null)
            return ResponseEntity.ok(address);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @GetMapping("/getUser/{userName}")
    public ResponseEntity<User> getUserDetails(@PathVariable("userName") String userName){

        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User not Found!!!!"));
        if(user!=null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{userName}/register-as-seller")
    public ResponseEntity<Seller> registerAsSeller(@PathVariable("userName")String userName){
        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User not Found!!!!"));
        try {
            Seller seller = sellerService.createSeller(user);
            return ResponseEntity.ok(seller);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
