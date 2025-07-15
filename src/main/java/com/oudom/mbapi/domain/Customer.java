package com.oudom.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, length = 15)
    private String gender;

    @Column(unique = true, updatable = false)
    private String email;

    @Column(unique = true, updatable = false)
    private String phoneNumber;

    @Column(unique = true, updatable = false)
    private String nationalCardId;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KYC kyc;

    @ManyToOne
    @JoinColumn(name = "segment_id", nullable = false, referencedColumnName = "id")
    private Segment segment;
}
