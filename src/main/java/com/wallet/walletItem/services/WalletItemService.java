package com.wallet.walletItem.services;

import com.wallet.enums.TypeEnum;
import com.wallet.walletItem.entities.WalletItem;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemService {
    WalletItem save(WalletItem walletItem);

    Page<WalletItem> findBetweenDates(long id, Date start, Date end, int page);

    List<WalletItem> findByWalletAndType(long walletId, TypeEnum type);

    BigDecimal sumByWalletId(long walletId);
}
