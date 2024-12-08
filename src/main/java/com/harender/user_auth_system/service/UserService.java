package com.harender.user_auth_system.service;

import com.harender.user_auth_system.entity.User;
import com.harender.user_auth_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserRepository getUserRepository(){
        return userRepository;
    }

    public User registerUser(User user) throws Exception{
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            throw new Exception("User already exists with this email.");
        }

//        Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> authenticate(String email, String password){
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isPresent()){
            User user = userOpt.get();

            if(passwordEncoder.matches(password,user.getPassword())){
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public String createPasswordResetToken(String email) throws Exception{
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(!userOpt.isPresent()){
            throw new Exception("No user found with this email");
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setTokenExpiry(System.currentTimeMillis()+900000);  //15 mins
        userRepository.save(user);

        return token;
    }

    public boolean resetPassword(String token, String newPassword){
        Optional<User> userOpt = userRepository.findByResetToken(token);

        if(userOpt.isPresent()){
            User user = userOpt.get();

            if(user.getTokenExpiry() > System.currentTimeMillis()){
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetToken(null);
                user.setTokenExpiry(null);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
