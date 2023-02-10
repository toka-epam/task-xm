package com.task.xm.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;

//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class Crypto {
//    @Id
//    @GeneratedValue
//    @Column(name = "crypto_id")
    private BigInteger id;
    private String name;
}
