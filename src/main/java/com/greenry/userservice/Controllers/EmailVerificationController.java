package com.greenry.userservice.Controllers;

import com.greenry.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/verify")
public class EmailVerificationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/verify-account",method={RequestMethod.GET,RequestMethod.PUT})
    public ResponseEntity<String> verifyAccount(@RequestParam String email,@RequestParam String OTP){
        return new ResponseEntity<>(userService.verifyAccount(email,OTP), HttpStatus.OK);
    }

    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOTP(@RequestParam String email){
        return new ResponseEntity<>(userService.regenerateOTP(email),HttpStatus.OK);
    }
}
