package com.wallet.user.services;

import com.wallet.user.entities.User;
import java.util.Optional;

// Essa interface será implementada no UserServiceImpl
public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);
}
