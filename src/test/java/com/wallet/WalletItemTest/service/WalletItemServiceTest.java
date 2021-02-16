package com.wallet.WalletItemTest.service;

import com.wallet.enums.TypeEnum;
import com.wallet.wallet.entities.Wallet;
import com.wallet.wallet.repositories.WalletRepository;
import com.wallet.walletItem.entities.WalletItem;
import com.wallet.walletItem.repositories.WalletItemRepository;
import com.wallet.walletItem.services.WalletItemService;
import com.wallet.walletItem.services.servicesImpl.WalletItemServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class WalletItemServiceTest {

    private WalletItemService walletItemService;

    @MockBean
    WalletItemRepository walletItemRepository;

    @BeforeEach
    public void setUp(){
        this.walletItemService = new WalletItemServiceImpl(walletItemRepository);
    }

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.IN;
    private static final String DESCRIPTION = "Light bill";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @Test
    @DisplayName("it should be able to save a wallet item")
    public void testSave() {
        BDDMockito.given(walletItemRepository.save(Mockito.any(WalletItem.class))).willReturn(getMockWalletItem());

        WalletItem response = walletItemService.save(new WalletItem());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getDescription()).isEqualTo(DESCRIPTION);
        Assertions.assertThat(response.getValue().compareTo(VALUE));
    }

    @Test
    @DisplayName("it should find wallet items between dates")
    public void testFindBetweenDates() {
        List<WalletItem> list = new ArrayList<>();
        list.add(getMockWalletItem());
        Page<WalletItem> page = new PageImpl(list);

        BDDMockito.given(walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class))).willReturn(page);

        Page<WalletItem> response = walletItemService.findBetweenDates(1L, new Date(), new Date(), 0);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getContent().get(0).getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    @DisplayName("it should be able to find by type")
    public void testFindByType() {
        List<WalletItem> list = new ArrayList<>();
        list.add(getMockWalletItem());

        BDDMockito.given(walletItemRepository.findByWalletIdAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class))).willReturn(list);

        List<WalletItem> response = walletItemService.findByWalletAndType(1L, TypeEnum.IN);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.get(0).getType()).isEqualTo(TYPE);
    }

    @Test
    @DisplayName("it should be able to sum wallet items")
    public void testSumByWallet() {
        BigDecimal value = BigDecimal.valueOf(45);

        BDDMockito.given(walletItemRepository.sumByWalletId(Mockito.anyLong())).willReturn(value);

        BigDecimal response = walletItemService.sumByWalletId(1L);

        Assertions.assertThat(response.compareTo(value));
    }

    private WalletItem getMockWalletItem() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);

        WalletItem wi = new WalletItem(1L, wallet, DATE, DESCRIPTION, TYPE, VALUE);
        return wi;
    }
}
