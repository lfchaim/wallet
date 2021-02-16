package com.wallet.userWallet.entities;

import com.wallet.user.entities.User;
import com.wallet.wallet.entities.Wallet;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users_wallet")
@Data
public class UserWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // referencedColumnName: qual coluna da tabela usuário ele vai se relacionar
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet", referencedColumnName = "id")
    private Wallet wallet;
}
