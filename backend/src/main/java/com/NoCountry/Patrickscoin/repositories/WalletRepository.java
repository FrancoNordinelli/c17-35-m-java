package com.NoCountry.Patrickscoin.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.NoCountry.Patrickscoin.entities.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{
    Optional<Wallet> findByUserId(Long userId);
}
