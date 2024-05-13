package com.nnk.springboot.domain;

//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer tradeId;


}
