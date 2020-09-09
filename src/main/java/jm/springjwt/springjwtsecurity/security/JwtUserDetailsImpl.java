package jm.springjwt.springjwtsecurity.security;

import jm.springjwt.springjwtsecurity.model.User;

import jm.springjwt.springjwtsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetails load user");
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
