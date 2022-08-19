package com.error504.baf.service;

import com.error504.baf.exception.DataNotFoundException;
import com.error504.baf.model.SiteUser;
import com.error504.baf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("siteuser not found");
        }
    }


    public SiteUser create(String username, String email, String password, int type, int getWheel){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setType(type);
        user.setGetWheel(getWheel);
        userRepository.save(user);
        return user;
    }

    public void updatePassword(SiteUser siteUser, String newPassword) {
        siteUser.updatePassword(passwordEncoder.encode(newPassword));
        userRepository.save(siteUser);
    }
    public void updateEmail(SiteUser siteUser, String newEmail) {
        siteUser.updateEmail(newEmail);
        userRepository.save(siteUser);
    }

    public void deleteMember(SiteUser siteUser) {
       userRepository.delete(siteUser);
    }

}

