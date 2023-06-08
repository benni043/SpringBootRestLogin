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

    private List<User> userList;
    private static int id = 0;

    public LoginStore() {
        this.userList = new ArrayList<>();

//        User user1 = new User(0, JwtUtil.generateToken("benni043"), "benni@mail.com", "benni043", "test");
//        User user2 = new User(1, JwtUtil.generateToken("tobinio"), "tobinio@mail.com", "tobinio", "hugo");
//
//        userList.addAll(Arrays.asList(user1, user2));
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
            System.out.println(user.get().getId());
            userList.remove(user.get());
            return id;
        } else {
            System.out.println(id);
            throw new IllegalArgumentException(String.valueOf(HttpError.USER_NOT_EXISTS));
        }
    }
}