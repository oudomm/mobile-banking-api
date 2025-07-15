package com.oudom.mbapi.repository;

import com.oudom.mbapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends JpaRepository<KYC, Integer> {
    boolean existsByNationalCardId(String nationalCardId);
}
