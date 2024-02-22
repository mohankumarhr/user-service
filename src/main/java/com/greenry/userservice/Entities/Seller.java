package com.greenry.userservice.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table
@Entity(name = "seller")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {

    @Id
    @GeneratedValue
    private int Sid;
    @Column(unique = true)
    private String sellerName;
    private String sellerPhone;
    private String sellerEmail;
    private int NoOfProducts;

    @OneToOne
    @JsonBackReference
    private User user;
}
