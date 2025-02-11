package org.unibl.etf.ip.rentalservice.security;

//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.model.dto.User;
import org.unibl.etf.ip.rentalservice.model.entities.UserEntity;
import org.unibl.etf.ip.rentalservice.repositories.UserEntityRepository;
import org.unibl.etf.ip.rentalservice.services.UserService;

import java.util.Collections;

/*@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String role = userService.getUserRole(user.getId());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPasswordHash(),
                Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }
}*/

