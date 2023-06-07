package at.benni043.springbootrestlogin.login.store;

import at.benni043.springbootrestlogin.login.model.user.User;
import at.benni043.springbootrestlogin.login.model.user.UserRequest;
import at.benni043.springbootrestlogin.login.util.JwtUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class LoginStore {

    private List<User> userList;
    private static int id = 2;

    public LoginStore() {
        this.userList = new ArrayList<>();

        User user1 = new User(0, JwtUtil.generateToken("benni043"), "benni@mail.com", "benni043", "test");
        User user2 = new User(1, JwtUtil.generateToken("tobinio"), "tobinio@mail.com", "tobinio", "hugo");

        userList.addAll(Arrays.asList(user1, user2));
    }

    public User getUser(int id) {
        return userList.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("user does not exists"));
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public void setUser(UserRequest userRequest) {
        User user = new User();

        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        user.setJwtToken(JwtUtil.generateToken(user.getUserName()));

        user.setId(id);
        id++;

        userList.add(user);
    }
}