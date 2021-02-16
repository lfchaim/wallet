package com.wallet.walletItem.dto;

import com.wallet.enums.TypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletItemDTO {

    private Long id;

    @NotNull(message = "Wallet must be provided")
    private Long wallet;

    @NotNull(message = "Date must be provided")
    private Date date;

    @NotNull(message = "Description must be provided")
    private String description;

    @NotNull(message = "Type must be provided")
    @Length(min = 5, message = "At least 5 characters")
    @Pattern(regexp = "ˆ(INCOME|OUTCOME)$", message = "Only INCOME or OUTCOME")
    private String type;

    @NotNull(message = "Value must be provided")
    private BigDecimal value;
}
