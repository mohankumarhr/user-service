package com.greenry.userservice.Service;

import com.greenry.userservice.Dao.UserRepository;
import com.greenry.userservice.Entities.Seller;
import com.greenry.userservice.Entities.User;
import com.greenry.userservice.Util.EmailUtil;
import com.greenry.userservice.Util.OTPutil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPutil otPutil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) throws MessagingException {
        String OTP = otPutil.generateOtp();
        emailUtil.sendotpEmail(user.getUserEmail(), OTP);
        user.setUserPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserASeller(false);
        user.setOTP(OTP);
        user.setOtpGeneratedTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user1,String userName,String values){
    int flag=0;
    Seller seller = null;
        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User not Found!!!!"));
        String attributesToUpdate[]=values.split(" ");
//        System.out.println(values+"\n"+ Arrays.toString(attributesToUpdate));

        if(user.isUserASeller()==true){
            seller=user.getSeller();
            flag=1;
        }

        for(String attributes:attributesToUpdate){
            if(attributes.equals("userEmail")){
                user.setUserEmail(user1.getUserEmail());
                if(flag==1)seller.setSellerEmail(user1.getUserEmail());
            }
            if(attributes.equals("userPhone")){
                user.setUserPhone(user1.getUserPhone());
                if(flag==1)seller.setSellerPhone(user1.getUserPhone());
            }
            if(attributes.equals("firstName")){
                user.setFirstName(user1.getFirstName());
            }
            if(attributes.equals("lastName")){
                user.setLastName(user1.getLastName());
            }


        }
        return user;

    }

    public void ConfirmLogin(String userName){

        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User Not Found!!!!"));
        user.setLoggedIn(true);
        user.setLoggedInTime(LocalDateTime.now());
        this.userRepository.save(user);

    }
    public void Logout(String userName){
        User user = this.userRepository.findUserByUserName(userName).orElseThrow(()->
                new RuntimeException("User Not Found!!!!"));
        user.setLoggedIn(false);
        user.setLoggedInTime(null);
        this.userRepository.save(user);
    }

    public String verifyAccount(String email, String otp) {
        User user = userRepository.findUserByUserEmail(email).orElseThrow(()
        -> new RuntimeException("User not found with this email : "+email));

        if(user.getOTP().equals(otp) && Duration.between(user.getOtpGeneratedTime(),LocalDateTime.now()).getSeconds() < (1*60)){
            user.setVerified(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }

        return "Please regenerate OTP and try again";

    }

    public String regenerateOTP(String email) {
        User user = userRepository.findUserByUserEmail(email).orElseThrow(()
                -> new RuntimeException("User not found with this email : "+email));

        String OTP = otPutil.generateOtp();
        try{
            emailUtil.sendotpEmail(email,OTP);

        }catch (MessagingException ex){
            throw new RuntimeException("Unable to send OTP please try again");
        }

        user.setOTP(OTP);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent...Please verify account within 1 minute";


    }
}
