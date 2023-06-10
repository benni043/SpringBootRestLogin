package at.benni043.springbootrestlogin.login.router;

import at.benni043.springbootrestlogin.login.model.user.User;
import at.benni043.springbootrestlogin.login.model.user.UserLoginRequest;
import at.benni043.springbootrestlogin.login.model.user.UserRegisterRequest;
import at.benni043.springbootrestlogin.login.model.user.UserResponse;
import at.benni043.springbootrestlogin.login.service.LoginService;
import at.benni043.springbootrestlogin.login.util.HttpError;
import at.benni043.springbootrestlogin.login.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginRouter {

    private final LoginService loginService;

    @Autowired
    public LoginRouter(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        if (loginService.isUserTokenValid(id, token)) {
            return null;
        }

        try {
            return loginService.getUser(id);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    @GetMapping("/user")
    public List<User> getAllUsers(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        if (!JwtUtil.validateToken(token)) {
            return null;
        }

        return loginService.getAllUsers();
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            return ResponseEntity.ok(loginService.setUser(userRegisterRequest));
        } catch (IllegalArgumentException illegalArgumentException) {
            if (illegalArgumentException.getMessage().equals(String.valueOf(HttpError.EMAIL_ALREADY_TAKEN))) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            return ResponseEntity.ok(loginService.getUserByEmail(userLoginRequest));
        } catch (IllegalArgumentException illegalArgumentException) {
            if (illegalArgumentException.getMessage().equals(String.valueOf(HttpError.USER_NOT_EXISTS))) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else if (illegalArgumentException.getMessage().equals(String.valueOf(HttpError.WRONG_PASSWORD))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @DeleteMapping("/user/{deleteId}")
    public ResponseEntity<Integer> deleteUser(@PathVariable int deleteId, @RequestParam int ownId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        if (loginService.isUserTokenValid(ownId, token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            int deletedUserId = loginService.deleteUser(deleteId);
            return ResponseEntity.ok(deletedUserId);
        } catch (IllegalArgumentException illegalArgumentException) {
            if (illegalArgumentException.getMessage().equals(String.valueOf(HttpError.USER_NOT_EXISTS))) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

}
