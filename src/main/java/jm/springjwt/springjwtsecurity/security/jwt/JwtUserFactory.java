package jm.springjwt.springjwtsecurity.security.jwt;

import jm.springjwt.springjwtsecurity.model.Role;
import jm.springjwt.springjwtsecurity.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public class JwtUserFactory {

    public JwtUserFactory(){

    }

    public static JwtUser create(User user){
        return new JwtUser(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getAge(),
                user.getFirstName(),
                user.getLastName(),
                roleToGrantedAuthoriry(user.getRoles()));
    }


    private static Set<GrantedAuthority> roleToGrantedAuthoriry(Set<Role> userRoles){
        return userRoles.stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
