package com.wallet.userWallet.services.servicesImpl;

import com.wallet.userWallet.entities.UserWallet;
import com.wallet.userWallet.repositories.UserWalletRepository;
import com.wallet.userWallet.services.UserWalletService;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    private UserWalletRepository userWalletRepository;

    public UserWalletServiceImpl(UserWalletRepository userWalletRepository){
        this.userWalletRepository = userWalletRepository;
    }

    @Override
    public UserWallet save(UserWallet userWallet) {
        return this.userWalletRepository.save(userWallet);
    }
}
