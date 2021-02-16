package com.wallet.userWallet.repositories;

import com.wallet.userWallet.entities.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
}
