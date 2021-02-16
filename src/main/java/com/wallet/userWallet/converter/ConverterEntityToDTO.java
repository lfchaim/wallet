package com.wallet.userWallet.converter;

import com.wallet.userWallet.dto.UserWalletDTO;
import com.wallet.userWallet.entities.UserWallet;

public class ConverterEntityToDTO {
    // Tive que fazer assim pq o map do modelMapper não preenche o id do usuário e o id da carteira
    public static UserWalletDTO convertEntityToDTO(UserWallet userWallet){
        UserWalletDTO userWalletDTO = new UserWalletDTO();
        userWalletDTO.setId(userWallet.getId());
        userWalletDTO.setUsers(userWallet.getUsers().getId());
        userWalletDTO.setWallet(userWallet.getWallet().getId());

        return userWalletDTO;
    }
}
