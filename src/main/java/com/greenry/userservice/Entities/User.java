package com.greenry.userservice.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Table
@Entity(name = "USER")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private int Uid;
    @Column(unique = true)
    private String userName;
    private String userPassword;

    @Column(unique = true)
    @Pattern(regexp = "[A-Za-z0-9._]+@[A-Za-z0-9]+\\.[A-Z|a-z]{2,}")
    private String userEmail;

    @Column(unique = true)
    @Pattern(regexp = "\\+\\{12}")
    private String userPhone;

    private boolean isUserASeller;
    private String firstName;
    private String lastName;
    private String OTP;
    private LocalDateTime otpGeneratedTime;
    private boolean isVerified=false;
    private boolean isLoggedIn=false;
    private LocalDateTime loggedInTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Address> address;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Seller seller;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isVerified();
    }
}
