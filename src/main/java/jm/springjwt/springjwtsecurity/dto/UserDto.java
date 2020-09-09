package jm.springjwt.springjwtsecurity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jm.springjwt.springjwtsecurity.model.Role;
import jm.springjwt.springjwtsecurity.model.User;

import java.util.HashSet;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@JsonIgnoreProperties
public class UserDto {

    private Long id;
    @NotBlank(message = "First name can't be empty")
    private String firstName;
    @NotBlank(message = "Last name can't be empty")
    private String lastName;
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Incorrect email address")
    private String email;
    @NotBlank(message = "Password can't be empty")
    private String password;
    @NotBlank(message = "Age can't be empty")
    private String age;


    private Long[] roles;

    public User toUser() {
        User user = new User();
        user.setPassword(password);
        user.setAge(age);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        Set<Role> rolesUser = new HashSet<>();
        for (Long role : roles) {
            rolesUser.add(new Role(role));
        }
        user.setRoles(rolesUser);
        return user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long[] getRoles() {
        return roles;
    }

    public void setRoles(Long[] roles) {
        this.roles = roles;
    }


}
