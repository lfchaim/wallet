package com.wallet.walletItem.services.servicesImpl;

import com.wallet.enums.TypeEnum;
import com.wallet.walletItem.entities.WalletItem;
import com.wallet.walletItem.repositories.WalletItemRepository;
import com.wallet.walletItem.services.WalletItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WalletItemServiceImpl implements WalletItemService {

    private WalletItemRepository walletItemRepository;

    public WalletItemServiceImpl(WalletItemRepository walletItemRepository) {
        this.walletItemRepository = walletItemRepository;
    }

    @Value("${pagination.items_per_page}")
    private int itemsPerPage;

    @Override
    public WalletItem save(WalletItem walletItem) {
        return this.walletItemRepository.save(walletItem);
    }

    @Override
    public Page<WalletItem> findBetweenDates(long id, Date start, Date end, int page) {
        @SuppressWarnings("deprecation")
        PageRequest pg = PageRequest.of(page, 10); // itemsPerPage no lugar do 10

        return this.walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(id, start, end, pg);
    }

    @Override
    public List<WalletItem> findByWalletAndType(long walletID, TypeEnum type) {
        return this.walletItemRepository.findByWalletIdAndType(walletID, type);
    }

    @Override
    public BigDecimal sumByWalletId(long walletId) {
        return this.walletItemRepository.sumByWalletId(walletId);
    }
}
