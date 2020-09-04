package jm.springjwt.springjwtsecurity.controller.rest;


import jm.springjwt.springjwtsecurity.dto.AuthenticationRequestDto;
import jm.springjwt.springjwtsecurity.model.User;
import jm.springjwt.springjwtsecurity.repository.UserRepository;
import jm.springjwt.springjwtsecurity.security.jwt.JwtProvider;
import jm.springjwt.springjwtsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("login")
    public ResponseEntity login(@RequestParam(name = "j_username") String name, @RequestParam(name = "j_password") String pass) {
        System.out.println("----In security method-----");
        try {
//            String username = requestDto.getUserName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, pass));
            User user = userService.getUserByName(name);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + name + " not found");
            }

            String token = jwtProvider.createToken(name, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", name);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/info")
    public String userInfo() {
        return "Hello User";
    }
}
