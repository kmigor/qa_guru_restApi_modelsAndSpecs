package models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserListResponseModel {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<UserModel.Data> data = new ArrayList<>();
    private UserModel.Support support;
}