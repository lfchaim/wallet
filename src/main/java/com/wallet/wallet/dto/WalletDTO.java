package com.wallet.wallet.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WalletDTO {
    private Long id;

    @Length(min = 3, message = "Name must have at least 3 characters")
    @NotNull
    private String name;

    @NotNull
    private BigDecimal value;
}
