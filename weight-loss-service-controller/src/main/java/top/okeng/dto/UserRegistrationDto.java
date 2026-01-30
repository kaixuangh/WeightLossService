package top.okeng.dto;

import lombok.Data;

/**
 * @author ray
 * @description
 * @since 2026/1/28
 */
@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;
}