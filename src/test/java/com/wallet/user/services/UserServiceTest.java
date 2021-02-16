package com.wallet.user.services;

import com.wallet.user.entities.User;
import com.wallet.user.repositories.UserRepository;
import com.wallet.user.services.servicesImpl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

// Teste do service

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    // Vou fazer um mock o repository
    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp(){
        this.userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("it should be able to return a user by email")
    public void testFindByEmail(){
        BDDMockito.given(userRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(new User()));

        Optional<User> user = userService.findByEmail("email@test.com");

        Assertions.assertThat(user.isPresent()).isTrue();

    }
}
