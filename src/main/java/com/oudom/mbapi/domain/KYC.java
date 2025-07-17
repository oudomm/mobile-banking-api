package com.oudom.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KYC {

    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nationalCardId;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cust_id")
    private Customer customer;
}
