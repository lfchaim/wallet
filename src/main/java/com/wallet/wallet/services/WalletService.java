package com.wallet.wallet.services;

import com.wallet.wallet.entities.Wallet;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    Wallet save(Wallet wallet);
}
