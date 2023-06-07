package at.benni043.springbootrestlogin.login.service;

import at.benni043.springbootrestlogin.login.model.user.User;
import at.benni043.springbootrestlogin.login.model.user.UserRequest;
import at.benni043.springbootrestlogin.login.store.LoginStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private LoginStore loginStore;

    @Autowired
    public LoginService(LoginStore loginStore) {
        this.loginStore = loginStore;
    }

    public User getUser(int id) {
        return loginStore.getUser(id);
    }

    public List<User> getAllUsers() {
        return loginStore.getAllUsers();
    }

    public void setUser(UserRequest userRequest) {
        loginStore.setUser(userRequest);
    }
}
