package findev.service.security;

public interface ISecurityService {
    String findLoggedInUsername();
    void autoLogin(String email, String password);
}
