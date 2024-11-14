package models;

import lombok.Data;

@Data
public class RegisterRequestModel {

    private String email;
    private String password;

}
