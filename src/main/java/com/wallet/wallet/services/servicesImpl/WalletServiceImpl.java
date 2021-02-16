package com.wallet.wallet.services.servicesImpl;

import com.wallet.wallet.entities.Wallet;
import com.wallet.wallet.repositories.WalletRepository;
import com.wallet.wallet.services.WalletService;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return this.walletRepository.save(wallet);
    }
}
