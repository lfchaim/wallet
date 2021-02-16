package com.wallet.WalletItemTest.repository;

import com.wallet.enums.TypeEnum;
import com.wallet.wallet.entities.Wallet;
import com.wallet.wallet.repositories.WalletRepository;
import com.wallet.walletItem.entities.WalletItem;
import com.wallet.walletItem.repositories.WalletItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class WalletItemRepositoryTest {

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.IN;
    private static final String DESCRIPTION = "Light bill";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);
    private Long savedWalletItemId = null;
    private Long savedWalletId = null;

    @Autowired
    private WalletItemRepository walletItemRepository;

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    public void setUp(){
        Wallet wallet = new Wallet();
        wallet.setName("Carteira Teste");
        wallet.setValue(BigDecimal.valueOf(250));
        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(null, wallet, DATE, DESCRIPTION, TYPE, VALUE);
        walletItemRepository.save(walletItem);

        savedWalletItemId = walletItem.getId();
        savedWalletId = wallet.getId();
    }

    @AfterEach
    public void tearDown(){
        walletItemRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    @DisplayName("it should be able to save a new wallet item")
    public void testSave(){
        // Terá que ser vinculado uma carteira
        Wallet wallet = new Wallet();
        wallet.setName("Wallet 1");
        wallet.setValue(BigDecimal.valueOf(500));
        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(1l, wallet ,DATE, DESCRIPTION, TYPE, VALUE);

        WalletItem walletItemSaved = walletItemRepository.save(walletItem);

        Assertions.assertThat(walletItemSaved).isNotNull();
        Assertions.assertThat(walletItemSaved.getDescription()).isEqualTo(DESCRIPTION);
        Assertions.assertThat(walletItemSaved.getType()).isEqualTo(TYPE);
        Assertions.assertThat(walletItemSaved.getValue()).isEqualTo(VALUE);
        Assertions.assertThat(walletItemSaved.getWallet().getId()).isEqualTo(wallet.getId());

    }

//    @Test
//    @DisplayName("it should not save a wallet item with not enough data")
//    public void testSaveInvalidWalletItem(){
//        WalletItem walletItem = new WalletItem(null, null, DATE, null, null, VALUE);
//        WalletItem savedWallet = walletItemRepository.save(walletItem);
//
//        Assertions.assertThat(savedWallet.getDescription()).isNull();
//    }

    @Test
    @DisplayName("it shoud be able to update a wallet item")
    public void testUpdateWalletItem(){
        Optional<WalletItem> walletItem = walletItemRepository.findById(savedWalletItemId);

        String description = "Descrição alterada";

        WalletItem changed = walletItem.get();
        changed.setDescription(description);

        walletItemRepository.save(changed);

        Optional<WalletItem> newWalletItem = walletItemRepository.findById(savedWalletItemId);

        Assertions.assertThat(description).isEqualTo(newWalletItem.get().getDescription());
    }

    @Test
    @DisplayName("it should be able to delete a wallet item")
    public void testDeleteWalletItem(){
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        WalletItem walletItem = new WalletItem(null, wallet.get(), DATE, DESCRIPTION, TypeEnum.IN, VALUE);

        walletItemRepository.save(walletItem);
        walletItemRepository.deleteById(walletItem.getId());

        Optional<WalletItem> response = walletItemRepository.findById(walletItem.getId());

        Assertions.assertThat(response.isPresent()).isFalse();
    }

    @Test
    @DisplayName("it should find wallet items between dates")
    public void testFindBetweenDates(){
        Optional<Wallet> w = walletRepository.findById(savedWalletId);

        LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
        Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());


        walletItemRepository.save(new WalletItem(null, w.get(), currentDatePlusFiveDays, DESCRIPTION, TYPE, VALUE));
        walletItemRepository.save(new WalletItem(null, w.get(), currentDatePlusSevenDays, DESCRIPTION, TYPE, VALUE));

        PageRequest pg = PageRequest.of(0, 10);
        Page<WalletItem> response = walletItemRepository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savedWalletId, DATE, currentDatePlusFiveDays, pg);

        Assertions.assertThat(response.getContent().size()).isEqualTo(2);
        Assertions.assertThat(response.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(response.getContent().get(0).getWallet().getId()).isEqualTo(savedWalletId);
    }

    @Test
    @DisplayName("it should be able to find wallet items by type")
    public void testFindByType() {
        List<WalletItem> response = walletItemRepository.findByWalletIdAndType(savedWalletId, TYPE);

        Assertions.assertThat(response.size()).isEqualTo(1);
        Assertions.assertThat(response.get(0).getWallet().getId()).isEqualTo(savedWalletId);
    }

    @Test
    @DisplayName("it should be able to find wallet items by type OC")
    public void testFindByTypeOC() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        walletItemRepository.save(new WalletItem(null, wallet.get(), DATE, DESCRIPTION, TypeEnum.OC, VALUE));

        List<WalletItem> response = walletItemRepository.findByWalletIdAndType(savedWalletId, TypeEnum.OC);

        Assertions.assertThat(response.size()).isEqualTo(1);
        Assertions.assertThat(response.get(0).getType()).isEqualTo(TypeEnum.OC);
    }

    @Test
    @DisplayName("it should be able to sum wallet items inside a wallet")
    public void testSumByWallet() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        walletItemRepository.save(new WalletItem(null, wallet.get(), DATE, DESCRIPTION, TYPE, BigDecimal.valueOf(150.80)));

        BigDecimal response = walletItemRepository.sumByWalletId(savedWalletId);

        Assertions.assertThat(response.compareTo(BigDecimal.valueOf(215.8)));
    }


}
