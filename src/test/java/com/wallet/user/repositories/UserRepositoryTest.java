package com.wallet.user.repositories;

import com.wallet.user.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

// Teste do repositorio

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {
    // constante
    private static final String EMAIL = "email@teste.com";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setName("setUp user");
        user.setPassword("Senha123");
        user.setEmail(EMAIL);

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("it should be able to save a new user")
    public void testSave(){
        // cenário
        User user = new User();
        user.setName("Teste");
        user.setPassword("123456");
        user.setEmail("test@test.com");

        // execução
        User response = userRepository.save(user);

        // verificação
        Assertions.assertThat(response.getId()).isNotNull();
    }

    @Test
    @DisplayName("it should be able to return a user by email")
    public void testFindByEmail(){
        // Já tem o usuário criado no método setUp();
        // Pode ou não existir um usuário com esse email
        Optional<User> response = userRepository.findByEmail(EMAIL);

        Assertions.assertThat(response.isPresent()).isTrue();
        Assertions.assertThat(response.get().getEmail()).isEqualTo(EMAIL);

    }
}
