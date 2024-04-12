package com.NoCountry.Patrickscoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NoCountry.Patrickscoin.dto.UserDto;
import com.NoCountry.Patrickscoin.entities.User;
import com.NoCountry.Patrickscoin.services.IUserService;
import com.NoCountry.Patrickscoin.utils.validator.UserValidator;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userdto) {
        userValidator.validateRegister(userdto);
        return new ResponseEntity<>(userService.registerUser(userdto), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    // FRONT api/users/log-in
    @PostMapping("/users/log-in")
    public ResponseEntity<?> getUserByEmail(@RequestBody UserDto user) {
        String email = user.getEmail();
        String password = user.getPassword();
    
        try {
            UserDto foundUser = userService.findByEmail(email, password);
            if (foundUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }
            return ResponseEntity.ok().body(foundUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al intentar iniciar sesión: " + e.getMessage());
        }
    }
}
