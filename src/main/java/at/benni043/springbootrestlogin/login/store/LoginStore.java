package at.benni043.springbootrestlogin.login.store;

import at.benni043.springbootrestlogin.login.model.user.User;
import at.benni043.springbootrestlogin.login.model.user.UserRegisterRequest;
import at.benni043.springbootrestlogin.login.model.user.UserResponse;
import at.benni043.springbootrestlogin.login.util.HttpError;
import at.benni043.springbootrestlogin.login.util.JwtUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LoginStore {

    private final List<User> userList;
    private static int id = 0;

    public LoginStore() {
        this.userList = new ArrayList<>();
    }

    public User getUser(int id) {
        return userList.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(HttpError.USER_NOT_EXISTS)));
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public UserResponse setUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();

        user.setUserName(userRegisterRequest.userName());
        user.setEmail(userRegisterRequest.email());
        user.setPassword(userRegisterRequest.password());

        user.setJwtToken(JwtUtil.generateToken(user.getUserName()));

        user.setId(id);
        id++;

        userList.add(user);

        return new UserResponse(user.getJwtToken(), user.getId());
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userList.stream().filter(u -> u.getEmail().equals(email)).findFirst();

        return user.orElse(null);
    }

    public int deleteUser(int id) {
        Optional<User> user = userList.stream().filter(u -> u.getId() == id).findFirst();

        if (user.isPresent()) {
            userList.remove(user.get());
            return id;
        } else {
            throw new IllegalArgumentException(String.valueOf(HttpError.USER_NOT_EXISTS));
        }
    }
}