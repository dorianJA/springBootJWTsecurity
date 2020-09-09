package jm.springjwt.springjwtsecurity.controller.rest;


import jm.springjwt.springjwtsecurity.dto.AuthenticationRequestDto;
import jm.springjwt.springjwtsecurity.dto.UserDto;
import jm.springjwt.springjwtsecurity.model.User;
import jm.springjwt.springjwtsecurity.repository.UserRepository;
import jm.springjwt.springjwtsecurity.security.jwt.JwtProvider;
import jm.springjwt.springjwtsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminRestController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtProvider jwtProvider;

    @Autowired
    public AdminRestController(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto ) {
        System.out.println("----In rest controller-----");
        try {
            String username = requestDto.getUserName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.getUserByName(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",token);
            response.put("username", username);
            response.put("token", token);
            response.put("Header",headers);

            return new ResponseEntity(response,headers,HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.getAllUsers();
        return  ResponseEntity.ok(users);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto, Errors errors) {
        return ResponseEntity.ok(userService.saveUser(userDto.toUser()));
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<?> editUser(@Valid @RequestBody UserDto user, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        } else {
            User updateUser = user.toUser();
            updateUser.setId(user.getId());
            userService.saveUser(updateUser);
            return  ResponseEntity.ok(updateUser);
        }
    }


    @DeleteMapping("/admin/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
    }


}
