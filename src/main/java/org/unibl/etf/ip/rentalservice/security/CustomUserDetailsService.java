package org.unibl.etf.ip.rentalservice.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.repositories.UserEntityRepository;
import org.unibl.etf.ip.rentalservice.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    private final UserService userService;

    public CustomUserDetailsService(UserEntityRepository userEntityRepository, UserService userService) {
        this.userEntityRepository = userEntityRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPasswordHash())
                        .passwordEncoder(password -> password) // Tell Spring Security NOT to hash it again
                        .roles(userService.getUserRole(user.getId()))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

