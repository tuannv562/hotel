package com.hotel.controller;

import com.hotel.model.ApplicationUser;
import com.hotel.model.Message;
import com.hotel.repository.ApplicationUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class ApplicationUserController {
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity addUser(@RequestBody ApplicationUser applicationUser) {
        if (applicationUserRepository.findByUsername(applicationUser.getUsername()) != null)
            return new ResponseEntity(new Message("Username already existed"), HttpStatus.CONFLICT);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        ApplicationUser savedApplicationUser = applicationUserRepository.save(applicationUser);
        savedApplicationUser.setPassword(null);
        return new ResponseEntity(savedApplicationUser, HttpStatus.CREATED);
    }
}
