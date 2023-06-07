package at.benni043.springbootrestlogin.login.router;

import at.benni043.springbootrestlogin.login.model.user.User;
import at.benni043.springbootrestlogin.login.model.user.UserRequest;
import at.benni043.springbootrestlogin.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginRouter {

    private LoginService loginService;

    @Autowired
    public LoginRouter(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/get_user")
    public User getUser(@RequestParam int id) {
        try {
            return loginService.getUser(id);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    @GetMapping("/get_all_users")
    public List<User> getAllUsers() {
        return loginService.getAllUsers();
    }

    @PostMapping("/post_user")
    public void postUser(@RequestBody UserRequest userRequest) {
        loginService.setUser(userRequest);
    }

}
