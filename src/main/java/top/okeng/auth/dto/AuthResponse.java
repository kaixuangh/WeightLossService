package top.okeng.auth.dto;

/**
 * @author ray
 * @description
 * @since 2026/1/28
 */
public class AuthResponse {
    private String token;
    private String username;

    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Getters
    public String getToken() { return token; }
    public String getUsername() { return username; }
}
