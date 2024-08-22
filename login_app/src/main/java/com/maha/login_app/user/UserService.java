package com.maha.login_app.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        var u = userRepository.findByEmail(user.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(u);
        return user;
    }

    public void delete(String email) {
        var user = userRepository.findByEmail(email);
        user.ifPresent(userRepository::delete);
    }
}
