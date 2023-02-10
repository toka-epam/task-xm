package com.task.xm.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

// String name is bad solution in this case
// I was trying to do as written in commented code
// but did not have enough time
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CryptoData {
    @Id
    @GeneratedValue
    private BigInteger id;
//    @ManyToOne
//    @JoinColumn(name = "crypto_id")
    private String name;
    private LocalDateTime date;
    @Column(precision = 18,scale = 10)
    private BigDecimal price;
}
