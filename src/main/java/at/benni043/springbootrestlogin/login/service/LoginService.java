package at.benni043.springbootrestlogin.login.service;

import at.benni043.springbootrestlogin.login.model.user.*;
import at.benni043.springbootrestlogin.login.store.LoginStore;
import at.benni043.springbootrestlogin.login.util.HttpError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final LoginStore loginStore;

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

    public DisplayUser getDisplayUser(int id) {
        User user = loginStore.getUser(id);
        return new DisplayUser(user.getEmail(), user.getUserName(), user.getId());
    }

    public List<DisplayUser> getAllDisplayUsers() {
        return loginStore.getAllUsers().stream().map(user -> new DisplayUser(user.getEmail(), user.getUserName(), user.getId())).collect(Collectors.toList());
    }

    public UserResponse setUser(UserRegisterRequest userRegisterRequest) {
        User user = loginStore.getUserByEmail(userRegisterRequest.email());

        if (user == null) {
            return loginStore.setUser(userRegisterRequest);
        } else {
            throw new IllegalArgumentException(String.valueOf(HttpError.EMAIL_ALREADY_TAKEN));
        }
    }

    public boolean isUserTokenValid(int id, String token) {
        try {
            User user = getUser(id);
            return user.getJwtToken().equals(token);
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public UserResponse getUserByEmail(UserLoginRequest userLoginRequest) {
        User user = loginStore.getUserByEmail(userLoginRequest.email());

        if (user == null) throw new IllegalArgumentException(String.valueOf(HttpError.USER_NOT_EXISTS));

        if (user.getPassword().equals(userLoginRequest.password())) {
            return new UserResponse(user.getJwtToken(), user.getId());
        } else {
            throw new IllegalArgumentException(String.valueOf(HttpError.WRONG_PASSWORD));
        }
    }

    public int deleteUser(int id) {
        return loginStore.deleteUser(id);
    }
}
