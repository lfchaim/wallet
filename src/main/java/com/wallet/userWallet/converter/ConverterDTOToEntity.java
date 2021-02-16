package com.wallet.userWallet.converter;

import com.wallet.user.entities.User;
import com.wallet.userWallet.dto.UserWalletDTO;
import com.wallet.userWallet.entities.UserWallet;
import com.wallet.wallet.entities.Wallet;

public class ConverterDTOToEntity {
    // Tive que fazer assim pq o map do modelMapper não preenche o id do usuário e o id da carteira
    public static UserWallet convertDtoToEntity(UserWalletDTO userWalletDTO){
        UserWallet userWallet = new UserWallet();
        User user = new User();
        user.setId(userWalletDTO.getUsers());
        Wallet wallet = new Wallet();
        wallet.setId(userWalletDTO.getWallet());

        userWallet.setId(userWalletDTO.getId());
        userWallet.setUsers(user);
        userWallet.setWallet(wallet);

        return userWallet;
    }
}
