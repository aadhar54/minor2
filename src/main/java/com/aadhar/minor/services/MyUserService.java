package com.aadhar.minor.services;

import com.aadhar.minor.models.MyUser;
import com.aadhar.minor.repositories.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return myUserRepository.findByUsername(username);
    }

    public MyUser saveMyUser(MyUser myUser){
        return myUserRepository.save(myUser);
    }
}
