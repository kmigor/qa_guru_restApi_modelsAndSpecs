package models;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String username;
    private String email;
    private String password;
}