package at.benni043.springbootrestlogin.login.model.user;

public record UserRegisterRequest(String email, String userName, String password) {
}
