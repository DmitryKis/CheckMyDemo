package ru.dmitry.checkmydemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dmitry.checkmydemo.Entity.Role;
import ru.dmitry.checkmydemo.Entity.Status;
import ru.dmitry.checkmydemo.Entity.User;
import ru.dmitry.checkmydemo.Repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSender mailSender;

    public boolean addUserInDB(User user){
        User userFromDB = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (userFromDB != null){
            return false;
        }
        user.setStatus(Status.UNCONFIRMED);
        user.setRoleSet(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setActivationToken(UUID.randomUUID().toString());
        mailSender.sendToActivateUser(user);
        userRepository.save(user);
        return true;
    }

    public boolean activateUserFromEmail(String token) {
        User findByTokenUser = userRepository.findByActivationToken(token);
        if (findByTokenUser == null){
            return false;
        } else {
            findByTokenUser.setStatus(Status.ACTIVATED);
            findByTokenUser.setActivationToken(null);
            userRepository.save(findByTokenUser);
            return true;
        }
    }
}
