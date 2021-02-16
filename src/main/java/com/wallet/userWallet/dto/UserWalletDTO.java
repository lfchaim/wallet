package com.wallet.userWallet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserWalletDTO {
    private Long id;
    // Estou colocando como Long pq eles tem o FetchType.LAZY
    // Com isso, vai ser trazido somente o id
    // Se fosse FetchType.EAGER iria trazer todas as propriedades preenchidas
    @NotNull(message = "User can not be null")
    private Long users;

    @NotNull(message = "Wallet can not be null")
    private Long wallet;
}
