package com.wallet.walletItem.entities;

import com.wallet.enums.TypeEnum;
import com.wallet.wallet.entities.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "wallet_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // IDENTITY
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet", referencedColumnName = "id")
    private Wallet wallet;

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Column(name = "value")
    @NotNull
    private BigDecimal value;
}
