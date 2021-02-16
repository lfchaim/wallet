package com.wallet.user.services.servicesImpl;

import com.wallet.user.entities.User;
import com.wallet.user.repositories.UserRepository;
import com.wallet.user.services.UserService;
import com.wallet.util.Bcrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;

// Regras de negócio e comunicação com o repositório

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        user.setPassword(Bcrypt.getHash(user.getPassword())); // Hash na senha
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
